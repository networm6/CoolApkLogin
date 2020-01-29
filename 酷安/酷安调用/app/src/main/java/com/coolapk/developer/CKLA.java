package com.coolapk.developer;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.webkit.CookieManager;

public class CKLA extends Activity
{
   private WebView mWebView;
   private CoolApkCellBack cacb;
   private boolean ispass=false;
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
								ispass=true;
								finish();
								CoolApk.stcaab=null;
							}
						}
					}
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
		if(!ispass)
		cacb.failure(this);
	}
	
}
