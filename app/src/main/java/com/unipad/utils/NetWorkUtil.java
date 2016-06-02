package com.unipad.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.unipad.brain.App;


public class NetWorkUtil {
    
    public static boolean isWiFiConnected(Context context) {
        return ConnectivityManager.TYPE_WIFI == checkActiveConnect(context);
    }
    
    public static boolean isMobileConnected(Context context) {
        return ConnectivityManager.TYPE_MOBILE == checkActiveConnect(context);
    }
    
    private static int checkActiveConnect(Context context){
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(null != networkInfo){
            return networkInfo.getType();
        }
        return -1;
    }
    
    public static boolean isNetworkAvailable(Context context) {
        if(null == context) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean checkNetwork(){
        if ( !isNetworkAvailable(App.getContext())) {
            ToastUtil.showToast("请检查网络");
            return false;
        }
        return true;
    }
}
