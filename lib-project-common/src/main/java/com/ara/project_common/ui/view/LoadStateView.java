package com.ara.project_common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.ara.project_common.R;


/**
 * Created by XieXin on 2021/5/12.
 * 加载状态View
 */
public class LoadStateView extends FrameLayout {
    private final View view;
    private LinearLayout llLoading;
    private TextView tvLoadText;
    private View bg;
    private LinearLayout llTips;
    private ImageView ivError;
    private ImageView ivEmpty;
    private TextView tvTipsText;

    public LoadStateView(@NonNull Context context) {
        this(context, null);
    }

    public LoadStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = inflate(context, R.layout.view_load_state, this);
        init();
    }

    private void init() {
        llLoading = view.findViewById(R.id.llLoading);
        tvLoadText = view.findViewById(R.id.tvLoadText);
        bg = view.findViewById(R.id.bg);
        llTips = view.findViewById(R.id.llTips);
        ivError = view.findViewById(R.id.ivError);
        ivEmpty = view.findViewById(R.id.ivEmpty);
        tvTipsText = view.findViewById(R.id.tvTipsText);
    }

    /**
     * 显示加载中
     */
    public void showLoading() {
        showLoading(com.ara.base.R.string.loading);
    }

    /**
     * 显示加载中
     */
    public void showLoading(@StringRes int resId) {
        if (llLoading != null) {
            setVisibility(VISIBLE);
            llLoading.setVisibility(VISIBLE);
            tvLoadText.setText(resId);
        }
    }

    /**
     * 显示加载中
     */
    public void showLoading(String text) {
        if (llLoading != null) {
            setVisibility(VISIBLE);
            llLoading.setVisibility(VISIBLE);
            tvLoadText.setText(text == null ? "" : text);
        }
    }

    /**
     * 隐藏加载中
     */
    public void hideLoading() {
        if (llLoading != null) {
            llLoading.setVisibility(GONE);
        }
    }

    /**
     * 是否能点击
     */
    public void setTipsEnabled(boolean enabled) {
        if (llTips != null) llTips.setEnabled(enabled);
    }

    /**
     * 显示网络异常
     */
    public void showError() {
        showError(com.ara.network.R.string.network_error);
    }

    /**
     * 显示网络异常
     */
    public void showError(OnClickListener onClickListener) {
        if (llTips != null) {
            llTips.setOnClickListener(onClickListener);
            bg.setVisibility(VISIBLE);
        }
        showError(com.ara.base.R.string.network_error_click_refresh);
    }

    /**
     * 显示网络异常
     */
    public void showError(@StringRes int resId, OnClickListener onClickListener) {
        if (llTips != null) {
            llTips.setOnClickListener(onClickListener);
            bg.setVisibility(VISIBLE);
        }
        showError(getContext().getString(com.ara.base.R.string.click_refresh_format, getContext().getText(resId)));
    }

    /**
     * 显示网络异常
     */
    public void showError(String text, OnClickListener onClickListener) {
        if (llTips != null) {
            llTips.setOnClickListener(onClickListener);
            bg.setVisibility(VISIBLE);
        }
        showError(getContext().getString(com.ara.base.R.string.click_refresh_format, text));
    }

    /**
     * 显示网络异常
     */
    public void showError(@StringRes int resId) {
        if (llTips != null) {
            setVisibility(VISIBLE);
            ivEmpty.setVisibility(GONE);
            llTips.setVisibility(VISIBLE);
            ivError.setVisibility(VISIBLE);
            tvTipsText.setVisibility(VISIBLE);
            tvTipsText.setText(resId);
            llLoading.setVisibility(GONE);
        }
    }

    /**
     * 显示网络异常
     */
    public void showError(String text) {
        if (llTips != null) {
            setVisibility(VISIBLE);
            ivEmpty.setVisibility(GONE);
            llTips.setVisibility(VISIBLE);
            ivError.setVisibility(VISIBLE);
            tvTipsText.setVisibility(VISIBLE);
            tvTipsText.setText(text == null ? "" : text);
            llLoading.setVisibility(GONE);
        }
    }

    /**
     * 显示无数据
     */
    public void showEmpty() {
        showEmpty(com.ara.base.R.string.no_data);
    }

    /**
     * 显示无数据
     */
    public void showEmpty(OnClickListener onClickListener) {
        if (llTips != null) {
            llTips.setOnClickListener(onClickListener);
            bg.setVisibility(VISIBLE);
        }
        showEmpty(com.ara.base.R.string.no_data_click_refresh);
    }

    /**
     * 显示无数据
     */
    public void showEmpty(@StringRes int resId, OnClickListener onClickListener) {
        if (llTips != null) {
            llTips.setOnClickListener(onClickListener);
            bg.setVisibility(VISIBLE);
        }
        showEmpty(getContext().getString(com.ara.base.R.string.click_refresh_format, getContext().getText(resId)));
    }

    /**
     * 显示无数据
     */
    public void showEmpty(String text, OnClickListener onClickListener) {
        if (llTips != null) {
            llTips.setOnClickListener(onClickListener);
            bg.setVisibility(VISIBLE);
        }
        showEmpty(getContext().getString(com.ara.base.R.string.click_refresh_format, text));
    }

    /**
     * 显示无数据
     */
    public void showEmpty(@StringRes int resId) {
        if (llTips != null) {
            setVisibility(VISIBLE);
            ivError.setVisibility(GONE);
            llTips.setVisibility(VISIBLE);
            ivEmpty.setVisibility(VISIBLE);
            tvTipsText.setVisibility(VISIBLE);
            tvTipsText.setText(resId);
            llLoading.setVisibility(GONE);
        }
    }

    public void showEmpty(String text) {
        if (llTips != null) {
            setVisibility(VISIBLE);
            ivError.setVisibility(GONE);
            llTips.setVisibility(VISIBLE);
            ivEmpty.setVisibility(VISIBLE);
            tvTipsText.setVisibility(VISIBLE);
            tvTipsText.setText(text == null ? "" : text);
            llLoading.setVisibility(GONE);
        }
    }

    /**
     * 设置提示文字
     */
    public void showTipsText(@StringRes int resId) {
        if (tvTipsText != null) {
            tvTipsText.setText(resId);
        }
    }

    /**
     * 设置提示文字
     */
    public void showTipsText(String text) {
        if (tvTipsText != null) {
            tvTipsText.setText(text == null ? "" : text);
        }
    }

    /**
     * 隐藏提示
     */
    public void hideTips() {
        if (bg != null) {
            bg.setVisibility(GONE);
            llTips.setVisibility(GONE);
            ivError.setVisibility(GONE);
            ivEmpty.setVisibility(GONE);
            tvTipsText.setVisibility(GONE);
        }
    }

    /**
     * 全部隐藏
     */
    public void hide() {
        if (llLoading != null) {
            setVisibility(GONE);
            llLoading.setVisibility(GONE);
            bg.setVisibility(GONE);
            llTips.setVisibility(GONE);
            ivError.setVisibility(GONE);
            ivEmpty.setVisibility(GONE);
            tvTipsText.setVisibility(GONE);
        }
    }

    public void showSuccess() {
        hide();
    }

    public void setBgVisibility(boolean visible) {
        if (bg != null) {
            bg.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    public void setBgVisibility(boolean visible, @ColorInt int color) {
        if (bg != null) {
            bg.setVisibility(visible ? VISIBLE : GONE);
            bg.setBackgroundColor(color);
        }
    }

    /**
     * 实现重试功能需要继承该接口
     * 在LoadStateBindingAdapter会回调此接口
     */
    public interface ClickProxy {
        void retry();
    }
}
