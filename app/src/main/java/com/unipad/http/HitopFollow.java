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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by hzj on 2016/6/27 0027.
 */
public class HitopFollow extends  HitopRequest<List<CompetitionBean>>{
    private GlobleObserService sevice;
    public HitopFollow(String path) {
        super(path);
    }

    public HitopFollow(){
        super(HttpConstant.GET_USER_FOLLOW);

        }
    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public List<CompetitionBean> handleJsonData(String json)throws JSONException {
        List<CompetitionBean> myFollows=null;
        JSONObject jsObj=null;
        String response = null;
        // 处理返回结果
        try {
            response=new String(json.getBytes(),"utf-8");
            jsObj=new JSONObject(response);
            Log.e("", "" + json);
            if(jsObj != null && jsObj.toString().length() != 0){
                if (jsObj.getInt("ret_code") == 0){
                    JSONObject data=new JSONObject(jsObj.getString("lists"));
                    JSONArray jsonArray=data.getJSONArray("match");
                    int iSize=jsonArray.length();
                    if (iSize !=0){
                        myFollows=new ArrayList<>();
                        for (int i=0;i<iSize;i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            CompetitionBean competitionBean = new CompetitionBean();
                            competitionBean.setName(jsonObject.getString("name"));
                            competitionBean.setProjectId(jsonObject.getString("projectId"));
                            competitionBean.setCompetitionDate(jsonObject.getString("startDate"));
                            competitionBean.setAddr(jsonObject.getString("city"));
                            competitionBean.setComId(jsonObject.getString("id"));
                            competitionBean.setCost(jsonObject.getString("applyMoney"));
                            myFollows.add(competitionBean);
                            
                        }
                    }
                }
                    Log.e("",json);
                }
            } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (myFollows!=null){
            ((PersonCenterService)AppContext.instance().getService
                    (Constant.PERSONCENTER)).noticeDataChange(HttpConstant.USER_FOLLOW,myFollows);

        }
        return null;
    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }

}
