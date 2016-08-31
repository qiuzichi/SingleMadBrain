package com.unipad.brain.home.bean;

import com.unipad.brain.consult.view.CompititionMainFragment;

/**
 * @描述：  赛事Bean
 * @author gongjiebin
 *
 */
public class CompetitionBean {

	private String id;

	private String name;//赛事名称

	private String projectId;//组别

	private String gradeId;//级别

	//private int groupId;//组别
	private String address;//比赛地址
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
	//总的页数
	private int totalPage;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "CompetitionBean [competitionDate=" + competitionDate
				+ ", competitionTime=" + competitionTime + ", cost=" + cost
				+ ", attention=" + attention + ", applyState=" + applyState
				+ "]";
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(this == o) return true;
		if(getClass() != o.getClass()) return false;
		if(o instanceof CompetitionBean){
			CompetitionBean bean = (CompetitionBean)o;
			if(bean.getId().equals(this.getId())){
				return true;
			}
		}
		return false;
	}
}
