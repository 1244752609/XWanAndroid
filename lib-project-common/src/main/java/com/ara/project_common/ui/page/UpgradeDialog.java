package com.ara.project_common.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ara.base.ui.page.BaseDialog;
import com.ara.project_common.BR;
import com.ara.project_common.R;
import com.ara.project_common.data.bean.UpgradeBean;
import com.ara.project_common.ui.state.UpgradeViewModel;
import com.kunminx.architecture.ui.page.DataBindingConfig;


/**
 * Created by XieXin on 2018/12/10.
 * <p>
 * UpgradeDialog upgradeDialog = new UpgradeDialog(upgradeBean, new UpgradeDialog.Callback() {
 *
 * @Override public void upgrade() {
 * LoggerUtils.e("upgrade");
 * }
 * @Override public void cancel() {
 * LoggerUtils.e("cancel");
 * }
 * });
 * upgradeDialog.show(activity.getSupportFragmentManager(), "UpgradeDialog");
 * </p>
 * 版本更新 Dialog
 */
public class UpgradeDialog extends BaseDialog {
    private UpgradeViewModel mState;
    private UpgradeBean mUpgradeBean;
    private final Callback mCallback;

    @Override
    protected void initViewModel() {
        mState = getDialogScopeViewModel(UpgradeViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.dialog_upgrade, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    public UpgradeDialog(@NonNull UpgradeBean upgradeBean, @NonNull Callback callback) {
        this.mUpgradeBean = upgradeBean;
        this.mCallback = callback;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mState.upgrade.setValue(mUpgradeBean);
    }

    public void setUpgradeBean(UpgradeBean upgradeBean) {
        this.mUpgradeBean = upgradeBean;
        mState.upgrade.setValue(mUpgradeBean);
    }

    public void show(AppCompatActivity activity) {
        show(activity.getSupportFragmentManager(), "UpgradeDialog");
    }

    public class ClickProxy {
        public void upgrade() {
            if (mCallback != null) mCallback.upgrade();
        }

        public void cancel() {
            dismiss();
            if (mCallback != null) mCallback.cancel();
        }
    }

    public interface Callback {
        void upgrade();

        default void cancel() {
        }
    }
}
