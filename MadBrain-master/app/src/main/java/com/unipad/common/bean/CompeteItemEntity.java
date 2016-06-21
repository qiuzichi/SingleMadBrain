package com.unipad.common.bean;

import java.io.Serializable;

/**
 * Created by Wbj on 2016/4/13.
 */
public class CompeteItemEntity implements Serializable {
    /**
     * 比赛项目
     */
    private String competeItem;


    /**
     * 比赛类型：城市赛、中国赛、世界赛
     */
    private String competeType;
    /**
     * 记忆时间，单位：秒
     */
    private int memoryTime;
    /**
     * 回忆时间，单位：秒
     */
    private int rememoryTime;

    private static CompeteItemEntity competeItemEntity = new CompeteItemEntity();

    private CompeteItemEntity() {
    }

    public String getCompeteItem() {
        return competeItem;
    }

    /**
     * 设置比赛项目
     *
     * @param competeItem 人名头像忘记、二进制数字、虚拟事件等比赛项目
     */
    public void setCompeteItem(String competeItem) {
        this.competeItem = competeItem;
    }

    public String getCompeteType() {
        return competeType;
    }

    /**
     * 设置比赛类型
     *
     * @param competeType 比赛类型：城市赛、中国赛、世界赛
     */
    public void setCompeteType(String competeType) {
        this.competeType = competeType;
    }

    public int getMemoryTime() {
        return memoryTime;
    }

    /**
     * 设置记忆时间
     *
     * @param memoryTime 秒
     */
    public void setMemoryTime(int memoryTime) {
        this.memoryTime = memoryTime;
    }

    public int getRememoryTime() {
        return rememoryTime;
    }

    /**
     * 设置回忆时间
     *
     * @param rememoryTime 秒
     */
    public void setRememoryTime(int rememoryTime) {
        this.rememoryTime = rememoryTime;
    }

    public static CompeteItemEntity getInstance() {
        if (competeItemEntity == null) {
            competeItemEntity = new CompeteItemEntity();
        }
        return competeItemEntity;
    }

}
