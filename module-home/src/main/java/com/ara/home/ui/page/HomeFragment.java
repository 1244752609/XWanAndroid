package com.ara.home.ui.page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;

import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseFragment;
import com.ara.home.BR;
import com.ara.home.R;
import com.ara.home.data.api.HomeRouterApi;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.data.bean.BannerListBean;
import com.ara.home.ui.page.adapter.HomeAdapter;
import com.ara.home.ui.state.HomeFragmentViewModel;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.util.AccountUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import pw.xiaohaozi.adapter_plus.data.ViewTyper;


/**
 * Created by XieXin on 2021/5/24.
 * 首页
 */
public class HomeFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private boolean isLoadMore;
    private int page = 1;
    private HomeFragmentViewModel mState;
    private HomeAdapter adapter;
    private final ObservableArrayList<ViewTyper> data = new ObservableArrayList<>();

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(HomeFragmentViewModel.class);
        adapter = new HomeAdapter();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.home_frament_home, BR.vm, mState)
                .addBindingParam(BR.onRefreshLoadMoreListener, this)
                .addBindingParam(BR.click, new CLickProxy())
                .addBindingParam(BR.adapter, adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getLifecycle().addObserver(mState.homeRequest);

        adapter.setOnItemClickListener((v, binding, position) -> {
            if (position > 0) {
                ArticleBean bean = (ArticleBean) data.get(position);
                CommonRouterApi.jumpWebActivity(bean.getLink());
            }
        });

        mState.homeRequest.getBannerLiveData().observe(this, result -> {
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            if (data.size() > 0 && data.get(0).getItemViewType() == 0) {
                data.remove(0);
            }
            BannerListBean bean = new BannerListBean();
            bean.setBeans(result.getResult().getData());
            data.add(0, bean);
            adapter.refresh(data);
        });

        mState.homeRequest.getArticleLiveData().observe(this, result -> {
            mState.autoRefresh.setValue(false);
            if (!result.isSuccess()) {
                mState.listLoadState.set(!isLoadMore ? LoadState.ERROR : LoadState.LOAD_MORE_ERROR);
                if (data.isEmpty()) mState.loadState.set(LoadState.ERROR);
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            if (!isLoadMore) {
                if (data.size() > 0 && data.get(0).getItemViewType() == 0) {
                    ViewTyper bean = data.get(0);
                    data.clear();
                    data.add(bean);
                } else {
                    data.clear();
                }
            }
            data.addAll(result.getResult().getData().getList());
            adapter.refresh(data);
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
                ArticleBean bean = (ArticleBean) data.get(position);
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
    public void onResume() {
        super.onResume();
        BarUtils.setStatusBarColor(requireActivity(), Color.WHITE);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = false;
        page = 1;
        mState.listLoadState.set(LoadState.LOADING);
        mState.homeRequest.getBanner();
        mState.homeRequest.getArticle(page);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = true;
        mState.listLoadState.set(LoadState.LOADING);
        mState.homeRequest.getBanner();
        mState.homeRequest.getArticle(page);
    }

    public class CLickProxy {
        public void search() {
            HomeRouterApi.jumpSearchActivity();
        }
    }

}