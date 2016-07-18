package com.unipad.brain.longPoker.dao;

import android.util.SparseIntArray;

import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.R;
import com.unipad.brain.longPoker.entity.LongPokerEntity;
import com.unipad.brain.quickPoker.entity.ChannelItem;
import com.unipad.common.Constant;
import com.unipad.http.HitopGetQuestion;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by gongkan on 2016/7/13.
 */
public class LongPokerService extends AbsBaseGameService {
    private String [] huaSe = new String []{"方块","黑桃","红桃","梅花"};
    private String [] dian = new String []{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    private String textDefault = "26_28_25_24_46_29_52_3_39_36_20_11_4_40_49_31_5_32_38_37_9_44_18_2_7_47_23_41_21_12_22_1_43_10_33_45_34_42_16_17_27_30_8_50_6_51_35_14_13_19_15_48" +
            ",26_28_25_24_46_29_52_3_39_36_20_11_4_40_49_31_5_32_38_37_9_44_18_2_7_47_23_41_21_12_22_1_43_10_33_45_34_42_16_17_27_30_8_50_6_51_35_14_13_19_15_48" +
            ",26_28_25_24_46_29_52_3_39_36_20_11_4_40_49_31_5_32_38_37_9_44_18_2_7_47_23_41_21_12_22_1_43_10_33_45_34_42_16_17_27_30_8_50_6_51_35_14_13_19_15_48" +
            ",26_28_25_24_46_29_52_3_39_36_20_11_4_40_49_31_5_32_38_37_9_44_18_2_7_47_23_41_21_12_22_1_43_10_33_45_34_42_16_17_27_30_8_50_6_51_35_14_13_19_15_48" +
            ",26_28_25_24_46_29_52_3_39_36_20_11_4_40_49_31_5_32_38_37_9_44_18_2_7_47_23_41_21_12_22_1_43_10_33_45_34_42_16_17_27_30_8_50_6_51_35_14_13_19_15_48";
    private SparseIntArray mPokerImageArray = new SparseIntArray();
    private ArrayList<LongPokerEntity> pokers = new ArrayList<>();
    public ArrayList<LongPokerEntity> pokersQuestion = new ArrayList<>();
    public int howMany;
    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public boolean init() {
        bindPokerImageRes();
        initCards();
       // parseData(textDefault);
        return true;
    }

    @Override
    public void parseData(String data) {
        super.parseData(data);
        pokersQuestion.clear();
        String [] allQu = textDefault.split(",");
        howMany = allQu.length;
        for (int i = 0; i < howMany; i++) {
            LongPokerEntity nullPoker = new LongPokerEntity();
            pokersQuestion.add(nullPoker);
            String[] allCard = allQu[i].split("_");
            for (int j = 0; j < allCard.length; j++) {
                pokersQuestion.add(pokers.get(Integer.valueOf(allCard[j]) - 1));
            }
        }
        initDataFinished();
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
    public String getAnswerData() {
        return null;
    }
    private void initCards() {
        for (int i = 0; i < Constant.POKER_NUM; i++) {
            pokers.add(new LongPokerEntity(i + 1, mPokerImageArray.get(i), huaSe[i / 13] + dian[i % 13],i+1));
        }
    }

    private void bindPokerImageRes() {
        for (int index = 0; index < Constant.POKER_NUM; index++) {
            mPokerImageArray.put(index, R.drawable.poker_fangkuai_01 + index);
        }
    }
}
