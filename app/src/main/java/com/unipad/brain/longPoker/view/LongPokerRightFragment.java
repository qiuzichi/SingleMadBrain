package com.unipad.brain.longPoker.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.unipad.AppContext;
import com.unipad.brain.R;
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
public class LongPokerRightFragment extends BasicCommonFragment {
    private RecyclerView recyclerView;
    private LongPokerService service;
    private RecyclerView rememoryPokerRecycle;
    private View shadeView;
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
        ;
    }

    @Override
    public void startMemory() {
        shadeView.setVisibility(View.GONE);
    }

    @Override
    public void startRememory() {
        super.startRememory();
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

    private void setRememoryAdapter(){
        ViewStub viewStub = (ViewStub) mViewParent.findViewById(R.id.browse_proker_muti_stub);
        rememoryPokerRecycle = (RecyclerView) viewStub.inflate();
        OnePokerRecycleAdapter onePokerRecycleAdapter = new OnePokerRecycleAdapter(mActivity,service.pokersQuestion);
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity,2);
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
        //设置布局管理器
        rememoryPokerRecycle.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        rememoryPokerRecycle.setAdapter(onePokerRecycleAdapter);
    }
    private void setMemoryAdapter() {
        HorRecycleAdapter recycleAdapter= new HorRecycleAdapter(mActivity,service.pokersQuestion);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置item间隙
        recyclerView.addItemDecoration(new SpaceItemDecoration(5, -100));
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        recyclerView.setAdapter( recycleAdapter);
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {

    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);
        clearMemoryRecycle();
        service.mode = 1;
        setRememoryAdapter();
    }

    private void clearMemoryRecycle() {
        ((HorRecycleAdapter) recyclerView.getAdapter()).clear();
        recyclerView.setAdapter(null);
        mViewParent.removeViewAt(0);
    }

    @Override
    public void onClick(View v) {

    }

}
