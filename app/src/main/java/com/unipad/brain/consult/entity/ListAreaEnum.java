package com.unipad.brain.consult.entity;

import com.unipad.brain.R;

/**
 * Created by hasee on 2016/7/4.
 */
public enum ListAreaEnum {
    CITYCOMPETITION(R.string.city_game, R.drawable.city_compitition),
    CHINACOMPETITION(R.string.china_game, R.drawable.china_compitition),
    WORLDCOMPETITION(R.string.world_game, R.drawable.world_compitition);


    private int nameResId;
    private int labelResId;
    private ListAreaEnum(int nameResId, int labelResId){
        this.nameResId = nameResId;
        this.labelResId = labelResId;
    }

    public int getNameResId(){
        return  nameResId;
    }

    public int getLabelResId(){
        return  labelResId;
    }
}
