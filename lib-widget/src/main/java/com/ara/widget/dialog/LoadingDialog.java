package com.ara.widget.dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ara.base.ui.page.BaseDialog;
import com.ara.widget.BR;
import com.ara.widget.R;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2020/1/9.
 * 加载对话框
 */
public class LoadingDialog extends BaseDialog {
    private LoadingViewModel mState;
    private final String text;

    public LoadingDialog() {
        this.text = "Loading...";
    }

    public LoadingDialog(@NonNull String text) {
        this.text = text;
    }

    @Override
    protected void initViewModel() {
        mState = getDialogScopeViewModel(LoadingViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.dialog_loading, BR.vm, mState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState.text.setValue(text);

        setCancelable(false);
    }

    public void setContent(@NonNull String text) {
        mState.text.setValue(text);
    }

    public void show(AppCompatActivity activity) {
        show(activity.getSupportFragmentManager(), "LoadingDialog");
    }
}
