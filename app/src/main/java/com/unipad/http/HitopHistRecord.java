package com.unipad.http;
import android.util.Log;
import com.unipad.AppContext;
import com.unipad.brain.home.bean.HisRecord;
import com.unipad.brain.home.dao.HisRecordService;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class HitopHistRecord extends HitopRequest<List<HisRecord>>{

    public HitopHistRecord(String path) {
        super(path);
    }
    public HitopHistRecord() {
        super(HttpConstant.HisRecord);
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
            Log.e("", "" + json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject dataJson=new JSONObject(jsObj.getString("data"));
                    JSONArray jsonArray=dataJson.getJSONArray("resultList");
                    int iSize=jsonArray.length();
                    for (int i = 0; i < iSize; i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                        HisRecord bean=new HisRecord();
                        bean.setProjectId(jsonObject.getString("projectId"));
                        bean.setGroup(jsonObject.getString("groupId"));
                        bean.setStartDate(jsonObject.getString("startDate"));
                        bean.setRectime(jsonObject.getString("rectime"));
                        bean.setMemtime(jsonObject.getString("memtime"));
                        bean.setScore(jsonObject.getString("score"));
                        bean.setRanking(jsonObject.getString("ranking"));
                        hisRecords.add(bean);
                    }
                }
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
    @Override
    public void buildRequestParams() {

    }
}
