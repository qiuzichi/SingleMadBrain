package com.unipad.singlebrain.longPoker.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.unipad.singlebrain.App;
import com.unipad.singlebrain.R;
import com.unipad.singlebrain.longPoker.IProgress;
import com.unipad.singlebrain.longPoker.entity.LongPokerEntity;
import com.unipad.utils.LogUtil;
import com.unipad.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gongkan on 2016/7/14.
 */
public class OnePokerRecycleAdapter extends RecyclerView.Adapter<OnePokerRecycleAdapter.APokerViewHolder> {
    private Context mContext;
    private List<LongPokerEntity> mDatas;
    private LayoutInflater inflater;
    private int start;
    private int length;
    private int mCurrentPosition =1;
    private int[] resDrawableId = new int[]{R.drawable.heitao, R.drawable.hongtao, R.drawable.meihua, R.drawable.fangkuai};
    private List<String> dianShuList;
    private IProgress progress;
    private TextView numTextView;
    private boolean isCurrentPage;

    public OnePokerRecycleAdapter(Context context, List<LongPokerEntity> datas, int start, int length,TextView textView) {
        this.mContext = context;
        if (datas == null) {
            this.mDatas = new ArrayList<>();
        } else {
            this.mDatas = datas;
        }
        dianShuList = Arrays.asList(mContext.getResources().getStringArray(R.array.poker));
        this.start = start;
        this.length = length;
        inflater = LayoutInflater.from(mContext);
        numTextView = textView;
        if (start == 0){
            isCurrentPage = true;
        }
    }
    public void setIsCurrentPage(boolean isCurrentPage){
        this.isCurrentPage = isCurrentPage;
    }
    private boolean isHeader(int position) {
        return position % 53 == 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 53 == 0 ? 0 : 1;
    }
    public int getCurrent(){
        return mCurrentPosition;
    }
    public LongPokerEntity getPositionItem(int position){
        return mDatas.get(start + position);
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
        LogUtil.e("", "adapter postion = " + (start + position));
        if (getItemViewType(position) == 0) {
            TextView tv = (TextView) holder.view;
            tv.setText(mContext.getString(R.string.seqening) +" " + (start / 53 + 1) + " " + mContext.getString(R.string.round_poker) + " "+ mContext.getString(R.string.round_poker_2));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(30);
        } else {

            final LongPokerEntity poker = mDatas.get(start + position);

            TextView tv_num = (TextView) holder.view.findViewById(R.id.num);
            final EditText textDianView = (EditText) holder.view.findViewById(R.id.tv_value);
            textDianView.setInputType(InputType.TYPE_NULL);
            final int userAnswer = poker.getUserAnswer();
            final RadioGroup radioGroup = (RadioGroup) holder.view.findViewById(R.id.radio_group);
            if (userAnswer != 0) {
                Drawable image = mContext.getResources().getDrawable(poker.getHuaseId());
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textDianView.setCompoundDrawables(image, null, null, null);
                textDianView.setText(dianShuList.get((userAnswer - 1) % 13 + 1));
            } else {
                textDianView.setCompoundDrawables(null, null, null, null);
                textDianView.setText("");
            }
            if (mCurrentPosition == position) {
                if (isCurrentPage) {
                    LogUtil.e("", mCurrentPosition + "requestFocus");

                    textDianView.requestFocus();
                }
            }
            LogUtil.e("", "start:" + start + ",positon=" + position + "," + poker.name + ",userAnswer:" + userAnswer + ",huase：" + poker.getHuaseId());
            textDianView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mCurrentPosition = position;
                    numTextView.setText(mContext.getResources().getString(R.string.long_poker_answer_num_tip,mCurrentPosition));
                    return false;
                }
            });
            tv_num.setText(String.valueOf(position % 53));
            radioGroup.setOnCheckedChangeListener(null);
            for (int i = 0; i < resDrawableId.length; i++) {
                if (resDrawableId[i] == poker.getHuaseId()) {
                    RadioButton radiobutton = (RadioButton) radioGroup.getChildAt(i + 1);
                    radiobutton.setChecked(true);
                    break;
                }
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    LogUtil.e("", "onOnCheckedChange :" + position);
                    LogUtil.e("", ",resPosition:" + (checkedId - group.getChildAt(1).getId()));
                    poker.setHuaseId(resDrawableId[checkedId - group.getChildAt(1).getId()]);
                    mCurrentPosition = position;
                    numTextView.setText(mContext.getResources().getString(R.string.long_poker_answer_num_tip, mCurrentPosition));
                    textDianView.requestFocus();
                    //LogUtil.e("","onCheck userAnswer:"+poker.getUserAnswer());
                    if (poker.getUserAnswer() == 0) {
                        return;
                    }
                    int dian = (poker.getUserAnswer() - 1) % 13 + 1;
                    if (group.getChildAt(1).getId() == checkedId) {
                        poker.setUserAnswer(13 + dian);

                    } else if (group.getChildAt(2).getId() == checkedId) {
                        poker.setUserAnswer(26 + dian);

                    } else if (group.getChildAt(3).getId() == checkedId) {
                        poker.setUserAnswer(39 + dian);

                    } else if (group.getChildAt(4).getId() == checkedId) {
                        poker.setUserAnswer(dian);

                    }
                    Drawable image = mContext.getResources().getDrawable(poker.getHuaseId());
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    textDianView.setCompoundDrawables(image, null, null, null);

                }
            });

            // LogUtil.e("","第"+position+"个，"+poker.getUserAnswer());
        }

    }

    @Override
    public int getItemCount() {
        return length;
    }

    public void setProgress(IProgress progress) {
        this.progress = progress;
    }


    public void onItemClick(int pos,TextView v) {
        if (mCurrentPosition >= length){
        }
        LongPokerEntity poker = mDatas.get(start + mCurrentPosition);
        if (pos == 0) {
            poker.setUserAnswer(0);


            v.setCompoundDrawables(null, null, null, null);
                v.setText("");
                return;
            //  poker.setUserAnswer("");
        } else {
            switch (poker.getHuaseId()) {
                case R.drawable.heitao://黑桃
                    poker.setUserAnswer(13 + pos);

                    break;
                case R.drawable.hongtao://红桃
                    poker.setUserAnswer(26 + pos);

                    break;
                case R.drawable.meihua://梅花
                    poker.setUserAnswer(39 + pos);

                    break;
                case R.drawable.fangkuai://方块
                    poker.setUserAnswer(pos);

                    break;
            }


            // poker.setUserAnswer(poker.getUserAnswer().substring(0, 2)+text);
        }
        if (progress != null) {
            int pro = 100 + (start + mCurrentPosition) * 100 / mDatas.size();
            if (pro <= 101) {
                pro = 101;
            } else if (pro > 199) {
                pro = 199;
            }
            progress.setProgress(pro);
        }
        Drawable image = mContext.getResources().getDrawable(poker.getHuaseId());
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        v.setCompoundDrawables(image, null, null, null);
        v.setText(dianShuList.get((poker.getUserAnswer() - 1) % 13 + 1));
        mCurrentPosition ++;
        if (mCurrentPosition >= length) {
            ToastUtil.showToast(App.getContext().getString(R.string.long_poker_answer_tip,start/53 +1));
        }
    }

   public class APokerViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public APokerViewHolder(View view) {
            super(view);
            this.view = view;
        }

    }
}
