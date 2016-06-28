package com.unipad.brain.consult.view;

import android.view.View;
import android.widget.ListView;

import com.unipad.brain.R;
import com.unipad.brain.consult.ConsultBaseFragment;
import com.unipad.brain.consult.entity.ConsultClassBean;
import com.unipad.brain.consult.entity.ListEnum;

import java.util.List;

/**
 * Created by 63 on 2016/6/23.
 */
public class InfoListFragment extends ConsultBaseFragment{
    private ListView mLvInfos;
    private List<ConsultClassBean> mList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_info_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLvInfos = (ListView)view.findViewById(R.id.lv_info_list);
    }

    @Override
    protected void initData() {
        super.initData();
        ListEnum[] datas = ListEnum.values();
//        mList.
    }
}
