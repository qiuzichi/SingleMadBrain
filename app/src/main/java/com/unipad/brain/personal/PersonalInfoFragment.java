package com.unipad.brain.personal;

import android.os.Bundle;
import android.view.View;

import com.unipad.brain.R;
import com.unipad.utils.ToastUtil;

/**
 * 个人中心之个人资料
 * Created by Wbj on 2016/4/26.
 */
public class PersonalInfoFragment extends PersonalCommonFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity.findViewById(R.id.personal_info_btn_save).setOnClickListener(this);
        mTitleBarRightText = mActivity.getString(R.string.save);
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_frg_info;
    }

    @Override
    public void clickTitleBarRightText() {
        this.saveInfoToServer();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_info_btn_save:
                this.saveInfoToServer();
                break;
            default:
                break;
        }
    }

    @Override
    protected void saveInfoToServer() {
        super.saveInfoToServer();
        ToastUtil.showToast(R.string.save);
    }

}
