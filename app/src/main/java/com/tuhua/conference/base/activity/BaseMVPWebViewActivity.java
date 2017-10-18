package com.tuhua.conference.base.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tuhua.conference.R;
import com.tuhua.conference.base.mvp.BasePresenter;

import butterknife.BindView;


/***
 * 注:使用webView,在继承此类时,需要实现 {@link BaseMVPWebViewActivity()}方法,
 *
 * 在编写此布局时,请将 {@link Toolbar}组件以及 {@link WebView} {@link ProgressBar}组件的id命名为
 *
 * 此类绑定控件一样的名称,避免出现 {@link NullPointerException}
 *
 */
public abstract class BaseMVPWebViewActivity<T extends BasePresenter> extends BaseMvpActivity<T> {

    @BindView(R.id.wv_webView)
    protected WebView mWebView;

    @BindView(R.id.pb_progressBar)
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWebViewWithDefaultConfig();

        //如果使用者需要重新定义webView的一些属性和参数时,可以在方法中实现
        resetWebViewConfig(mWebView, progressBar);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

    /***重置webView为用户定义的参数,可以重写此方法 fixme 未经测试*/
    protected abstract void resetWebViewConfig(WebView webView, ProgressBar progressBar);

    /****默认参数初始化webView*/
    private void initWebViewWithDefaultConfig() {
        if (mWebView == null) {
            throw new NullPointerException("请将布局文件中为webView组件id设置为---wv_webView---");
        }
        if (progressBar == null) {
            throw new NullPointerException("请将布局文件中为progressBar组件id设置为---pb_progressBar---");
        }

        //以下为webView默认设置的一些参数,需要重新设置可以在 {@link #resetWebViewConfig()}中重置参数
        mWebView.setVerticalScrollbarOverlay(true);
        WebSettings settings = mWebView.getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);// 设置允许访问文件数据
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setGeolocationEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);//设定缩放控件隐藏
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar != null) progressBar.setProgress(newProgress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        onLoadUrl(view, view.getUrl());
                    }
                });
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                onPageFinish();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            onLoadUrl(view, url);
                        }
                    });
                }
                return true;
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /***初始化操作*/
    protected abstract void initEventAndData();

    /***将加载的url传到实现类*/
    protected abstract void onLoadUrl(WebView webView, String loadedUrl);

    /***页面加载完成*/
    protected void onPageFinish() {
    }
}
