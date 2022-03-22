package com.ara.wanandroid.ui.page;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ara.base.ui.page.BaseActivity;
import com.ara.wanandroid.BR;
import com.ara.wanandroid.R;
import com.ara.wanandroid.data.api.AppRouterApi;
import com.ara.wanandroid.ui.state.SplashActivityViewModel;
import com.blankj.utilcode.util.BarUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/2/17.
 * 启动页
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {
    private final static int DELAY_MILLIS = 2 * 1000;//停留两秒
    private SplashActivityViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(SplashActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_splash, BR.vm, mState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDebug()) {
            isLogin();
        } else {
            getBinding().getRoot().postDelayed(this::isLogin, DELAY_MILLIS);
        }
    }

    private void isLogin() {
        AppRouterApi.jumpMainActivity();
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}