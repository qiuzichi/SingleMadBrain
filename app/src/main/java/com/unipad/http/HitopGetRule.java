package com.unipad.http;

import com.unipad.AppContext;
import com.unipad.UserDetailEntity;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.brain.home.dao.HomeGameHandService;
import com.unipad.common.Constant;

import org.json.JSONObject;

/**
 * Created by gongkan on 2016/6/17.
 */
public class HitopGetRule extends HitopRequest<RuleGame> {


    public HitopGetRule(String path) {
        super(path);
    }

    public HitopGetRule(String matchId, String path) {
        super(HttpConstant.GET_RULE);
        mParams.addQueryStringParameter("matchId", matchId);
    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public RuleGame handleJsonData(String json) {
        RuleGame rule = null;
        JSONObject jsObj = null;
        String response = null;
        try {
            response = new String(json.getBytes(), "utf-8");
            jsObj = new JSONObject(response);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject dataJson = new JSONObject(jsObj.getString("data"));
                    if (dataJson != null) {
                        rule.setId(dataJson.getString("id"));
                        rule.setGradeId(dataJson.getString("gradeId"));
                        rule.setProjectId(dataJson.getString("projectId"));
                        rule.setTiltle(dataJson.getString("title"));
                        rule.setRuleNo(dataJson.getString("ruleNo"));
                        rule.setGradeId(dataJson.getString("groupId"));
                        rule.setMemeryTime1(dataJson.getInt("momeryTime"));
                        rule.setRecallTime1(dataJson.getInt("recallTime"));
                        rule.setMemeryTime2(dataJson.getInt("momeryTime2"));
                        rule.setRecallTime2(dataJson.getInt("recallTime2"));
                        rule.setMemeryTime3(dataJson.getInt("momeryTime3"));
                        rule.setRecallTime3(dataJson.getInt("recallTime3"));
                        rule.setMemeryTip(dataJson.getString("MEMORY_TEXT"));
                        rule.setReCallTip(dataJson.getString("RECALL_TEXT"));
                        rule.setCountRecall(dataJson.getInt("RECALL_COUNT"));
                        rule.setCountRule(dataJson.getString("SCORE_TEXT"));
                    }
                }
            }
        } catch (Exception e) {

        }
        if (rule != null) {
            ((HomeGameHandService) AppContext.instance().getService(Constant.HOME_GAME_HAND_SERVICE)).noticeDataChange(HttpConstant.GET_RULE_NOTIFY,rule);
        }
        return rule;
    }

    @Override
    public void buildRequestParams() {

    }

}
