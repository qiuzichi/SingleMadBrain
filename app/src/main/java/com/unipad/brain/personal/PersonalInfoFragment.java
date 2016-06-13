package com.unipad.brain.personal;

import android.os.Bundle;
import android.view.View;

import com.unipad.AppContext;
import com.unipad.UserDetailEntity;
import com.unipad.brain.R;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

/**
 * 个人中心之个人资料
 * Created by Wbj on 2016/4/26.
 */
public class PersonalInfoFragment extends PersonalCommonFragment implements IDataObserver {

    private PersonCenterService service;

    private UserDetailEntity userDetailEntity = AppContext.instance().loginUser;

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity.findViewById(R.id.personal_info_btn_save).setOnClickListener(this);
        mTitleBarRightText = mActivity.getString(R.string.save);
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.PERSONALDATA,this);
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
    public void onDestroyView() {
        service.unregistDataChangeListenerObj(this);
        super.onDestroyView();
    }

    @Override
    protected void saveInfoToServer() {
        super.saveInfoToServer();
        ToastUtil.showToast(R.string.save);
    }

    @Override
    public void update(int key, Object o) {
        switch (key){
            case HttpConstant.PERSONALDATA:
                break;
        };
    }
}
