package com.unipad.http;

import com.unipad.UserDetailEntity;

/**
 * Created by gongkan on 2016/5/31.
 */
public class HitopLogin extends HitopRequest<UserDetailEntity>{
    public HitopLogin(String path) {
        super(path);
    }
    public HitopLogin() {
        super(HttpConstant.LOGIN);
    }
    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public UserDetailEntity handleJsonData(String json) {
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
