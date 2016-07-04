package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.common.Constant;
import com.unipad.observer.GlobleObserService;
import com.unipad.utils.ToastUtil;

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
        Log.d(this.getClass().getSimpleName(),json);
        // 处理返回结果
        List<CompetitionBean> competitionBeans = new ArrayList<CompetitionBean>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int ret_code = jsonObject.optInt("ret_code");
            if(ret_code == 0){
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                if(null == jsonArray){
                    this.sevice.noticeDataChange(HttpConstant.USER_APPLYED,
                            App.getContext().getString(R.string.error_apply));
                    return null;
                }
                int length = jsonArray.length();
                CompetitionBean competitionBean = null;
                for(int i = 0; i < length; i ++) {
                    JSONObject objCom = jsonArray.optJSONObject(i);
                    competitionBean = new CompetitionBean();
                    competitionBean.setAddr(objCom.optString("city"));
                    competitionBean.setComId(objCom.optString("id"));
                    competitionBean.setName(objCom.optString("name"));
                    competitionBean.setCost(objCom.optString("applyMoney"));
                    competitionBean.setCompetitionDate(objCom.optString("startDate"));
                    competitionBean.setProjectId(objCom.optString("projectId"));
                    competitionBean.setProjecNname(Constant.getProjectName(objCom.optString("projectId")));
                    competitionBean.setApplyState(objCom.optInt("status"));
                    competitionBeans.add(competitionBean);
                }
                this.sevice.noticeDataChange(HttpConstant.USER_APPLYED,competitionBeans);
            } else {
                this.sevice.noticeDataChange(HttpConstant.USER_APPLYED, App.getContext().getString(R.string.string_json_error));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.sevice.noticeDataChange(HttpConstant.USER_APPLYED, App.getContext().getString(R.string.string_json_error));
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
