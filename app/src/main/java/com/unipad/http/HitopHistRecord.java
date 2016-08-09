package com.unipad.http;
import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.HisRecord;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class HitopHistRecord extends HitopRequest<List<HisRecord>>{

    public HitopHistRecord(String path) {
        super(path);
    }
    public HitopHistRecord(int status ,String startTime,String endDate,int page,int size) {
        super(HttpConstant.HisRecord);
        mParams.addBodyParameter("userId", AppContext.instance().loginUser.getUserId());

        if (status == 0 || status == 1||status == 2||status == 3) {
            mParams.addBodyParameter("status", "" + status);
        }
        if (null != startTime) {
            //startTime=startTime.replaceAll("/","|");
            mParams.addBodyParameter("startDate", startTime);
        }
        if (null != endDate) {
            //endDate=endDate.replaceAll("/","|");
            mParams.addBodyParameter("endDate", endDate);
        }
        mParams.addBodyParameter("page", "" + page);
        mParams.addBodyParameter("size", "" + size);

    }
    @Override
    public String buildRequestURL() {
        return getHost();
    }
    @Override
    public List<HisRecord> handleJsonData(String json) throws JSONException {
        List<HisRecord> hisRecords=null;
        JSONObject jsObj=null;
        String response = null;
        try {
            response = new String(json.getBytes(), "utf-8");
            jsObj = new JSONObject(response);
            Log.e("", "" + json);
            if (jsObj != null && jsObj.toString().length() != 0)
                if (jsObj.getInt("ret_code") == 0) {

                    JSONObject data = new JSONObject(jsObj.getString("data"));
                    JSONArray jsonArray = data.getJSONArray("resultList");
                    int iSize = jsonArray.length();

                    if (!(iSize==0)){
                        hisRecords = new ArrayList<>();
                    }
                    for (int i = 0; i < iSize; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HisRecord bean = new HisRecord();
                        bean.setMatchId(jsonObject.getString("matchId"));
                        bean.setProjectId(jsonObject.getString("projectId"));
                        bean.setGroupId(jsonObject.getString("groupId"));
                        bean.setGradeId(jsonObject.getString("gradeId"));
                        bean.setStartDate(jsonObject.getString("startDate"));
                        bean.setRectime(jsonObject.getString("rectime"));
                        bean.setMemtime(jsonObject.getString("memtime"));
                        bean.setScore(jsonObject.getString("score"));
                        bean.setRanking(jsonObject.optString("ranking"));
                        hisRecords.add(bean);
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if (hisRecords != null) {
            ((PersonCenterService)AppContext.instance().getService
                    (Constant.PERSONCENTER)).noticeDataChange(HttpConstant.HISRECORD_OK,hisRecords);
        }
        return null;
    }
}
