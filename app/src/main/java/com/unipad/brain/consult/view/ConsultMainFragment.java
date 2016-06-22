package com.unipad.brain.consult.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.brain.R;
import com.unipad.common.BaseFragment;
import com.unipad.utils.LogUtil;


/**
 * Created by 63 on 2016/6/20.
 */
public class ConsultMainFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.d("liuxiangg", "onCreateView" + " : " + "onCreateView");
//		if(mView == null)
        mView = inflater.inflate(R.layout., null);
        initView(mView);
        initData();
        initListener(mView);
        return mView;
    }
}
