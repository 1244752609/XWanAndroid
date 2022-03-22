package com.ara.web.ui.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.base.ui.page.BaseDialog;
import com.ara.web.BR;
import com.ara.web.R;
import com.ara.web.ui.state.WebMoreDialogViewModel;
import com.blankj.utilcode.util.ScreenUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/3/9.
 * Web更多对话框
 */
public class WebMoreDialog extends BaseDialog {
    private WebMoreDialogViewModel mState;
    private final String url;

    public WebMoreDialog(String url) {
        this.url = url;
    }

    @Override
    protected void initViewModel() {
        mState = getDialogScopeViewModel(WebMoreDialogViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.web_more_dialog, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setWindowAnimations(com.ara.base.R.style.ActionSheetDialogAnimation);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogWidth(ScreenUtils.getScreenWidth());
    }

    public class ClickProxy {
        public void browserOpen() {
            dismiss();
            if (!TextUtils.isEmpty(url) && (url.startsWith("http") || url.startsWith("https"))) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        }

        public void cancel() {
            dismiss();
        }
    }
}
