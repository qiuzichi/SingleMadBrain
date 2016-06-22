package com.unipad.brain.consult.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.brain.R;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.common.BaseFragment;
import com.unipad.utils.LogUtil;


/**
 * Created by 63 on 2016/6/20.
 */
public class ConsultMainFragment extends MainBasicFragment {


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        LogUtil.d("liuxiangg", "onCreateView" + " : " + "onCreateView");
////		if(mView == null)
////         = inflater.inflate(R.layout.fragment_consult_main, null);
////        initView(mView);
////        initData();
////        initListener(mView);
////        return mView;
//    }

    @Override
    public  int getLayoutId(){
        return R.layout.fragment_consult_main;
    }

    @Override
    public void onClick(View v) {

    }
}
