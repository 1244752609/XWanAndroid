package com.ara.account.ui.page;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.account.BR;
import com.ara.account.R;
import com.ara.account.data.api.AccountRouterApi;
import com.ara.account.ui.state.RegisterActivityViewModel;
import com.ara.base.ui.page.BaseActivity;
import com.ara.base.data.response.LoadState;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

@Route(path = AccountRouterApi.API_REGISTER)
public class RegisterActivity extends BaseActivity {
    private RegisterActivityViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(RegisterActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.acc_activity_register, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(mState.accountRequest);

        mState.accountRequest.getRegisterLiveData().observe(this, result -> {
            mState.loadState.set(LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            finish();
            ToastUtils.showLong("注册成功");
        });
    }

    public class ClickProxy {
        public void register() {
            if (TextUtils.isEmpty(mState.username.get())) {
                ToastUtils.showLong(R.string.acc_input_username);
                return;
            }
            if (TextUtils.isEmpty(mState.password.get())) {
                ToastUtils.showLong(R.string.acc_input_password);
                return;
            }
            if (TextUtils.isEmpty(mState.repassword.get())) {
                ToastUtils.showLong(R.string.acc_input_repassword);
                return;
            }
            mState.loadState.set(LoadState.LOADING);
            mState.accountRequest.register(mState.username.get(), mState.password.get(), mState.repassword.get());
        }
    }
}