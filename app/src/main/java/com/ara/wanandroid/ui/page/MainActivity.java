package com.ara.wanandroid.ui.page;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.base.ui.page.BaseActivity;
import com.ara.common.rxbus.RxBus;
import com.ara.common.rxbus.RxMsgEvent;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.domain.message.ShareViewModel;
import com.ara.wanandroid.BR;
import com.ara.wanandroid.R;
import com.ara.wanandroid.data.api.AppRouterApi;
import com.ara.wanandroid.ui.page.adapter.MainFragmentStateAdapter;
import com.ara.wanandroid.ui.state.MainActivityViewModel;
import com.blankj.utilcode.util.BarUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

@Route(path = AppRouterApi.API_MAIN)
public class MainActivity extends BaseActivity {
    private MainActivityViewModel mState;
    private ShareViewModel mEvent;
    private MainFragmentStateAdapter mAdapter;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MainActivityViewModel.class);
        mState.index.setValue(0);
        mEvent = getApplicationScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mAdapter = new MainFragmentStateAdapter(this);
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mState)
                .addBindingParam(BR.adapter, mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT, true);

        RxBus.register(RxMsgEvent.class, getLifecycle()).subscribe(rxMsgEvent -> {
            if (rxMsgEvent.getCode() == BaseBean.REQUEST_LOGIN_OVERDUE) {
                CommonRouterApi.jumpReLoginActivity();
            }
        });

        mEvent.getToHomeBottomNavigationIndex().observe(this, index -> {
            mState.index.setValue(index);
        });
    }
}