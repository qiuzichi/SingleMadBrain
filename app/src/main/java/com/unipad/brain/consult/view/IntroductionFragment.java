package com.unipad.brain.consult.view;


import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.unipad.brain.main.MainActivity;
import com.unipad.common.BaseFragment;

/**
 * 推荐
 * Created by Administrator on 2016/6/20.
 */
public class IntroductionFragment extends BaseTagFragment {

    private ListView mListViewTab;

    public IntroductionFragment(MainActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_introduction, null);

        mListViewTab = (ListView) view.findViewById(R.id.lv_introduction_listview );


        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
