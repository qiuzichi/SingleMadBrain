package com.unipad.common.bean;

import java.io.Serializable;

/**
 * Created by Wbj on 2016/4/13.
 */
public class CompeteEntity implements Serializable {
    /**
     * 比赛项目
     */
    private int competeItem;
    /**
     * 比赛模式：城市赛、中国赛、世界赛
     */
    private int competeMode;
    /**
     * 记忆时间
     */
    private int memoryTime;
    /**
     * 回忆时间
     */
    private int rememoryTime;

    public CompeteEntity() {
    }

    public CompeteEntity(int competeItem, int memoryTime, int rememoryTime) {
        this.competeItem = competeItem;
        this.memoryTime = memoryTime;
        this.rememoryTime = rememoryTime;
    }

    public int getCompeteItem() {
        return competeItem;
    }

    public void setCompeteItem(int competeItem) {
        this.competeItem = competeItem;
    }

    public int getMemoryTime() {
        return memoryTime;
    }

    public void setMemoryTime(int memoryTime) {
        this.memoryTime = memoryTime;
    }

    public int getRememoryTime() {
        return rememoryTime;
    }

    public void setRememoryTime(int rememoryTime) {
        this.rememoryTime = rememoryTime;
    }

}
