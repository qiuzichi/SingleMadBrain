package com.unipad.brain.consult.view;


import android.view.View;
import android.widget.TextView;

import com.unipad.brain.main.MainActivity;
import com.unipad.common.BaseFragment;

/**
 * 赛事
 * Created by Administrator on 2016/6/20.
 */
public class OccasionsFragment extends BaseTagFragment {
    public OccasionsFragment(MainActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        TextView view  = new TextView(mActivity);
        view.setText("赛事新闻");

        return view;
    }
}
