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
import com.unipad.brain.consult.view.ConsultMainFragment;
import com.unipad.brain.consult.view.InfoListFragment;
import com.unipad.brain.consult.view.IntroductionFragment;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.Constant;
import com.unipad.http.HitopNewsList;

public class MainHomeFragment extends MainBasicFragment {

    private FrameLayout mLayoutHome;

    //    private IntroductionFragment introductionFragment;
    private Fragment mLeftFragment;

    private MainBasicFragment mRightFragment;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化界面;
        initView();
//        introductionFragment = new IntroductionFragment();
//        fl_homepager.addView(introductionFragment.getRoot());

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    //界面的初始化
    private void initView() {

        //左侧组件
//        fl_hmepage_left = (FrameLayout)mActivity.findViewById(R.id.fl_mainpager_left);
        initLeftFragment();
        //frame 组件
//        fl_homepager = (FrameLayout) mActivity.findViewById(R.id.fl_mainpager_info);
        initHomeFragment();

    }

    @Override
    public void onClick(View v) {

    }

    public void initLeftFragment() {
        if (mLeftFragment == null) {
            mLeftFragment = new InfoListFragment();
        }
        getTransaction().replace(R.id.fl_mainpager_left, mLeftFragment);
    }

    // 1.获取FragmentManager对象
    public FragmentTransaction getTransaction() {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        return transaction;
    }

    public void initHomeFragment() {
        mLayoutHome = (FrameLayout) mActivity.findViewById(R.id.fl_mainpager_info);
        mRightFragment = new ConsultMainFragment();
        FragmentTransaction transaction = getTransaction();
        transaction.add(R.id.fl_mainpager_info, mRightFragment);
        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }
}
