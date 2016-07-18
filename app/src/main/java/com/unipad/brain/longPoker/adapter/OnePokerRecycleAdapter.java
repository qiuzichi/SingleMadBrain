package com.unipad.brain.longPoker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.unipad.brain.R;
import com.unipad.brain.longPoker.entity.LongPokerEntity;
import com.unipad.brain.longPoker.view.SpinerPopWindow;
import com.unipad.common.Constant;
import com.unipad.common.widget.HIDDialog;
import com.unipad.utils.LogUtil;
import com.unipad.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongkan on 2016/7/14.
 */
public class OnePokerRecycleAdapter extends RecyclerView.Adapter<OnePokerRecycleAdapter.APokerViewHolder> {
    private Context mContext;
    private List<LongPokerEntity> mDatas;
    private LayoutInflater inflater;
    private SpinerPopWindow spinnerPopWindow;
    private ArrayList<String> dianShuList;
    private boolean isFirst = true;
    public OnePokerRecycleAdapter(Context context, List<LongPokerEntity> datas) {
        this.mContext = context;
        if (datas == null) {
            this.mDatas = new ArrayList<>();
        } else {
            this.mDatas = datas;
        }
        inflater = LayoutInflater.from(mContext);
    }

    private boolean isHeader(int position) {
        return position % 53 == 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 53 == 0 ? 0 : 1;
    }

    @Override
    public APokerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = new TextView(mContext);
        } else {
            view = inflater.inflate(R.layout.recycle_one_poker_item, parent, false);
        }
        return new APokerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OnePokerRecycleAdapter.APokerViewHolder holder, final int position) {
        LogUtil.e("","adapter postion = "+position);
        if (isFirst) {
            if (position+1 == getItemCount()) {
                isFirst = false;
            }
            if (position > 52) {
                return;
            }
        }
        LogUtil.e("","first = "+isFirst);
        if (getItemViewType(position) == 0) {
            TextView tv = (TextView) holder.view;
            tv.setText("第" + (position / 53 + 1) + "副");
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(30);
        } else {
            final LongPokerEntity poker = mDatas.get(position);
            TextView tv_num = (TextView) holder.view.findViewById(R.id.num);
            final TextView textDianView =  (TextView) holder.view.findViewById(R.id.tv_value);

            RadioGroup radioGroup = (RadioGroup) holder.view.findViewById(R.id.radio_group);
           // String radioText = radioGroup.getCheckedRadioButtonId()
            textDianView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSpinnerPopWinsow(textDianView, position);
                }
            });
                tv_num.setText(String.valueOf(position % 53));

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (TextUtils.isEmpty(poker.getUserAnswer())) {
                            return;
                        }
                        if (group.getChildAt(0).getId() == checkedId) {
                            poker.setUserAnswer("黑桃" + poker.getUserAnswer().substring(2, 3));
                        } else if (group.getChildAt(1).getId() == checkedId) {
                            poker.setUserAnswer("红桃" + poker.getUserAnswer().substring(2, 3));
                        } else if (group.getChildAt(2).getId() == checkedId) {
                            poker.setUserAnswer("梅花" + poker.getUserAnswer().substring(2, 3));
                        } else if (group.getChildAt(3).getId() == checkedId) {
                            poker.setUserAnswer("方块" + poker.getUserAnswer().substring(2, 3));
                        }
                    }
                });
            LogUtil.e("","第"+position+"个，"+poker.getUserAnswer());
            }

    }

    @Override
    public int getItemCount() {
        if (isFirst) {
            return 52;
        }
        return mDatas.size();
    }

    class APokerViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public APokerViewHolder(View view) {
            super(view);
            this.view = view;
        }

    }
    private void showSpinnerPopWinsow(final TextView textView , final int dataPositon){
        if (spinnerPopWindow == null) {
            spinnerPopWindow = new SpinerPopWindow(mContext);
            dianShuList = new ArrayList<>();
            dianShuList.add("");
            dianShuList.add("A");
            dianShuList.add("2");
            dianShuList.add("3");
            dianShuList.add("4");
            dianShuList.add("5");
            dianShuList.add("6");
            dianShuList.add("7");
            dianShuList.add("8");
            dianShuList.add("9");
            dianShuList.add("10");
            dianShuList.add("J");
            dianShuList.add("Q");
            dianShuList.add("K");
            spinnerPopWindow.refreshData(dianShuList, 0);
            spinnerPopWindow.setWidth(textView.getWidth());
            spinnerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                @Override
                public void onItemClick(int pos) {
                    LongPokerEntity poker = mDatas.get(dataPositon);
                    String text = dianShuList.get(pos);
                    if (pos == 0) {
                        poker.setUserAnswer("");
                    } else {
                        poker.setUserAnswer(poker.getUserAnswer().substring(0, 2)+text);
                    }
                    textView.setText(poker.getUserAnswer());
                    //spinnerPopWindow.dismiss();
                }
            });
        }
        if (!spinnerPopWindow.isShowing()){
            spinnerPopWindow.showAsDropDown(textView);
        }
    }
}
