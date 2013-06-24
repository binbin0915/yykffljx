package com.Example;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends Activity{

	private WebView mweb;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		mweb = (WebView) findViewById(R.id.web1);
		mweb.loadUrl("file:///android_asset/index.html");
		
		mweb.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		mweb.getSettings().setJavaScriptEnabled(true);
		mweb.setWebChromeClient(new WebChromeClient(){
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				Builder builder = new Builder(MyWebView.this); 
				builder.setTitle("信息提示");
				builder.setMessage(message); 
				builder.setPositiveButton("确定", new AlertDialog.OnClickListener(){  				
					public void onClick(DialogInterface arg0, int arg1) {
						result.confirm(); 
					}                       
				});  
				builder.setCancelable(false);  
				builder.create();  
				builder.show();  
				return true; 
			}

			public boolean onJsConfirm(WebView view, String url,String message, final JsResult result) {
				Builder builder = new Builder(MyWebView.this); 
				builder.setTitle("信息确认");
				builder.setMessage(message); 
				builder.setPositiveButton("确定", new AlertDialog.OnClickListener(){  				
					public void onClick(DialogInterface arg0, int arg1) {
						result.confirm(); 
					}                       
				});  
				builder.setNeutralButton("取消", new AlertDialog.OnClickListener(){  				
					public void onClick(DialogInterface arg0, int arg1) {
						result.cancel();
					}                       
				});
				builder.setCancelable(false);  
				builder.create();  
				builder.show(); 
				return true;
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mweb.canGoBack()) {
			mweb.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
