package com.unipad.singlebrain.virtual.dao;

import android.text.TextUtils;

import com.unipad.http.HitopGetQuestion;
import com.unipad.singlebrain.AbsBaseGameService;
import com.unipad.singlebrain.App;
import com.unipad.singlebrain.virtual.bean.VirtualEntity;
import com.unipad.singlebrain.words.dao.UnicodeReader;
import com.unipad.utils.LogUtil;
import com.unipad.utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
        httpGetQuestion.buildRequestParams("questionId", data.get("QUESTIONID"));
        httpGetQuestion.setService(this);
        httpGetQuestion.post();
    }

    @Override
    public void parseData() {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        try {
            InputStream inputStream;
            if (Util.isZh()) {
                inputStream = App.getContext().getResources().getAssets().open("virtualtime_china.txt");
            } else {
                inputStream = App.getContext().getResources().getAssets().open("virtualtime_eng.txt");
            }
            inputReader = new InputStreamReader(inputStream, "UTF-8");
            bufferReader = new BufferedReader(new UnicodeReader(inputStream, "utf-8"));

            // 读取一行
            String line = null;

            while ((line = bufferReader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (IOException e) {

        } finally {
            try {
                if (bufferReader != null) {
                    bufferReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LogUtil.e("VirtualTimeService", "data ==" + stringBuilder.toString());
        String[] persData = stringBuilder.toString().split(",");
        VirtualEntity virtualEntity;
        for (int i = 0; i < persData.length; i++) {
            virtualEntity = new VirtualEntity();
            String[] detail = persData[i].split("\\^");
            virtualEntity.setDate(detail[1]);
            virtualEntity.setEvent(detail[2]);
            virtualEntity.setNumber(Integer.valueOf(detail[0]));
            this.virtualList.add(virtualEntity);
        }
        initDataFinished();
    }

    @Override
    public void parseData(String data) {
        super.parseData(data);
        //data = data+","+data+","+data;
        String[] entity = data.split(",");
        for (int i = 0; i < entity.length; i++) {
            String[] word = entity[i].split("\\^");
            VirtualEntity virtualEntity = new VirtualEntity();
            virtualEntity.setDate(word[1]);
            virtualEntity.setEvent(word[2]);
            virtualEntity.setNumber(Integer.valueOf(word[0]));
            virtualList.add(virtualEntity);
        }
        initDataFinished();
    }

    public void shuffData() {
        Collections.shuffle(virtualList);
    }

    @Override
    public void clear() {
        virtualList.clear();
    }

    @Override
    public double getScore() {
        return getVirtualTimeScore(1.0f, 0.5f)[0];
    }

    /**
     * @param correctScore 正确一个所得分数
     * @param errorScore   错误一个所扣分数
     * @return [0]下标0为总得分  [1]下标1为犯错次数
     * @描述： 虚拟日期时间和日期 计分。
     * <p/>
     * 1、每写一个正确年份得一分，整个年份的 4 位数字必须正确写 上。
     * 2、每个事件前只可写上一个 4 位数字的年份，每个错误的年份 会倒扣 0.5 分。
     * 3、空白行数不会扣分。
     * 4、总分四舍五入，即 45.5 分会调高至 46 分。
     * 5、如总分为负数者将以 0 分计。
     * 6、如有相同的分数，则以较少错误的选手胜。
     */
    public int[] getVirtualTimeScore(float correctScore, float errorScore) {
        float countScore = 0.0f; // 最后总得分，四舍五入
        int errorNum = 0; // 犯错次数
        if (null == virtualList)
            return new int[]{0, errorNum};

        for (int i = 0; i < virtualList.size(); i++) {
            // 判断答题与试卷是否一致
            if (virtualList.get(i).getDate().equals(virtualList.get(i).getAnswerDate())) {
                countScore = countScore + correctScore; // 填写正确答案， 加上相应分数
            } else {
                // 累积犯错次数
                errorNum++;
                if (TextUtils.isEmpty(virtualList.get(i).getAnswerDate())) {
                    // 选手未填写答案， 不扣分
                } else {
                    countScore = countScore - errorScore; // 填写错误答案， 扣除相应分数
                }
            }
        }

        if (countScore < 0) {
            // 总分为负数者将以 0 分计。
            return new int[]{0, errorNum};
        }
        // 得到最后得分 四舍五入
        BigDecimal bigDecimal = new BigDecimal(countScore).setScale(0, BigDecimal.ROUND_HALF_UP);
        //int lastScore = bigDecimal.
        Integer lastScore = bigDecimal.intValue();
        return new int[]{lastScore, errorNum};
    }

    @Override
    public String getAnswerData() {
        StringBuilder userdate = new StringBuilder();
        for (int i = 0; i < virtualList.size(); i++) {
            userdate.append(virtualList.get(i).toString()).append(",");

        }
        userdate.deleteCharAt(userdate.length() - 1);
        LogUtil.e("", userdate.toString());
        return userdate.toString();
    }

    @Override
    public String initData() {
        return null;
    }
}
