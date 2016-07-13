package com.unipad.brain.virtual.dao;

import android.app.Service;
import android.util.Log;
import android.util.SparseArray;

import com.unipad.AppContext;
import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.virtual.bean.VirtualEntity;
import com.unipad.common.Constant;
import com.unipad.http.HitopGetQuestion;
import com.unipad.utils.LogUtil;

import java.security.Key;
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
        data = data+","+data+","+data;
        String [] entity = data.split(",");
        for(int i=0;i<entity.length;i++){
            String[] word=entity[i].split("\\^");
            VirtualEntity virtualEntity=new VirtualEntity();
            virtualEntity.setDate(word[1]);
            virtualEntity.setNumber(Integer.valueOf(word[0]));
            virtualEntity.setEvent(word[2]);
            virtualList.add(virtualEntity);


        }
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
