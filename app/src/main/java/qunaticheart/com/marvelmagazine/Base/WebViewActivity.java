package qunaticheart.com.marvelmagazine.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import qunaticheart.com.marvelmagazine.R;

public class WebViewActivity extends AppCompatActivity {

    public static String webviewKey = "webviewKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webview);
        loadWebViewLoad(webView , getIntent().getStringExtra(webviewKey));

    }

    private void loadWebViewLoad(WebView webview, String url) {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl(url);
        Log.w("URL", url);
    }

}
