package com.unipad.brain.consult.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.brain.main.MainActivity;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class BaseTagFragment {
    protected MainActivity mActivity;
    protected View root;

    public BaseTagFragment(MainActivity mActivity){
        this.mActivity = mActivity;
        initView();

        initData();

        initEvent();
    }


    public abstract View initView();


    public void initData(){

    }
    public void initEvent(){

    }
    public View getRootView(){
        return root;
    }
}
