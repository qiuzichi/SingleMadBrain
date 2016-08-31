package com.unipad.singlebrain.longPoker.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.singlebrain.R;
import com.unipad.singlebrain.longPoker.dao.LongPokerService;

/**
 * Created by gongkan on 2016/7/14.
 */
public class AllPokerAnswerAdapter extends RecyclerView.Adapter<AllPokerAnswerAdapter.AllPokerViewHolder>{
    private int howMany;//扑克牌中的第几副how
    private Context mContext;
    private LayoutInflater inflater;
    private LongPokerService service;
    public AllPokerAnswerAdapter(Context context,int howMany,LongPokerService service){
        mContext = context;
        this.howMany = howMany;
        this.service = service;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AllPokerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllPokerViewHolder(inflater.inflate(R.layout.one_poker_item_recycleview,parent, false));
    }

    @Override
    public void onBindViewHolder(AllPokerViewHolder holder, int position) {
        RecyclerView recyclerView = holder.recyclerView;
        if (recyclerView.getAdapter() == null){
            setMemoryAdapter(recyclerView,position);
        }
    }
    private void setMemoryAdapter(RecyclerView recyclerView,int position) {
       // OnePokerRecycleAdapter recycleAdapter= new OnePokerRecycleAdapter(mContext,service.pokersQuestion);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
       // recyclerView.setAdapter( recycleAdapter);
    }
    @Override
    public int getItemCount() {
        return howMany;
    }

    class AllPokerViewHolder extends  RecyclerView.ViewHolder {

        public RecyclerView recyclerView;
        public AllPokerViewHolder(View view) {
            super(view);
            recyclerView = (RecyclerView) view;
        }

    }
}
