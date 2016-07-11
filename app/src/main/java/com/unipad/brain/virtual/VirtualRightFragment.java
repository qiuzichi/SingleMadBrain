package com.unipad.brain.virtual;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.virtual.bean.DividerGridItemDecoration;
import com.unipad.brain.virtual.bean.VirtualEntity;
import com.unipad.brain.virtual.dao.VirtualTimeService;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.utils.ToastUtil;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * yzj----项目:虚拟事件
 */
public class VirtualRightFragment extends BasicCommonFragment {
    private static final String TAG = VirtualRightFragment.class.getSimpleName();
    List<EditText> editList=new ArrayList<>();
    /**
     * 最大年份
     */
    private int maxYear=2099;
    /**
     * 最小年份
     */
    private int minYear=1000;

    /**
     * 存储回忆界面的数据
     */
    private VirtualTimeService service;

    private VirtualMemoryAdapter memoryAdapter;

    /**
     * 记忆界面
     */
    private RecyclerView memoryRv;

    /**
     * 回忆界面底部按钮
     */
    private ImageButton numButton_0,numButton_1,numButton_2,numButton_3,numButton_4,numButton_5,
            numButton_6,numButton_7,numButton_8,numButton_9,numButton_clear;

    /**
     * 回忆界面下方的数字布局
     */
    private LinearLayout jianpan_linlayout;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jianpan_linlayout= (LinearLayout) mViewParent.findViewById(R.id.jianpan_linlayout);
        jianpan_linlayout.setVisibility(View.GONE);

        numButton_1= (ImageButton) mViewParent.findViewById(R.id.numButton_1);
        numButton_2= (ImageButton) mViewParent.findViewById(R.id.numButton_2);
        numButton_3= (ImageButton) mViewParent.findViewById(R.id.numButton_3);
        numButton_4= (ImageButton) mViewParent.findViewById(R.id.numButton_4);
        numButton_5= (ImageButton) mViewParent.findViewById(R.id.numButton_5);
        numButton_6= (ImageButton) mViewParent.findViewById(R.id.numButton_6);
        numButton_7= (ImageButton) mViewParent.findViewById(R.id.numButton_7);
        numButton_8= (ImageButton) mViewParent.findViewById(R.id.numButton_8);
        numButton_9= (ImageButton) mViewParent.findViewById(R.id.numButton_9);
        numButton_0= (ImageButton) mViewParent.findViewById(R.id.numButton_0);
        numButton_clear= (ImageButton) mViewParent.findViewById(R.id.numButton_clear);

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

        memoryRv = (RecyclerView) mViewParent.findViewById(R.id.memoryRv);

        memoryRv.setLayoutManager(new GridLayoutManager(mActivity,2));
        //添加分割线
        memoryRv.addItemDecoration(new DividerGridItemDecoration(mActivity));



        service = (VirtualTimeService) AppContext.instance().getGameServiceByProject(mActivity.getProjectId());

    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        memoryRv.setAdapter(memoryAdapter = new VirtualMemoryAdapter(service.virtualList));

    }
    /**
     * 记忆的adapter
     */
    class VirtualMemoryAdapter extends RecyclerView.Adapter<VirtualMemoryAdapter.MyViewHolder> {

        private List<VirtualEntity> virtualList;
        public VirtualMemoryAdapter (List<VirtualEntity> virtualList) {
            if (virtualList  == null){
                this.virtualList =  new ArrayList<>();
            }else {
                this.virtualList = virtualList;
            }
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    mActivity).inflate(R.layout.virtual_memory_line, parent,
                    false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            VirtualEntity entity = virtualList.get(position);
            holder.tv_num.setText(entity.getNumber()+"" );
            holder.tv_event.setText(entity.getEvent()+"");
            if (service.mode == 0) {
                //记忆模式

                holder.tv_date.setText(virtualList.get(position).getDate()+"");

                 holder.editNUmView.setVisibility(View.GONE);


            } else if (service.mode == 1) {
                holder.editNUmView.setVisibility(View.VISIBLE);
                holder.tv_date.setVisibility(View.GONE);

            }else if (service.mode == 2) {
                holder.editNUmView.setVisibility(View.GONE);
                holder.tv_date.setVisibility(View.VISIBLE);
                if(!entity.getAnswerDate().equals(entity.getDate())) {
                    holder.tv_date.setTextColor(getResources().getColor(R.color.red));
                    holder.tv_date.setText(entity.getDate()+"/"+entity.getAnswerDate());
                }
            }
        }

        @Override
        public int getItemCount() {
            return virtualList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
           public TextView tv_num,tv_date,tv_event;
            public  EditText editNUmView;
            public MyViewHolder(View view) {
                super(view);
                tv_num=(TextView) view.findViewById(R.id.id_num);
                tv_date = (TextView) view.findViewById(R.id.id_datetxt);
                tv_event=(TextView)view.findViewById(R.id.event_txt);
                editNUmView=(EditText)view.findViewById(R.id.editText);

            }

        }
    }


    /**
     * 开始答题
     */
    public void inAnswerMode() {
        int key=HttpConstant.VIRTUAL_RIGHT;
        ToastUtil.showToast("开始");
        if (service.mode==0) {
            key=HttpConstant.STRAT_MEMORY;
            memoryRv.setVisibility(View.GONE);
            jianpan_linlayout.setVisibility(View.VISIBLE);

        }

    }

    /**
     * 结束答题
     *
     * @param takeTime 答题耗时
     */
    public void endAnswerMode(int takeTime) {

    }

    /**
     * 输入的索引
      */
    int index=0;
    /**
     * 输入的年份
     */
   private  String text = "";

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
                   // memoriesMap.put(index, new VirtualEntity(index, text, virtualList.get(index).getEvent()));
                    service.virtualList.get(index).setAnswerDate(text);
                    //
                    text ="";
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
                       // memoriesMap.clear();
                    }
                    break;
                }

                if(editList.get(index).getText().length()>0){
                    text=text.substring(0,editList.get(index).getText().length()-1);//清除格子中的一个数字
                    editList.get(index).setText(text);
                }else if(editList.get(index).getText().length()==0){
                    //memoriesMap.remove(index);//整个格子中的数字都清除完之后在map集合移除这个item
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

    @Override
    public int getLayoutId() {
        return R.layout.virtual_frg_right;
    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);
        this.inAnswerMode();
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        endAnswerMode(1);
    }

}
