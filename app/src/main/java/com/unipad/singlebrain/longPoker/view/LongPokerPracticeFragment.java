package com.unipad.singlebrain.longPoker.view;

/**
 * Created by gongkan on 2016/8/16.
 */
public class LongPokerPracticeFragment extends  LongPokerRightFragment {
    @Override
    protected boolean isMatchMode() {
        return false;
    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);
        mActivity.getService().starRememory();
    }
}
