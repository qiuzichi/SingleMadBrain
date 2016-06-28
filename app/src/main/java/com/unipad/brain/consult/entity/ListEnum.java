package com.unipad.brain.consult.entity;

import com.unipad.brain.R;

/**
 * Created by 63 on 2016/6/27.
 */
public enum ListEnum{
    CONSULT_PRESENT(R.string.consult_present, R.drawable.news_present),
    COMPETITION_PRESENT(R.string.competition_present, R.drawable.occasions_pesent);

    private int nameResId;
    private int labelResId;

    private ListEnum(int nameResId, int labelResId){
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