package com.unipad.brain.number.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.number.bean.BinaryNumberEntity;
import com.unipad.brain.number.bean.RandomNumberEntity;
import com.unipad.utils.StringUtil;

/**
 * 数据记忆界面
 */
public class NumberMemoryLayout extends LinearLayout implements App.HandlerCallback {
    private Context mContext;
    private LayoutInflater mInflater;
    private App.AppHandler mHandler = new App.AppHandler(this);
    private int mMaxNumber = 2;
    private int mRows = BinaryNumberEntity.rows;
    private int mLines = BinaryNumberEntity.lines;
    /**
     * 比赛项目：二进制数字、快速随机数字、马拉松数字
     */
    private String mCompeteType;
    /**
     * 已经加载了多少行
     */
    private int mLoadedItem = 0;

    public NumberMemoryLayout(Context context) {
        super(context);
    }

    public NumberMemoryLayout(Context context, String competeType) {
        super(context);
        mCompeteType = competeType;

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
        this.makeBinaryNumber();
    }

    /**
     * 产生(行数*列数)个0到1之间数据
     */
    private void makeBinaryNumber() {
        BinaryNumberEntity.lineNumbers.clear();
        if (mCompeteType.equals(mContext.getString(R.string.project_3))
                || mCompeteType.equals(mContext.getString(R.string.project_5))) {
            mMaxNumber = 10;
            mRows = RandomNumberEntity.rows;
            mLines = RandomNumberEntity.lines;
        }

        final Random ran = new Random();
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<String> lineNumbers;
                for (int i = 0; i < mLines; i++) {
                    lineNumbers = new ArrayList<>();

                    for (int j = 0; j < mRows; j++) {
                        lineNumbers.add(String.valueOf(ran.nextInt(mMaxNumber)));
                    }
                    BinaryNumberEntity.lineNumbers.put(i, lineNumbers);
                }

                mHandler.sendEmptyMessage(BinaryNumberEntity.MSG_OPEN_THREAD);//产生完成后再发消息更新界面
            }
        }).start();
    }

    @Override
    public void dispatchMessage(Message msg) {
        switch (msg.what) {
            case BinaryNumberEntity.MSG_OPEN_THREAD:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = mHandler.obtainMessage();
                        msg.what = BinaryNumberEntity.MSG_REFRESH_UI;
                        msg.arg1 = mLoadedItem;

                        mLoadedItem += 5;// 每次加载五行
                        if (mLoadedItem >= mLines) {
                            mLoadedItem = mLines;
                        }

                        mHandler.sendMessage(msg);
                    }
                }).start();
                break;
            case BinaryNumberEntity.MSG_REFRESH_UI:
                for (int index = msg.arg1; index < mLoadedItem; index++) {
                    this.addLineLayout(index);
                }

                if (mLoadedItem >= mLines) {
                    mHandler.removeMessages(BinaryNumberEntity.MSG_OPEN_THREAD);
                    mHandler.removeMessages(BinaryNumberEntity.MSG_REFRESH_UI);
                } else {
                    mHandler.sendEmptyMessage(BinaryNumberEntity.MSG_OPEN_THREAD);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 每次添加一行数据的视图
     *
     * @param index 数据所在父视图的索引
     */
    private void addLineLayout(int index) {
        ViewGroup parent = (ViewGroup) mInflater.inflate(
                R.layout.number_v_line, null);
        LinearLayout layoutNums = (LinearLayout) parent.getChildAt(0);
        this.addNumText(layoutNums, index);

        TextView textNum = (TextView) parent.findViewById(R.id.tv_row);
        textNum.setText("Row" + StringUtil.addZero(1 + index, 2));

        addView(parent, index);
    }

    /**
     * 为第一行显示"0"，"1"的TextView布局
     *
     * @param parent TextView所在的父视图
     * @param index  TextView所在的父视图的索引
     */
    private void addNumText(LinearLayout parent, int index) {
        List<String> numList = BinaryNumberEntity.lineNumbers.get(index);
        TextView textNum;
        LayoutParams params;

        for (int i = 0; i < mRows; i++) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            textNum = new TextView(mContext);
            textNum.setLayoutParams(params);
            textNum.setText(numList.get(i));
            textNum.setTextSize(26.0f);
            textNum.setTextColor(mContext.getResources().getColor(
                    R.color.main_lf_bg));
            textNum.setGravity(Gravity.CENTER);
            parent.addView(textNum, i);
        }
    }

}
