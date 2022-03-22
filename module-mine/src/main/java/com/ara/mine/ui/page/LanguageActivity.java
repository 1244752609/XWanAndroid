package com.ara.mine.ui.page;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.base.ui.page.BaseActivity;
import com.ara.mine.BR;
import com.ara.mine.R;
import com.ara.mine.data.api.MineRouterApi;
import com.ara.mine.ui.state.LanguageActivityViewModel;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LanguageUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;

import java.util.Locale;

/**
 * Created by XieXin on 2022/3/21.
 * 语言
 */
@Route(path = MineRouterApi.API_LANGUAGE)
public class LanguageActivity extends BaseActivity {
    private LanguageActivityViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(LanguageActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.mine_activity_language, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = LanguageUtils.getAppliedLanguage();
        if (TextUtils.equals(locale.getLanguage(), "en")) {
            mState.language.setValue(1);
        } else {
            mState.language.setValue(0);
        }
    }

    public class ClickProxy {
        public void zhCN() {
            Locale locale = LanguageUtils.getAppliedLanguage();
            mState.language.setValue(0);
            if (TextUtils.equals(locale.getLanguage(), "zh")) return;
            ActivityUtils.finishAllActivities();
            LanguageUtils.applyLanguage(Locale.SIMPLIFIED_CHINESE, true);
        }

        public void en() {
            Locale locale = LanguageUtils.getAppliedLanguage();
            mState.language.setValue(1);
            if (TextUtils.equals(locale.getLanguage(), "en")) return;
            ActivityUtils.finishAllActivities();
            LanguageUtils.applyLanguage(Locale.ENGLISH, true);
        }
    }
}