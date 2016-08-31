package com.unipad.singlebrain.personal;

import android.os.Bundle;
import android.view.View;

import com.unipad.singlebrain.R;
import com.unipad.utils.ToastUtil;

/**
 * 个人中心之我的钱包
 * Created by Wbj on 2016/4/27.
 */
public class PersonalWalletFragment extends PersonalCommonFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.personal_wallet);
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_frg_wallet;
    }

    @Override
    public void clickTitleBarRightText() {
        this.tempClick();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();
        thisShowView = 5;
    }

    private void tempClick() {
        ToastUtil.showToast(mTitleBarRightText);
    }

}
