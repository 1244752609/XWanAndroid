package com.ara.module_copy.ui.page;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.base.ui.page.BaseActivity;
import com.ara.module_copy.BR;
import com.ara.module_copy.R;
import com.ara.module_copy.data.api.CopyRouterApi;
import com.ara.module_copy.ui.state.CopyMainActivityViewModel;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/2/24.
 * 复制模块Main
 */
@Route(path = CopyRouterApi.API_COPY_MAIN)
public class CopyMainActivity extends BaseActivity {
    private CopyMainActivityViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(CopyMainActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.copy_activity_main, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState.text.setValue("Copy");
        getLifecycle().addObserver(mState.copyRequest);
        mState.copyRequest.getCopyLiveData().observe(this, result -> {

        });
    }

    public static class ClickProxy {
        public void copy() {
            // TODO: 2022/2/24 在此实现点击事件
        }
    }
}