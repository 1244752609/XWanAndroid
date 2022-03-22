package com.ara.mine.ui.page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseActivity;
import com.ara.mine.BR;
import com.ara.mine.R;
import com.ara.mine.data.api.MineRouterApi;
import com.ara.mine.ui.page.adapter.MyShareAdapter;
import com.ara.mine.ui.state.MyShareActivityViewModel;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.widget.dialog.XPromptDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Created by XieXin on 2022/3/17.
 * 我的分享
 */
@Route(path = MineRouterApi.API_MY_SHARE)
public class MyShareActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private boolean isLoadMore;
    private int page = 1;
    private MyShareActivityViewModel mState;
    private MyShareAdapter adapter;
    private final ObservableArrayList<ArticleBean> data = new ObservableArrayList<>();

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MyShareActivityViewModel.class);
        adapter = new MyShareAdapter();
        adapter.refresh(data);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.mine_activity_my_share, BR.vm, mState)
                .addBindingParam(BR.onRefreshLoadMoreListener, this)
                .addBindingParam(BR.adapter, adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(mState.shareRequest);

        adapter.setOnItemClickListener((v, binding, position) -> {
            CommonRouterApi.jumpWebActivity(data.get(position).getLink());
        });

        mState.shareRequest.getShareListLiveData().observe(this, result -> {
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
            data.addAll(result.getResult().getData().getShareArticles().getList());
            if (result.getResult().getData().getShareArticles().isLast()) {
                mState.listLoadState.set(LoadState.LOAD_MORE_NO_MORE_DATA);
            } else {
                page++;
                mState.listLoadState.set(!isLoadMore ? LoadState.SUCCESS : LoadState.LOAD_MORE_SUCCESS);
            }

            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
        });
        mState.autoRefresh.setValue(true);

        adapter.setOnItemLongClickListener((v, binding, position) -> {
            ArticleBean bean = data.get(position);
            XPromptDialog.Builder.create(this)
                    .content("是否确定删除" + bean.getTitle())
                    .onPositive(dialog -> {
                        mState.loadState.set(LoadState.LOADING);
                        mState.shareRequest.deleteArticle(bean.getId());
                    })
                    .show();
        });
        mState.shareRequest.getDeleteArticleLiveData().observe(this, result -> {
            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            mState.autoRefresh.setValue(true);
            ToastUtils.showLong("删除成功");
        });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = false;
        page = 1;
        mState.listLoadState.set(LoadState.LOADING);
        mState.shareRequest.getShareList(page);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = true;
        mState.listLoadState.set(LoadState.LOADING);
        mState.shareRequest.getShareList(page);
    }
}