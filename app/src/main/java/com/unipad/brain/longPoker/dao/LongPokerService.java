package com.unipad.brain.longPoker.dao;

import android.util.SparseIntArray;

import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.R;
import com.unipad.brain.longPoker.entity.LongPokerEntity;
import com.unipad.brain.quickPoker.entity.ChannelItem;
import com.unipad.common.Constant;
import com.unipad.http.HitopGetQuestion;
import com.unipad.utils.LogUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private ArrayList<ChannelItem> pokers = new ArrayList<>();

    public ArrayList<LongPokerEntity> pokersQuestion = new ArrayList<>();

    public int howMany;


    @Override
    public double getScore() {
        return getLongPokerScore(52f,26f,1f,2f,1)[0];
    }


    /**
     * 1、每副完整并正确回忆的扑克牌得 52 分。
     * 2、如有一个错处（包括漏空）得 26 分。
     * 3、两个或以上的错处得 0 分。
     * 4、两张次序调换的牌当作两个错处。
     * 5、即使没有回忆全部的扑克牌也不会倒扣分。
     * 6、关于最后一副：如最后一副没有记完，例如，只记了前 38 张， 且全部正确，则得 38 分。 如最后一副没有记成，且有一个错处，其得分为正确扑克牌数目 的一半分。 如出现小数点则四舍五入。例如，作答了 29 张扑克牌但有一错
     *    处，分数将除 2，即 29/2 = 14.5，然后调高至 15 分。 最后一副扑克牌有两个或以上的错处得 0 分。
     * 7、如出现相同分数，将比较选手已经记忆并且写出来却没有得 分的扑克牌。每正确一张扑克牌得 1 分，分数较高者胜。
     * @param  correctScore 完全正确记忆一副牌所得分数
     * @param  errorOneScore 一副牌中有错误一处所得分数
     * @param  lastScore 最后一副牌正确一张所得分数
     * @param  lastOneErrorScore  最后一行没有写满并且出现一处错误时所扣分数的倍数
     * @param  outRangeScore 一行如果出现两个错误的情况这一行将不得分。 累积不得分行中正确答案的个数。 此参数为不得分行中一个正确答案所得分数（用于服务器区分相同得分的情况）
     * @return [0]总得分 [1]错误行中的正确扑克所得分数
     */
    public int[] getLongPokerScore(float correctScore, float errorOneScore,float lastScore,float lastOneErrorScore,int outRangeScore){
        float countScore = 0; // 总得分
        int errorNum = 0; // 错误行中的正确次数
        if(null == pokersQuestion){

            return new int[]{0,errorNum};
        }

        int correctPoker = 0;

        int errorPoker = 0;

        List<int[]> pokerScoer = new ArrayList<>();
        // 装去除标题的扑克牌
        ArrayList<LongPokerEntity> newPokersQuestion = new ArrayList<>();
        // 一副牌为 53个元素 其中52个元素为纸牌元素， 一个元素为标题元素。
        for(int i = 0 ; i < pokersQuestion.size() ; i ++ ){
           //  过滤标题元素

            if(i % 53 != 0){
                LogUtil.e("","answer :"+pokersQuestion.get(i).getUserAnswer()+","+pokersQuestion.get(i).name);
                newPokersQuestion.add(pokersQuestion.get(i));
            }
        }

        for(int i =0 ; i < newPokersQuestion.size() ; i ++ ){
            if(newPokersQuestion.get(i).getUserAnswer() == newPokersQuestion.get(i).id){
                correctPoker ++;  // 累积整幅牌的正确张数
            } else {
                errorPoker ++;   // 累积整幅牌的错误张数
            }
            if(i % 52 == 0){
                // 一副牌结束
                pokerScoer.add(new int[]{correctPoker,errorPoker});
                // 分数清零
                correctPoker = 0;
                errorPoker = 0;
            }
        }

        // 最后一副牌答题数量
        int number = getLastAnswer(newPokersQuestion);

        for(int i = 0; i < pokerScoer.size() ; i ++ ){

            int[] pokers = pokerScoer.get(i);
            if((i+1) == pokerScoer.size()){
                if(pokers[0] == number){
                    // 完全正确
                    countScore = countScore + (pokers[0] * lastScore);
                } else if(pokers[0] == (number - 1)){
                    // 错误一次
                    countScore = countScore + (number / lastOneErrorScore);
                } else {
                    errorNum = errorNum + pokers[0];
                }
            } else {
                // 计算总得分
                if(pokers[0] == 52){
                    // 完全正确
                    countScore = countScore + correctScore;
                } else if(pokers[0] == 51){
                    // 错误一处
                    countScore = countScore + (pokers[0] * errorOneScore) ;
                } else {
                    // 错误
                    errorNum = errorNum + pokers[0];
                }
            }
        }
        // 得到最后得分
        BigDecimal bigDecimal =  new BigDecimal(countScore).setScale(0, BigDecimal.ROUND_HALF_UP);
        //int lastScore = bigDecimal. 四舍五入
        Integer last = bigDecimal.intValue();

        return new int[]{last,  errorNum * outRangeScore};
    }

    /**
     * 得到最后一副牌选手所答的题目数量
     * @return
     */
    private int getLastAnswer(ArrayList<LongPokerEntity> newPokersQuestion){
        //
       List<LongPokerEntity> pokerEntity =  newPokersQuestion.subList(newPokersQuestion.size() - 52, newPokersQuestion.size());
        StringBuffer str = new StringBuffer();
        if(null != pokerEntity && pokerEntity.size() > 0){
            // 去除末尾的0
            int lastNum =  pokerEntity.get(pokerEntity.size() -1).getUserAnswer();
            if(lastNum != 0){
                return pokerEntity.size();
            } else {
                int length = 0;
                for(int i = 0; i < pokerEntity.size(); i ++ ){
                    int kl = pokerEntity.get(pokerEntity.size() -(i + 1)).getUserAnswer();
                    if(kl == 0){
                        length ++;
                    } else {
                        break;
                    }
                }
                return pokerEntity.size() - length;
            }
        }
        return str.toString().trim().length();
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
        LogUtil.e("", "long Poker data:" + data);
        pokersQuestion.clear();
        String [] allQu = data.split("&");
        howMany = allQu.length;
        for (int i = 0; i < howMany; i++) {
            LongPokerEntity nullPoker = new LongPokerEntity();
            pokersQuestion.add(nullPoker);
            String[] allCard = allQu[i].split("_");
            for (int j = 0; j < allCard.length; j++) {
                pokersQuestion.add(new LongPokerEntity(pokers.get(Integer.valueOf(allCard[j]) - 1)));
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
        StringBuilder answerData = new StringBuilder();
        for(int i = 0;i< pokersQuestion.size();i++){
            if (i%53 == 0){
                if (i != 0){
                    answerData.deleteCharAt(answerData.length() - 1);
                    answerData.append(",");
                }
                answerData.append(i/53).append("^");
            }else{
                answerData.append(pokersQuestion.get(i).getUserAnswer()).append("_");
            }
        }
        answerData.deleteCharAt(answerData.length()-1);
        LogUtil.e("","马拉松扑克牌用户答案："+answerData.toString());
        return answerData.toString();
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
