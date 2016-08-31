package com.unipad.singlebrain.consult.entity;

/**
 * Created by 63 on 2016/6/20.
 */
public enum ConsultTab {
    INTRODUCATION("推荐", "00001"),
    OCCASIONS("赛事", "00002"),
    HOTSPOT("热点", "00003");

    private String type;
    private String typeId;

    private ConsultTab(String type, String typeId){
        this.type = type;
        this.typeId = typeId;
    }

    public String getType(){
        return type;
    }
    public String getTypeId(){
        return typeId;
    }
}
