package com.unipad.brain.personal;

import android.os.Bundle;
import android.view.View;

import com.unipad.brain.R;
import com.unipad.utils.ToastUtil;

/**
 * 个人中心之我的消息
 * Created by Wbj on 2016/4/27.
 */
public class PersonalMsgFragment extends PersonalCommonFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.clear);
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_frg_msg;
    }

    @Override
    public void clickTitleBarRightText() {
        this.clearMsgList();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 清空消息列表
     */
    private void clearMsgList() {
        ToastUtil.showToast(mTitleBarRightText);
    }

}
