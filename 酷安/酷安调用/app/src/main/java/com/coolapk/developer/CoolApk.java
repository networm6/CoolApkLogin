package com.coolapk.developer;
import android.content.Context;
import android.content.Intent;
import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.Connection;


public class CoolApk
{
	static CoolApkCellBack stcaab;
	public static boolean CheckLogin(Context in) throws IOException{
		return isLogin(in);
	}
	public static void logout(Context in){
		UserSave.logout(in);
	}
	public static void startLoginActivity(Context act,CoolApkCellBack cacb) {
        Intent pass=new Intent(act, CKLA.class);
		stcaab=cacb;
		act.startActivity(pass);
	}
	private static boolean isLogin(Context v) throws IOException  {
		Connection connection = Jsoup.connect("http://developer.coolapk.com");
		connection.cookies(new UserSave(v).buildWebRequestCookie());
		return analyze(connection.execute().body());
    }
    private static boolean analyze(String in){
		if(in.contains("退出"))
			return true;
		else
			return false;
	}
	
}
