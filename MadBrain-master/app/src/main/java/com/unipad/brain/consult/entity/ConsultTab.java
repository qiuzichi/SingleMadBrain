package com.unipad.brain.consult.entity;

/**
 * Created by 63 on 2016/6/20.
 */
public enum ConsultTab {
    INTRODUCATION("推荐"),
    OCCASIONS("赛事"),
    HOTSPOT("热点"),
    SUBSCRIBE("订阅");

    private String type;

    private ConsultTab(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
