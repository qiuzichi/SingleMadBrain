package com.unipad.http;

import com.unipad.AppContext;

import com.unipad.singlebrain.location.bean.CompetitionBean;
import com.unipad.singlebrain.location.dao.LocationService;
import com.unipad.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by gongkan on 2016/6/17.
 */
public class LoactionApplyGame extends HitopRequest<CompetitionBean> {
    private int applyGameKey;
    private int isPay;
    public LoactionApplyGame(String path) {
        super(path);
    }

    public LoactionApplyGame(String userId, String matchId, String projectId, String gradeId, int isPay) {
        super(HttpConstant.APPLY_GAME);
        mParams.addQueryStringParameter("match_id", matchId);
        mParams.addQueryStringParameter("user_id", userId);
        mParams.addQueryStringParameter("pay", "" + isPay);
        mParams.addQueryStringParameter("projectId", projectId);
        mParams.addQueryStringParameter("gradeId", gradeId);
    }

    public LoactionApplyGame(String userId, int key, String matchId, String projectId, String gradeId, int isPay) {
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
                       // competitionBean.setGradeId(dataJson.optString("gradeId"));
                        competitionBean.setProjectId(dataJson.optString("projectId"));
                        competitionBean.setComId(dataJson.optString("matchId"));
                        competitionBean.setIsApply(1);
                    }
                }else if(jsObj.getInt("ret_code") == -1){
                    ((LocationService) AppContext.instance().getService(Constant.LOCATION_SERVICE)).noticeDataChange(applyGameKey, jsObj.optString("ret_msg"));
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
        }

        return null;
    }

}
