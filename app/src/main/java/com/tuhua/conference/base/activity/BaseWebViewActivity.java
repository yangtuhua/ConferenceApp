package com.tuhua.conference.base.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.facebook.stetho.common.LogUtil;
import com.tuhua.conference.R;
import com.tuhua.conference.http.CookieHolder;

import java.io.Serializable;

import butterknife.BindView;
import okhttp3.Cookie;


/***
 * 注:使用webView,在继承此类时,需要实现 {@link BaseWebViewActivity#getLayout()}方法,
 *
 * 在编写此布局时,请将 {@link Toolbar}组件以及 {@link WebView} {@link ProgressBar}组件的id命名为
 *
 * 此类绑定控件一样的名称,避免出现 {@link NullPointerException}
 *
 */
public abstract class BaseWebViewActivity extends BaseActivity {

    @BindView(R.id.wv_webView)
    protected WebView mWebView;

    @BindView(R.id.pb_progressBar)
    protected ProgressBar progressBar;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    private PopupWindow popupWindow;

    private static final int REQUEST_FILE_PICKER = 1;
    private ValueCallback<Uri> mFilePathCallback4;
    private ValueCallback<Uri[]> mFilePathCallback5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWebViewWithDefaultConfig();

        initEventAndData();

        //如果使用者需要重新定义webView的一些属性和参数时,可以在方法中实现
        resetWebViewConfig(mWebView, progressBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    /***重置webView为用户定义的参数,可以重写此方法 fixme 未经测试*/
    protected abstract void resetWebViewConfig(WebView webView, ProgressBar progressBar);

    /****默认参数初始化webView*/
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
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
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAppCacheEnabled(true);
        //支持加载http的内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //TODO 解决cookie同步的问题
        CookieSyncManager.createInstance(BaseWebViewActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        for (Cookie cookie : CookieHolder.getCookies()) {
            String cookieString = cookie.name() + "=" + cookie.value() + ";domain=" + cookie.domain();
            cookieManager.setCookie(cookie.domain(), cookieString);
            CookieSyncManager.getInstance().sync();
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar != null) progressBar.setProgress(newProgress);
            }

            public void openFileChooser(ValueCallback<Uri> filePathCallback) {
                mFilePathCallback4 = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
            }

            public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType) {
                mFilePathCallback4 = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
            }

            public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
                mFilePathCallback4 = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
            }

            //FIXME 重写此方法主要是解决webview无法调用选择图片的问题
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mFilePathCallback5 = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
                return true;
            }
        });

        mWebView.addJavascriptInterface(new JavaProxy(), "java_proxy");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
                if (mWebView != null) {
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            onLoadUrl(mWebView, view.getUrl());
                        }
                    });
                }
                return super.shouldInterceptRequest(view, request);
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

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtil.e("拦截错误:" + failingUrl);
                showErrorPage();
            }
        });
    }

    /****FIXME 解决webview无法调起选择文件的问题*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_FILE_PICKER) {
            if (mFilePathCallback4 != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
                if (result != null) {
                    mFilePathCallback4.onReceiveValue(result);
                } else {
                    mFilePathCallback4.onReceiveValue(null);
                }
            }
            if (mFilePathCallback5 != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
                if (result != null) {
//                    String path = MediaUtility.getPath(this, result);
//                    Uri uri = Uri.fromFile(new File(path));
                    mFilePathCallback5.onReceiveValue(new Uri[]{result});
                } else {
                    mFilePathCallback5.onReceiveValue(null);
                }
            }
            mFilePathCallback4 = null;
            mFilePathCallback5 = null;
        }
    }

    /***显示错误页面*/
    private void showErrorPage() {
        mWebView.loadUrl("file:///android_asset/webViewErrorPage.html");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /***js交互对象自*/
    private class JavaProxy implements Serializable {
        // 注意这里的注解。出于安全的考虑，4.2 之后强制要求，不然无法从 Javascript
        // 中发起调用
        //TODO 公用的js交互方法可以放此处
    }

    @Override
    protected void initEventAndSimpleData() {
        //do nothing
    }

    /***初始化操作*/
    protected abstract void initEventAndData();

    /***将加载的url传到实现类*/
    protected abstract void onLoadUrl(WebView webView, String loadedUrl);

    /***页面加载完成*/
    protected void onPageFinish() {
    }
}
