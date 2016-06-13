package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.UserDetailEntity;
import com.unipad.observer.GlobleObserService;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by gongkan on 2016/5/31.
 */
public class HitopLogin extends HitopRequest<UserDetailEntity> {

    private GlobleObserService sevice;

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

    /**
     * @param json
     * @return
     */
    @Override
    public UserDetailEntity handleJsonData(String json) {
        UserDetailEntity user = null;
        JSONObject jsObj = null;
        String response = null;
        try {
            response = new String(json.getBytes(), "utf-8");
            jsObj = new JSONObject(response);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code")==0) {
                    JSONObject dataJson = new JSONObject(jsObj.getString("data"));
                    if (dataJson != null) {
                        user = new UserDetailEntity();
                        user.setUserName(dataJson.getString("name"));
                        user.setUserId(dataJson.getString("id"));
                        user.setBirthday(dataJson.getString("born"));
                        user.setTel(dataJson.getString("phone"));
                        user.setCountry(dataJson.getString("country"));
                        user.setAuth(dataJson.getInt("auth"));
                        user.setSex(dataJson.getInt("sex") == 0 ? "男" : "女");
                        if (sevice != null) {
                            sevice.noticeDataChange(HttpConstant.LOGIN_UPDATE_UI, user);
                        }
                    }

                }
                if (sevice != null) {
                    sevice.noticeDataChange(HttpConstant.LOGIN_WRONG_MSG, jsObj.getString("ret_msg"));
                }

            }
        } catch (Exception e) {
            if (sevice != null) {
                sevice.noticeDataChange(HttpConstant.LOGIN_WRONG_MSG, "Json 数据格式不对");
            }
            e.printStackTrace();
        }

        return user;
    }


    @Override
    public void buildRequestParams() {

    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }

}
