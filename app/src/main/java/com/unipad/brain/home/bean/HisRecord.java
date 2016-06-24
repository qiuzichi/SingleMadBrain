package com.unipad.brain.home.bean;
/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class HisRecord {

    /*历史战绩id*/
    private String projectId;
    /*项目*/
    private String item;
    /*级别*/
    private String grade;
    /*组别*/
    private String group;
    /*比赛日期*/
    private String startDate;
    /*记忆时间*/
    private String rectime;
    /*回忆时间*/
    private String memtime;
    /*得分*/
    private String score;
    /*排名*/
    private String ranking;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRectime() {
        return rectime;
    }

    public void setRectime(String rectime) {
        this.rectime = rectime;
    }

    public String getMemtime() {
        return memtime;
    }

    public void setMemtime(String memtime) {
        this.memtime = memtime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String id) {
        this.projectId = id;
    }
    public String getItem() {
        return item;
    }
}
