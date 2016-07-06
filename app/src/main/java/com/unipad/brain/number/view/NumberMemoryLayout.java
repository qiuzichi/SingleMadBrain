package com.unipad.brain.number.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipad.brain.R;
import com.unipad.utils.StringUtil;

/**
 * 数据记忆界面
 */
public class NumberMemoryLayout extends LinearLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    /**
     * 比赛项目：二进制数字、快速随机数字、马拉松数字
     */
    private SparseArray<String> lineNumbers;
    /**
     * 已经加载了多少行
     */
    private int mLoadedItem = 0;

    public NumberMemoryLayout(Context context) {
        super(context);
    }

    public NumberMemoryLayout(Context context, SparseArray<String> lineNumbers) {
        super(context);
        this.lineNumbers = lineNumbers;
        this.initGridView(context);
    }

    private void initGridView(Context context) {
        mContext = context;
        this.initData();
    }

    private void initData() {
        setOrientation(VERTICAL);
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initChidView();


    }
    private void initChidView(){
        if (null != lineNumbers && lineNumbers.size()!=0){
            for (int i =0;i<lineNumbers.size();i++) {
                addLineLayout(lineNumbers.keyAt(i),lineNumbers.valueAt(i));
            }
        }
    }
    /**
     * 每次添加一行数据的视图
     *
     * @param index 数据所在父视图的索引
     */
    private void addLineLayout(int index,String value) {
        ViewGroup parent = (ViewGroup) mInflater.inflate(
                R.layout.number_v_line, null);
        LinearLayout layoutNums = (LinearLayout) parent.getChildAt(0);
        this.addNumText(layoutNums, index);

        TextView textNum = (TextView) parent.findViewById(R.id.tv_row);
        textNum.setText("Row" + StringUtil.addZero(index, 2));

        addView(parent, index-1);
    }

    /**
     * 为第一行显示"0"，"1"的TextView布局
     *
     * @param parent TextView所在的父视图
     * @param index  TextView所在的父视图的索引
     */
    private void addNumText(LinearLayout parent, int index) {
        String numList = lineNumbers.get(index);
        LayoutParams params;
        params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        for (int i = 0; i < numList.length(); i++) {

            TextView textNum = new TextView(mContext);
            textNum.setLayoutParams(params);
            textNum.setText(String.valueOf(numList.charAt(i)));
            textNum.setTextSize(26.0f);
            textNum.setTextColor(mContext.getResources().getColor(
                    R.color.main_lf_bg));
            textNum.setGravity(Gravity.CENTER);
            parent.addView(textNum, i);
        }
    }

}
