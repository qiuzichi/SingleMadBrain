package com.unipad.brain.consult.entity;

/**
 * Created by 63 on 2016/6/27.
 */
public class ConsultClassBean {
    private int nameResId;
    private int labelResId;
    private boolean isSelected;

    public int getNameResId(){
        return this.nameResId;
    }

    public void setNameResId(int resId){
        this.nameResId = resId;
    }

    public int getLabelResId(){
        return this.labelResId;
    }

    public void setLabelResId(int resId){
        this.labelResId = resId;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
}
