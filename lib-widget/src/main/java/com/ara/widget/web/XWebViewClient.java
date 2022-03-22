package com.ara.widget.web;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.ara.common.util.LoggerUtils;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

/**
 * Created by XieXin on 2018/12/18.
 * 自定义WebViewClient
 */

public class XWebViewClient extends BridgeWebViewClient {
    private OnWebViewListener listener;
    private XWebView webView;

    public XWebViewClient(OnWebViewListener listener, XWebView webView) {
        super(webView);
        this.listener = listener;
        this.webView = webView;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        listener.onPageStarted(view, url);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        listener.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //Android6.0以下判断断网和链接超时
        listener.onReceivedError();
        LoggerUtils.e("Android6.0以下判断断网和链接超时");
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        //Android6.0以上判断断网和链接超时
        listener.onReceivedError();
        LoggerUtils.e("Android6.0以上判断断网和链接超时");
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }


    public interface OnWebViewListener {
        void onPageStarted(WebView view, String url);

        void onReceivedError();

        void onPageFinished(WebView view, String url);
    }
}
