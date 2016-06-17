package com.unipad.brain.personal.dao;

import android.text.TextUtils;

import com.unipad.AppContext;
import com.unipad.ICoreService;
import com.unipad.AuthEntity;
import com.unipad.UserDetailEntity;
import com.unipad.common.MobileInfo;
import com.unipad.http.HitopApplyed;
import com.unipad.http.HitopAuth;
import com.unipad.http.HitopAuthInfo;
import com.unipad.http.HitopAuthUploadFile;
import com.unipad.http.HitopLogin;

import com.unipad.http.HitopMatchStart;
import com.unipad.http.HitopUserInfoUpdate;


import com.unipad.observer.GlobleObserService;
import com.unipad.utils.MD5Utils;

import java.io.File;
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
        hitopAuth.buildRequestParams("user_onphoto",authBean.getIdFrontUrl());
        hitopAuth.buildRequestParams("user_idephoto", authBean.getIdReverseUrl());
        hitopAuth.buildRequestParams("sex",authBean.getSex());
        hitopAuth.buildRequestParams("user_gradcert",authBean.getRating_certificate1() + (authBean.getRating_certificate2() == ""? "" : ","+authBean.getRating_certificate2()));
        hitopAuth.setSevice(this);
        hitopAuth.post();
    }

    /**
     * 上传实名认证所需图片
     * @param path   本地文件地址
     */
    public void uploadAuthFile(String path){
        HitopAuthUploadFile hitopAuthUploadFile = new HitopAuthUploadFile();
        hitopAuthUploadFile.setSevice(this);
        Map<String,Object> mapFile = new HashMap<String,Object>();
        mapFile.put("image", new File(path));
        hitopAuthUploadFile.UpLoadFile(mapFile);
    }

    /**
     * @描述： 得到实名认证信息
     * @param userId
     */
    public void getAuthInfo(String userId){
        HitopAuthInfo hitopAuthInfo = new HitopAuthInfo();
        hitopAuthInfo.buildRequestParams("user_id",userId);
        hitopAuthInfo.setSevice(this);
        hitopAuthInfo.post();
    }

    /**
     * @描述： 保存用户基本信息
     * @param userDetailEntity  用户基本信息
     */
    public void saveUserInfo(UserDetailEntity userDetailEntity){
        if(userDetailEntity == null)
            return;
        HitopUserInfoUpdate userInfoUpdate = new HitopUserInfoUpdate();
        userInfoUpdate.buildRequestParams("id", AppContext.instance().loginUser.getUserId());
        if(!TextUtils.isEmpty(userDetailEntity.getPhoto()))
            userInfoUpdate.buildRequestParams("photo", userDetailEntity.getPhoto());
        if(!TextUtils.isEmpty(userDetailEntity.getUserName()))
            userInfoUpdate.buildRequestParams("name", userDetailEntity.getUserName());
        if(!TextUtils.isEmpty(userDetailEntity.getAddr()))
            userInfoUpdate.buildRequestParams("address",userDetailEntity.getAddr());
        if(!TextUtils.isEmpty(userDetailEntity.getSchool()))
            userInfoUpdate.buildRequestParams("scho", userDetailEntity.getSchool());
        if(!TextUtils.isEmpty(userDetailEntity.getCountry()))
            userInfoUpdate.buildRequestParams("country", userDetailEntity.getCountry());
        if(!TextUtils.isEmpty(userDetailEntity.getTel()))
            userInfoUpdate.buildRequestParams("phone", userDetailEntity.getTel());
        if(!TextUtils.isEmpty(userDetailEntity.getMail()))
            userInfoUpdate.buildRequestParams("mail",userDetailEntity.getMail());
        userInfoUpdate.setSevice(this);
        userInfoUpdate.post();
    }

    /**
     * 获取
     * @param uid
     */
    public void getApplyList(String uid){
        HitopApplyed hitopApplyed = new HitopApplyed();
        hitopApplyed.buildRequestParams("userId",uid);
        hitopApplyed.setSevice(this);
        hitopApplyed.post();
    }

    /**
     * 检查是否可以进入比赛
     * @param matchId
     */
    public void checkMatchStart(String matchId){
        HitopMatchStart hitopMatchStart = new HitopMatchStart();
        hitopMatchStart.buildRequestParams("matchId",matchId);
        hitopMatchStart.setSevice(this);
        hitopMatchStart.post();
    }
}
