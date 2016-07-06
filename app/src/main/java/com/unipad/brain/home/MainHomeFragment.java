package com.unipad.brain.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.ConsultBaseFragment;
import com.unipad.brain.consult.entity.ConsultClassBean;
import com.unipad.brain.consult.view.CompititionMainFragment;
import com.unipad.brain.consult.view.ConsultMainFragment;
import com.unipad.brain.consult.view.InfoListFragment;
import com.unipad.brain.consult.view.IntroductionFragment;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.Constant;
import com.unipad.http.HitopNewsList;

public class MainHomeFragment extends MainBasicFragment implements InfoListFragment.OnHomePageChangeListener{

    private FrameLayout mLayoutHome;


    private InfoListFragment mLeftFragment;

    private MainBasicFragment mRightFragment;

    CompititionMainFragment mCompititionMainFragment;
    ConsultMainFragment mConsultMainFragment;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化界面;
        initView();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    //界面的初始化
    private void initView() {

        initLeftFragment();

        initHomeFragment();
    }

    @Override
    public void onClick(View v) {

    }

    public void initLeftFragment() {
        if (mLeftFragment == null) {
            mLeftFragment = new InfoListFragment();
        }
        FragmentTransaction transaction = getTransaction();
        transaction.replace(R.id.fl_mainpager_left, mLeftFragment);

        transaction.commit();
        //监听事件
        mLeftFragment.setOnHomePageChangeListener(this);
    }

    // 1.获取FragmentManager对象
    public FragmentTransaction getTransaction() {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        return transaction;
    }

    public void initHomeFragment() {
        mLayoutHome = (FrameLayout) mActivity.findViewById(R.id.fl_mainpager_info);
        FragmentTransaction transaction = getTransaction();
        if(mConsultMainFragment == null) {
            mConsultMainFragment = new ConsultMainFragment();
            mRightFragment = mConsultMainFragment;
        }
        transaction.add(R.id.fl_mainpager_info, mRightFragment);
        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    public void onNeedConsultPageShow(){
//        if(mRightFragment instanceof ConsultMainFragment)return;
//        mRightFragment = new ConsultMainFragment();
//        FragmentTransaction transaction = getTransaction();
//        transaction.add(R.id.fl_mainpager_info, mRightFragment);
        FragmentTransaction transaction = getTransaction();
        transaction.hide(mRightFragment);

        mRightFragment = mConsultMainFragment;
        if(mConsultMainFragment == null){
            mConsultMainFragment = new ConsultMainFragment();
            mRightFragment = mConsultMainFragment;
            transaction.add(R.id.fl_mainpager_info, mRightFragment);
        }else {
            transaction.show(mRightFragment);
        }

        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    @Override
    public void onNeedCompetitionPageShow() {
//        if(mRightFragment instanceof CompititionMainFragment){
//            return;
//        }
        FragmentTransaction transaction = getTransaction();
        transaction.hide(mRightFragment);

        mRightFragment = mCompititionMainFragment;
        if(mCompititionMainFragment == null){
            mCompititionMainFragment = new CompititionMainFragment();
            mRightFragment = mCompititionMainFragment;
            transaction.add(R.id.fl_mainpager_info, mRightFragment);
        }else {
            transaction.show(mRightFragment);
        }


        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    private void hideAll(){
        FragmentTransaction transaction = getTransaction();
        if(mConsultMainFragment != null && mConsultMainFragment.isAdded()){
            transaction.hide(mConsultMainFragment);
        }
        if(mCompititionMainFragment != null && !mCompititionMainFragment.isAdded()){
            transaction.hide(mCompititionMainFragment);
        }
    }
}
