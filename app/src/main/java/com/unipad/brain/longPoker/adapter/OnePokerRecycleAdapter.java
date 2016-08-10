package com.unipad.brain.longPoker.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.unipad.brain.R;
import com.unipad.brain.longPoker.IProgress;
import com.unipad.brain.longPoker.entity.LongPokerEntity;
import com.unipad.brain.longPoker.view.SpinerPopWindow;
import com.unipad.common.Constant;
import com.unipad.common.widget.HIDDialog;
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
    private SpinerPopWindow spinnerPopWindow;
    private int start;
    private int length;
    private int mCurrentPosition;
    private int[] resDrawableId = new int[]{R.drawable.heitao,R.drawable.hongtao,R.drawable.meihua,R.drawable.fangkuai};
    private List<String> dianShuList ;
    private IProgress progress;
    public OnePokerRecycleAdapter(Context context, List<LongPokerEntity> datas,int start,int length) {
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
        LogUtil.e("","adapter postion = "+(start+position));
        if (getItemViewType(position) == 0) {
            TextView tv = (TextView) holder.view;
            tv.setText("第" + (start / 53 + 1) + "副");
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(30);
        } else {

            final LongPokerEntity poker = mDatas.get(start+position);

            TextView tv_num = (TextView) holder.view.findViewById(R.id.num);
            final TextView textDianView =  (TextView) holder.view.findViewById(R.id.tv_value);
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

            LogUtil.e("","start:"+start+",positon="+position+","+poker.name+",userAnswer:"+userAnswer+",huase："+poker.getHuaseId());
            textDianView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentPosition = position;
                    if (progress != null){
                        int pro= (start+position)*100/mDatas.size();
                        if (pro ==100){
                            pro--;
                        }
                        progress.setProgress(pro+100);
                    }
                    showSpinnerPopWinsow(textDianView);

                }
            });
                tv_num.setText(String.valueOf(position % 53));

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        LogUtil.e("","onOnCheckedChange :"+position);
                        LogUtil.e("",",resPosition:"+(checkedId-group.getChildAt(1).getId()));
                        poker.setHuaseId(resDrawableId[checkedId-group.getChildAt(1).getId()]);
                        mCurrentPosition = position;
                        int dian = poker.getUserAnswer()%13;
                        if (poker.getUserAnswer() == 0) {
                            return;
                        }

                        if (group.getChildAt(1).getId() == checkedId) {
                            poker.setUserAnswer(13+dian);

                        } else if (group.getChildAt(2).getId() == checkedId) {
                            poker.setUserAnswer(26+dian);

                        } else if (group.getChildAt(3).getId() == checkedId) {
                            poker.setUserAnswer(39+dian);

                        } else if (group.getChildAt(4).getId() == checkedId) {
                            poker.setUserAnswer(dian);

                        }
                        Drawable image = mContext.getResources().getDrawable(poker.getHuaseId());
                        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                        textDianView.setCompoundDrawables(image,null,null,null);

                    }
                });
                for(int i = 0;i<resDrawableId.length;i++){
                if (resDrawableId[i] == poker.getHuaseId()){
                    RadioButton radiobutton = (RadioButton) radioGroup.getChildAt(i + 1);
                    radiobutton.setChecked(true);
                    break;
                }
            }
            LogUtil.e("","第"+position+"个，"+poker.getUserAnswer());
            }

    }

    @Override
    public int getItemCount() {
        return length;
    }

    public void setProgress(IProgress progress) {
        this.progress = progress;
    }

    class APokerViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public APokerViewHolder(View view) {
            super(view);
            this.view = view;
        }

    }
    private void showSpinnerPopWinsow(final TextView textView){
        if (spinnerPopWindow == null) {
            spinnerPopWindow = new SpinerPopWindow(mContext);

            spinnerPopWindow.refreshData(dianShuList, 0);
            spinnerPopWindow.setWidth(textView.getWidth());
            spinnerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                @Override
                public void onItemClick(int pos) {
                    LongPokerEntity poker = mDatas.get(start + mCurrentPosition);
                    if (pos == 0) {
                        poker.setUserAnswer(0);
                      //  poker.setUserAnswer("");
                    } else {
                        switch (poker.getHuaseId()){
                            case R.drawable.heitao://黑桃
                                poker.setUserAnswer(13+pos);

                                break;
                            case R.drawable.hongtao://红桃
                                poker.setUserAnswer(26+pos);

                                break;
                            case R.drawable.meihua://梅花
                                poker.setUserAnswer(39+pos);

                                break;
                            case R.drawable.fangkuai://方块
                                poker.setUserAnswer(pos);

                                break;
                        }


                       // poker.setUserAnswer(poker.getUserAnswer().substring(0, 2)+text);
                    }
                    notifyItemChanged(mCurrentPosition);
                    //spinnerPopWindow.dismiss();
                }
            });
        }
        if (!spinnerPopWindow.isShowing()){
            spinnerPopWindow.showAtLocation(textView, Gravity.CENTER, 0, 0);
        }
    }
}
