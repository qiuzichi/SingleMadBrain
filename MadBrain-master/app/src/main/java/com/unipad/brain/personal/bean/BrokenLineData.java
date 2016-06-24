package com.unipad.brain.personal.bean;

/**
 * 柱状图实体类
 */
public class BrokenLineData {
    private int type;
    private String date;
    private int score;

    public BrokenLineData(String date, int type, int score) {
        this.date = date;
        this.type = type;
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

}
