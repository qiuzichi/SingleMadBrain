package com.unipad.http;

import com.unipad.AppContext;

import com.unipad.brain.home.bean.CompetitionBean;
import com.unipad.brain.home.dao.HomeGameHandService;
import com.unipad.brain.home.dao.NewsService;

import com.unipad.brain.location.dao.LocationService;
import com.unipad.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by gongkan on 2016/6/17.
 */
public class HitopApplyGame extends HitopRequest<CompetitionBean> {
    private int applyGameKey;
    private int isPay;
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

    public HitopApplyGame(String userId, int key, String matchId, String projectId, String gradeId, int isPay) {
        super(HttpConstant.APPLY_GAME);
        applyGameKey = key;
        this.isPay = isPay;
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

                //{"data":{"isNow":0,"matchId":"B4D3CE9A840043769801E05BAB6F238C","projectId":"00002"},"ret_code":"0"}
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject dataJson = new JSONObject(jsObj.getString("data"));
                    if (dataJson != null) {
                        competitionBean = new CompetitionBean();
                        competitionBean.setGradeId(dataJson.optString("gradeId"));
                        competitionBean.setProjectId(dataJson.optString("projectId"));
                        competitionBean.setId(dataJson.optString("matchId"));
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


        if (applyGameKey == HttpConstant.LOCATION_APPLY_GAME) {
            ((LocationService) AppContext.instance().getService(Constant.LOCATION_SERVICE)).noticeDataChange(applyGameKey, competitionBean);
            applyGameKey = 0;
            return null;
        } else if (applyGameKey == HttpConstant.NOTIFY_APPLY_NEWCOMPETITION) {
            ((NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(applyGameKey, competitionBean);
            applyGameKey = 0;
            return null;
        }

        int key = HttpConstant.CITY_APPLY_GAME;
        if (competitionBean != null) {
            if (Constant.CHIMA_GAME.equals(competitionBean.getGradeId())) {
                key = HttpConstant.CHINA_APPLY_GAME;
            } else if (Constant.WORD_GAME.equals(competitionBean.getGradeId())) {
                key = HttpConstant.WORD_APPLY_GAME;
            }
            ((HomeGameHandService) AppContext.instance().getService(Constant.HOME_GAME_HAND_SERVICE)).noticeDataChange(key, competitionBean);
        }

        return null;
    }

}
