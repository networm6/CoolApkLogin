package com.coolapk.UserListener;

import android.app.*;
import android.os.*;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;
import java.io.IOException;
import android.webkit.CookieSyncManager;
import android.widget.TextView;
import android.widget.Button;

public  class MainActivity extends Activity 
{
	TextView ac;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		ac=findViewById(R.id.mainTextView1);
    }
	String is="";
	public void isk(View v){
		 
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					try
					{
						is=""+CoolApk.CheckLogin(MainActivity.this);
					runon(is);
						}
					catch (IOException e)
					{
                        is=e.getMessage();
					}
				}
			}).start();
		
	}
	public void co(View v){
		
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					try
					{
						String firstsub,image,name,id;
						String aa=	CoolApk.get(MainActivity.this);
						int start=aa.indexOf("<header class=\"ex-drawer__header\">");
						int end=aa.indexOf("<a class=\"ex-drawer__header-username\"");
					    firstsub=aa.substring(start,end);
						//runon(aa);
						int aystart=firstsub.indexOf("http");
						int syend=firstsub.indexOf(".jpg")+4;
						image=firstsub.substring(aystart,syend);
						int nastart=firstsub.indexOf("username")+10;
						int naend=firstsub.indexOf("</span>");
						name=firstsub.substring(nastart,naend);
						int idstart=aa.indexOf("uid")+5;
						int idend=aa.indexOf("request")-7;
						id=aa.substring(idstart,idend);
						runon("头像"+"\n"+image+"\n"+"名称"+"\n"+name+"\n"+"用户id"+"\n"+id);
						}
					catch (IOException e)
					{
						runon(e.getMessage());
					}

				}
			}).start();
	}
	public void log(View v){
		CoolApk.logout(this);toa("成功");
	}
	public void oo(View v){
		
					
						CoolApk.startLoginActivity(MainActivity.this, new CoolApkCellBack(){

								@Override
								public void success(Activity ac)
								{
									Toast.makeText(ac,"success",10).show();
								}

								@Override
								public void CKLAoncreate(Activity ac)
								{
									Toast.makeText(ac,"oncreate",10).show();
								}
								

							
							});

					}
					
					
				
		
	public void toa(String in)
    {
		Toast.makeText(this,in,10).show();
	}
	
	public void runon(final String im){
		runOnUiThread(new Runnable(){

				@Override
				public void run()
				{
					ac.setText(im);
				}
			});
	}
	
	
	
}
