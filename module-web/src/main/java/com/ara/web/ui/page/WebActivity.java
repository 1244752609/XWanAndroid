package com.ara.web.ui.page;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ara.base.ui.page.BaseActivity;
import com.ara.web.BR;
import com.ara.web.R;
import com.ara.web.data.api.WebRouterApi;
import com.ara.web.ui.state.WebActivityViewModel;
import com.ara.web.ui.view.XWebLayout;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/2/24.
 * 复制模块Main
 */
@Route(path = WebRouterApi.API_WEB)
public class WebActivity extends BaseActivity {
    private WebActivityViewModel mState;

    @Autowired
    String url;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(WebActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.web_activity_web, BR.vm, mState)
                .addBindingParam(BR.webViewClient, webViewClient)
                .addBindingParam(BR.webChromeClient, webChromeClient)
                .addBindingParam(BR.webLayout, new XWebLayout(this))
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mState.webUrl.setValue(url);
    }

    private final WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private final WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mState.title.setValue(title);
        }
    };

    public class ClickProxy {
        private WebMoreDialog webMoreDialog;

        public void more() {
            if (webMoreDialog == null) {
                webMoreDialog = new WebMoreDialog(url);
            }
            webMoreDialog.show(getSupportFragmentManager(), "WebMoreDialog");
        }
    }

}