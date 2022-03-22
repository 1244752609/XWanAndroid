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
import com.ara.mine.data.bean.IntegralBean;
import com.ara.mine.data.config.Config;
import com.ara.mine.ui.page.adapter.MyIntegralAdapter;
import com.ara.mine.ui.state.MyIntegralActivityViewModel;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.util.AccountUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Created by XieXin on 2022/3/17.
 * 我的积分
 */
@Route(path = MineRouterApi.API_MY_INTEGRAL)
public class MyIntegralActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private boolean isLoadMore;
    private int page = 1;
    private MyIntegralActivityViewModel mState;
    private MyIntegralAdapter adapter;
    private final ObservableArrayList<IntegralBean> data = new ObservableArrayList<>();

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MyIntegralActivityViewModel.class);
        adapter = new MyIntegralAdapter();
        adapter.refresh(data);

        mState.account.setValue(AccountUtils.getAccount());
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.mine_activity_my_integral, BR.vm, mState)
                .addBindingParam(BR.onRefreshLoadMoreListener, this)
                .addBindingParam(BR.adapter, adapter)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(mState.mineRequest);

        mState.mineRequest.getIntegralListLiveData().observe(this, result -> {
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
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = false;
        page = 1;
        mState.listLoadState.set(LoadState.LOADING);
        mState.mineRequest.getIntegralList(page);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = true;
        mState.listLoadState.set(LoadState.LOADING);
        mState.mineRequest.getIntegralList(page);
    }

    public static class ClickProxy {
        public void help() {
            CommonRouterApi.jumpWebActivity(Config.INTEGRAL_HELP);
        }
    }
}