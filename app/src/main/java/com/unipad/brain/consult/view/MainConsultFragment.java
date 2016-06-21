package com.unipad.brain.consult.view;


import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;
import com.unipad.brain.R;
/**
 * 推荐
 * Created by Administrator on 2016/6/20.
 */
public class MainConsultFragment extends BaseConsultFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private List<String> tabview;
    private List<BaseTagFragment> mList_fragment;

    @Override
    public int getLayoutById() {
        return R.layout.fragment_main_pager_info;
    }

    @Override
    public void initData() {
       //标签页
        mTabLayout = (TabLayout) getRoot().findViewById(R.id.tab_introduction_title);
        //viewpager 显示内容页面
        mViewpager = (ViewPager) getRoot().findViewById(R.id.vp_introduction_pager);
        //tab填充 view集合
        String[] tags = new String[]{"推荐", "赛事", "热点"};

        tabview = new ArrayList<String>();
        mList_fragment = new ArrayList<BaseTagFragment>();
//
//        for(int i=0; i<tags.length; i++){
//
//            TextView tv_check = new TextView(mActivity);
//            TabLayout.LayoutParams params = new TabLayout.LayoutParams(80,TabLayout.LayoutParams.MATCH_PARENT);
//            params.gravity = Gravity.CENTER;
//            tv_check.setLayoutParams(params);
//
//            tv_check.setText(tags[i]);
//            tabview.add(tv_check);
//
//            tv_check = null;
//        }

        tabview.add("推荐");
        tabview.add("赛事");
        tabview.add("热点");

        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        // /还有一个搜索栏 需加入
        mTabLayout.addTab(mTabLayout.newTab().setText(tabview.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabview.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabview.get(2)));


        IntroductionFragment mIntroduction_fragment = new IntroductionFragment(mActivity);
        OccasionsFragment occasion_fragment = new OccasionsFragment(mActivity);
        HotspotFragment hotspot_fragment = new HotspotFragment(mActivity);

        mList_fragment.add(mIntroduction_fragment);
        mList_fragment.add(occasion_fragment);
        mList_fragment.add(hotspot_fragment);

        FindTabAdapter mAdapter = new FindTabAdapter();
        //关联 tab
        mViewpager.setAdapter(mAdapter);

        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewpager);

    }

    class FindTabAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mList_fragment.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseTagFragment tabPager = mList_fragment.get(position);
            View root = tabPager.getRootView();

            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabview.get(position % tabview.size()).toString();
        }
    }
}

