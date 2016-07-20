package com.unipad.brain.quickPoker.dao;

import java.util.ArrayList;
import java.util.Map;

import android.util.SparseIntArray;

import com.unipad.brain.AbsBaseGameService;
import com.unipad.brain.R;
import com.unipad.brain.quickPoker.entity.ChannelItem;
import com.unipad.brain.quickPoker.entity.PokerEntity;
import com.unipad.common.Constant;
import com.unipad.http.HitopGetQuestion;
import com.unipad.utils.LogUtil;


/**
 * 牌的控制类
 * 
 * @author Created by gongkan on 2016/5/30.
 * 
 */
public class QuickCardService extends AbsBaseGameService{
	// boolean flag = false;//在下面，还没有移到上面去。
	private static final String TAG = "QuickCardService";
	private SparseIntArray mPokerImageArray = new SparseIntArray();

	/**
	 * 跟服务器约定，0-12为方块，13-25为黑桃，26-38为红桃，39-51为梅花
	 * */
	private String [] huaSe = new String []{"方块","黑桃","红桃","梅花"};
	private String [] dian = new String []{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	private ArrayList<ChannelItem> bottomCards = new ArrayList<ChannelItem>();
	private String round1 = "26_28_25_24_46_29_52_3_39_36_20_11_4_40_49_31_5_32_38_37_9_44_18_2_7_47_23_41_21_12_22_1_43_10_33_45_34_42_16_17_27_30_8_50_6_51_35_14_13_19_15_48";
	private String round2 = round1;
	@Override
	public boolean init() {
		bindPokerImageRes();
		initCards();
		parseDataByRound(1);
		return true;
	}

	public void clear() {
		bottomCards.clear();

	}

	@Override
	public void parseData(String data) {
		super.parseData(data);
		String[] allCard = data.split(",");
		allround = allCard.length;
		round1 = allCard[0];
		round2 = allCard[1];
		parseDataByRound(round);
	}

	public void parseDataByRound(int round) {
		ArrayList<ChannelItem> orgin = PokerEntity.getInstance().getPokerSortArray();
		orgin.clear();
		String data = round1;
		 if (round == 2){
			data = round2;
		}
		try {
			String[] allCard = data.split("_");
			for (int i = 0; i < allCard.length; i++) {
				orgin.add(bottomCards.get(Integer.valueOf(allCard[i]) - 1));
			}
		}catch (Exception e) {
			LogUtil.e(TAG,"服务器数据错误："+round1+","+round2);
			e.printStackTrace();
		}
		finally {
			initDataFinished();
		}

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
		return 0;
	}

	@Override
	public String getAnswerData() {
		return null;
	}



	public ArrayList<ChannelItem> getBottomCards() {
		return bottomCards;
	}

	private void bindPokerImageRes() {
		for (int index = 0; index < Constant.POKER_NUM; index++) {
			mPokerImageArray.put(index, R.drawable.poker_fangkuai_01 + index);
		}
	}



	public void initCards() {

		for (int i = 0; i < Constant.POKER_NUM; i++) {

			bottomCards.add(new ChannelItem(i+1, mPokerImageArray.get(i),huaSe[i/13]+dian[i%13]));

		}

	}

}
