package com.ara.square.ui.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.base.data.response.LoadState;
import com.ara.base.ui.page.BaseFragment;
import com.ara.square.BR;
import com.ara.square.R;
import com.ara.square.ui.state.NavigationFragmentViewModel;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/2/24.
 * 复制模块Main
 */
public class NavigationFragment extends BaseFragment {
    public static final String TYPE_SYSTEM = "system";
    public static final String TYPE_NAVIGATION = "navigation";
    private NavigationFragmentViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(NavigationFragmentViewModel.class);
        mState.adapter.refresh(mState.data);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.squ_fragment_navigation, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(mState.squareRequest);
        if (TextUtils.equals(getArguments().getString("type"), TYPE_SYSTEM)) {
            mState.squareRequest.getSystemLiveData().observe(this, result -> {
                mState.loadState.set(LoadState.SUCCESS);
                if (!result.isSuccess()) {
                    ToastUtils.showLong(result.getError().getMsg());
                    return;
                }
                mState.data.clear();
                mState.data.addAll(result.getResult().getData());
            });
            mState.loadState.set(LoadState.LOADING);
            mState.squareRequest.getSystem();
        } else {
            mState.squareRequest.getNavigationLiveData().observe(this, result -> {
                mState.loadState.set(LoadState.SUCCESS);
                if (!result.isSuccess()) {
                    ToastUtils.showLong(result.getError().getMsg());
                    return;
                }
                mState.data.clear();
                mState.data.addAll(result.getResult().getData());
            });
            mState.loadState.set(LoadState.LOADING);
            mState.squareRequest.getNavigation();
        }
    }
}