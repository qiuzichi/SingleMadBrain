package com.unipad.brain.consult.view;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabWidget;
import android.widget.TextView;

import com.slidingmenu.lib.CustomViewAbove;
import com.unipad.brain.R;
import com.unipad.brain.consult.ConsultBaseFragment;
import com.unipad.brain.consult.entity.ConsultTab;

import java.lang.reflect.Field;


/**
 * Created by 63 on 2016/6/20.
 */
public class ConsultMainFragment extends ConsultBaseFragment{
    private TabWidget mTabWidget;
    private ViewPager mViewPager;
    private int mCurrentIndex;
    private ConsultTab[] mConsultTabs;

    @Override
    public  int getLayoutId(){
        return R.layout.fragment_consult_main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTabWidget = (TabWidget) view.findViewById(R.id.tabwidget_consult_main);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager_consult);

        initMyTabWidget();
        initViewPager();
    }

    private void initMyTabWidget(){
        mConsultTabs = ConsultTab.values();
        LayoutInflater layoutInflater = LayoutInflater.from(getmContext());
        if(mConsultTabs != null){
            for (int i = 0; i < mConsultTabs.length; i++) {
                ConsultTab enumTab = mConsultTabs[i];
                if(enumTab == null){
                    continue;
                }
                View tabWidgetItem = layoutInflater.inflate(R.layout.layout_tabwidget_item, null);
                TextView tvTabItem = (TextView) tabWidgetItem.findViewById(R.id.tv_tab_item);
                tvTabItem.setText(enumTab.getType());
                if(i == 0){
                    tabWidgetItem.setBackgroundColor(Color.RED);
                }else{
                    tabWidgetItem.setBackgroundColor(Color.BLUE);
                }

                tabWidgetItem.setTag(i);
                mTabWidget.addView(tabWidgetItem);
                tabWidgetItem.setOnClickListener(mTabClickListener);

            }
        }
        mTabWidget.setCurrentTab(0);
    }

    private void initViewPager(){
        FragmentPagerAdapter adapter = new ChildsFragmentAdapter(getChildFragmentManager()
                );
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(mSimpleOnPageChangeListener);
    }

    private ViewPager.SimpleOnPageChangeListener mSimpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
        public void onPageSelected(int position) {
            if(position == mCurrentIndex)return;
            setTabSelectedChanged(mCurrentIndex, false);
            mCurrentIndex = position;
            setTabSelectedChanged(mCurrentIndex, true);
        }
    };

    private View.OnClickListener mTabClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag();
            if (index == mCurrentIndex) {
                return;
            }
            mViewPager.setCurrentItem(index);
            setTabSelectedChanged(mCurrentIndex, false);
            mCurrentIndex = index;
            setTabSelectedChanged(mCurrentIndex, true);
        }

    };

    private void setTabSelectedChanged(int index, boolean selected){
        View itemView = mTabWidget.getChildTabViewAt(index);
        itemView.setBackgroundColor(selected ? Color.RED : Color.BLUE);
    }

    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    class ChildsFragmentAdapter extends FragmentPagerAdapter {
        public ChildsFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            initTabs();
            return com.unipad.brain.consult.Manager.FragmentManager.getFragment(mConsultTabs[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            initTabs();
            return mConsultTabs[position].getType();
        }

        @Override
        public int getCount() {
            initTabs();
            return mConsultTabs.length;
        }
    }

    private void initTabs() {
        if (mConsultTabs == null) {
            mConsultTabs = ConsultTab.values();
        }
    }
}
