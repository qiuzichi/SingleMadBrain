package com.unipad.brain.personal.dao;

import android.util.Log;

import com.unipad.ICoreService;
import com.unipad.common.MobileInfo;
import com.unipad.http.HitopLogin;
import com.unipad.io.XmlUtil;
import com.unipad.io.bean.Response;
import com.unipad.io.w.SocketThreadManager;
import com.unipad.observer.GlobleObserService;
import com.unipad.utils.MD5Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongkan on 2016/6/6.
 */
public class PersonCenterService extends GlobleObserService implements ICoreService{

    public void loginIn(String userName,String pwd) {
        HitopLogin httpLogin = new HitopLogin();
        httpLogin.buildRequestParams("user_name", userName);
        httpLogin.buildRequestParams("user_password", MD5Utils.MD5_two(pwd));
        httpLogin.buildRequestParams("device_name", MobileInfo.getDeviceName());
        httpLogin.buildRequestParams("device_did", MobileInfo.getDeviceId());
        httpLogin.setSevice(this);
        httpLogin.post();
        SocketThreadManager.sharedInstance();

    }


    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }
}
