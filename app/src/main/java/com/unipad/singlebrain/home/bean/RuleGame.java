package com.unipad.singlebrain.home.bean;

/**
 * Created by gongkan on 2016/6/20.
 */
public class RuleGame {
    private String id;

    private String tiltle;

    private String ruleNo;

    private String projectId;

    private String gradeId;

    private String groupId;

    private String memeryTip;

    private String reCallTip;

    /**记忆次数*/
    private int countRecall;

    /**计分规则*/
    private String countRule;

    private int[] memoryTime ;

    private int [] reMemoryTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTiltle() {
        return tiltle;
    }

    public void setTiltle(String tiltle) {
        this.tiltle = tiltle;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getMemeryTip() {
        return memeryTip;
    }

    public void setMemeryTip(String memeryTip) {
        this.memeryTip = memeryTip;
    }

    public String getReCallTip() {
        return reCallTip;
    }

    public void setReCallTip(String reCallTip) {
        this.reCallTip = reCallTip;
    }

    public int getCountRecall() {
        return countRecall;
    }

    public void setCountRecall(int countRecall) {
        this.countRecall = countRecall;
    }

    public String getCountRule() {
        return countRule;
    }

    public void setCountRule(String countRule) {
        this.countRule = countRule;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public int[] getMemoryTime() {
        return memoryTime;
    }

    public void setMemoryTime(int[] memoryTime) {
        this.memoryTime = memoryTime;
    }

    public int[] getReMemoryTime() {
        return reMemoryTime;
    }

    public void setReMemoryTime(int[] reMemoryTime) {
        this.reMemoryTime = reMemoryTime;
    }
}
