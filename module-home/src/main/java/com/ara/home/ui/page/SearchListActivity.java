package com.ara.home.ui.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseActivity;
import com.ara.db.entity.SearchRecord;
import com.ara.home.BR;
import com.ara.home.R;
import com.ara.home.data.api.HomeRouterApi;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.ui.page.adapter.SearchListAdapter;
import com.ara.home.ui.state.SearchListViewModel;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.util.AccountUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Created by XieXin on 2022/3/11.
 * 搜索列表
 */
@Route(path = HomeRouterApi.API_SEARCH_LIST)
public class SearchListActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private boolean isLoadMore;
    private int page = 1;
    private SearchListViewModel mState;

    private SearchListAdapter adapter;

    private TextView.OnEditorActionListener onEditorActionListener;

    private final ObservableArrayList<ArticleBean> data = new ObservableArrayList<>();

    @Autowired
    String keyword;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(SearchListViewModel.class);
        adapter = new SearchListAdapter();
        adapter.refresh(data);

        onEditorActionListener = (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = v.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    ToastUtils.showLong(R.string.home_input_search_keyword);
                    return true;
                }
                mState.searchRequest.insertSearchRecord(new SearchRecord(v.getText().toString()));
                this.keyword = keyword;
                mState.autoRefresh.setValue(true);
            }
            return true;
        };
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.home_activity_search_list, BR.vm, mState)
                .addBindingParam(BR.onRefreshLoadMoreListener, this)
                .addBindingParam(BR.onEditorActionListener, onEditorActionListener)
                .addBindingParam(BR.adapter, adapter)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        getLifecycle().addObserver(mState.homeRequest);
        getLifecycle().addObserver(mState.searchRequest);

        mState.keyword.set(keyword);

        mState.searchRequest.getArticleLiveData().observe(this, result -> {
            mState.autoRefresh.setValue(false);
            if (!result.isSuccess()) {
                mState.listLoadState.set(!isLoadMore ? LoadState.ERROR : LoadState.LOAD_MORE_ERROR);
                if (data.isEmpty()) mState.loadState.set(LoadState.ERROR);
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            if (!isLoadMore) {
                data.clear();
            }
            data.addAll(result.getResult().getData().getList());

            if (result.getResult().getData().isLast()) {
                mState.listLoadState.set(LoadState.LOAD_MORE_NO_MORE_DATA);
            } else {
                page++;
                mState.listLoadState.set(!isLoadMore ? LoadState.SUCCESS : LoadState.LOAD_MORE_SUCCESS);
            }

            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
        });
        mState.autoRefresh.setValue(true);

        adapter.setOnClickListener((v, binding, position) -> {
            if (AccountUtils.isLogin()) {
                ArticleBean bean = data.get(position);
                mState.loadState.set(LoadState.LOADING);
                if (bean.isCollect()) {
                    mState.homeRequest.unCollect(bean.getId());
                } else {
                    mState.homeRequest.collect(bean.getId());
                }
            } else {
                CommonRouterApi.jumpReLoginActivity();
            }
        });
        mState.homeRequest.getCollectLiveData().observe(this, result -> {
            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            mState.autoRefresh.setValue(true);
            ToastUtils.showLong("收藏成功");
        });
        mState.homeRequest.getUnCollectLiveData().observe(this, result -> {
            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            mState.autoRefresh.setValue(true);
            ToastUtils.showLong("取消成功");
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = false;
        page = 1;
        mState.listLoadState.set(LoadState.LOADING);
        mState.searchRequest.getListArticle(page, keyword);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = true;
        mState.listLoadState.set(LoadState.LOADING);
        mState.searchRequest.getListArticle(page, keyword);
    }

    public class ClickProxy {
        public void back() {
            finishAfterTransition();
        }
    }


}