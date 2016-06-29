package com.unipad.brain.number.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.number.dao.BinaryNumService;
import com.unipad.brain.number.bean.RandomNumberEntity;
import com.unipad.utils.StringUtil;

/**
 * 数据回忆界面
 */
public class NumberRememoryLayout extends LinearLayout implements
        App.HandlerCallback, View.OnClickListener {
    private static final String TAG = NumberRememoryLayout.class.getSimpleName();
    public static final int ID = 0x3333333;
    private Context mContext;
    private LayoutInflater mInflater;
    private App.AppHandler mHandler = new App.AppHandler(this);
    private int mRows ;
    private int mLines ;
    /**
     * 数字总数
     */
    private int mTotalNumbers;
    private String mRowNumber = "Row";
    /**
     * 比赛项目：二进制数字、快速随机数字、马拉松数字
     */
    private String mCompeteType;
    /**
     * 已经加载了多少行
     */
    private int mLoadedItem = 0;
    private float mTextSize = 25.0f;
    /**
     * 左光标Gif动画
     */
    private Drawable mLeftCursorBg;
    /**
     * 右光标Gif动画
     */
    private Drawable mRightCursorBg;
    /**
     * 用于控制左光标Gif动画的播放与暂停
     */
    private AnimationDrawable mLeftCursorAnim;
    /**
     * 用于控制右光标Gif动画的播放与暂停
     */
    private AnimationDrawable mRightCursorAnim;

    public NumberRememoryLayout(Context context) {
        super(context);
    }

    public NumberRememoryLayout(Context context, String competeType,int rows,int lines,int totalNumbers) {
        super(context);
        mContext = context;
        mCompeteType = competeType;
        mRows = rows;
        this.mLines = lines;
        this.mTotalNumbers = totalNumbers;
        this.initData();
    }

    private void initData() {
        setOrientation(VERTICAL);
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (mCompeteType.equals(mContext.getString(R.string.project_3))
                || mCompeteType.equals(mContext.getString(R.string.project_5))) {
            mRowNumber = "";
            mTextSize = 23.0f;

            mLeftCursorBg = mContext.getResources().getDrawable(R.drawable.cursor_left_anim);
            mRightCursorBg = mContext.getResources().getDrawable(R.drawable.cursor_right_anim);
            mLeftCursorAnim = (AnimationDrawable) mLeftCursorBg;
            mRightCursorAnim = (AnimationDrawable) mRightCursorBg;
        }

        mHandler.sendEmptyMessage(BinaryNumService.MSG_OPEN_THREAD);
    }

    private void addLineLayout(int index) {
        RelativeLayout parent = (RelativeLayout) mInflater.inflate(
                R.layout.number_v_line, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, (int) mContext.getResources()
                .getDimension(R.dimen.binary_line_h));
        params.bottomMargin = 36;
        parent.setLayoutParams(params);

        LinearLayout layoutNums = (LinearLayout) parent.getChildAt(0);
        layoutNums.setId(ID + index);
        this.addNumText(layoutNums);

        TextView textNum = (TextView) parent.findViewById(R.id.tv_row);

        textNum.setText(mRowNumber + StringUtil.addZero(1 + index, 2));

        addView(parent, index);
    }

    private void addNumText(LinearLayout parent) {
        TextView textNum;
        LayoutParams params;
        params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        for (int i = 0; i < mRows; i++) {
            textNum = new TextView(mContext);
            textNum.setLayoutParams(params);
            textNum.setTextSize(mTextSize);
            textNum.setTextColor(mContext.getResources().getColor(
                    R.color.main_lf_bg));
            textNum.setGravity(Gravity.CENTER);
            textNum.setBackground(mContext.getResources().getDrawable(
                    R.drawable.number_text_bg));
            if (mCompeteType.equals(mContext.getString(R.string.project_2))) {
                textNum.setOnClickListener(this);
            }
            parent.addView(textNum, i);
        }
    }

    @Override
    public void dispatchMessage(Message msg) {
        switch (msg.what) {
            case BinaryNumService.MSG_OPEN_THREAD:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = mHandler.obtainMessage();
                        msg.what = BinaryNumService.MSG_REFRESH_UI;
                        msg.arg1 = mLoadedItem;

                        mLoadedItem += 5;// 每次加载五行
                        if (mLoadedItem >= mLines) {
                            mLoadedItem = mLines;
                        }

                        mHandler.sendMessage(msg);
                    }
                }).start();
                break;
            case BinaryNumService.MSG_REFRESH_UI:
                for (int index = msg.arg1; index < mLoadedItem; index++) {
                    this.addLineLayout(index);
                }

                if (mLoadedItem >= mLines) {
                    mHandler.removeMessages(BinaryNumService.MSG_OPEN_THREAD);
                    mHandler.removeMessages(BinaryNumService.MSG_REFRESH_UI);

                    //快速随机、马拉松数字项目的回忆界面需要显示光标
                    if (mCompeteType.equals(mContext.getString(R.string.project_3))
                            || mCompeteType.equals(mContext.getString(R.string.project_5))) {
                        ViewGroup viewGroup = (ViewGroup) getChildAt(0);// 获取行号
                        viewGroup = (ViewGroup) viewGroup.getChildAt(0);
                        TextView textNumber = (TextView) viewGroup
                                .getChildAt(0);// 获取行中第几个格子
                        textNumber.setBackground(null);
                        textNumber.setBackground(mLeftCursorBg);
                        mLeftCursorAnim.start();
                        mTextDiffBg = textNumber;
                    }
                } else {
                    mHandler.sendEmptyMessage(BinaryNumService.MSG_OPEN_THREAD);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        TextView textNumber = (TextView) view;
        String number = textNumber.getText().toString().trim();
        if (TextUtils.isEmpty(number) || BinaryNumService.TEXT_ZERO.equals(number)) {
            textNumber.setText(BinaryNumService.TEXT_ONE);
        } else {
            textNumber.setText(BinaryNumService.TEXT_ZERO);
        }
    }

    /**
     * 设置格子中的文字
     *
     * @param cursorPosition 光标位置
     * @param numberText     数字文字，当为空时是删除操作
     */
    public void setGridText(int cursorPosition, String numberText) {
        if (cursorPosition < 0 || cursorPosition > mTotalNumbers - 1) {
            return;
        }

        ViewGroup viewGroup = (ViewGroup) getChildAt(cursorPosition
                / mRows);// 获取行号
        viewGroup = (ViewGroup) viewGroup.getChildAt(0);
        TextView textNumber = (TextView) viewGroup
                .getChildAt(cursorPosition % mRows);// 获取行中第几个格子
        textNumber.setText(numberText);
    }

    private TextView mTextDiffBg;

    /**
     * 设置光标的位置(快速随机、马拉松数字项目的回忆界面需要显示光标)
     *
     * @param cursorPosition 光标位置
     */
    public void setCursorPosition(int cursorPosition) {
        if (cursorPosition < 0 || cursorPosition > mTotalNumbers - 1) {
            return;
        }

        if (mTextDiffBg != null) {
            mTextDiffBg.setBackground(null);
            mTextDiffBg.setBackground(mContext.getResources().getDrawable(
                    R.drawable.number_text_bg));
        }

        if (mLeftCursorAnim.isRunning()) {
            mLeftCursorAnim.stop();
        }

        if (mRightCursorAnim.isRunning()) {
            mRightCursorAnim.stop();
        }

        ViewGroup viewGroup = (ViewGroup) getChildAt(cursorPosition
                / mRows);// 获取行号
        viewGroup = (ViewGroup) viewGroup.getChildAt(0);
        TextView textNumber = (TextView) viewGroup
                .getChildAt(cursorPosition % mRows);// 获取行中第几个格子
        textNumber.setBackground(null);
        //if (TextUtils.isEmpty(textNumber.getText())) {//如果存在文本，则光标在格子的左侧
        textNumber.setBackground(mLeftCursorBg);
        mLeftCursorAnim.start();
        //} else {//否则光标在格子的右侧
        //    textNumber.setBackground(mRightCursorBg);
        //    mRightCursorAnim.start();
        // }


        mTextDiffBg = textNumber;
    }

    /**
     * 获取选手答题答案
     *
     * @return 以行号为key值保存的每一行数字
     */
    public SparseArray<List<String>> getAnswer() {
        try {
            SparseArray<List<String>> linesAnswer = new SparseArray<>();
            List<String> lineNumbers;

            for (int i = 0; i < mLines; i++) {
                lineNumbers = new ArrayList<>();
                ViewGroup viewGroup = (ViewGroup) getChildAt(i);
                viewGroup = (ViewGroup) viewGroup.getChildAt(0);

                for (int j = 0; j < mRows; j++) {
                    TextView textNumber = (TextView) viewGroup.getChildAt(j);
                    lineNumbers.add(textNumber.getText().toString());
                }
                linesAnswer.put(i, lineNumbers);
            }
            return linesAnswer;
        } catch (Exception e) {
            Log.e(TAG, e.toString(), new Exception());
        }
        return null;
    }

    public void showAnswer(SparseArray<String> orginArray) {
        for (int i = 0; i < mLines; i++) {
           String orgin = orginArray.valueAt(i);
            ViewGroup viewGroup = (ViewGroup) getChildAt(i);
            viewGroup = (ViewGroup) viewGroup.getChildAt(0);
            for (int j = 0; j < orgin.length(); j++) {
                TextView textNumber = (TextView) viewGroup.getChildAt(j);
                String userAnswer  = textNumber.getText().toString();
                String a  = String.valueOf(orgin.charAt(j));
                if (!a.equals(userAnswer)) {
                    textNumber.setTextColor(getResources().getColor(R.color.red));
                    textNumber.setText(userAnswer+"/"+a);
                }
            }
        }
    }
}
