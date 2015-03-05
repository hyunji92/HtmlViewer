package sample.info.htmlviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class WebViewerActivity extends Activity {
    private Dialog webViewDialog;
    private WebView mWebView;
    private TextView mTextView;
    private EditText mEditText;
    private WebView mLocalWebView;

    private Button mBtLaunchWVD;
    private Button mBtLocalHtml;
    private Button mBtClose;

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
                webViewDialog.dismiss();
            }
        });

        // ** 테스트 사이트를 웹뷰 다이얼로그에 로딩한다.
        mWebView = (WebView) webViewDialog.findViewById(R.id.wb_webview);

        //URL 링크
        //browser.loadUrl("http://m.naver.com");


        //HTML 직접 생성
        /*
		String msg ="<html><body>Hellow Android World!!!</body></html>";
		//baseURL, data, mimeType(문서타입), encoding, historyUrl
		browser.loadDataWithBaseURL(null, msg, "text/html", "UTF-8", null);*/


        //assets의 HTML파일 이용
        mWebView.loadUrl("file:///android_asset/test.html");

        //mWebView 스크립트 허용
        mWebView.getSettings().setJavaScriptEnabled(true);


        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        //브라우저에 등록
        mWebView.setWebChromeClient(myWebChromeClient);

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

    //자바 스크립트의 alert 대체 코드 작성
    //자바 스크립트의 alert 경고창을 대체하는 안드로이드  경고창 구현
    private class MyWebChromeClient extends WebChromeClient {

       /** @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            //message	: '경고창'
            new AlertDialog.Builder(WebViewDialogActivity.this).setTitle("경고")
                    .setCancelable(false)
                    .setNeutralButton("확인", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

            result.confirm();

            return true;
        }**/

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            // 모든 Activity는 자체 Progress를 가지고 있다.
            WebViewerActivity.this.setProgress(newProgress * 100); // 값의 차이가 100차이라서 곱함

            //super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {

            // Url 문자열을 가져옴.
            WebView.HitTestResult result = view.getHitTestResult();
            String url = result.getExtra();

            if (Uri.parse(url).getHost().equals("www.example.com")) {
                mTextView.setText("TTTEST CHANGE");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            }

            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }


    }
}