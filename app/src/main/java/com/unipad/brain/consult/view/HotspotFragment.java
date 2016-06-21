package com.unipad.brain.consult.view;

import android.view.View;
import android.widget.TextView;


import com.unipad.brain.main.MainActivity;
import com.unipad.common.BaseFragment;

/**
 * 热点
 * Created by Administrator on 2016/6/20.
 */
public class HotspotFragment extends BaseTagFragment {

    public HotspotFragment(MainActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
//        View view = View.inflate(mActivity, R.layout.fragment_hotspot, null);
        TextView view  = new TextView(mActivity);
        view.setText("热点新闻");

        return view;
    }
}
