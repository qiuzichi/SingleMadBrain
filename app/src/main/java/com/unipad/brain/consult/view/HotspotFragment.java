package com.unipad.brain.consult.view;

import android.view.View;
import android.widget.TextView;


import com.unipad.brain.R;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.BaseFragment;
import com.unipad.observer.IDataObserver;

/**
 * 热点
 * Created by jiangLu on 2016/6/20.
 */
public class HotspotFragment extends MainBasicFragment implements IDataObserver {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_hotspot;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(int key, Object o) {

    }
}
