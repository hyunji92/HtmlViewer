package sample.info.htmlviewer;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class WebViewDialogActivity extends Activity 
{
	private Dialog webViewDialog;
	private WebView webView;
	
	private Button btLaunchWVD;
	private Button btClose;
	

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btLaunchWVD = (Button) findViewById(R.id.bt_launchwvd);
        
        btLaunchWVD.setOnClickListener(new OnClickListener()
        {
			@Override

			public void onClick(View v) 
			{
				webViewDialog.show();
			}
		});
        
        webViewDialog = new Dialog(this);
        webViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        webViewDialog.setContentView(R.layout.webviewdialog);
        webViewDialog.setCancelable(true);
        
        btClose = (Button) webViewDialog.findViewById(R.id.bt_close);
        btClose.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				//Dismiss the dialog
				webViewDialog.dismiss();
			}
		});
        
        webView = (WebView) webViewDialog.findViewById(R.id.wb_webview);
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setUserAgentString("AndroidWebView");
        webView.clearCache(true);
        //webView.loadUrl("file://sdcard/html/updates.html");


        //case1
        webView.loadUrl("http://m.naver.com");
        //case2
        //webView.loadData("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><html><body>Hello, 마이크!</body></html>", "text/html", "utf-8");
        //case3
        //browser.loadUrl("file:///android_asset/test.htm");


    }
}