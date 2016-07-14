package com.unipad.brain.home;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.HttpUtils;
import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.view.CompititionMainFragment;
import com.unipad.brain.consult.view.ConsultMainFragment;
import com.unipad.brain.consult.view.InfoListFragment;
import com.unipad.brain.dialog.BaseConfirmDialog;
import com.unipad.brain.dialog.ConfirmDialog;
import com.unipad.brain.home.dao.LoadService;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.io.mina.XmlUtil;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

public class MainHomeFragment extends MainBasicFragment implements InfoListFragment.OnHomePageChangeListener{

    private FrameLayout mLayoutHome;
    private InfoListFragment mLeftFragment;
    private MainBasicFragment mRightFragment;
    private CompititionMainFragment mCompititionMainFragment;
    private ConsultMainFragment mConsultMainFragment;
    private ConfirmDialog mConfirmDialog;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化界面;
        initView();
        // 1  获取本地版本号  2获取服务器 版本号  弹出对话框
        getApplicationVersion();

        showUpdateVersionDialog(mActivity);
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
        if(mRightFragment instanceof ConsultMainFragment)return;
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

    //获取当前应用的版本名称
    private String  getApplicationVersion(){
        PackageInfo mPackageInfo = null;
        String currentVersionName = null;
        PackageManager mPackageManager = mActivity.getPackageManager();
        try {
            mPackageInfo = mPackageManager.getPackageInfo(mActivity.getPackageName(), PackageManager.GET_CONFIGURATIONS);
//            int currentVersionCode = mPackageInfo.versionCode;
            currentVersionName = mPackageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionName;
    }
    //访问网络 获取服务器 上的版本信息
    private void showUpdateVersionDialog(Context context) {
        mConfirmDialog = new ConfirmDialog(context, getString(R.string.update_version_info),
                getString(R.string.cancel),  getString(R.string.confirm_update), dialogListener);
        mConfirmDialog.show();
    }

    private BaseConfirmDialog.OnActionClickListener dialogListener = new BaseConfirmDialog.OnActionClickListener() {
        @Override
        public void onActionRight(BaseConfirmDialog dialog) {
            ToastUtil.showToast("马上进行更新");
            mConfirmDialog.dismiss();
            //从网络下载文件
            loadApkFile("http://192.168.0.104:8090/crazybrain-mng/image/getFile?filePath=\\api\\20160713\\A5DFD9B8A7654F0A80932B8DF297E433.jpg");
        }

        @Override
        public void onActionLeft(BaseConfirmDialog dialog) {
            mConfirmDialog.dismiss();
        }
    };

    private void loadApkFile(String path){
        Intent server = new Intent("com.loaddown.application");
        mActivity.startService(server);
        LoadService.handler.obtainMessage(1,path).sendToTarget();
    }
}
