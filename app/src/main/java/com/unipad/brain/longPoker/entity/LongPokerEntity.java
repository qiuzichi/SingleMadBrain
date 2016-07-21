package com.unipad.brain.longPoker.entity;

import android.support.v7.widget.StaggeredGridLayoutManager;

import com.unipad.brain.quickPoker.entity.ChannelItem;

/**
 * Created by gongkan on 2016/7/13.
 */
public class LongPokerEntity extends ChannelItem {
    private int userAnswer;
    private int num;
    public LongPokerEntity(int id, int orderId, String name,int num) {
        super(id, orderId, name);
        this.num = num;
    }
    public LongPokerEntity() {

    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getNum() {
        return num;
    }

}
