package com.unipad.http;

import android.util.Log;

import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.location.bean.CompetitionBean;
import com.unipad.common.Constant;
import com.unipad.observer.GlobleObserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongjiebin on 2016/6/22.
 *
 * @描述： 根据城市获取比赛ID
 */
public class HitopGetCityGame extends HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopGetCityGame(String path) {
        super(path);
    }

    public HitopGetCityGame() {
        super(HttpConstant.GET_CITY_GAME);
    }

    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public Object handleJsonData(String json) {
        //  {"lists":[{"applyMoney":5,"city":"广东省深圳市赋安科技大厦","createDate":"2016-06-15 00:00:00","creater":"admin","deleted":0,"gradeId":"00002","groupId":"00001","id":"6C1B3A3B3C7842A390A2D037C60FD9C1","matchNo":"2016061500001","matchcityId":"7CF57BB6470D46FEBF7624489A43EA74","name":"深圳脑王大赛2","projectId":"00003","roleId":"A08195CED7CF4203815D5F10BFC8B99C","startDate":"2016-06-30 00:00:00","startTime":"1970-01-01 12:00:00","status":0,"topicNum":1200},{"applyMoney":5,"city":"广东省深圳市赋安科技大厦","createDate":"2016-06-22 00:00:00","creater":"admin","deleted":0,"gradeId":"00002","groupId":"00003","id":"7F3CF08AF7EC438EB5690E3F8D1587FD","matchNo":"201606220001","matchcityId":"7CF57BB6470D46FEBF7624489A43EA74","name":"中国脑王大赛","projectId":"00001","roleId":"EAA6194AC395466498E861B0DBD66272","startDate":"2016-06-23 00:00:00","startTime":"1970-01-01 14:00:00","status":0,"topicNum":18}],"ret_code":"0","ret_msg":"获取成功"
        Log.d(this.getClass().getSimpleName(), json);
        // 处理返回结果
         List<CompetitionBean> competitionBeans = new ArrayList<CompetitionBean>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int ret_code = jsonObject.optInt("ret_code");
            if (ret_code == 0) {
                JSONArray jsonArray = jsonObject.optJSONArray("lists");
                if (null == jsonArray) {
                    this.sevice.noticeDataChange(HttpConstant.USER_APPLYED, App.getContext().getString(R.string.error_apply));
                    return null;
                }
                int length = jsonArray.length();
                CompetitionBean competitionBean = null;
                for (int i = 0; i < length; i++) {
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
                    competitionBean.setIsApply(objCom.optInt("isApply"));
                    competitionBeans.add(competitionBean);
                }
                this.sevice.noticeDataChange(HttpConstant.CITY_GAME, competitionBeans);
            } else {
                this.sevice.noticeDataChange(HttpConstant.CITY_GAME, App.getContext().getString(R.string.string_json_error));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.sevice.noticeDataChange(HttpConstant.CITY_GAME, App.getContext().getString(R.string.string_json_error));
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
