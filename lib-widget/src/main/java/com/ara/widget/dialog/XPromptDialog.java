package com.ara.widget.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ara.base.ui.page.BaseDialog;
import com.ara.widget.BR;
import com.ara.widget.R;
import com.kunminx.architecture.ui.page.DataBindingConfig;


/**
 * Created by XieXin on 2018/12/26.
 * <p>
 * XPromptDialog.Builder.create((AppCompatActivity) nav.getContext())
 * .title("测试标题")
 * .content("测试内容")
 * .titleColorResource(com.ara.base.R.color.blue)
 * .contentColorResource(com.ara.base.R.color.text_99)
 * .textNegativeColorResource(com.ara.base.R.color.green)
 * .textPositiveColorResource(com.ara.base.R.color.red)
 * .show();
 * </p>
 * 提示对话框
 */
public class XPromptDialog extends BaseDialog {
    private XPromptViewModel mState;
    private final Builder builder;

    private XPromptDialog(@NonNull Builder builder) {
        this.builder = builder;
    }

    @Override
    protected void initViewModel() {
        mState = getDialogScopeViewModel(XPromptViewModel.class);
        mState.builder.setValue(builder);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.dialog_x_prompt, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        setCancelable(builder.cancelable);
    }

    public void setTitle(String title) {
        this.builder.title(title);
        mState.builder.setValue(builder);
    }

    public void setTitle(@StringRes int resId) {
        this.builder.title(resId);
        mState.builder.setValue(builder);
    }

    public void setContent(String content) {
        this.builder.content(content);
        mState.builder.setValue(builder);
    }

    public void setContent(@StringRes int resId) {
        this.builder.content(resId);
        mState.builder.setValue(builder);
    }

    public void setOnPositive(XClickListener onPositive) {
        this.builder.onPositive(onPositive);
        mState.builder.setValue(builder);
    }

    public void setOnNegative(XClickListener onNegative) {
        this.builder.onNegative(onNegative);
        mState.builder.setValue(builder);
    }

    public void setShowButton(boolean isShowButton) {
        this.builder.showDoubleButton(isShowButton);
        mState.builder.setValue(builder);
    }

    public class ClickProxy {
        public void onNegative() {
            if (builder.isDismiss) dismiss();
            if (builder.onNegative != null) {
                builder.onNegative.onXClickListener(XPromptDialog.this);
            }
        }

        public void onPositive() {
            if (builder.isDismiss) dismiss();
            if (builder.onPositive != null)
                builder.onPositive.onXClickListener(XPromptDialog.this);
        }
    }

    public static class Builder {
        public final AppCompatActivity mActivity;
        public String title;
        public String content;
        public String textPositive;
        public String textNegative;
        public int titleColor;
        public int contentColor;
        public int textPositiveColor;
        public int textNegativeColor;
        public boolean cancelable = true;
        public boolean isShowDoubleButton = true;
        public boolean isDismiss = true;
        public XClickListener onPositive;
        public XClickListener onNegative;

        public static Builder create(@NonNull AppCompatActivity activity) {
            return new Builder(activity);
        }

        private Builder(@NonNull AppCompatActivity activity) {
            this.mActivity = activity;
            title = activity.getString(com.ara.base.R.string.tips);
            content = activity.getString(com.ara.base.R.string.nothing);
            textPositive = activity.getString(com.ara.base.R.string.confirm);
            textNegative = activity.getString(com.ara.base.R.string.cancel);
            titleColor = activity.getColor(com.ara.base.R.color.text_33);
            contentColor = activity.getColor(com.ara.base.R.color.text_33);
            textPositiveColor = activity.getColor(com.ara.base.R.color.colorPrimary);
            textNegativeColor = activity.getColor(com.ara.base.R.color.text_66);
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder title(@StringRes int resId) {
            this.title = mActivity.getString(resId);
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder content(@StringRes int resId) {
            this.content = mActivity.getString(resId);
            return this;
        }

        public Builder textPositive(String textPositive) {
            this.textPositive = textPositive;
            return this;
        }

        public Builder textPositive(@StringRes int resId) {
            this.textPositive = mActivity.getString(resId);
            return this;
        }

        public Builder textNegative(String textNegative) {
            this.textNegative = textNegative;
            return this;
        }

        public Builder textNegative(@StringRes int resId) {
            this.textNegative = mActivity.getString(resId);
            return this;
        }

        public Builder titleColor(@ColorInt int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder titleColorResource(@ColorRes int resId) {
            this.titleColor = ContextCompat.getColor(mActivity, resId);
            return this;
        }

        public Builder contentColor(@ColorInt int contentColor) {
            this.contentColor = contentColor;
            return this;
        }

        public Builder contentColorResource(@ColorRes int resId) {
            this.contentColor = ContextCompat.getColor(mActivity, resId);
            return this;
        }

        public Builder textPositiveColor(@ColorInt int textPositiveColor) {
            this.textPositiveColor = textPositiveColor;
            return this;
        }

        public Builder textPositiveColorResource(@ColorRes int resId) {
            this.textPositiveColor = ContextCompat.getColor(mActivity, resId);
            return this;
        }

        public Builder textNegativeColor(@ColorInt int textNegativeColor) {
            this.textNegativeColor = textNegativeColor;
            return this;
        }

        public Builder textNegativeColorResource(@ColorRes int resId) {
            this.textNegativeColor = ContextCompat.getColor(mActivity, resId);
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder showDoubleButton(boolean isShowButton) {
            this.isShowDoubleButton = isShowButton;
            return this;
        }

        public Builder dismiss(boolean isDismiss) {
            this.isDismiss = isDismiss;
            return this;
        }

        public Builder onPositive(XClickListener onPositive) {
            this.onPositive = onPositive;
            return this;
        }

        public Builder onNegative(XClickListener onNegative) {
            this.onNegative = onNegative;
            return this;
        }

        public XPromptDialog build() {
            return new XPromptDialog(this);
        }

        public XPromptDialog show() {
            XPromptDialog dialog = build();
            dialog.show(mActivity.getSupportFragmentManager(), "XPromptDialog");
            return dialog;
        }
    }

    public interface XClickListener {
        void onXClickListener(XPromptDialog dialog);
    }
}
