package com.unipad.brain.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心之我的关注
 * Created by Wbj on 2016/4/27.
 */
public class PersonalFavoriteFragment extends PersonalCommonFragment implements IDataObserver {
    private ListView lv_follow;
    private List<CompetitionBean> competitionBeans;
    private PersonCenterService service;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.clear);
        lv_follow =(ListView) mActivity.findViewById(R.id.lv_apple);
        competitionBeans = new ArrayList<CompetitionBean>();
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.USER_FOLLOW,this);
        service.getFollwList(AppContext.instance().loginUser.getUserId());
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

    @Override
    public void onStart() {
        super.onStart();
        thisShowView = 4;
    }

    /**
     * 清空关注列表
     */
    private void clearFavoriteList() {
        ToastUtil.showToast(mTitleBarRightText);
    }

    @Override
    public void update(int key, Object o) {

    }
}
