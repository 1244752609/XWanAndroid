package com.ara.web.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.web.R;
import com.just.agentweb.IWebLayout;

/**
 * Created by XieXin on 2022/3/9.
 */
public class XWebLayout implements IWebLayout<WebView, WebView> {
    private WebView webView;

    public XWebLayout(Context context) {
        this.webView = (WebView) LayoutInflater.from(context).inflate(R.layout.web_view, null);
    }

    @NonNull
    @Override
    public WebView getLayout() {
        return webView;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return webView;
    }
}
