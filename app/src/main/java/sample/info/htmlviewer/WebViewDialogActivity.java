package sample.info.htmlviewer;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WebViewDialogActivity extends Activity {
    private Dialog webViewDialog;
    private WebView mWebView;
    private TextView mTextView;
    private EditText mEditText;

    private Button btLaunchWVD;
    private Button btClose;


    String mStringHead;
    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTextView = (TextView) findViewById(R.id.web_edittext);
        mEditText = (EditText) findViewById(R.id.web_edittext);
        btLaunchWVD = (Button) findViewById(R.id.bt_launchwvd);

        btLaunchWVD.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {
                webViewDialog.show();
                mWebView.loadUrl("javascript:setMessage('"+mEditText.getText()+"')");
            }
        });

        webViewDialog = new Dialog(this);
        webViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        webViewDialog.setContentView(R.layout.webviewdialog);
        webViewDialog.setCancelable(true);

        btClose = (Button) webViewDialog.findViewById(R.id.bt_close);
        btClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dismiss the dialog
                webViewDialog.dismiss();
            }
        });

        mWebView = (WebView) webViewDialog.findViewById(R.id.wb_webview);
        mWebView.setScrollbarFadingEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new AndroidBridge(), "WebViewDialogActivity");

        mWebView.loadUrl("file:///android_asset/test.html");

        mWebView.setWebViewClient(new HelloWebViewClient());
        //webView.getSettings().setUserAgentString("AndroidWebView");
        mWebView.clearCache(true);


        //case1
        //mWebView.loadUrl("http://m.naver.com");
        //case2
        //webView.loadData("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><html><body>Hello, 마이크!</body></html>", "text/html", "utf-8");
        //case3
        //webView.loadUrl("file://sdcard/html/updates.html");
        //browser.loadUrl("file:///android_asset/test.htm");


        // 브라우저안의 링크 클릭시 외부앱 작동이나 메인액티비티에 알림
        


    }

    /** Object exposed to JavaScript */
    private class AndroidBridge {
        public void setMessage(final String arg) { // must be final
            handler.post(new Runnable() {
                public void run() {
                    Log.d("HybridApp", "setMessage(" + arg + ")");
                    mTextView.setText(mStringHead + arg);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}