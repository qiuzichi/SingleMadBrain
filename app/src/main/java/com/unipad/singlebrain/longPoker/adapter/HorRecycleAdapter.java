package com.unipad.singlebrain.longPoker.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipad.singlebrain.R;
import com.unipad.singlebrain.longPoker.entity.LongPokerEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gongkan on 2016/7/13.
 */
public class HorRecycleAdapter extends RecyclerView.Adapter<HorRecycleAdapter.PokerViewHolder> {
    private Context mContext;
    private List<LongPokerEntity> mDatas;
    private LayoutInflater inflater;
    public  HorRecycleAdapter(Context context, List<LongPokerEntity> datas){
        this. mContext=context;
        if (datas == null) {
            this.mDatas = new ArrayList<>();
        }else {
            this.mDatas = datas;
        }
        inflater= LayoutInflater.from(mContext);
    }
    @Override
    public PokerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            View view = inflater.inflate(R.layout.long_cardview,parent, false);
            return new PokerViewHolder(view);
        } else {
            return  new PokerViewHolder(new TextView(mContext));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position%53 == 0?0:-1;
    }

    @Override
    public void onBindViewHolder(PokerViewHolder holder, int position) {
        if (getItemViewType(position) == -1) {
            ImageView image  = new ImageView(mContext);
            image.setImageResource(mDatas.get(position).resId);
          // x.image().bind(image,"drawable://"+mDatas.get(position).resId);
            ((CardView) holder.tv).addView(image);
        } else {
            TextView textView = (TextView) holder.tv;
            textView.setTextDirection(View.TEXT_DIRECTION_RTL);
            textView.setGravity(Gravity.CENTER);
            textView.setText(mContext.getString(R.string.seqening) + "\n"+(position/53 + 1)+"\n" +
                    mContext.getString(R.string.round_poker) + "\n" + mContext.getString(R.string.round_poker_2));
            textView.setTextSize(28);
        }
    }
    public void clear(){
        mDatas = null;
    }
    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

     class PokerViewHolder extends  RecyclerView.ViewHolder {

       public View tv;

        public PokerViewHolder(View view) {
            super(view);
            tv=view;
        }

    }
}
