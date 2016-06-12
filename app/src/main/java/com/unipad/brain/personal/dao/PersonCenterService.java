package com.unipad.brain.personal.dao;

import com.unipad.ICoreService;
import com.unipad.AuthEntity;
import com.unipad.common.MobileInfo;
import com.unipad.http.HitopAuth;
import com.unipad.http.HitopLogin;
import com.unipad.observer.GlobleObserService;
import com.unipad.utils.MD5Utils;

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
    }

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }

    /*
        实名认证
     */
    public void userAuth(AuthEntity authBean){
        HitopAuth hitopAuth = new HitopAuth();
        hitopAuth.buildRequestParams("user_id",authBean.getId());
        hitopAuth.buildRequestParams("user_category",authBean.getType());
        hitopAuth.buildRequestParams("user_reaName",authBean.getName());
        hitopAuth.buildRequestParams("user_identity",authBean.getIdentity());
        hitopAuth.buildRequestParams("user_born",authBean.getBirthDate());
        hitopAuth.buildRequestParams("user_idephoto",authBean.getIdFrontUrl()+ (authBean.getIdReverseUrl() == ""? "" : ","+authBean.getIdReverseUrl()));
        hitopAuth.buildRequestParams("user_gradCert",authBean.getRating_certificate1() + (authBean.getRating_certificate2() == ""? "" : ","+authBean.getRating_certificate2()));
        hitopAuth.setSevice(this);
        hitopAuth.post();
    }
}
