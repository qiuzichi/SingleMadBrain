package com.unipad.brain.personal.bean;
/**
 * @描述：  赛事Bean
 * @author gongjiebin
 *
 */
public class CompetitionBean {

	// 比赛日期
	private String competitionDate;
	// 比赛时间
	private String competitionTime; 
	// 所需费用
	private String cost;
	// 是否关注
	private boolean attention;
	// 报名的状态 0未报名
	private int applyState;
	// 0 城市赛 | 1中国赛  | 2 世界赛
	private int competitionLevel;

	private String addr; // 比赛地址

	private String name;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCompetitionLevel() {
		return competitionLevel;
	}


	public void setCompetitionLevel(int competitionLevel) {
		this.competitionLevel = competitionLevel;
	}


	public String getCompetitionDate() {
		return competitionDate;
	}


	public void setCompetitionDate(String competitionDate) {
		this.competitionDate = competitionDate;
	}


	public String getCompetitionTime() {
		return competitionTime;
	}


	public void setCompetitionTime(String competitionTime) {
		this.competitionTime = competitionTime;
	}


	public String getCost() {
		return cost;
	}


	public void setCost(String cost) {
		this.cost = cost;
	}


	public boolean isAttention() {
		return attention;
	}


	public void setAttention(boolean attention) {
		this.attention = attention;
	}


	public int getApplyState() {
		return applyState;
	}


	public void setApplyState(int applyState) {
		this.applyState = applyState;
	}


	@Override
	public String toString() {
		return "CompetitionBean [competitionDate=" + competitionDate
				+ ", competitionTime=" + competitionTime + ", cost=" + cost
				+ ", attention=" + attention + ", applyState=" + applyState
				+ "]";
	}
	
	
	
	
}
