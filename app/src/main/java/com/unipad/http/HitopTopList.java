package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.brain.personal.dao.PersonCenterService;
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
        super(path);
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
        List<CompetitionBean> listTop=null;
        JSONObject jsObj=null;
        String response=null;
        //处理返回结果
    try {
        Log.e("",""+json);
        response=new String(json.getBytes(),"utf-8");
        jsObj=new JSONObject(response);
        if (jsObj!=null&&jsObj.toString().length()!=0){
            if (jsObj.getInt("ret_code") == 0){
            JSONObject data=new JSONObject(jsObj.getString("lists"));
            JSONArray jsonArray=data.getJSONArray("userId");
            int iSize=jsonArray.length();
            if (iSize!=0){
                listTop=new ArrayList<>();
                for (int i=0;i<iSize;i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CompetitionBean competitionBean = new CompetitionBean();
                    competitionBean.setUserId(jsonObject.getString("userId"));
                    competitionBean.setName(jsonObject.getString("userName"));
                    listTop.add(competitionBean);
                    }
                }
            }

        }

    }catch (Exception e){
        e.printStackTrace();
    }
        if (listTop!=null){
            ((PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER)).noticeDataChange(
                    HttpConstant.LIST_TOP,listTop);
        }
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setSevice(PersonCenterService sevice) {
        this.service=sevice;

    }
}
