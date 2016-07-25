package com.unipad.http;

import com.unipad.AppContext;
import com.unipad.UserDetailEntity;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.brain.home.dao.HomeGameHandService;
import com.unipad.common.Constant;
import com.unipad.observer.GlobleObserService;

import org.json.JSONObject;

/**
 * Created by gongkan on 2016/6/17.
 */
public class HitopGetRule extends HitopRequest<RuleGame> {

    private GlobleObserService sevice;

    public HitopGetRule(String path) {
        super(path);
    }

    public HitopGetRule(String matchId, String path,GlobleObserService service) {
        super(HttpConstant.GET_RULE);
        this.sevice = service;
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
        try {
            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject dataJson = new JSONObject(jsObj.getString("data"));
                    if (dataJson != null) {
                        rule = new RuleGame();
                        rule.setId(dataJson.getString("id"));
                        rule.setGradeId(dataJson.getString("gradeId"));
                        rule.setProjectId(dataJson.getString("projectId"));
                        rule.setTiltle(dataJson.getString("title"));
                        rule.setRuleNo(dataJson.getString("ruleNo"));
                        rule.setCountRule(dataJson.getString("scoreText"));
                        rule.setCountRecall(dataJson.optInt("recallCount",1));
                        int[] memoryTime = new int[rule.getCountRecall()];
                        int[] reMemoryTime = new int[rule.getCountRecall()];
                        switch (rule.getCountRecall()){
                            case 3:
                                memoryTime[2] = dataJson.getInt("memoryTime3");
                                reMemoryTime[2] = dataJson.getInt("recallTime3");
                            case 2:
                                memoryTime[1] = dataJson.getInt("memoryTime2");
                                reMemoryTime[1] = dataJson.getInt("recallTime2");
                            case 1:
                                memoryTime[0] = dataJson.getInt("memoryTime");
                                reMemoryTime[0] = dataJson.getInt("recallTime");
                                break;
                        }
                        rule.setMemoryTime(memoryTime);
                        rule.setReMemoryTime(reMemoryTime);
                        rule.setMemeryTip(dataJson.getString("memoryText"));
                        rule.setReCallTip(dataJson.getString("recallText"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rule != null) {
            sevice.noticeDataChange(HttpConstant.GET_RULE_NOTIFY, rule);
        }
        return rule;
    }

    @Override
    public void buildRequestParams() {

    }
}
