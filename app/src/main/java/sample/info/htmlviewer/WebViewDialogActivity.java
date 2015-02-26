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
	//Create a dialog object, that will contain the WebView
	private Dialog webViewDialog;
	//a WebView object to display a web page
	private WebView webView;
	
	//The button to launch the WebView dialog
	private Button btLaunchWVD;
	//The button that closes the dialog
	private Button btClose;
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Inflate the btLaunchWVD button from the 'main.xml' layout file
        btLaunchWVD = (Button) findViewById(R.id.bt_launchwvd);
        
        //Set the 35. OnClickListener for the launch button
        btLaunchWVD.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				//Display the WebView dialog
				webViewDialog.show();
			}
		});
        
        //Create a new dialog
        webViewDialog = new Dialog(this);
        //Remove the dialog's title
        webViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Inflate the contents of this dialog with the Views defined at 'webviewdialog.xml'
        webViewDialog.setContentView(R.layout.webviewdialog);
        //With this line, the dialog can be dismissed by pressing the back key
        webViewDialog.setCancelable(true);
        
        //Initialize the Button object with the data from the 'webviewdialog.xml' file 
        btClose = (Button) webViewDialog.findViewById(R.id.bt_close);
        //Define what should happen when the close button is pressed.
        btClose.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				//Dismiss the dialog
				webViewDialog.dismiss();
			}
		});
        
        //Initialize the WebView object with data from the 'webviewdialog.xml' file 
        webView = (WebView) webViewDialog.findViewById(R.id.wb_webview);
        //Scroll bars should not be hidden
        webView.setScrollbarFadingEnabled(false);
        //Disable the horizontal scroll bar
        webView.setHorizontalScrollBarEnabled(false);
        //Enable JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        //Set the user agent
        webView.getSettings().setUserAgentString("AndroidWebView");
        //Clear the cache
        webView.clearCache(true);
        //Make the webview load the specified URL
        webView.loadUrl("file://sdcard/html/updates.html");
    }
}