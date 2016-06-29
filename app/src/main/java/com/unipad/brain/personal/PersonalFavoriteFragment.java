package com.unipad.brain.personal;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.personal.bean.CompetitionBean;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.LogUtil;
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
        mTitleBarRightText = mActivity.getString(R.string.my_follow);
        lv_follow =(ListView) mActivity.findViewById(R.id.lv_follow);
        competitionBeans = new ArrayList<CompetitionBean>();
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.FOLLOW_OK,this);

        LogUtil.e("","onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int i = 0;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.e("","onAttach");

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
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        service.unregistDataChangeListenerObj(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (competitionBeans.size() == 0){
            service.getFollwList(AppContext.instance().loginUser.getUserId());
        }
        thisShowView = 4;
    }

    /*列表清空*/
    private void clearFavoriteList() {

    }
    /*更新UI*/
    @Override
    public void update(int key, Object o) {
        switch (key){
            case HttpConstant.USER_FOLLOW:

               competitionBeans.addAll((List<CompetitionBean>)o);
               lv_follow.setAdapter(new CommonAdapter<CompetitionBean>(mActivity, competitionBeans, R.layout.personal_msg_item_layout) {
                   @Override
                   public void convert(ViewHolder holder, CompetitionBean competitionBean) {
                       holder.setText(R.id.txt_year,competitionBean.getCompetitionDate());
                         holder.setText(R.id.txt_name,competitionBean.getName()+ "/" + competitionBean.getProjecNname());
                       holder.setText(R.id.txt_addr,competitionBean.getAddr());
                       holder.setText(R.id.txt_cost,competitionBean.getCost());
                   }
               });
                break;

           }

        }

    }

