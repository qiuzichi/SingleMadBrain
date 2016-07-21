package com.unipad.brain.number;

import com.unipad.brain.number.view.KeyboardDialog;
import com.unipad.common.Constant;

/**
 * 听记数字界面
 */
public class ListenToWriteNumFragment extends NumberRightFragment{
    private KeyboardDialog mKeyboardDialog;

    @Override
    public boolean isNeedShowCurrent() {
        return true;
    }

    @Override
    public String getCompeteItem() {
        return Constant.getProjectName(mActivity.getProjectId());
    }

    @Override
    public void initAnswerView() {
        mKeyboardDialog = new KeyboardDialog(mActivity);
        mKeyboardDialog.setKeyboardClickListener(this);
    }

    @Override
    public void startRememory() {
        super.startRememory();
        mKeyboardDialog.show();
    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
    }
}
