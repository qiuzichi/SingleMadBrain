package com.unipad.brain.home.competitionpj.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.ProjectBean;
import com.unipad.brain.home.iview.IHome;
import com.unipad.brain.home.util.MyTools;
import com.unipad.common.BaseFragment;

/**
 * @author gongjiebin
 * @描述： 首页
 */
public class HomeFragment extends BaseFragment implements IHome {
    final public static String TAG = "HomeFragment";
    private static HomeFragment homeFragment;

    @ViewInject(R.id.lv_project)
    private ListView lv_project;

    private HomePresenter homePresenter;

    @ViewInject(R.id.fl_project)
    private FrameLayout fl_project;

    // 侧边栏的start -------------///
//    @ViewInject(R.id.txt_title)
//    private TextView txt_title;
//
//    @ViewInject(R.id.txt_attention_content)
//    private TextView txt_attention_content;
//
//    @ViewInject(R.id.txt_memory_content)
//    private TextView txt_memory_content;
//
//    @ViewInject(R.id.txt_recall_content)
//    private TextView txt_recall_content;
//
//    @ViewInject(R.id.txt_function_content)
//    private TextView txt_function_content;

    public static HomeFragment getHomeFragment() {
        if (null == homeFragment)
            return homeFragment = new HomeFragment();
        return homeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.main_compete_fragment, container, false);
        ViewUtils.inject(this, homeView);
        initView(homeView);
        return homeView;
    }


    @OnItemClick(R.id.lv_project)
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        homePresenter.changeUI(arg2);
        homePresenter.setProjectContent(arg2);
    }

    @Override
    public void initView(View view) {
        homePresenter = new HomePresenter(this);
        View sidebarView = homePresenter.setSidebar();
        ViewUtils.inject(this, sidebarView);
        //homePresenter.switchFragment(R.id.fl_project, homePresenter.getNullClassFragment(), NullClassFragment.TAG);
    }


    @OnClick(R.id.img_close)
    public void onCloseClick(View view) {
        homePresenter.setMenuOpen();
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    @Override
    public void request(String jsonStr, int flag) {

    }

    @Override
    public void showToast(String checkResult) {
        MyTools.showToast(getContext(), checkResult);
    }

    @Override
    public void loadingDialog(long total, long current, boolean isUploading) {

    }

    @Override
    public void showDialog(boolean isOpen) {

    }

    @Override
    public void setAdapter(BaseAdapter baseAdapter) {
        baseAdapter.notifyDataSetChanged();
        lv_project.setAdapter(baseAdapter);
    }

    @Override
    public void setSlidingMenuTxt(ProjectBean projectBean) {
//        txt_title.setText(projectBean.getName() + getResources().getString(R.string.action_rule));
//        txt_attention_content.setText(projectBean.getAttention());
//        txt_memory_content.setText(projectBean.getMemory_rule());
//        txt_recall_content.setText(projectBean.getRecall_rule());
//        txt_function_content.setText(projectBean.getRecord_rule());
    }

}
