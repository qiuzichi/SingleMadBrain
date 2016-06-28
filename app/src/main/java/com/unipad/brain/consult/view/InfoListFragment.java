package com.unipad.brain.consult.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unipad.brain.R;
import com.unipad.brain.consult.ConsultBaseFragment;
import com.unipad.brain.consult.adapter.MyInfoListAdapter;
import com.unipad.brain.consult.entity.ConsultClassBean;
import com.unipad.brain.consult.entity.ListEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 63 on 2016/6/23.
 */
public class InfoListFragment extends ConsultBaseFragment implements AdapterView.OnItemClickListener{
    private ListView mLvInfos;
    private List<ConsultClassBean> mList;
    private MyInfoListAdapter mInfoListAdapter;
    private int mCurrentSelectedPosition;
    private OnHomePageChangeListener mOnHomePageChangeListener;

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
        initMyListData();
        initAdapter();
    }

    public void initMyListData(){
        ListEnum[] datas = ListEnum.values();
        if(mList == null){
            mList = new ArrayList<ConsultClassBean>();
        }
        for(int i = 0; i < datas.length; i ++){
            ListEnum data=  datas[i];
            ConsultClassBean bean = new ConsultClassBean();
            bean.setNameResId(data.getNameResId());
            bean.setLabelResId(data.getLabelResId());
            if(data.equals(ListEnum.CONSULT_PRESENT)){
                bean.setSelected(true);
                mCurrentSelectedPosition = 0;
            }else{
                bean.setSelected(false);
            }
        }
    }

    public void initAdapter(){
        if(mInfoListAdapter == null){
            mInfoListAdapter = new MyInfoListAdapter(getmContext(), mList, R.layout.layout_consult_info_item);
        }
        mLvInfos.setAdapter(mInfoListAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLvInfos.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        if(position == mCurrentSelectedPosition)return;
        ConsultClassBean item = (ConsultClassBean)parent.getAdapter().getItem(position);
        mList.get(mCurrentSelectedPosition).setSelected(false);
        mCurrentSelectedPosition = position;
        item.setSelected(true);
        mInfoListAdapter.notifyDataSetChanged();
        if(mOnHomePageChangeListener != null) {
            switch (item.getNameResId()) {
                case R.string.consult_present:
                    mOnHomePageChangeListener.onNeedConsultPageShow();
                    break;
                case R.string.competition_present:
                    mOnHomePageChangeListener.onNeedCompetitionPageShow();
                    break;
            }
        }
    }

    public interface OnHomePageChangeListener{
        void onNeedConsultPageShow();
        void onNeedCompetitionPageShow();
    }

    public void setOnHomePageChangeListener(OnHomePageChangeListener onHomePageChangeListener){
        this.mOnHomePageChangeListener = onHomePageChangeListener;
    }

    public OnHomePageChangeListener getOnHomePageChangeListener(){
        return mOnHomePageChangeListener;
    }
}
