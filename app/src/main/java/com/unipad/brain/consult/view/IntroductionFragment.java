package com.unipad.brain.consult.view;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 * Created by jianglu on 2016/6/20.
 */
public class IntroductionFragment extends BaseTagFragment {

    private ListView mListViewTab;
    private List<NewEntity> newsDatas = new ArrayList<NewEntity>();

    public IntroductionFragment(MainActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.l, null);
        mListViewTab = (ListView) view.findViewById(R.id.lv_introduction_listview );
        return view;
    }

    @Override
    public void initData() {
        MyAdapter mListViewAdapter = new MyAdapter();
        mListViewTab.setAdapter(mListViewAdapter);


        super.initData();
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return newsDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //标签tag缓存
            ViewHolder holder ;
            if(convertView == null){
                convertView = View.inflate(mActivity, R.layout.,null);
                holder = new ViewHolder();
                holder.iv_head_icon = (ImageView) convertView.findViewById(R.id.iv_item_introduction_icon);


                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            if(position % 2 == 0){
                //当是复数页面的时候  红色界面

            }else {
                //当时单数页面 是浅蓝色页面
            }


            return convertView;
        }

        class ViewHolder {
            ImageView iv_head_icon;
            TextView  tv_newsTitle;
            View view_line_split;
            ImageButton ib_pager_favorite;
            ImageButton ib_pager_zan;
            ImageButton ib_pager_comment;
            ImageButton ib_pager_share;
            TextView  tv_updateTime;
            TextView  tv_checkDetail;
        }
    }
}
