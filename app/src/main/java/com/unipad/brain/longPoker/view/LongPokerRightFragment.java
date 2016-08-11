package com.unipad.brain.longPoker.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.longPoker.IProgress;
import com.unipad.brain.longPoker.adapter.AllPokerAnswerAdapter;
import com.unipad.brain.longPoker.adapter.HorRecycleAdapter;
import com.unipad.brain.longPoker.adapter.OnePokerRecycleAdapter;
import com.unipad.brain.longPoker.dao.LongPokerService;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by gongkan on 2016/7/13.
 */
public class LongPokerRightFragment extends BasicCommonFragment implements IProgress {
    private RecyclerView recyclerView;
    private LongPokerService service;
    private RecyclerView[] rememoryPokerRecycle;
    private View shadeView;
    private ViewPager viewpager;
   // private GridLayoutManager layoutManager;
    private LinearLayout memoryLayout;
    @Override
    public int getLayoutId() {
        return R.layout.long_poker_frg_right;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) mViewParent.findViewById(R.id.recycle_view);

        service = (LongPokerService) AppContext.instance().getService(Constant.LONG_POKER_SERVICE);
        //setMemoryAdapter();
        shadeView = mViewParent.findViewById(R.id.view_shade_answer);
        memoryLayout = (LinearLayout) mViewParent.findViewById(R.id.browse_proker_hlayout);
    }

    @Override
    public void startMemory() {
        mViewParent.addView(memoryLayout);
        shadeView.setVisibility(View.GONE);
    }

    @Override
    public void startRememory() {
        super.startRememory();
        setRememoryAdapter();
    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        setMemoryAdapter();
    }

    @Override
    public void pauseGame() {
        super.pauseGame();
        shadeView.setVisibility(View.VISIBLE);
    }

    @Override
    public void reStartGame() {
        super.reStartGame();
        shadeView.setVisibility(View.GONE);
    }

    private GridLayoutManager createGridLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 53 == 0) {
                    return 2;
                } else {
                    return 1;
                }

            }
        });
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        return layoutManager;
    }

    private void setRememoryAdapter() {
        ViewStub viewStub = (ViewStub) mViewParent.findViewById(R.id.browse_proker_muti_stub);
        viewpager = (ViewPager) viewStub.inflate();
        rememoryPokerRecycle = new RecyclerView[service.howMany];

        for (int i = 0; i < service.howMany; i++) {
            RecyclerView recyclerView = new RecyclerView(getActivity());
            recyclerView.setLayoutManager(createGridLayoutManager());
            rememoryPokerRecycle[i] = recyclerView;
        }
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return rememoryPokerRecycle.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(rememoryPokerRecycle[position]);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                RecyclerView recyclerView = rememoryPokerRecycle[position];
                if (recyclerView.getAdapter() == null) {
                    LogUtil.e("","viewpager postion = "+position);
                    OnePokerRecycleAdapter onePokerRecycleAdapter = new OnePokerRecycleAdapter(getActivity(), service.pokersQuestion, position * 53, 53);
                    onePokerRecycleAdapter.setProgress(LongPokerRightFragment.this);
                    recyclerView.setAdapter(onePokerRecycleAdapter);
                }
                container.addView(rememoryPokerRecycle[position]);


                return rememoryPokerRecycle[position];
            }

        };

        viewpager.setAdapter(pagerAdapter);
        //设置布局管理器
    }

    private void setMemoryAdapter() {
        HorRecycleAdapter recycleAdapter = new HorRecycleAdapter(getActivity(), service.pokersQuestion);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置item间隙
        recyclerView.addItemDecoration(new SpaceItemDecoration(5, -100));
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        mViewParent.removeView(memoryLayout);
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        super.rememoryTimeToEnd(answerTime);
    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);
        clearMemoryRecycle();
        service.mode = 1;
        sendMsgToPreper();
    }

    private void clearMemoryRecycle() {
        ((HorRecycleAdapter) recyclerView.getAdapter()).clear();
        recyclerView.setAdapter(null);
        mViewParent.removeView(memoryLayout);
        memoryLayout =null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }
}
