package com.unipad.brain.consult.entity;

import com.unipad.brain.R;

/**
 * Created by hasee on 2016/7/4.
 */
public enum ListCompetitionEnum {
    PROJECTONE(R.string.project_1, R.drawable.person_icon),
    PROJECTTWO(R.string.project_2, R.drawable.person_icon),
    PROJECTTHREE(R.string.project_3, R.drawable.person_icon),
    PROJECTFOUR(R.string.project_4, R.drawable.person_icon),
    PROJECTFIVE(R.string.project_5, R.drawable.person_icon),
    PROJECTSIX(R.string.project_6, R.drawable.person_icon),
    PROJECTSEVEN(R.string.project_7, R.drawable.person_icon),
    PROJECTEIGHT(R.string.project_8, R.drawable.person_icon),
    PROJECTNIGHT(R.string.project_9, R.drawable.person_icon),
    PROJECTTEN(R.string.project_10, R.drawable.person_icon);


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
