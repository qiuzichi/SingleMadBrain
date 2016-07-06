package com.unipad.brain.consult.entity;

import com.unipad.utils.StringUtil;

/**
 * Created by hasee on 2016/7/4.
 */
public class NewCompetitionBean {
    //比赛id
    private String id;
    //
    private String projectId;
    //级别id
    private String gradeId;
    //组别id
    private String groupId;
    //比赛日期  YYYY-MM-DD
    private String startDate;
    //题库的题量
    private int topicNum;
    //报名费用
    private int applyMoney;
    //比赛地址；
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(int topicNum) {
        this.topicNum = topicNum;
    }

    public int getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(int applyMoney) {
        this.applyMoney = applyMoney;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
