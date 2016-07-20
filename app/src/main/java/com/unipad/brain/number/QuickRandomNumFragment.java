package com.unipad.brain.number;

import android.view.View;

import com.unipad.brain.number.view.KeyboardDialog;
import com.unipad.common.Constant;

/**
 * Created by gongkan on 2016/7/18.
 */
public class QuickRandomNumFragment extends  NumberRightFragment{
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
    public void onClick(View view) {
        super.onClick(view);
    }
}
