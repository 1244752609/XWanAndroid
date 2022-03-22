package com.ara.widget.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.SizeUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;


/**
 * Created by XieXin on 2018/12/10.
 * 自定义WebView
 */
public class XWebView extends BridgeWebView {
    private Context context;
    private ProgressBar mProgressBar;
    private boolean isCache;
    private boolean isShowProgress = true;

    public XWebView(Context context) {
        this(context, null);
    }

    public XWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initWebView(attrs);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(AttributeSet attrs) {
        setShowProgress(true);

        WebSettings mWebSettings = this.getSettings();
        //是否使用缓存
        if (isCache) {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //是否加载JS
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true); //设置网页自适应屏幕大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 屏幕适配
        mWebSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        // 设置支持插件, 主要为支持Flash
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        //支持屏幕缩放
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        mWebSettings.setDisplayZoomControls(false);
        //保证字体正常大小
        mWebSettings.setTextZoom(100);
        setWebChromeClient(new XinWebChromeClient());

        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.i("XinWebView", "url=" + url);
                Log.i("XinWebView", "userAgent=" + userAgent);
                Log.i("XinWebView", "contentDisposition=" + contentDisposition);
                Log.i("XinWebView", "mimetype=" + mimetype);
                Log.i("XinWebView", "contentLength=" + contentLength);

                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }


    public class XinWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i("XinWebView", "webview progress : " + newProgress);
            if (mProgressBar != null) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(GONE);
                } else {
                    if (mProgressBar.getVisibility() == GONE) {
                        mProgressBar.setVisibility(VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    }

    /*** 水平进度条 */
    private void setLevelProgressBar() {
        if (mProgressBar != null) {
            this.removeView(mProgressBar);
        }
        if (isShowProgress) {
            mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    -1, SizeUtils.dp2px(3f));
            mProgressBar.setLayoutParams(params);
            this.addView(mProgressBar);
        }
    }

    /**
     * 是否显示进度条
     *
     * @param isShowProgress
     */
    public void setShowProgress(boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
        setLevelProgressBar();
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    /**
     * 销毁Webview
     */
    public void destroyWebView() {
        stopLoading();//停止加载
        ((ViewGroup) getParent()).removeView(this);//把webview从视图中移除
        removeAllViews();//移除webview上子view
        clearCache(true);//清除缓存
        clearHistory();//清除历史
        destroy();//销毁webview自身
//        Process.killProcess(Process.myPid());//杀死WebView所在的进程
    }
}
