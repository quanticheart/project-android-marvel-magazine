package qunaticheart.com.marvelmagazine.Base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.GlideUtil;

public class WebViewActivity extends BaseActivity {

    //key for url
    public static String webviewKey = "webviewKey";

    //init
    private WebView webView;

    //Load Layout
    private CoordinatorLayout loadingLayout;
    private TextView labelLoading;
    private ImageView imgFavicon;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initVars();
        initActions();

    }

    private void initVars() {
        //
        webView = findViewById(R.id.webview);
        //
        loadingLayout = findViewById(R.id.loading);
        labelLoading = findViewById(R.id.label_loading);
        imgFavicon = findViewById(R.id.label_favicon);
        progressBar = findViewById(R.id.progress);
    }

    private void initActions() {
        loadWebViewLoad(webView, getIntent().getStringExtra(webviewKey));
    }

    //==============================================================================================
    //
    // Webview init
    //
    //==============================================================================================

    @SuppressLint("SetJavaScriptEnabled")
    private void loadWebViewLoad(WebView webview, String url) {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading(url, favicon);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showWebview();
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                setProgressBar(newProgress);
            }
        });
        webview.loadUrl(url);
        Log.w("URL", url);
    }

    //==============================================================================================
    //
    // Utils
    //
    //==============================================================================================

    private void showLoading(String url, Bitmap favicon) {
        loadingLayout.setVisibility(View.VISIBLE);
        GlideUtil.initGlide(activity, favicon, imgFavicon);
        webView.setVisibility(View.GONE);
    }

    private void showWebview() {
        loadingLayout.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        //
        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(0);
    }

    private void setProgressBar(int newProgress) {
        progressBar.setProgress(newProgress);
        progressBar.setSecondaryProgress(newProgress + 10);
    }

    //==============================================================================================
    //
    // @Override
    //
    //==============================================================================================

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
