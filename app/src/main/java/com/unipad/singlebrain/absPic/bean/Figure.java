package com.unipad.singlebrain.absPic.bean;

/**
 * Created by gongkan on 2016/4/12.
 */
public class Figure {
    private String path;
    // 行号
    private int rawId;
    // 答卷 题目
    private int answerId;
    private int matchId;
    private double score;
    private int memoryTime;
    private int rememoryTime;
    private int userId;
    private String userName;

    public Figure(String path, int rawId) {
        this.path = path;
        this.rawId = rawId;

    }
    public Figure(){};
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRawId() {
        return rawId;
    }

    public void setRawId(int rawId) {
        this.rawId = rawId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public void setMemoryTime(int memoryTime) {
        this.memoryTime = memoryTime;
    }

    public int getMemoryTime() {
        return memoryTime;
    }

    public int getRememoryTime() {
        return rememoryTime;
    }

    public void setRememoryTime(int rememoryTime) {
        this.rememoryTime = rememoryTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return answerId+"^"+rawId;


    }

}
