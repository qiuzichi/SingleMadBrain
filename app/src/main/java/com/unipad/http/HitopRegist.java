package com.unipad.http;

import android.util.Log;

import com.unipad.UserDetailEntity;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by gongkan on 2016/5/30.
 */
public class HitopRegist extends HitopRequest<Object>{

    public HitopRegist(String path) {
        super(path);
    }
    public HitopRegist() {
        super(HttpConstant.Regist);
    }
    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public Object handleJsonData(String json) {
        Log.e("", ""+json);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
