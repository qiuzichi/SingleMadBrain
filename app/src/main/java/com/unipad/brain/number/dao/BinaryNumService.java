package com.unipad.brain.number.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.util.SparseArray;

import com.unipad.ICoreService;
import com.unipad.brain.AbsBaseGameService;
import com.unipad.common.Constant;
import com.unipad.http.HitopDownLoad;
import com.unipad.http.HitopGetQuestion;

/**
 * 二进制数字项目实体
 */
public class BinaryNumService extends AbsBaseGameService {
    /**
     * 文字0
     */
    public static String TEXT_ZERO = "0";
    /**
     * 文字1
     */
    public static String TEXT_ONE = "1";
    /**
     * 发消息开启线程准备加载界面
     */
    public static final int MSG_OPEN_THREAD = 105;
    /**
     * 发消息更新界面
     */
    public static final int MSG_REFRESH_UI = 106;
    /**
     * 行数
     */
    public  int lines = 25;
    /**
     * 列数
     */
    public  int rows = 30;
    /**
     * 以行号为key值保存题目的每一行数字的出现顺序
     */
    public  SparseArray<String> lineNumbers = new SparseArray<>();
    /**
     * 评分：完全写满并正确的一行得30分
     */
    public static final int LINE_FULL_SCORE = 30;
    /**
     * 评分：完全写满但有一个错处（或漏空）的一行得15分
     */
    public static final int LINE_ONE_ERROR_SCORE = 15;

    public SparseArray<String> answer = new SparseArray<>();
    @Override
    public void downloadingQuestion(Map<String, String> mData) {
        super.downloadingQuestion(mData);
        HitopGetQuestion httpGetQuestion = new HitopGetQuestion();
        httpGetQuestion.buildRequestParams("questionId",  mData.get("QUESTIONID"));
        httpGetQuestion.setService(this);
        httpGetQuestion.post();
    }

    @Override
    public void parseData(String data) {
        super.parseData(data);
        String [] persData = data.split(",");
        lines = persData.length;
        for (int i = 0;i<lines;i++){
            String[] detail = persData[i].split("\\^");
            lineNumbers.put(Integer.valueOf(detail[0]),detail[1]);
        }
        initDataFinished();
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public String getAnswerData() {
        StringBuilder answerData = new StringBuilder();
        for (int i = 0;i<answer.size();i++){
            answerData.append(answer.keyAt(i))
                    .append("\\^")
                    .append(answer.valueAt(i))
                    .append(",");
        }
        int length = answerData.length();
        if (length > 0)
        answerData.deleteCharAt(length-1);
        return answerData.toString();
    }
}
