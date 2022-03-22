package com.ara.mine.ui.page;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.base.ui.page.BaseActivity;
import com.ara.mine.BR;
import com.ara.mine.R;
import com.ara.mine.data.api.MineRouterApi;
import com.ara.mine.data.config.Config;
import com.ara.mine.ui.state.SettingsActivityViewModel;
import com.ara.network.OkHttpHolder;
import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.util.AccountUtils;
import com.ara.project_common.domain.message.ShareViewModel;
import com.ara.widget.dialog.XPromptDialog;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LanguageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

import java.util.Locale;

/**
 * Created by XieXin on 2022/2/24.
 * 复制模块Main
 */
@Route(path = MineRouterApi.API_SETTINGS)
public class SettingsActivity extends BaseActivity {
    private SettingsActivityViewModel mState;
    private ShareViewModel mEvent;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(SettingsActivityViewModel.class);
        mEvent = getApplicationScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.mine_activity_settings, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = LanguageUtils.getAppliedLanguage();
        if (TextUtils.equals(locale.getLanguage(), "en")) {
            mState.language.setValue(getString(R.string.mine_english));
        } else {
            mState.language.setValue(getString(R.string.mine_zh_cn));
        }
        mState.cacheSize.setValue(FileUtils.getSize(getCacheDir()));
        mState.version.setValue("V" + AppUtils.getAppVersionName());

        getLifecycle().addObserver(mState.mineRequest);

        mState.mineRequest.getUserInfoLiveData().observe(this, result -> {

        });

        mState.mineRequest.getLogoutLiveData().observe(this, result -> {
            OkHttpHolder.clearCookie();
            AccountUtils.logout();
            mEvent.requestToHomeBottomNavigationIndex(0);
            finish();
        });

    }

    public class ClickProxy {
        private XPromptDialog clearCacheDialog;
        private XPromptDialog copyrightDialog;

        public void languageSetting() {
            MineRouterApi.jumpLanguageActivity();
        }

        public void clearCache() {
            if (clearCacheDialog == null) {
                clearCacheDialog = XPromptDialog.Builder.create(getActivity())
                        .content("是否确定要清除缓存？")
                        .onPositive(dialog -> {
                            FileUtils.deleteAllInDir(getCacheDir());
                            mState.cacheSize.setValue(FileUtils.getSize(getCacheDir()));
                        })
                        .build();
            }
            clearCacheDialog.show(getSupportFragmentManager(), "clearCacheDialog");

        }

        public void currentVersion() {
            ToastUtils.showLong("当前已是最新版本");
        }

        public void copyright() {
            if (copyrightDialog == null) {
                copyrightDialog = XPromptDialog.Builder.create(getActivity())
                        .showDoubleButton(false)
                        .content("本APP中所有API均由WanAndroid官网提供，仅供学习，禁止作为商业用途。")
                        .build();
            }
            copyrightDialog.show(getSupportFragmentManager(), "copyrightDialog");
        }

        public void aboutUs() {
            CommonRouterApi.jumpWebActivity(Config.ABOUT_URL);
        }

        public void logout() {
            mState.mineRequest.logout();
        }
    }
}