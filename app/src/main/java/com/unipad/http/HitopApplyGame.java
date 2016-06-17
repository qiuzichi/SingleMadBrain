package com.unipad.http;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.CompetitionBean;
import com.unipad.brain.home.dao.HomeGameHandService;
import com.unipad.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by gongkan on 2016/6/17.
 */
public class HitopApplyGame extends HitopRequest<CompetitionBean> {

    public HitopApplyGame(String path) {
        super(path);
    }

    public HitopApplyGame(String userId, String matchId, String projectId, String gradeId, int isPay) {
        super(HttpConstant.APPLY_GAME);
        mParams.addQueryStringParameter("match_id", matchId);
        mParams.addQueryStringParameter("user_id", userId);
        mParams.addQueryStringParameter("pay", "" + isPay);
        mParams.addQueryStringParameter("projectId", projectId);
        mParams.addQueryStringParameter("gradeId", gradeId);
    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public CompetitionBean handleJsonData(String json) {
        JSONObject jsObj = null;
        String response = null;
        CompetitionBean competitionBean = null;
        try {
            response = new String(json.getBytes(), "utf-8");
            jsObj = new JSONObject(response);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject dataJson = new JSONObject(jsObj.getString("data"));
                    if (dataJson != null) {
                        competitionBean = new CompetitionBean();
                        competitionBean.setGradeId(dataJson.getString("gradeId"));
                        competitionBean.setProjectId(dataJson.getString("projectId"));
                        competitionBean.setId(dataJson.getString("matchId"));
                        competitionBean.setApplyState(1);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            competitionBean = null;
        } catch (JSONException e) {
            e.printStackTrace();
            competitionBean = null;
        }
        int key = HttpConstant.CITY_APPLY_GAME;
        if (competitionBean != null) {
            if (Constant.CHIMA_GAME.equals(competitionBean.getGradeId())) {
                key = HttpConstant.CHINA_APPLY_GAME;
            } else if (Constant.WORD_GAME.equals(competitionBean.getGradeId())) {
                key = HttpConstant.WORD_APPLY_GAME;
            }
        }
        ((HomeGameHandService) AppContext.instance().getService(Constant.HOME_GAME_HAND_SERVICE)).noticeDataChange(key, competitionBean);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
