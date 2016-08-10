package com.unipad.brain.absPic.view;

/**
 * Created by gongkan on 2016/8/4.
 */
public class PractiseFigureFragment extends  AbsFigureFragment{
    @Override
    protected boolean isMatchMode() {
        return false;
    }

    @Override
    public void startRememory() {
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        super.rememoryTimeToEnd(answerTime);
    }
}
