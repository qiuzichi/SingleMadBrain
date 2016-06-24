package com.unipad.http;
import android.text.TextUtils;
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
        if (status == 0 || status == 1||status == 2||status == 3){
            mParams.addBodyParameter("status",""+status);
        }
        mParams.addBodyParameter("user_id",AppContext.instance().loginUser.getUserId());
        if (null != startTime){
        mParams.addBodyParameter("startDate",startTime);}
        if (null != endDate) {
            mParams.addBodyParameter("endDate", endDate);
        }
        mParams.addBodyParameter("page",""+page);
        mParams.addBodyParameter("size",""+size);
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
                    JSONArray jsonArray = jsObj.getJSONArray("lists");
                    int iSize = jsonArray.length();
                    if (!(iSize==0)){
                        hisRecords = new ArrayList<>();
                    }
                    for (int i = 0; i < iSize; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HisRecord bean = new HisRecord();
                        bean.setMatchId(jsonObject.getString("matchId"));
                        bean.setProjectId(jsonObject.getString("projectName"));
                        bean.setGroupId(jsonObject.getString("groupText"));
                        bean.setGradeId(jsonObject.getString("gradeText"));
                        bean.setStartDate(jsonObject.getString("startDate"));
                        bean.setRectime(jsonObject.getString("rectime"));
                        bean.setMemtime(jsonObject.getString("memtime"));
                        bean.setScore(jsonObject.getString("score"));
                        bean.setRanking(jsonObject.getString("ranking"));
                        hisRecords.add(bean);
                    }
                }
        }catch (Exception e){
            return null;
        }
        if (hisRecords != null) {
            ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).noticeDataChange(HttpConstant.HISRECORD_OK,hisRecords);
        }
        return null;
    }
    @Override
    public void buildRequestParams() {

    }
}
