package com.unipad.http;

import android.view.View;

import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.App;
import com.unipad.common.Constant;
import com.unipad.common.widget.HIDDialog;
import com.unipad.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gongkan on 2016/8/4.
 */
public class HitopGetRandomQuestion extends HitopRequest<String> {

    private AbsBaseGameService service;

    public HitopGetRandomQuestion(String projectId) {
        super(HttpConstant.RANDOM_GET_QUESTION);
        mParams.addQueryStringParameter("projectId", projectId);
    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public String handleJsonData(String json) throws JSONException {
        String msg  = "请求失败";
        JSONObject jsObj = null;
        try {
            jsObj = new JSONObject(new String(json.getBytes(), "utf-8"));
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject dataJson = new JSONObject(jsObj.getString("data"));
                    if (dataJson != null) {
                        String data = dataJson.getString("content1");
                        String questionId = dataJson.getString("id");
                        if (service != null) {
                            service.parseData(data);
                            service.downloadResource(questionId);
                            service.noticeDataChange(HttpConstant.GET_RANDOM_QUESTION_OK, questionId);
                            return null;
                        }
                    }
                }else{
                    msg = jsObj.getString("ret_msg");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (service != null){
            service.noticeDataChange(HttpConstant.GET_RANDOM_QUESTION_ERR,msg);
        }

        return null;
    }

    public void setService(AbsBaseGameService service) {
        this.service = service;
    }
}
