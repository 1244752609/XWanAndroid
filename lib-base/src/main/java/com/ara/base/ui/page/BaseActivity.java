package com.ara.base.ui.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.ara.base.BaseApplication;
import com.ara.base.data.response.manager.NetworkStateManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.kunminx.architecture.ui.page.DataBindingActivity;

/**
 * Created by XieXin on 2022/2/15.
 * activity基类
 * DataBinding 严格模式（详见 DataBindingActivity - - - - - ）：
 * 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
 * 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
 */
public abstract class BaseActivity extends DataBindingActivity {
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarLightMode(this, true);
        super.onCreate(savedInstanceState);
        //需要在setContentView后使用，否则isDecor设置为True
        BarUtils.setStatusBarColor(this, Color.WHITE);

        getLifecycle().addObserver(NetworkStateManager.getsInstance());
    }

    public Context getContext() {
        return this;
    }

    public AppCompatActivity getActivity() {
        return this;
    }

    /**
     * Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
     * 目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
     * 值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
     * 所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
     */
    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) mActivityProvider = new ViewModelProvider(this);
        return mActivityProvider.get(modelClass);
    }

    /**
     * Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
     * 目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
     * 值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
     * 所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
     */
    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null)
            mApplicationProvider = new ViewModelProvider((BaseApplication) this.getApplicationContext());
        return mApplicationProvider.get(modelClass);
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void openUrlInBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
