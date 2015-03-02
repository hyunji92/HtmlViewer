package sample.info.htmlviewer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
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
    private WebView mLocalWebView;

    private Button mBtLaunchWVD;
    private Button mBtLocalHtml;
    private Button mBtClose;


    String mStringHead;
    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTextView = (TextView) findViewById(R.id.web_textview);
        mEditText = (EditText) findViewById(R.id.web_edittext);
        mBtLaunchWVD = (Button) findViewById(R.id.bt_launchwvd);

        mBtLaunchWVD.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {
                webViewDialog.show();
                //mWebView.loadUrl("javascript:setMessage('" + mEditText.getText() + "')");
            }
        });

        webViewDialog = new Dialog(this);
        webViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        webViewDialog.setContentView(R.layout.webviewdialog);
        webViewDialog.setCancelable(true);

        mBtClose = (Button) webViewDialog.findViewById(R.id.bt_close);
        mBtClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dismiss the dialog
                webViewDialog.dismiss();
            }
        });

        mWebView = (WebView) webViewDialog.findViewById(R.id.wb_webview);

        WebSettings set = mWebView.getSettings();
        mWebView.setScrollbarFadingEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        //웹뷰가 자바스크립트를 사용할 수 있도록 설정
        set.setJavaScriptEnabled(true);
        set.setCacheMode(WebSettings.LOAD_NO_CACHE); // 웹뷰가 캐시를 사용하지 않도록 설정

        //mWebView.addJavascriptInterface(new JavascriptInterface(), "WebViewDialogActivity");
        // javascript에게 공개할 클래스와 그 클래스의 공개명칭
        //mWebView.addJavascriptInterface(new AndroidBridge(), "WebViewDialogActivity");

        //case1 보통 Url
        //mWebView.loadUrl("http://m.naver.com");
        mWebView.loadUrl("http://www.google.com");

        //case2
        //mWebView.loadData("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><html><body>Hello, 마이크!</body></html>", "text/html", "utf-8");
        //case3
        //mWebView.loadUrl(testUrl);


        mWebView.setWebViewClient(new TestWebViewClient());
        //mWebView.setWebChromeClient(new TestWebViewClient()); // 4.4 kitkat

        // ** 로컬 HTML은 메인액티비티에서 바로 볼수 있도록 따로 처리한다.
        mLocalWebView = (WebView) findViewById(R.id.local_webView);
        mBtLocalHtml = (Button) findViewById(R.id.bt_localfile);
        mBtLocalHtml.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //로컬 html을 불러오기 위한 url
                String testUrl = "file:///android_asset/test.html";
                mLocalWebView.getSettings().setJavaScriptEnabled(true);
                mLocalWebView.loadUrl(testUrl);
                mLocalWebView.setWebViewClient(new LocalWebViewClient());

            }
        });





    }

    /**
     * Object exposed to JavaScript
     */
    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String arg) { // must be final
            handler.post(new Runnable() {
                public void run() {
                    Log.d("WebViewDialogActivity", "setMessage(" + arg + ")");
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

    private class TestWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //현재 처음 띄운 Url과 같지않으면 새로운 브라우저를 띄움니다. (임시 url )
            //if (!Uri.parse(url).getHost().equals("http://www.google.com")) {
            if (!url.startsWith("http://www.google")) {

                //새로운 브라우저가뜰때 메인액티비티의 텍스트를 바꾸는 임시 테스트
                mTextView.setText("TTTEST CHANGE");

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            return true;
        }
    }

    private class LocalWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}