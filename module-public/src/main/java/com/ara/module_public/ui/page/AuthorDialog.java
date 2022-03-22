package com.ara.module_public.ui.page;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;

import com.ara.base.ui.page.BaseDialog;
import com.ara.module_public.BR;
import com.ara.module_public.R;
import com.ara.module_public.data.bean.PublicAuthorBean;
import com.ara.module_public.ui.state.AuthorDialogViewModel;
import com.blankj.utilcode.util.ScreenUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

import java.util.List;

/**
 * Created by XieXin on 2022/3/9.
 * 作者对话框
 */
public class AuthorDialog extends BaseDialog {
    private AuthorDialogViewModel mState;
    private final ObservableArrayList<PublicAuthorBean> beans;

    public AuthorDialog(List<PublicAuthorBean> beans) {
        this.beans = new ObservableArrayList<>();
        this.beans.addAll(beans);
    }

    @Override
    protected void initViewModel() {
        mState = getDialogScopeViewModel(AuthorDialogViewModel.class);
        mState.adapter.refresh(this.beans);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.pub_author_dialog, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setWindowAnimations(com.ara.base.R.style.ActionSheetDialogAnimation);

        mState.adapter.setOnItemClickListener((v, proItemProjectClassifyBinding, position) -> {
            dismiss();
            if (callback != null) callback.onClick(beans.get(position));
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogWidth(ScreenUtils.getScreenWidth());
    }

    public class ClickProxy {
        public void cancel() {
            dismiss();
        }
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onClick(PublicAuthorBean bean);
    }
}
