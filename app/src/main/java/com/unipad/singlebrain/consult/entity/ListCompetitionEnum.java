package com.unipad.singlebrain.consult.entity;

import com.unipad.singlebrain.R;

/**
 * Created by hasee on 2016/7/4.
 */
public enum ListCompetitionEnum {
    PROJECTONE(R.string.project_1, R.drawable.person_icon),
    PROJECTTWO(R.string.project_2, R.drawable. binary_num),
    PROJECTTHREE(R.string.project_3, R.drawable.long_num),
    PROJECTFOUR(R.string.project_4, R.drawable.abs_picture),
    PROJECTFIVE(R.string.project_5, R.drawable.random_num),
    PROJECTSIX(R.string.project_6, R.drawable.virtual_date),
    PROJECTSEVEN(R.string.project_7, R.drawable.quickiy_pocker),
    PROJECTEIGHT(R.string.project_8, R.drawable.random_words),
    PROJECTNIGHT(R.string.project_9, R.drawable.liston_and_memory),
    PROJECTTEN(R.string.project_10, R.drawable.quickiy_pocker);

    private int nameResId;
    private int labelResId;
    private ListCompetitionEnum(int nameResId, int labelResId){
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
