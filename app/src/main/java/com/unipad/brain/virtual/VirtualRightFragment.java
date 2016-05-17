package com.unipad.brain.virtual;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.unipad.brain.R;
import com.unipad.brain.virtual.bean.DividerGridItemDecoration;
import com.unipad.brain.virtual.bean.VirtualEntity;
import com.unipad.common.BasicFragment;
import com.unipad.common.CommonActivity;
import com.unipad.common.CommonFragment;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.common.adapter.ListGridAdapter;
import com.unipad.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * yzj----项目:虚拟事件
 */
public class VirtualRightFragment extends BasicFragment{
    private static final String TAG = VirtualRightFragment.class.getSimpleName();
    List<EditText> editList=new ArrayList<EditText>();
    /**
     * 最大年份
     */
    private int maxYear=2099;

    /**
     * 最小年份
     */
    private int minYear=1000;

    /**
     * 存储记忆界面的数据
     */
    Map<Integer,VirtualEntity> memoryMap=new HashMap<>();

    /**
     * 存储回忆界面的数据
     */
    Map<Integer,VirtualEntity> memoriesMap=new HashMap<>(memoryMap.size());


    List<VirtualEntity> virtualList=new ArrayList<VirtualEntity>();

    /**
     * Fragment界面父布局
     */
    private RelativeLayout mParentLayout;


    private VirtualMemoryAdapter memoryAdapter;

    private VirtualMemoriesAdapter memoriesAapter;

    /**
     * 记忆界面
     */
    private RecyclerView memoryRv;

    /**
     * 回忆界面
     */
    private RecyclerView memoriesRv;

    /**
     * 回忆界面底部按钮
     */
    private ImageButton numButton_0,numButton_1,numButton_2,numButton_3,numButton_4,numButton_5,numButton_6,numButton_7,numButton_8,numButton_9,numButton_clear;

    /**
     * 回忆界面下方的数字布局
     */
    private LinearLayout jianpan_linlayout;
    /**
     * 屏幕宽度
     */
    private int width;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentLayout = (RelativeLayout) inflater.inflate(R.layout.virtual_frg_right, container,
                false);
        return mParentLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 屏幕宽度（像素）
        width=mActivity.getResources().getDisplayMetrics().widthPixels;
        getdata();
        jianpan_linlayout= (LinearLayout) mParentLayout.findViewById(R.id.jianpan_linlayout);
        jianpan_linlayout.setVisibility(View.GONE);

        numButton_1= (ImageButton) mParentLayout.findViewById(R.id.numButton_1);
        numButton_2= (ImageButton) mParentLayout.findViewById(R.id.numButton_2);
        numButton_3= (ImageButton) mParentLayout.findViewById(R.id.numButton_3);
        numButton_4= (ImageButton) mParentLayout.findViewById(R.id.numButton_4);
        numButton_5= (ImageButton) mParentLayout.findViewById(R.id.numButton_5);
        numButton_6= (ImageButton) mParentLayout.findViewById(R.id.numButton_6);
        numButton_7= (ImageButton) mParentLayout.findViewById(R.id.numButton_7);
        numButton_8= (ImageButton) mParentLayout.findViewById(R.id.numButton_8);
        numButton_9= (ImageButton) mParentLayout.findViewById(R.id.numButton_9);
        numButton_0= (ImageButton) mParentLayout.findViewById(R.id.numButton_0);
        numButton_clear= (ImageButton) mParentLayout.findViewById(R.id.numButton_clear);

        numButton_1.setOnClickListener(this);
        numButton_2.setOnClickListener(this);
        numButton_3.setOnClickListener(this);
        numButton_4.setOnClickListener(this);
        numButton_5.setOnClickListener(this);
        numButton_6.setOnClickListener(this);
        numButton_7.setOnClickListener(this);
        numButton_8.setOnClickListener(this);
        numButton_9.setOnClickListener(this);
        numButton_0.setOnClickListener(this);
        numButton_clear.setOnClickListener(this);

        memoryRv = (RecyclerView) mParentLayout.findViewById(R.id.memoryRv);
        memoryRv.setLayoutManager(new LinearLayoutManager(mActivity));
        memoryRv.setAdapter(memoryAdapter = new VirtualMemoryAdapter());
        memoryRv.setLayoutManager(new GridLayoutManager(mActivity, 2));
        //添加分割线
        memoryRv.addItemDecoration(new DividerGridItemDecoration(mActivity));

        memoriesRv = (RecyclerView) mParentLayout.findViewById(R.id.memoriesRv);
        memoriesRv.setLayoutManager(new LinearLayoutManager(mActivity));
        memoriesRv.setAdapter(memoriesAapter = new VirtualMemoriesAdapter());
        memoriesRv.setLayoutManager(new GridLayoutManager(mActivity, 2));

