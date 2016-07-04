package com.unipad.brain.virtual.dao;

import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.virtual.bean.VirtualEntity;
import com.unipad.http.HitopGetQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gongkan on 2016/7/4.
 */
public class VirtualTimeService extends AbsBaseGameService {

    public List<VirtualEntity> virtualList = new ArrayList<>();
    @Override
    public void downloadingQuestion(Map<String, String> data) {
        super.downloadingQuestion(data);
        HitopGetQuestion httpGetQuestion = new HitopGetQuestion();
        httpGetQuestion.buildRequestParams("questionId",  data.get("QUESTIONID"));
        httpGetQuestion.setService(this);
        httpGetQuestion.post();
    }

    @Override
    public void parseData(String data) {
        super.parseData(data);
        //

        initDataFinished();
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public String getAnswerData() {
        return null;
    }
}
