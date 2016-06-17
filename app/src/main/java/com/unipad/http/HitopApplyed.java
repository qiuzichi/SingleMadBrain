package com.unipad.http;

import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.observer.GlobleObserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongjiebin on 2016/6/17.
 */
public class HitopApplyed extends  HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopApplyed(String path) {
        super(path);
    }

    public HitopApplyed(){
        super(HttpConstant.USER_APPLYED_HTTP);
    }

    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public Object handleJsonData(String json) {
        // 处理返回结果
        List<CompetitionBean> competitionBeans = new ArrayList<CompetitionBean>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int ret_code = jsonObject.optInt("ret_code");
            if(ret_code == 0){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                int length = jsonArray.length();
                CompetitionBean competitionBean = null;
                for(int i = 0; i < length; i ++) {
                    JSONObject objCom = jsonArray.optJSONObject(i);
                    competitionBean = new CompetitionBean();
                    competitionBean.setAddr(objCom.optString("city"));
                    competitionBean.setName(objCom.optString("name"));
                    competitionBean.setCost(objCom.optString("applyMonery"));
                    competitionBean.setApplyState(objCom.optInt("status"));
                }
                this.sevice.noticeDataChange(HttpConstant.USER_APPLYED,json);
            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }
}
