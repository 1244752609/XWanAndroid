package com.ara.base.ui.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ara.base.BaseApplication;
import com.kunminx.architecture.ui.page.DataBindingFragment;

/**
 * Created by XieXin on 2022/2/15.
 * Fragment基类
 * DataBinding 严格模式（详见 DataBindingFragment - - - - - ）：
 * 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
 * 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
 */
public abstract class BaseFragment extends DataBindingFragment {
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    /**
     * Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
     * 目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
     * 值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
     * 所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
     */
    protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) mFragmentProvider = new ViewModelProvider(this);
        return mFragmentProvider.get(modelClass);
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
        mApplicationProvider = new ViewModelProvider((BaseApplication) this.getApplicationContext());
        return mApplicationProvider.get(modelClass);
    }

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void openUrlInBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    protected Context getApplicationContext() {
        return mActivity.getApplicationContext();
    }

}
