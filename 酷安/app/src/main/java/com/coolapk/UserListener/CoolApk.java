package com.coolapk.UserListener;
import android.content.Context;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import android.content.Intent;
import android.app.Activity;
import android.widget.Toast;

public class CoolApk
{
	static CoolApkCellBack stcaab;
	public static boolean CheckLogin(Context in) throws IOException{
		return isLogin(in);
	}
	public static void logout(Context in){
		UserSave.logout(in);
	}
	public static void startLoginActivity(Activity act,CoolApkCellBack cacb) {
        Intent pass=new Intent(act, CKLA.class);
		stcaab=cacb;
		act.startActivity(pass);
	}
	private static boolean isLogin(Context v) throws IOException  {
			return analyze(get(v));
    }
	public static String get(Context v) throws IOException{
		Connection connection = Jsoup.connect("http://developer.coolapk.com");
		connection.cookies(new UserSave(v).buildWebRequestCookie());
		return connection.execute().body();
		}
    private static boolean analyze(String in){
		if(in.contains("退出"))
			return true;
		else
			return false;
	}
	
}
