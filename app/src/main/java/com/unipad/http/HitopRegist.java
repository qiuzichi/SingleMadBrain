package com.unipad.http;

import android.util.Log;

import com.unipad.UserDetailEntity;

import java.util.List;

/**
 * Created by gongkan on 2016/5/30.
 */
public class HitopRegist extends HitopRequest<List<UserDetailEntity>>{

    @Override
    public String buildRequestURL() {
        path = HttpConstant.Regist;
        return getHost()+path;
    }

    @Override
    public List<UserDetailEntity> handleJsonData(String json) {
        Log.e("", ""+json);
        return null;
    }
    public void buildRequestParams(String key,String value){
        mParams.addQueryStringParameter(key, value);
    }
    @Override
    public void buildRequestParams() {

    }
}
