package com.unipad.brain.home.bean;

import java.io.Serializable;

/**
 * @描述：  比赛项目的实体
 * @author gongjiebin
 *
 */
public class ProjectBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 项目名称
	private String name;
	// 目标
	private String target;
	// 记忆时间  下标【0】城市赛  【1】中国赛  【2】世界赛
	private String[] memorysDate;  
	// 回忆时间   下标【0】城市赛  【1】中国赛  【2】世界赛 
	private String[] recallsDate;
	// 记忆次数  下标【0】城市赛  【1】中国赛  【2】世界赛 
	private String[] memorysNum;
	
	// 比赛注意事项
	private String attention;
	// 记忆规则
	private String memory_rule;
	// 回忆规则
	private String recall_rule;
	// 记分规则
	private String record_rule;
	public ProjectBean(String name, String target, String[] memorysDate,
			String[] recallsDate, String[] memorysNum,String attention, String memory_rule,String recall_rule,String record_rule) {
		super();
		this.name = name;
		this.target = target;
		this.memorysDate = memorysDate;
		this.recallsDate = recallsDate;
		this.memorysNum = memorysNum;
		this.attention = attention;
		this.memory_rule = memory_rule;
		this.recall_rule = recall_rule;
		this.record_rule = record_rule;
	}
	
	
	public ProjectBean(){};
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String[] getMemorysDate() {
		return memorysDate;
	}
	public void setMemorysDate(String[] memorysDate) {
		this.memorysDate = memorysDate;
	}
	public String[] getRecallsDate() {
		return recallsDate;
	}
	public void setRecallsDate(String[] recallsDate) {
		this.recallsDate = recallsDate;
	}
	public String[] getMemorysNum() {
		return memorysNum;
	}
	public void setMemorysNum(String[] memorysNum) {
		this.memorysNum = memorysNum;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getMemory_rule() {
		return memory_rule;
	}

	public void setMemory_rule(String memory_rule) {
		this.memory_rule = memory_rule;
	}

	public String getRecall_rule() {
		return recall_rule;
	}

	public void setRecall_rule(String recall_rule) {
		this.recall_rule = recall_rule;
	}

	public String getRecord_rule() {
		return record_rule;
	}

	public void setRecord_rule(String record_rule) {
		this.record_rule = record_rule;
	}
 }
