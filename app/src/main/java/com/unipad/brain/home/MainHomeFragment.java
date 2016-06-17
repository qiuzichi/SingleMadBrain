package com.unipad.brain.home;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.brain.R;

public class MainHomeFragment extends MainBasicFragment {
    ListView lv_homepage;
    FrameLayout fl_homepager;
    TextView txt_back;
    TextView txt_showmain;
    TextView txt_flod;
    TextView txt_time;
    TextView txt_power;
    TextView txt_wifi;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化界面;
        initView();
    }
    //界面的初始化
    private void initView(){

        //listview 组件
        lv_homepage = (ListView)mActivity.findViewById(R.id.lv_mainpager_info);
        //frame 组件
        fl_homepager = (FrameLayout) mActivity.findViewById(R.id.fl_mainpager_info);
        //返回键
        txt_back = (TextView) mActivity.findViewById(R.id.tv_title_back_left);
        //主菜单组件
        txt_showmain = (TextView) mActivity.findViewById(R.id.tv_title_mainmenu_show);
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
        public int getLayoutId() {
            return R.layout.main_home_fragment;
        }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_title_back_left:  //返回键
                mActivity.finish();
                break;
            case R.id.tv_title_mainmenu_show:  //返回到主界面


                break;
            case R.id.tv_title_main_flod_show:  //折叠显示;

                break;

        }
    }
}
