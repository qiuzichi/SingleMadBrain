package com.unipad.singlebrain.quickPoker.dao;

import com.unipad.common.Constant;
import com.unipad.http.HitopGetQuestion;
import com.unipad.singlebrain.AbsBaseGameService;
import com.unipad.singlebrain.R;
import com.unipad.singlebrain.quickPoker.entity.ChannelItem;
import com.unipad.singlebrain.quickPoker.entity.PokerEntity;
import com.unipad.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


/**
 * 牌的控制类
 *
 * @author Created by gongkan on 2016/5/30.
 */
public class QuickCardService extends AbsBaseGameService {
    // boolean flag = false;//在下面，还没有移到上面去。
    private static final String TAG = "QuickCardService";

    /**
     * 跟服务器约定，0-12为方块，13-25为黑桃，26-38为红桃，39-51为梅花
     */
    private String[] huaSe = new String[]{"方块", "黑桃", "红桃", "梅花"};
    private String[] dian = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private ArrayList<ChannelItem> bottomCards = new ArrayList<ChannelItem>();
    private String round1 = "26_28_25_24_46_29_52_3_39_36_20_11_4_40_49_31_5_32_38_37_9_44_18_2_7_47_23_41_21_12_22_1_43_10_33_45_34_42_16_17_27_30_8_50_6_51_35_14_13_19_15_48";
    private String[] roundData;
    private ArrayList<Integer> num = new ArrayList<>();

    public void setUserData(String userData) {
        this.userData = userData;
    }

    private String userData = "";

    @Override
    public boolean init() {
        //parseDataByRound(1);
        for (int i = 1; i < 53; i++) {
            num.add(i);
        }
        return true;
    }

    public void clear() {
        bottomCards.clear();

    }

    @Override
    public void parseData(String data) {
        super.parseData(data);
        LogUtil.e(TAG, data);
        roundData = data.split("&");
        allround = roundData.length;
        round = 0;
        parseDataByRound();
    }

    public void parseDataByRound() {
        if (isLastRound()) {
            return;
        }
        ArrayList<ChannelItem> orgin = PokerEntity.getInstance().getPokerSortArray();
        orgin.clear();
        round++;
        round1 = roundData[round - 1];
        try {
            if (bottomCards.size() == 0) {
                initCards();
            }
            String[] allCard = round1.split("_");
            for (int i = 0; i < allCard.length; i++) {
                orgin.add(bottomCards.get(Integer.valueOf(allCard[i]) - 1));
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "服务器数据错误：" + round1);
            e.printStackTrace();
        } finally {
            initDataFinished();
        }

    }

    @Override
    public void parseData() {
        Collections.shuffle(num);
        ArrayList<ChannelItem> orgin = PokerEntity.getInstance().getPokerSortArray();
        orgin.clear();
        if (bottomCards.size() == 0) {
            initCards();
        }
        for (int i = 0; i < num.size(); i++) {
            orgin.add(bottomCards.get(num.get(i) - 1));
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
    public void initDataFinished() {
        super.initDataFinished();
    }

    @Override
    public double getScore() {
        LogUtil.e("", "userData == " + userData.toString());
        String[] data = userData.split("_");
        for (int i = 0; i < data.length; i++) {
            LogUtil.e("","num="+String.valueOf(num.get(i)));
            if(!data[i].equals(String.valueOf(num.get(i)))){
                return i;
            }
        }
        return data.length;
    }

    @Override
    public String getAnswerData() {
        LogUtil.e("", "快速扑克牌" + userData);
        return userData;
    }

    @Override
    public String initData() {
        return null;
    }


    public ArrayList<ChannelItem> getBottomCards() {
        return bottomCards;
    }


    private void initCards() {

        for (int i = 0; i < Constant.POKER_NUM; i++) {

            bottomCards.add(new ChannelItem(i + 1, R.drawable.poker_fangkuai_01 + i, huaSe[i / 13] + dian[i % 13]));

        }
        LogUtil.e("", "bottom size = " + bottomCards.size());
    }

}
