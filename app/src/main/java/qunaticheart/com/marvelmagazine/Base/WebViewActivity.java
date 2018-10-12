package qunaticheart.com.marvelmagazine.Base;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    //
    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFirstContainerView(R.layout.activity_webview);

        initVars();
        initActions();
        dontShowSnackbarConnection();

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
    private void loadWebViewLoad(final WebView webview, String url) {

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

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

//                if (webView.canGoBack()) {
//                    webView.goBack();
//                }

                //in process 07/10/18

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                alertDialog.show();
                super.onReceivedError(view, request, error);

            }
        });

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                GlideUtil.initGlide(activity, icon, imgFavicon);
            }

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
        labelLoading.setText(getResources().getString(R.string.label_loading));
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
    // @Connections
    //
    //==============================================================================================

    @Override
    public void ConnectionOK() {
        super.ConnectionOK();
        if (loadingLayout.getVisibility() == View.VISIBLE) {
            labelLoading.setText(getResources().getString(R.string.label_loading));
        }
    }

    @Override
    public void ConnectionFail() {
        super.ConnectionFail();
        if (loadingLayout.getVisibility() == View.VISIBLE) {
            labelLoading.setText(getResources().getString(R.string.msg_no_connection));
        }
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
            finish();
        }
    }
}
