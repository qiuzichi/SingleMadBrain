package com.unipad.http;

import com.unipad.ICoreService;
import com.unipad.UserDetailEntity;

import org.json.JSONObject;

/**
 * Created by gongkan on 2016/6/22.
 */
public class HitopGetQuestion extends HitopRequest<String>{

    public HitopGetQuestion(String path) {
        super(path);
    }
    public  HitopGetQuestion(){
        super(HttpConstant.GET_QUESTION);
    }

    private ICoreService.IGameHand service;

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public String handleJsonData(String json) {

        JSONObject jsObj = null;
        try {
            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                //if (jsObj.getInt("ret_code") == 0) {
                    JSONObject dataJson = new JSONObject(jsObj.getString("data"));
                    if (dataJson != null) {
                        String data = dataJson.getString("content1");
                        if (service != null){
                            service.parseData(data);
                        }
                   // }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setService(ICoreService.IGameHand service) {
        this.service = service;
    }
}
