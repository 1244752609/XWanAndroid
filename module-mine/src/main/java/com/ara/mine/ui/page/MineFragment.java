package com.ara.mine.ui.page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseFragment;
import com.ara.mine.BR;
import com.ara.mine.R;
import com.ara.mine.data.api.MineRouterApi;
import com.ara.mine.data.bean.IntegralBean;
import com.ara.mine.ui.state.MineFragmentViewModel;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.bean.AccountBean;
import com.ara.project_common.data.util.AccountUtils;
import com.ara.project_common.domain.message.ShareViewModel;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * Created by XieXin on 2022/3/16.
 * 我的
 */
public class MineFragment extends BaseFragment implements OnRefreshListener {
    private MineFragmentViewModel mState;
    private ShareViewModel mEvent;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(MineFragmentViewModel.class);
        mEvent = getApplicationScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.mine_fragment_mine, BR.vm, mState)
                .addBindingParam(BR.onRefreshListener, this)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mState.account.setValue(AccountUtils.getAccount());

        mState.mineRequest.getUserInfoLiveData().observe(this, result -> {
            mState.autoRefresh.setValue(false);
            mState.listLoadState.set(LoadState.SUCCESS);
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            IntegralBean coinInfo = result.getResult().getData().getCoinInfo();
            AccountBean userInfo = result.getResult().getData().getUserInfo();
            userInfo.setCoinCount(coinInfo.getCoinCount());
            userInfo.setRank(coinInfo.getRank());
            AccountUtils.setAccount(userInfo);
            mState.account.setValue(userInfo);
            mState.integral.setValue(coinInfo);
        });

        mEvent.getToLoginSuccess().observe(this, isLogin -> {
            mState.autoRefresh.setValue(true);
        });
        mState.autoRefresh.setValue(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        BarUtils.setStatusBarColor(requireActivity(), Color.TRANSPARENT);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mState.listLoadState.set(LoadState.LOADING);
        mState.mineRequest.getUserInfo();
    }

    public class ClickProxy {
        public void settings() {
            if (!AccountUtils.isLogin()) {
                CommonRouterApi.jumpLoginActivity();
                return;
            }
            MineRouterApi.jumpSettingsActivity();
        }

        public void integralRank() {
            if (!AccountUtils.isLogin()) {
                CommonRouterApi.jumpLoginActivity();
                return;
            }
            MineRouterApi.jumpIntegralRankActivity();
        }

        public void myIntegral() {
            if (!AccountUtils.isLogin()) {
                CommonRouterApi.jumpLoginActivity();
                return;
            }
            MineRouterApi.jumpMyIntegralActivity();
        }

        public void myCollect() {
            if (!AccountUtils.isLogin()) {
                CommonRouterApi.jumpLoginActivity();
                return;
            }
            MineRouterApi.jumpMyCollectActivity();
        }

        public void myShare() {
            if (!AccountUtils.isLogin()) {
                CommonRouterApi.jumpLoginActivity();
                return;
            }
            MineRouterApi.jumpMyShareActivity();
        }
    }
}