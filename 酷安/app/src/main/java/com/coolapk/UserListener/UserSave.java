package com.coolapk.UserListener;
import java.util.Map;
import java.util.HashMap;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class UserSave {
    private String mToken;
    private String mUserName;
    private String mUID;
    private String mSESSID;
    private Context mcon;
    public String getUID () {
        return mUID;
    }

    public void updateToSave () {
        SharedPreferences.Editor editor = mcon.getSharedPreferences("SpUtils", Context.MODE_PRIVATE).edit();
        editor.putString("LOGIN-TOKEN", mToken);
        editor.putString("LOGIN-USERNAME", mUserName);
        editor.putString("LOGIN-UID", mUID);
        editor.putString("LOGIN-SESSID", mSESSID);
        editor.apply();
    }

    public UserSave (Context in) {
		mcon=in;
        SharedPreferences preferences = mcon.getSharedPreferences("SpUtils", Context.MODE_PRIVATE);
        mToken = preferences.getString("LOGIN-TOKEN", null);
        mUserName = preferences.getString("LOGIN-USERNAME", null);
        mUID = preferences.getString("LOGIN-UID", null);
        mSESSID = preferences.getString("LOGIN-SESSID", null);
    }

    public UserSave (Context m,String webCookieString) {
		if(m!=null)
			mcon=m;
        String [] str = webCookieString.split("; ");
        for (String s : str) {
            String [] str1 = s.split("=");
            switch (str1[0]) {
                case "SESSID" :
                    mSESSID = str1[1];
                    break;
                case "uid" :
                    mUID = str1[1];
                    break;
                case "username" :
                    mUserName = str1[1];
                    break;
                case "token" :
                    mToken = str1[1];
                    break;
            }
        }
    }

    public boolean isLogin () {
        if (mUID == null || mUID.isEmpty())
            return false;
        if (mUserName == null || mUserName.isEmpty())
            return false;
        if (mToken == null || mToken.isEmpty())
            return false;
        return true;
    }

    public Map<String, String> buildWebRequestCookie () {
        Map<String, String> map = new HashMap<>();
        if (!isLogin())
            return map;
        map.put("SESSID", mSESSID);
        map.put("uid", mUID);
        map.put("username", mUserName);
        map.put("token", mToken);
        return map;
    }

    public static void logout (Context context) {
        UserSave userSave = new UserSave(context);
        userSave.mToken = "";
        userSave.mUserName = "";
        userSave.mSESSID = "";
        userSave.mUID = "";
        userSave.updateToSave();
        clearCookies(context.getApplicationContext());
    }

	@SuppressWarnings("deprecation")
    private static void clearCookies(Context context)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
            CookieSyncManager cookieSyncMngr= CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager= CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }
}

