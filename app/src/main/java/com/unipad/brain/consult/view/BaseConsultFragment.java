package com.unipad.brain.consult.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.brain.main.MainActivity;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class BaseConsultFragment extends Fragment{
    protected MainActivity mActivity;
    protected View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(this.getLayoutById(), container, false);
        return root;
}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化数据;
        initData();
        //初始化事件
        initEvent();

    }

    public abstract int getLayoutById();

    public View getRoot(){
        return root;
    }

    public void initData(){

    }
    public void initEvent(){

    }
}
