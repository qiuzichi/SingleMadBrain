package com.unipad.brain.personal;

import android.os.Bundle;
import android.view.View;

import com.unipad.brain.R;
import com.unipad.utils.ToastUtil;

/**
 * 个人中心之我的关注
 * Created by Wbj on 2016/4/27.
 */
public class PersonalFavoriteFragment extends PersonalCommonFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.hidePersonalInfoLayout();
        mTitleBarRightText = mActivity.getString(R.string.clear);
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_favorite_frg;
    }

    @Override
    public void clickTitleBarRightText() {
        this.clearFavoriteList();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 清空关注列表
     */
    private void clearFavoriteList() {
        ToastUtil.showToast(mTitleBarRightText);
    }

}
