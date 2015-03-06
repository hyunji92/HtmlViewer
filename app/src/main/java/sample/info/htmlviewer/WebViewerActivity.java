package sample.info.htmlviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
public class WebViewerActivity extends Activity implements OnClickListener {
    private Dialog webViewDialog;
    private WebView mWebView;
    private TextView mTextView;
    private EditText mEditText;

    private Button mBtnClose;
    private Button mBtnTextOnly;
    private Button mBtnImageOnly;
    private Button mBtnImageFull;
    private Button mBtnHorizontal;
    private Button mBtnVertical;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTextView = (TextView) findViewById(R.id.web_textview);
        mEditText = (EditText) findViewById(R.id.web_edittext);
        mBtnTextOnly = (Button) findViewById(R.id.textonly_btn);
        mBtnImageOnly = (Button) findViewById(R.id.imageonly_btn);
        mBtnImageFull = (Button) findViewById(R.id.imagefull_btn);
        mBtnHorizontal = (Button) findViewById(R.id.horizontal_btn);
        mBtnVertical = (Button) findViewById(R.id.vertical_btn);
        mBtnTextOnly.setOnClickListener(this);
        mBtnImageOnly.setOnClickListener(this);
        mBtnImageFull.setOnClickListener(this);
        mBtnHorizontal.setOnClickListener(this);
        mBtnVertical.setOnClickListener(this);


        webViewDialog = new Dialog(this);
        webViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        webViewDialog.setContentView(R.layout.webviewdialog);
        webViewDialog.setCancelable(true);

        mBtnClose = (Button) webViewDialog.findViewById(R.id.bt_close);
        mBtnClose.setOnClickListener(this);

        // ** 테스트 사이트를 웹뷰 다이얼로그에 로딩한다.
        mWebView = (WebView) webViewDialog.findViewById(R.id.wb_webview);

        //URL 링크
        //browser.loadUrl("http://m.naver.com");


        //HTML 직접 생성
        /*
        String msg ="<html><body>Hellow Android World!!!</body></html>";
		//baseURL, data, mimeType(문서타입), encoding, historyUrl
		browser.loadDataWithBaseURL(null, msg, "text/html", "UTF-8", null);*/


    }

    public void webViewSetting() {
        //mWebView 스크립트 허용
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(false);//must be true
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");

        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        //브라우저에 등록
        mWebView.setWebChromeClient(myWebChromeClient);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_close:
                webViewDialog.dismiss();
                break;
            case R.id.textonly_btn:
                webViewDialog.show();
                //assets의 HTML파일 이용
                //mWebView.loadUrl("file:///android_asset/www/test.html");
                webViewSetting();
                mWebView.loadDataWithBaseURL("file:///android_asset/www/",
                        "<meta http-equiv=’Content-Type’ content=’text/html; charset=utf-8′ /><html><body> <img src='file:///android_asset/test1.png'/> Hello, TESTㅆㅅ" +
                                "ㅅㅁㅅㅇㅅㅇㅅㄴㅇㅅㄴㅇㅅㄴㅇㅅㄴㅇㅅㄴㅇㅅㄴㅇㅅㄴㅇㅅzfd" +
                                "아어리나아어리아어자다아어 zDFgafgafgadfgafdgafgasdfgafgafdgafgafdgafgaf" +
                                "아어리나어리나어린아ㅓㄹ미ㅏ얼미ㅏ어미ㅏ얼;미ㅏ얼;미ㅏ얼미ㅏ얾;ㅣㅏ얾;나이러gafgafdgadfgafgafdgafgadfgadfgartqrtqrtq!</body></html>",
                        "text/html",
                        "utf-8",
                        "file:///android_asset/www/");
                break;
            case R.id.imageonly_btn:
                //mWebView.loadDataWithBaseURL("file:///android_asset/loadtest.html",
                //        "file:///android_asset/loadtest.html",
                //        "text/html", "utf-8", "file:///android_asset/");
                mWebView.loadUrl("file:///android_asset/www/loadtest.html");
                webViewDialog.show();
                break;
            case R.id.imagefull_btn:
                String data = "<meta http-equiv=’Content-Type’ content=’text/html; charset=utf-8′ /><html><body><img src='file:///android_asset/test1.png'/></body></html>";
                mWebView.loadData(data, "text/html", "UTF-8");
                // mWebView.loadUrl("file:///android_asset/www/ic_launcher.png");
                webViewDialog.show();
                break;
            case R.id.horizontal_btn:
                webViewDialog.show();
                webViewSetting();
                String tt = "dkjahdlkjfhakewhflakehfakehflakjehflakjehfdka그럼 한글로하면 개행이 된다는 이 이상한거는 무엇일까 망얼마ㅓㄹ먇ㄹ모며ㅗㄹ미ㅏㅕ도리맞돌미ㅏㅕ돎자ㅕ로미ㅑㅕㄷ뢰먀젿로미ㅑ젿로미ㅑ젿로미ㅑㅕㄷ뢰먀젿룀";
                // 프로젝트내 Asset 폴더내에 포함되어 있는 index.html 읽어오기
                //mWebView.loadUrl("file:///android_asset/www/test.html");
                // 동적으로 만든 데이터 읽어오기
                String html = "<html><body>" + tt + "</body></html> ";
                mWebView.loadData(html, "text/html:UTF-8", "utf-8");
                break;
            case R.id.vertical_btn:
                break;


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

                return true;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            return true;
        }
    }

    //자바 스크립트의 alert 대체 코드 작성
    //자바 스크립트의 alert 경고창을 대체하는 안드로이드  경고창 구현
    private class MyWebChromeClient extends WebChromeClient {

        /**
         * @Override public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
         * //message	: '경고창'
         * new AlertDialog.Builder(WebViewDialogActivity.this).setTitle("경고")
         * .setCancelable(false)
         * .setNeutralButton("확인", new DialogInterface.OnClickListener() {
         * @Override public void onClick(DialogInterface dialog, int which) {
         * <p/>
         * }
         * }).show();
         * <p/>
         * result.confirm();
         * <p/>
         * return true;
         * }*
         */

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