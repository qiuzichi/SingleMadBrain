package com.unipad.brain.words.dao;

import android.text.TextUtils;

import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.words.bean.WordEntity;
import com.unipad.http.HitopGetQuestion;
import com.unipad.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gongkan on 2016/5/30.
 */
public class WordsService extends AbsBaseGameService {
    public static int VOLUM = 5;//每行词语的个数
    private List<WordEntity> entitys = new ArrayList<>();
    private String[] wordArr = new String[]{"地毯", "公里", "唇", "双生儿", "狗窝", "剃刀", "橙", "允许", "香水", "发刷", "河马", "战舰", "跑步者", "坚果", "游艇", "风格", "省略", "意思", "小猫", "羽毛",
            "报纸", "知道", "鲍鱼", "套头毛衣", "恐龙", "伞", "梯子", "退休", "石英", "衣领", "项链", "吸收", "车库", "誓约", "格子饼", "拉链", "头痛", "虹膜", "失业", "雨雪",
            "斑马", "手表", "飞机", "教练", "文具", "坐浴盆", "工作", "羊毛", "组织", "录像", "苹果", "雨", "须", "婴儿", "骑师", "鼓槌", "鼓", "编辑", "资格", "行政人员",
            "报纸", "知道", "鲍鱼", "套头毛衣", "恐龙", "伞", "梯子", "退休", "石英", "衣领", "项链", "吸收", "车库", "誓约", "格子饼", "拉链", "头痛", "虹膜", "失业", "雨雪",
            "开始", "维他命", "股东", "面包店", "大自然", "股东", "梯子", "退休", "石英", "衣领", "项链", "吸收", "车库", "誓约", "格子饼", "拉链", "头痛", "虹膜", "失业", "雨雪",};
    public int rows = 20;
    public WordEntity[] wordEntities;
    public int lines;

    @Override
    public boolean init() {

        return true;
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void downloadingQuestion(Map<String, String> data) {
        super.downloadingQuestion(data);
        HitopGetQuestion httpGetQuestion = new HitopGetQuestion();
        httpGetQuestion.buildRequestParams("questionId", data.get("QUESTIONID"));
        httpGetQuestion.setService(this);
        httpGetQuestion.post();
    }

    @Override
    public void parseData(String data) {
        super.parseData(data);
        String[] allWords = data.split(",");
        LogUtil.e("","-------"+data);
        lines = (int) Math.ceil(allWords.length * 1.0 / rows);
        wordEntities = new WordEntity[rows * lines];
        for (int i = 0; i < allWords.length; i++) {
            WordEntity word = new WordEntity();
            String [] aWord = allWords[i].split("\\^");
            int num = Integer.parseInt(aWord[0].trim());
            word.setNumber(num);
            word.setWord(aWord[1]);
            LogUtil.e("", "i = " + i + "," + word.getWord() + word.getNumber());
           int index = (num-1) / rows + (num-1) % rows * lines;
            wordEntities[num-1] = word;
            entitys.add(word);
        }
        int i =  entitys.size();
        LogUtil.e("","begin update!--"+i);
        initDataFinished();
    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        LogUtil.e("","initDataFinished---");
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public String getAnswerData() {
        StringBuilder answerData = new StringBuilder();
        for (int i = 0;i<entitys.size();i++) {
            if(TextUtils.isEmpty(entitys.get(i).getAnswer())){
                entitys.get(i).setAnswer("@");
            }
            answerData.append(entitys.get(i).getNumber())
                    .append("^")
                    .append(entitys.get(i).getAnswer())
                    .append(",");
        }
        int length = answerData.length();
        if (length > 0)
            answerData.deleteCharAt(length-1);
        LogUtil.e("","随机词语;"+answerData.toString());
        return answerData.toString();
    }
}
