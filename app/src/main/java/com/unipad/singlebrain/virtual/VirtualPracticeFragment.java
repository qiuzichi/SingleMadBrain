package com.unipad.singlebrain.virtual;

/**
 * Created by gongkan on 2016/8/16.
 */
public class VirtualPracticeFragment extends  VirtualRightFragment {
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
