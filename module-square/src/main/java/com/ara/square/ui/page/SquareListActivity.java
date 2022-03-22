package com.ara.square.ui.page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseActivity;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.util.AccountUtils;
import com.ara.square.BR;
import com.ara.square.R;
import com.ara.square.data.api.SquareRouterApi;
import com.ara.square.data.bean.ChildrenBean;
import com.ara.square.ui.page.adapter.SquareListAdapter;
import com.ara.square.ui.state.SquareListActivityViewModel;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

@Route(path = SquareRouterApi.API_SQUARE_LIST)
public class SquareListActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private boolean isLoadMore;
    private int page = 1;
    private SquareListActivityViewModel mState;
    private SquareListAdapter adapter;
    private final ObservableArrayList<ArticleBean> data = new ObservableArrayList<>();

    @Autowired
    ChildrenBean children;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(SquareListActivityViewModel.class);
        adapter = new SquareListAdapter();
        adapter.refresh(data);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.squ_activity_square_list, BR.vm, mState)
                .addBindingParam(BR.onRefreshLoadMoreListener, this)
                .addBindingParam(BR.adapter, adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        getLifecycle().addObserver(mState.squareRequest);
        mState.title.set(children.getName());

        mState.squareRequest.getArticleLiveData().observe(this, result -> {
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
                    mState.squareRequest.unCollect(bean.getId());
                } else {
                    mState.squareRequest.collect(bean.getId());
                }
            } else {
                CommonRouterApi.jumpReLoginActivity();
            }
        });
        mState.squareRequest.getCollectLiveData().observe(this, result -> {
            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            mState.autoRefresh.setValue(true);
            ToastUtils.showLong("收藏成功");
        });
        mState.squareRequest.getUnCollectLiveData().observe(this, result -> {
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
        mState.squareRequest.getListArticle(page, children.getId());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = true;
        mState.listLoadState.set(LoadState.LOADING);
        mState.squareRequest.getListArticle(page, children.getId());
    }
}