package com.ara.account.ui.page;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ara.account.BR;
import com.ara.account.R;
import com.ara.account.data.api.AccountRouterApi;
import com.ara.account.ui.state.LoginActivityViewModel;
import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseActivity;
import com.ara.project_common.data.util.AccountUtils;
import com.ara.project_common.domain.message.ShareViewModel;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;


/**
 * Created by XieXin on 2021/5/24.
 * 登录
 */
@Route(path = AccountRouterApi.API_LOGIN)
public class LoginActivity extends BaseActivity {
    private LoginActivityViewModel mState;
    private ShareViewModel mEvent;
    /*** 是否重新登录 */
    @Autowired
    boolean isReLogin;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(LoginActivityViewModel.class);
        mEvent = getApplicationScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.acc_activity_login, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

        mState.username.set(AccountUtils.getLoginAccount());

        mState.isReLogin.set(isReLogin);
        // 让 Request 可观察页面生命周期，
        // 从而在页面即将退出、且登录请求由于网络延迟尚未完成时，
        // 及时通知数据层取消本次请求，以避免资源浪费和一系列不可预期的问题。
        getLifecycle().addObserver(mState.accountRequest);

        // 将 request 作为 state-ViewModel 的成员暴露给 Activity/Fragment，
        // 如此便于语义的明确，以及实现多个 request 在 state-ViewModel 中的组合和复用。
        mState.accountRequest.getLoginLiveData().observe(this, result -> {
            mState.loadState.set(LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            AccountUtils.setIsLogin(true);
            AccountUtils.setLoginAccount(mState.username.get());
            AccountUtils.setAccount(result.getResult().getData());
            ToastUtils.showLong("登录成功");
            mEvent.requestToLoginSuccess(true);
            finish();
        });
    }

    public class ClickProxy {
        public void login() {
            // 通过 xml 中的双向绑定，使得能够通过 state-ViewModel 中与控件发生绑定的"可观察数据"拿到控件的数据，
            // 避免直接接触控件实例而埋下视图实例 null 安全的一致性隐患。
            if (TextUtils.isEmpty(mState.username.get())) {
                ToastUtils.showLong(R.string.acc_input_username);
                return;
            }
            if (TextUtils.isEmpty(mState.password.get())) {
                ToastUtils.showLong(R.string.acc_input_password);
                return;
            }
            mState.loadState.set(LoadState.LOADING);
            mState.accountRequest.login(mState.username.get(), mState.password.get());
        }

        public void register() {
            AccountRouterApi.jumpRegisterActivity();
        }
    }
}