        memoriesRv.setVisibility(View.GONE);
    }

    /**
     * 记忆的adapter
     *
     */
    class VirtualMemoryAdapter extends RecyclerView.Adapter<VirtualMemoryAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    mActivity).inflate(R.layout.virtual_memory_line, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_num.setText(virtualList.get(position).getNumber()+"");
            holder.tv_date.setText(virtualList.get(position).getDate());
            holder.tv_event.setText(virtualList.get(position).getEvent());
        }

        @Override
        public int getItemCount() {
            return virtualList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_num,tv_date,tv_event;

            public MyViewHolder(View view) {
                super(view);
                tv_num=(TextView) view.findViewById(R.id.id_num);
                tv_date = (TextView) view.findViewById(R.id.id_datetxt);
                tv_event=(TextView)view.findViewById(R.id.event_txt);
            }
        }
    }


    /**
     * 回忆的adapter
     */
    class VirtualMemoriesAdapter extends RecyclerView.Adapter<VirtualMemoriesAdapter.MyViewHolder> {
        MyViewHolder holders;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view = View.inflate(mActivity,R.layout.virtual_memories_line,null);
            holders = new MyViewHolder(view);

            return holders;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tv_num.setText(virtualList.get(position).getNumber() + "");
            holder.tv_event.setText(virtualList.get(position).getEvent());
            holder.edit_date.setId(position);

            editList.add(holder.edit_date);
        }

        @Override
        public int getItemCount() {
            return virtualList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_num,tv_event;
            EditText edit_date;

            public MyViewHolder(View view) {
                super(view);
                tv_num=(TextView) view.findViewById(R.id.id_num);
                tv_event=(TextView)view.findViewById(R.id.event_txt);
                edit_date= (EditText) view.findViewById(R.id.editText);
            }
        }
    }

    /**
     * 初始化数据
     */
    public void getdata(){
        VirtualEntity virtual;
        for (int i=0;i<40;i++){
            virtual=new VirtualEntity(i+1,getDate(),"耳朵可以再生"+i);
            virtualList.add(virtual);
            memoryMap.put(i,virtual);
        }
    }

    /**
     * 随机生成不同的年份
     */
    Random  virtualRandom=new Random();
    String s="";
    public String getDate(){

        s = (virtualRandom.nextInt(maxYear)%(maxYear-minYear+1) + minYear)+"";

        if(virtualList!=null&&virtualList.size()>0){
            for(int i=0;i<virtualList.size();i++){
                if(virtualList.get(i).getDate().equals(s)){
                    s = (virtualRandom.nextInt(maxYear)%(maxYear-minYear+1) + minYear)+"";
                    i=0;
                }
            }
        }
        return s;
    }

    /**
     * 开始答题
     */
    public void inAnswerMode() {

        ToastUtil.showToast("开始");
        memoryRv.setVisibility(View.GONE);

        jianpan_linlayout.setVisibility(View.VISIBLE);
        memoriesRv.setVisibility(View.VISIBLE);
        /*mParentLayout.removeView(mMemoryLayout);// 先移除记忆界面
        mStubShade.setVisibility(View.GONE);// 隐藏遮罩层*/

        /*if (BinaryActivity.CompeteItem == R.id.main_lf_message) {
            mRememoryLayout.removeView(mRememoryLayout.findViewById(R.id.bottom_layout));
        } else {
            mRememoryLayout.findViewById(R.id.ibtn_binary_0).setOnClickListener(this);
            mRememoryLayout.findViewById(R.id.ibtn_binary_1).setOnClickListener(this);
        }

        mRememoryLayout.setVisibility(View.VISIBLE);
        mScrollAnswerView = (ScrollView) mRememoryLayout
                .findViewById(R.id.scroll_rememory_layout);
        mNumberRememoryLayout = new NumberRememoryLayout(mActivity, BinaryActivity.CompeteItem);
        FrameLayout frameLayout = (FrameLayout) mRememoryLayout.findViewById(R.id.binary_rememory_layout);
        frameLayout.addView(mNumberRememoryLayout);*/

    }


    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            memoriesRv.smoothScrollToPosition(pos);
        } else {
           //memoriesRv.sele.setSelection(pos);
        }
    }

    /**
     * 结束答题
     *
     * @param takeTime 答题耗时
     */
    public void endAnswerMode(int takeTime) {
        double totalScore = 0;// 总得分，总分需要四舍五入
        int error=0;
        //SparseArray<List<String>> arrayQuestion = BinaryNumberEntity.lineNumbers;
        //SparseArray<List<String>> arrayAnswer = mNumberRememoryLayout.getAnswer();

            // 计算答题成绩————end

            //this.showAnswerResult(takeTime, totalScore);
        for(int i=0;i<memoryMap.size();i++){
            if(memoriesMap.get(i)!=null){//判断是否有空白
                if(memoryMap.get(i).getDate().equals(memoriesMap.get(i).getDate())){//答对加1分
                    totalScore=totalScore+1;
                }else{
                    totalScore=totalScore-0.5;//打错扣0.5分
                    error++;
                }
            }
        }

        if(totalScore<0){
            totalScore=0;
        }

        ToastUtil.showToast("总分:" + totalScore+"空白个数"+(memoryMap.size()-memoriesMap.size())+"错误个数"+error);

            //this.showAnswerError();

    }

    /**
     * 显示错误信息
     */
    private void showAnswerError() {
//		DialogConfirm dialogConfirm = new DialogConfirm(mActivity,
//				mActivity.getString(R.string.binary_scroe_exception),
//				mActivity.getString(R.string.confirm), "");
//		dialogConfirm.setOnClickDialog(new OnClickDialog() {
//
//			@Override
//			public void clickConfirmBtn() {
//				mActivity.finish();
//			}
//
//			@Override
//			public void clickCancelBtn() {
//			}
//		});
    }

    /**
     * 显示答题成绩
     */
    /*private void showAnswerResult(int takeTime, int scores) {
        mStubShade.setVisibility(View.GONE);// 隐藏遮罩层*/

