package com.ara.project.ui.page;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;

import com.ara.base.ui.page.BaseDialog;
import com.ara.project.BR;
import com.ara.project.R;
import com.ara.project.data.bean.ProjectClassifyBean;
import com.ara.project.ui.state.ClassifyDialogViewModel;
import com.blankj.utilcode.util.ScreenUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

import java.util.List;

/**
 * Created by XieXin on 2022/3/9.
 * 分类对话框
 */
public class ClassifyDialog extends BaseDialog {
    private ClassifyDialogViewModel mState;
    private final ObservableArrayList<ProjectClassifyBean> beans;

    public ClassifyDialog(List<ProjectClassifyBean> beans) {
        this.beans = new ObservableArrayList<>();
        this.beans.addAll(beans);
    }

    @Override
    protected void initViewModel() {
        mState = getDialogScopeViewModel(ClassifyDialogViewModel.class);
        mState.adapter.refresh(this.beans);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.pro_classify_dialog, BR.vm, mState)
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
        void onClick(ProjectClassifyBean bean);
    }
}
