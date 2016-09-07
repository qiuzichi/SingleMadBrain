package com.unipad.singlebrain.words.dao;

import android.text.TextUtils;

import com.unipad.http.HitopGetQuestion;
import com.unipad.singlebrain.AbsBaseGameService;
import com.unipad.singlebrain.App;
import com.unipad.singlebrain.words.bean.WordEntity;
import com.unipad.utils.LogUtil;
import com.unipad.utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by gongkan on 2016/5/30.
 */
public class WordsService extends AbsBaseGameService {
    public static int VOLUM = 5;//每行词语的个数
    public List<WordEntity> entitys = new ArrayList<>();
    private String[] wordArr = new String[]{"地毯", "公里", "唇", "双生儿", "狗窝", "剃刀", "橙", "允许", "香水", "发刷", "河马", "战舰", "跑步者", "坚果", "游艇", "风格", "省略", "意思", "小猫", "羽毛",
            "报纸", "知道", "鲍鱼", "套头毛衣", "恐龙", "伞", "梯子", "退休", "石英", "衣领", "项链", "吸收", "车库", "誓约", "格子饼", "拉链", "头痛", "虹膜", "失业", "雨雪",
            "斑马", "手表", "飞机", "教练", "文具", "坐浴盆", "工作", "羊毛", "组织", "录像", "苹果", "雨", "须", "婴儿", "骑师", "鼓槌", "鼓", "编辑", "资格", "行政人员",
            "报纸", "知道", "鲍鱼", "套头毛衣", "恐龙", "伞", "梯子", "退休", "石英", "衣领", "项链", "吸收", "车库", "誓约", "格子饼", "拉链", "头痛", "虹膜", "失业", "雨雪",
            "开始", "维他命", "股东", "面包店", "大自然", "股东", "梯子", "退休", "石英", "衣领", "项链", "吸收", "车库", "誓约", "格子饼", "拉链", "头痛", "虹膜", "失业", "雨雪",};
    public int rows = 20;
    public WordEntity[] wordEntities;
    public int lines;
    private final String EMPTY = "@";
    private String mData;

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
    public void parseData() {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        try
        {
            InputStream inputStream;
            if(Util.isZh()){
                inputStream = App.getContext().getResources().getAssets().open("words/wordc.txt");
            }else{
                inputStream = App.getContext().getResources().getAssets().open("words/worde.txt");
            }
            inputReader = new InputStreamReader(inputStream,"UTF-8");
            bufferReader = new BufferedReader(new UnicodeReader(inputStream,"utf-8"));

            // 读取一行
            String line = null;

            while ((line = bufferReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }

        }
        catch (IOException e)
        {

        }
        finally
        {
            try {
                if(bufferReader!=null){
                    bufferReader.close();
                }
                if(inputReader!=null){
                    inputReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String string = "1^第三方,21^程序,41^水电费,61^饿法,81^问,101^太," +
                "2^爱的色放,22^规范化,42^得分,62^周星驰,82^儿童,102^的萨芬," +
                "3^梵蒂冈,23^环境,43^梵蒂冈,63^撒点粉,83^儿童,103^风格," +
                "4^风格,24^乳突炎,44^梵蒂冈,64^士大夫,84^体育,104^乳突炎," +
                "5^爱的色放,25^问,45^后台,65^想,85^天后宫,105^乳突炎," +
                "6^水电费,26^阿斯顿,46^一天要,66^用户,86^乳突炎,106^vb," +
                "7^太,27^愉快,47^放假,67^风格,87^体育,107^讽德诵功," +
                "8^想,28^隐居,48^好基友,68^如同,88^风格,108^范德萨," +
                "9^热狗,29^儿女,49^一体机,69^问题,89^放到,109^关乎," +
                "10^而,30^问法,50^体育,70^娥,90^大风歌,110^一条语句," +
                "11^大风歌,31^梵蒂冈,51^体育,71^风格,91^谁的风格,111^体育," +
                "12^阿斯顿,32^二,52^环境,72^try,92^人员,112^与," +
                "13^大风歌,33^太,53^图,73^一天,93^与,113^风格," +
                "14^虽然个人,34^er,54^体育,74^3穷人,94^鳄鱼,114^安慰人," +
                "15^任天行,35^er,55^体育,75^问,95^荣誉,115^娃," +
                "16^突然,36^er,56^而我,76^儿童,96^u,116^撒点粉," +
                "17^大蓉和,37^er,57^前儿童,77^4他V,97^体育,117^儿童," +
                "18^vb,38^而,58^问,78^第三方,98^二,118^二," +
                "19^双方的,39^儿童,59^而我,79^小从V,99^梵蒂冈,119^日游," +
                "20^谁的风格,40^而,60^梵蒂冈,80^体育,100^风格,120^风格";
        parseData(stringBuilder.toString());
    }

    @Override
    public void parseData(String data) {
        super.parseData(data);
        String[] allWords = data.split(",");
        LogUtil.e("","-------"+data);
        mData = data;
        lines = (int) Math.ceil(allWords.length * 1.0 / rows);
        wordEntities = new WordEntity[rows * lines];
        for (int i = 0; i < allWords.length; i++) {
            WordEntity word = new WordEntity();
            String [] aWord = allWords[i].split("\\^");
            int num = Integer.parseInt(aWord[0].trim());
            word.setNumber(num);
            word.setWord(aWord[1]);
            LogUtil.e("", "i = " + i + "," + word.getWord() + word.getNumber());
          // int index = (num-1) / rows + (num-1) % rows * lines;
            wordEntities[num-1] = word;
            entitys.add(word);
        }
        initDataFinished();
    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        LogUtil.e("","initDataFinished---");
    }

    @Override
    public double getScore() {
        return caculateScore(getAnswerData());
    }

    public double caculateScore(String text) {
        String qrListStr[] = mData.split(",");
        String QRList[][] = new String[qrListStr.length][qrListStr[0].split("\\^").length];

        Map<Integer, String> map = new TreeMap<Integer, String>();
        for (String qr : qrListStr) {
            String[] temp = qr.split("\\^");
            map.put(Integer.parseInt(temp[0]), temp[1]);
        }
        Set<Integer> keySet = map.keySet();
        for(Integer i : keySet){
            QRList[i-1][0] = String.valueOf(i);
            QRList[i-1][1] = map.get(i);
        }

        String contentStr[] = text.split(",");
        String userContent[][] = new String[contentStr.length][contentStr[0]
                .split("\\^").length];
        Map<Integer, String> userMap = new TreeMap<Integer, String>();
        for (String qr : contentStr) {
            String[] temp = qr.split("\\^");
            userMap.put(Integer.parseInt(temp[0]), temp[1]);
        }
        Set<Integer> keySetU = userMap.keySet();
        for(Integer i : keySetU){
            userContent[i-1][0] = String.valueOf(i);
            userContent[i-1][1] = userMap.get(i);
        }
        // 对比答案
        double score = 0; // 当前用户总得分
        double pageScore = 20; // 每页得分分值
        int errorTotalNum = 0; // 累积错误次数

        int pageNum = 20; // 每页20个词语
        int pages = 0; // 总页数

        pages = userContent.length % pageNum == 0 ? userContent.length
                / pageNum : userContent.length / pageNum + 1; // 总页数

        for (int clum = 0; clum < pages - 1; clum++) {
            int errorNum = 0; // 每页错误数,包括留空
            for (int j = clum * pageNum; j < (clum + 1) * pageNum; j++) { // 每20条比一次
                for (int i = 0; i < QRList.length; i++) {
                    if (QRList[i][0].equals(userContent[j][0])) { // 找到相应题目
                        if (!QRList[i][1].equals(userContent[j][1])) { // 比对词语
                            errorTotalNum++;
                            errorNum++;
                        }
                        break;
                    }
                }
            }
            if (errorNum == 0) { // 此页全对
                score += pageScore;
            } else if (errorNum == 1) { // 错漏一个
                score += pageScore / 2;
            } else { // 错漏两个以上或全留空
                score += 0;
            }
        }

        // 最后一行积分方法
        int answerLastIndex = 0; // 最后一行最后一个答案下标
        int answerLastCo = 0; // 最后一行回答正确的个数
        int lastErrorNum = 0; // 最后一行错漏个数

        for (int k = userContent.length - 1; k >= (pages - 1) * pageNum; k--) {
            if (!userContent[k][1].equals(EMPTY)) {
                answerLastIndex = k;
                break;
            }
        }
        for (int j = (pages - 1) * pageNum; j <= answerLastIndex; j++) { // 最后一页积分方法
            for (int i = 0; i < QRList.length; i++) {
                if (QRList[i][0].equals(userContent[j][0])) { // 找到相应题目
                    if (QRList[i][1].equals(userContent[j][1])) { // 比对词语
                        answerLastCo++;
                    } else {
                        lastErrorNum++;
                        errorTotalNum++;
                    }
                    break;
                }
            }
        }
        if (lastErrorNum == 0) { // 此页全对
            score += answerLastCo;
        } else if (lastErrorNum == 1) { // 错漏一个
            score += answerLastCo / 2;
        } else { // 错漏两个以上或全留空
            score += 0;
        }
        if(score < 0) {
            score = 0;
        }
        score = Math.round(score);
        return score;
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

    @Override
    public String initData() {
        return null;
    }
}
