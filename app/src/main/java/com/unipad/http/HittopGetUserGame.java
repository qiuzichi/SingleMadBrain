package com.unipad.http;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.CompetitionBean;
import com.unipad.brain.home.dao.HomeGameHandService;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongkan on 2016/6/15.
 */
public class HittopGetUserGame extends HitopRequest<List<CompetitionBean>> {

    private String gradeID;

    public HittopGetUserGame(String path) {
        super(path);
    }

    public HittopGetUserGame(String userId, String gameId, String group, int page, int size) {
        super(HttpConstant.GET_USER_GAME_LIST);
        this.gradeID = group;
        mParams.addQueryStringParameter("userId", userId);
        mParams.addQueryStringParameter("projectId", gameId);
        mParams.addQueryStringParameter("gradeId", group);
        mParams.addQueryStringParameter("page", "" + page);
        mParams.addQueryStringParameter("size", "" + size);
    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public List<CompetitionBean> handleJsonData(String json) {
        JSONObject jsObj = null;
        String response = null;

        ArrayList<CompetitionBean> competitionBeanArrayList = new ArrayList<>();
        try {
            response = new String(json.getBytes(), "utf-8");

            jsObj = new JSONObject(response);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject data = new JSONObject(jsObj.getString("data"));
                    JSONArray jsonArray = data.getJSONArray("resultList");
                    int iSize = jsonArray.length();
                    for (int i = 0; i < iSize; i++) {
                        CompetitionBean bean = new CompetitionBean();
                        JSONObject jsonObj2 = jsonArray.getJSONObject(i);
                        bean.setId(jsonObj2.getString("id"));
                        bean.setApplyState(jsonObj2.getInt("isApply"));
                        bean.setAddress(jsonObj2.getString("city"));
                        String dataAndTime = jsonObj2.getString("startDate");
                        String[] datasTime = dataAndTime.split(" ");
                        bean.setCompetitionDate(datasTime[0]);
                        bean.setCompetitionTime(datasTime[1]);
                        bean.setCost(jsonObj2.getString("applyMoney"));
                        bean.setName(jsonObj2.getString("name"));
                        bean.setGradeId(jsonObj2.getString("gradeId"));
                        bean.setProjectId(jsonObj2.getString("projectId"));
                        bean.setAttention(jsonObj2.optInt("isAttention") == 0 ? false : true);
                        competitionBeanArrayList.add(bean);

                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int key = HttpConstant.CITY_GET_HOME_GAME_LIST;
        if (Constant.CHIMA_GAME.equals(gradeID)) {
            key = HttpConstant.CHINA_GET_HOME_GAME_LIST;
        }else if (Constant.WORD_GAME.equals(gradeID)) {
            key = HttpConstant.WORD_GET_HOME_GAME_LIST;
        }else if(gradeID == null){
            key = HttpConstant.NOTIFY_GET_NEWCOMPETITION;

            ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(key, competitionBeanArrayList);

            key = HttpConstant.CITY_GET_HOME_GAME_LIST;

        }
        // {"data":{"curPage":1,"nextPage":1,"pageSize":10,"prePage":1,"resultList":[{"applyMoney":5,"city":"广东省深圳市赋安科技大厦","
        // createDate":"2016-06-15 00:00:00","creater":"admin","deleted":0,"grade":"中国赛","gradeId":"00002","groupId":"00001",
        // "id":"6C1B3A3B3C7842A390A2D037C60FD9C1","matchNo":"2016061500001",
        // "matchcityId":"7CF57BB6470D46FEBF7624489A43EA74","name":"深圳脑王大赛2","projectId":"00003","roleId":"A08195CED7CF4203815D5F10BFC8B99C","startDate":"2016-06-30 00:00:00","startTime":"1970-01-01 12:00:00","status":0,"topicNum":1200}],"totalCount":1,"totalPage":1},"ret_code":"0000"}
        ((HomeGameHandService) AppContext.instance().getService(Constant.HOME_GAME_HAND_SERVICE)).noticeDataChange(key,competitionBeanArrayList);
        return competitionBeanArrayList;
    }


    @Override
    public void buildRequestParams() {

    }
}
