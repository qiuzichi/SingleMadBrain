package com.unipad.brain.virtual.bean;

/**
 * Created by yzj on 2016/4/11.
 */
public class VirtualEntity {


    /**
     * 序号
     */
    public int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 虚拟日期
     */
    public String date;

    /**
     * 事件
     */
    public String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public VirtualEntity(int number, String date, String event) {
        this.number = number;
        this.date = date;
        this.event = event;
    }

    public VirtualEntity() {
    }

    /**
     * 答对正确得1分
     */
    public static int scoreOK = 1;

    /**
     * 打错扣0.5分
     */
    public static double scoreError = 0.5;


}
