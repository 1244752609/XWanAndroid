package com.ara.web.ui.binding_adapter;

import android.app.Activity;
import android.widget.FrameLayout;

import androidx.databinding.BindingAdapter;

import com.ara.web.R;
import com.ara.web.ui.view.XWebLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

/**
 * Created by XieXin on 2022/3/9.
 */
public class WebActivityBindingAdapter {
    @BindingAdapter(value = {"webUrl", "webLayout", "webViewClient", "webChromeClient"}, requireAll = false)
    public static void initWebView(FrameLayout fl, String webUrl, XWebLayout webLayout,
                                   WebViewClient webViewClient, WebChromeClient webChromeClient) {
        if (webUrl == null) webUrl = "";
        AgentWeb.with((Activity) fl.getContext())
                .setAgentWebParent(fl, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(webChromeClient)
                .setWebViewClient(webViewClient)
                .setMainFrameErrorView(R.layout.web_agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(webLayout)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(webUrl);
    }

}
