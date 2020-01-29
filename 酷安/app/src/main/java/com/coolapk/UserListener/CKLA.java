package com.coolapk.UserListener;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.view.View;
import android.view.KeyEvent;

public class CKLA extends Activity
{
   private WebView mWebView;
   private CoolApkCellBack cacb;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 mWebView=new WebView(this);
		setContentView(mWebView);
		initwebview();
		cacb=CoolApk.stcaab;
		cacb.CKLAoncreate(this);
	}
	private void initwebview(){
		mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.clearCache(true);
        mWebView.loadUrl("https://account.coolapk.com/auth/login?forward=http%3A%2F%2Fdeveloper.coolapk.com");
     
		mWebView.setWebViewClient(new WebViewClient(){
				@Override
				public void onPageFinished(WebView view, String url) 
				{
				    String jsStr="document.getElementsByClassName(\"weui-flex\")[0].style.display = \"none\";void(0);";
					mWebView.loadUrl("javascript:" + jsStr );
					String Right="Copyright © 2010-2019 酷安网, All Rights Reserved.";
					String jsStr2="document.getElementsByClassName(\"weui-loadmore__tips\")[0].innerText = \""+Right+"\";void(0);";
					mWebView.loadUrl("javascript:" + jsStr2 );
					String jsStr3="document.getElementsByClassName(\"weui-footer__link\")[0].style.display = \"none\";void(0);";
					mWebView.loadUrl("javascript:" + jsStr3 );
					super.onPageFinished(view,url);
					}
				
			@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
					if (url.contains("developer.coolapk.com")) {
						String cookies = CookieManager.getInstance().getCookie(url);
						if (cookies != null) {
							UserSave userSave = new UserSave(CKLA.this,cookies);
							if (userSave.isLogin()) {
								userSave.updateToSave();
								mWebView.stopLoading();
	        					cacb.success(CKLA.this);
								finish();
								CoolApk.stcaab=null;
							}
						}
					}
				}
			});
			
	
		mWebView.setOnKeyListener(new View.OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						//按返回键操作并且能回退网页      
						if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
							//后退
							mWebView.goBack();
							return true;
						}
					}
					return false;
				}
			});
			
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if(mWebView != null){
		    mWebView.destroy();
			mWebView = null;
			}
	}
	
}
