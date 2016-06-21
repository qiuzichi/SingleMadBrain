package com.unipad.common;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.unipad.brain.App;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by gongkan on 2016/5/31.
 */
public class MobileInfo {


    public static String getDeviceName(){
        String deviceName = android.os.Build.MODEL;
        /**Can not delete the follow code.URl can not contain blank.
         * for example ,"HUAWEI P6-U06 can be transformed to "HUAWEI+P6-U06"
         * */
        try {
            deviceName = URLEncoder.encode(deviceName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return deviceName;
    }
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) App.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        if (deviceid == null || deviceid.length() == 0) {
            WifiManager manager = (WifiManager) App.getContext().getSystemService(Context.WIFI_SERVICE);
            if (manager != null) {
                deviceid = manager.getConnectionInfo().getMacAddress();
            }
        }
        return deviceid;
    }
}
