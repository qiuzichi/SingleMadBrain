package com.unipad.http;
import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.MyFollow;
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
 * Created by 深圳冰凌信息科技有限公司 黄祖嘉 on 2016/6/27 0027.
 */
public class HitopFollow extends  HitopRequest<Object> {

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
                    ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).noticeDataChange(HttpConstant.USER_APPLYED, App.getContext().getString(R.string.error_apply));
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
                    Log.e("",json);
                }
                this.sevice.noticeDataChange(HttpConstant.USER_FOLLOW,competitionBeans);
            } else {
                this.sevice.noticeDataChange(HttpConstant.USER_FOLLOW, App.getContext().getString(R.string.string_json_error));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.sevice.noticeDataChange(HttpConstant.USER_FOLLOW, App.getContext().getString(R.string.string_json_error));
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
