package com.ara.base.ui.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ara.base.BaseApplication;
import com.ara.base.R;
import com.ara.common.system.ScreenUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/2/22.
 * 对话框基础类
 */
public abstract class BaseDialog extends DialogFragment {
    protected AppCompatActivity mActivity;
    private ViewDataBinding mBinding;
    private ViewModelProvider mDialogProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    private TextView mTvStrictModeTip;

    public BaseDialog() {
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (AppCompatActivity) context;
    }

    protected abstract void initViewModel();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogBgTranslucentStyle);
        this.initViewModel();
    }

    public void setStyle(int theme) {
        super.setStyle(STYLE_NO_TITLE, theme);
    }

    protected abstract DataBindingConfig getDataBindingConfig();

    protected ViewDataBinding getBinding() {
        if (this.isDebug() && this.mBinding != null && this.mTvStrictModeTip == null) {
            this.mTvStrictModeTip = new TextView(this.getContext());
            this.mTvStrictModeTip.setAlpha(0.5F);
            this.mTvStrictModeTip.setPadding(this.mTvStrictModeTip.getPaddingLeft() + 24, this.mTvStrictModeTip.getPaddingTop() + 64, this.mTvStrictModeTip.getPaddingRight() + 24, this.mTvStrictModeTip.getPaddingBottom() + 24);
            this.mTvStrictModeTip.setGravity(1);
            this.mTvStrictModeTip.setTextSize(10.0F);
            this.mTvStrictModeTip.setBackgroundColor(-1);
            String tip = this.getString(com.kunminx.strictdatabinding.R.string.debug_databinding_warning, new Object[]{this.getClass().getSimpleName()});
            this.mTvStrictModeTip.setText(tip);
            ((ViewGroup) this.mBinding.getRoot()).addView(this.mTvStrictModeTip);
        }

        return this.mBinding;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataBindingConfig dataBindingConfig = this.getDataBindingConfig();
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
        int i = 0;

        for (int length = bindingParams.size(); i < length; ++i) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }

        this.mBinding = binding;
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogWidth();
    }

    public boolean isDebug() {
        return this.mActivity.getApplicationContext().getApplicationInfo() != null && (this.mActivity.getApplicationContext().getApplicationInfo().flags & 2) != 0;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mBinding.unbind();
        this.mBinding = null;
    }

    /**
     * Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
     * 目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
     * 值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
     * 所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
     */
    protected <T extends ViewModel> T getDialogScopeViewModel(@NonNull Class<T> modelClass) {
        if (mDialogProvider == null) mDialogProvider = new ViewModelProvider(this);
        return mDialogProvider.get(modelClass);
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


    /**
     * 设置对话框宽度
     */
    public void setDialogWidth() {
        Window window = getDialog().getWindow();
        //获得窗体的属性
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ScreenUtils.getScreenWidth(requireContext());
            if (ScreenUtils.isScreenOrientation(requireContext())) {
                lp.width = lp.width - (lp.width / 2) - (lp.width / 10);
            } else {
                lp.width = lp.width - (lp.width / 6);
            }
            //将属性设置给窗体
            window.setAttributes(lp);
        }
    }

    /**
     * 设置对话框宽度
     *
     * @param width 宽度
     */
    public void setDialogWidth(int width) {
        Window window = getDialog().getWindow();
        //获得窗体的属性
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            //将属性设置给窗体
            window.setAttributes(lp);
        }
    }

    /**
     * 设置对话框宽度
     *
     * @param window  dialog 窗体
     * @param context 上下文
     */
    public static void setDialogWidth(Window window, Context context) {
        //获得窗体的属性
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ScreenUtils.getScreenWidth(context);
            if (ScreenUtils.isScreenOrientation(context)) {
                lp.width = lp.width - (lp.width / 2) - (lp.width / 10);
            } else {
                lp.width = lp.width - (lp.width / 6);
            }
            //将属性设置给窗体
            window.setAttributes(lp);
        }
    }

    /**
     * 设置对话框宽度
     *
     * @param window dialog 窗体
     * @param width  宽度
     */
    public static void setDialogWidth(Window window, int width) {
        //获得窗体的属性
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            //将属性设置给窗体
            window.setAttributes(lp);
        }
    }
}
