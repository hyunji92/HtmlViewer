package sample.info.htmlviewer; // 41 Post - Created by DimasTheDriver on Feb/21/2012 . Part of the 'Android: creating a WebView dialog' post. Available at: http://www.41post.com/?p=4673

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
	

    /** Called when the activity is first created. */
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
        webView.getSettings().setUserAgentString("AndroidWebView");
        webView.clearCache(true);
        webView.loadUrl("file://sdcard/html/updates.html");
    }
}