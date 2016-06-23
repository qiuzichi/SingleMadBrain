package com.unipad.brain.home;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.view.IntroductionFragment;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.Constant;
import com.unipad.http.HitopNewsList;

public class MainHomeFragment extends MainBasicFragment {

    private FrameLayout fl_homepager;
    private TextView txt_back;
    private TextView txt_showmain;
    private TextView txt_flod;
    private TextView txt_time;
    private TextView txt_power;
    private TextView txt_wifi;
    private FrameLayout fl_hmepage_left;
    private IntroductionFragment introductionFragment;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化界面;
        initView();
        introductionFragment = new IntroductionFragment();
//        fl_homepager.addView(introductionFragment.getRoot());

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    //界面的初始化
    private void initView(){

        //左侧组件
        fl_hmepage_left = (FrameLayout)mActivity.findViewById(R.id.fl_mainpager_left);
        //frame 组件
        fl_homepager = (FrameLayout) mActivity.findViewById(R.id.fl_mainpager_info);

        //返回键
        txt_back = (TextView) mActivity.findViewById(R.id.tv_title_back_left);
        //主菜单组件
        txt_showmain = (TextView) mActivity.findViewById(R.id.tv_title_mainmenu_show);
        //折叠显示
        txt_flod = (TextView) mActivity.findViewById(R.id.tv_title_main_flod_show);
        //当前时间
        txt_time = (TextView) mActivity.findViewById(R.id.tv_current_time_text);
        //电量
        txt_power = (TextView) mActivity.findViewById(R.id.tv_title_battery_power_icon);
        //wifi标示
        txt_wifi = (TextView) mActivity.findViewById(R.id.tv_title_wifi_icon);



        txt_back.setOnClickListener(this);
        txt_showmain.setOnClickListener(this);
        txt_flod.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
