package com.unipad.singlebrain.portraits.view;

/**
 * Created by gongkan on 2016/8/16.
 */
public class HeadPracticeFragment extends HeadPortraitFragment {
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