//		DialogConfirm dialogConfirm = new DialogConfirm(mActivity,
//				mActivity.getString(R.string.game_over, takeTime, scores),
//				mActivity.getString(R.string.confirm), "");
//		dialogConfirm.setOnClickDialog(new OnClickDialog() {
//
//			@Override
//			public void clickConfirmBtn() {
//				mActivity.finish();
//			}
//			@Override
//			public void clickCancelBtn() {
//			}
//		});
   // }

    /**
     * 输入的索引
      */
    int index=0;
    /**
     * 输入的年份
     */
    String text = "";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.numButton_1:
            case R.id.numButton_2:
            case R.id.numButton_3:
            case R.id.numButton_4:
            case R.id.numButton_5:
            case R.id.numButton_6:
            case R.id.numButton_7:
            case R.id.numButton_9:
            case R.id.numButton_8:
            case R.id.numButton_0:
                if(v.getId()==R.id.numButton_1){
                    text=text+"1";
                }else if(v.getId()==R.id.numButton_2){
                    text=text+"2";
                }else if(v.getId()==R.id.numButton_3){
                    text=text+"3";
                }else if(v.getId()==R.id.numButton_4){
                    text=text+"4";
                }else if(v.getId()==R.id.numButton_5){
                    text=text+"5";
                }else if(v.getId()==R.id.numButton_6){
                    text=text+"6";
                }else if(v.getId()==R.id.numButton_7){
                    text=text+"7";
                }else if(v.getId()==R.id.numButton_8){
                    text=text+"8";
                }else if(v.getId()==R.id.numButton_9){
                    text=text+"9";
                }else if(v.getId()==R.id.numButton_0){
                    text=text+"0";
                }
                editList.get(index).setText(text);
                if(text.trim().length()==4){//输入了4位数字之后，自动跳到下一个，index加1
                    memoriesMap.put(index, new VirtualEntity(index, text, virtualList.get(index).getEvent()));
                    text="";
                    index++;
                }
                break;
            case R.id.numButton_clear:
                //在第一个格子时
                if(index==0){
                    if(editList.get(index).getText().length()>0){//如果格子中有数字继续清除
                        text=text.substring(0,editList.get(index).getText().length()-1);
                        editList.get(index).setText(text);
                    }else if(editList.get(index).getText().length()==0){//如果格子中没有数字，清除全部集合
                        memoriesMap.clear();
                    }
                    break;
                }

                if(editList.get(index).getText().length()>0){
                    text=text.substring(0,editList.get(index).getText().length()-1);//清除格子中的一个数字
                    editList.get(index).setText(text);
                }else if(editList.get(index).getText().length()==0){
                    memoriesMap.remove(index);//整个格子中的数字都清除完之后在map集合移除这个item
                    index--;//索引减1
                    text=editList.get(index).getText().toString();
                    text=text.substring(0,editList.get(index).getText().length()-1);
                    editList.get(index).setText(text);
                }
                break;
            default:
                break;
        }
    }

    TextWatcher mTextWatcher=new TextWatcher() {
        CharSequence texts;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            editList.get(index).setText("1234");
        }

        @Override
        public void afterTextChanged(Editable s) {
            index++;
        }
    };


    @Override
    public void changeBg(int color) {
        mParentLayout.setBackgroundColor(color);
    }

    @Override
    public void memoryTimeToEnd() {
        this.inAnswerMode();
    }

    @Override
    public void rememoryTimeToEnd() {
        endAnswerMode(1);
    }

}
