package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.singlebrain.personal.bean.CompetitionBean;
import com.unipad.singlebrain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.observer.GlobleObserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzj on 2016/6/30 0030.
 */
public class HitopTopList extends HitopRequest <List<CompetitionBean>>{
private GlobleObserService service;

    public HitopTopList(String path) {
        super(HttpConstant.GAME_TOP);
//        mParams.addBodyParameter("matchId",path);
    }

    public HitopTopList(){
        super(HttpConstant.GAME_TOP);

    }

    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public List<CompetitionBean> handleJsonData(String json) throws JSONException {
        List<CompetitionBean> listTop = null;
        JSONObject jsObj = null;
        String response = null;
        //处理返回结果
        try {
            Log.e("", "" + json);
            response = new String(json.getBytes(), "utf-8");
            jsObj = new JSONObject(response);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
//                    JSONObject data = new JSONObject(jsObj.getString("lists"));
                    JSONArray jsonArray = jsObj.getJSONArray("lists");
                    int iSize = jsonArray.length();
                    if (iSize != 0) {
                        listTop = new ArrayList<>();
                        for (int i = 0; i < iSize; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            CompetitionBean competitionBean = new CompetitionBean();
                            competitionBean.setUserId(jsonObject.optString("userId"));
                            competitionBean.setName(jsonObject.optString("userName"));
                            competitionBean.setScore(jsonObject.optInt("score"));
                            competitionBean.setRectime(jsonObject.optInt("recallTime"));
                            competitionBean.setMemtime(jsonObject.optInt("memoryTime"));
                            competitionBean.setRank(jsonObject.optInt("rank"));
                            listTop.add(competitionBean);
                            competitionBean = null;
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listTop != null) {
            ((PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER)).noticeDataChange(
                    HttpConstant.LIST_TOP, listTop);
        }
        return null;
    }
    public void setSevice(PersonCenterService sevice) {
        this.service=sevice;

    }
}
