package com.ara.module_public.ui.page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;

import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseFragment;
import com.ara.module_public.data.bean.PublicAuthorBean;
import com.ara.module_public.ui.page.adapter.PublicAdapter;
import com.ara.module_public.ui.state.PublicFragmentViewModel;
import com.ara.module_public.BR;
import com.ara.module_public.R;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.util.AccountUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;


/**
 * Created by XieXin on 2021/5/24.
 * 首页
 */
public class PublicFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private boolean isLoadMore;
    private int page = 1;
    private String cid = "";
    private PublicFragmentViewModel mState;
    private PublicAdapter adapter;
    private final ObservableArrayList<ArticleBean> data = new ObservableArrayList<>();
    private List<PublicAuthorBean> classifyBeans;
    private AuthorDialog authorDialog;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(PublicFragmentViewModel.class);
        adapter = new PublicAdapter();
        adapter.refresh(data);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.pub_frament_public, BR.vm, mState)
                .addBindingParam(BR.onRefreshLoadMoreListener, this)
                .addBindingParam(BR.click, new CLickProxy())
                .addBindingParam(BR.adapter, adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getLifecycle().addObserver(mState.publicRequest);
        mState.title.set(getString(R.string.pub_public));

        adapter.setOnItemClickListener((v, binding, position) -> {
            CommonRouterApi.jumpWebActivity(data.get(position).getLink());
        });

        mState.publicRequest.getAuthorLiveData().observe(this, result -> {
            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            classifyBeans = result.getResult().getData();
            showAuthorDialog();
        });

        mState.publicRequest.getPublicLiveData().observe(this, result -> {
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
                    mState.publicRequest.unCollect(bean.getId());
                } else {
                    mState.publicRequest.collect(bean.getId());
                }
            } else {
                CommonRouterApi.jumpReLoginActivity();
            }
        });
        mState.publicRequest.getCollectLiveData().observe(this, result -> {
            mState.loadState.set(data.isEmpty() ? LoadState.EMPTY : LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            mState.autoRefresh.setValue(true);
            ToastUtils.showLong("收藏成功");
        });
        mState.publicRequest.getUnCollectLiveData().observe(this, result -> {
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
        mState.publicRequest.getListPublic(page, cid);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = true;
        mState.listLoadState.set(LoadState.LOADING);
        mState.publicRequest.getListPublic(page, cid);
    }

    public class CLickProxy {
        public void selectClassify() {
            if (classifyBeans == null) {
                mState.loadState.set(LoadState.LOADING);
                mState.publicRequest.getAuthor();
                return;
            }
            showAuthorDialog();
        }
    }

    private void showAuthorDialog() {
        if (authorDialog == null) {
            authorDialog = new AuthorDialog(classifyBeans);
            authorDialog.setCallback(bean -> {
                cid = bean.getId();
                mState.title.set(bean.getName());
                mState.autoRefresh.setValue(true);
            });
        }
        authorDialog.show(getParentFragmentManager(), "AuthorDialog");
    }

}