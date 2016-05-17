package com.unipad.brain.number;

import java.math.BigDecimal;
import java.util.List;

import android.app.Service;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.number.bean.BinaryNumberEntity;
import com.unipad.brain.number.bean.RandomNumberEntity;
import com.unipad.brain.number.view.KeyboardDialog;
import com.unipad.brain.number.view.NumberMemoryLayout;
import com.unipad.brain.number.view.NumberRememoryLayout;
import com.unipad.common.BasicFragment;
import com.unipad.common.CommonActivity;
import com.unipad.utils.ToastUtil;

public class NumberRightFragment extends BasicFragment implements KeyboardDialog.KeyboardClickListener {
    private static final String TAG = NumberRightFragment.class.getSimpleName();
    /**
     * Fragment界面父布局：适用于二进制数字、快速随机数字、马拉松数字
     */
    private RelativeLayout mParentLayout;
    /**
     * Fragment界面“听记数字”项目的记忆部分父布局：只适用于听记数字
     */
    private RelativeLayout mParentListenMemoryLayout;
    /**
     * 记忆界面父布局
     */
    private View mMemoryLayout;
    /**
     * 回忆界面父布局
     */
    private RelativeLayout mRememoryLayout;
    /**
     * 包裹数字回忆界面布局的ScrollView布局
     */
    private ScrollView mScrollAnswerView;
    /**
     * 数字回忆界面布局
     */
    private NumberRememoryLayout mNumberRememoryLayout;
    /**
     * 界面底部布局
     */
    private ViewGroup mLayoutBottom;
    /**
     * 遮罩层
     */
    private ViewStub mStubShade;
    /**
     * 记录mScrollAnswerView滑动了多少次
     */
    private int mScrollCount = 1;
    private int mRows = BinaryNumberEntity.rows;
    private int mLines = BinaryNumberEntity.lines;
    /**
     * 数字总数
     */
    private int mTotalNumbers = mLines * mRows;
    private SparseArray<String> mNumberArray = new SparseArray<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentLayout = (RelativeLayout) inflater.inflate(R.layout.number_frg_right, container,
                false);
        return mParentLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (CommonActivity.CompeteItem == R.id.main_lf_home) {
            ViewStub viewStub = (ViewStub) mParentLayout.findViewById(R.id.view_stub_listen_frg_right_memory);
            mMemoryLayout = viewStub.inflate();
            AnimationDrawable animationDrawable = (AnimationDrawable) mMemoryLayout.getBackground();
            animationDrawable.start();
        } else {
            ViewStub viewStub = (ViewStub) mParentLayout.findViewById(R.id.view_stub_complex_item_scroll_layout);
            mMemoryLayout = viewStub.inflate();
            FrameLayout layout = (FrameLayout) mMemoryLayout.findViewById(R.id.memory_container);
            layout.addView(new NumberMemoryLayout(mActivity, CommonActivity.CompeteItem));
        }

        mRememoryLayout = (RelativeLayout) mParentLayout.findViewById(R.id.answer_layout);
        mLayoutBottom = (ViewGroup) mRememoryLayout.findViewById(R.id.bottom_layout);
        mStubShade = (ViewStub) mParentLayout.findViewById(R.id.view_shade);
    }

    /**
     * 开始答题
     */
    public void inAnswerMode() {
        mParentLayout.removeView(mMemoryLayout);// 先移除记忆界面
        mStubShade.setVisibility(View.GONE);// 隐藏遮罩层

        // 再加载回忆界面
        mRememoryLayout.setVisibility(View.VISIBLE);
        mScrollAnswerView = (ScrollView) mRememoryLayout
                .findViewById(R.id.scroll_rememory_layout);
        mNumberRememoryLayout = new NumberRememoryLayout(mActivity, CommonActivity.CompeteItem);
        FrameLayout frameLayout = (FrameLayout) mRememoryLayout.findViewById(R.id.binary_rememory_layout);
        frameLayout.addView(mNumberRememoryLayout);

        if (CommonActivity.CompeteItem == 2 || CommonActivity.CompeteItem == 4) {
            KeyboardDialog keyboardDialog = new KeyboardDialog(mActivity);
            keyboardDialog.show();
            keyboardDialog.setKeyboardClickListener(this);

            mRows = RandomNumberEntity.rows;
            mLines = RandomNumberEntity.lines;
            mTotalNumbers = mLines * mRows;
        } else if (CommonActivity.CompeteItem == 1) {
            View.inflate(mActivity, R.layout.binary_v_bottom, mLayoutBottom);
            mLayoutBottom.findViewById(R.id.ibtn_binary_0).setOnClickListener(this);
            mLayoutBottom.findViewById(R.id.ibtn_binary_1).setOnClickListener(this);
            mLayoutBottom.findViewById(R.id.btn_vibrate).setOnClickListener(this);
            mLayoutBottom.findViewById(R.id.btn_delete).setOnClickListener(this);
            mLayoutBottom.findViewById(R.id.btn_go_top).setOnClickListener(this);
        } else if (CommonActivity.CompeteItem == 8) {
            View.inflate(mActivity, R.layout.listen_v_bottom, mLayoutBottom);
            mLayoutBottom.findViewById(R.id.listen_keyboard_0).setOnClickListener(this);
            mLayoutBottom.findViewById(R.id.listen_keyboard_delete).setOnClickListener(this);
            int number = 0;
            mNumberArray.put(R.id.listen_keyboard_0, (number++) + "");
            for (int id = R.id.listen_keyboard_1; id <= R.id.listen_keyboard_9; ++id) {
                mNumberArray.put(id, (number++) + "");
                mLayoutBottom.findViewById(id).setOnClickListener(this);
            }
        }

    }

    /**
     * 结束答题
     *
     * @param takeTime 答题耗时
     */
    public void endAnswerMode(int takeTime) {
        int totalScore = 0;// 总得分
        SparseArray<List<String>> arrayQuestion = BinaryNumberEntity.lineNumbers;
        SparseArray<List<String>> arrayAnswer = mNumberRememoryLayout.getAnswer();
        if (arrayAnswer != null && arrayAnswer.size() == arrayQuestion.size()) {
            // 计算答题成绩————start
            for (int line = 0; line < BinaryNumberEntity.lines; line++) {
                List<String> question = arrayQuestion.get(line);
                List<String> answer = arrayAnswer.get(line);
                int lineScore = BinaryNumberEntity.LINE_FULL_SCORE;// 每一行的得分
                int errorCount = 0;// 每一行错误的个数

                if (line == BinaryNumberEntity.lines - 1) {// 最后一行单独处理
                    int emptyGridCount = 0;// 记录空格的个数
                    int lastNotEmptyGirdIndex = 0;// 最后一个不为空的格子的索引
                    for (int row = 0; row < BinaryNumberEntity.rows; row++) {
                        if (TextUtils.isEmpty(answer.get(row))) {
                            emptyGridCount++;
                        } else {
                            lastNotEmptyGirdIndex = row;
                        }
                    }

                    if (emptyGridCount > 0) {// 最后一行有超过0个空格，表示没有填写完成
                        lineScore -= emptyGridCount;
                        for (int row = 0; row <= lastNotEmptyGirdIndex; row++) {
                            if (!(question.get(row).equals(answer.get(row)))) {// 判断填写的数字的正确性
                                errorCount++;
                            }
                        }

                        if (errorCount == 1) {// 没有填完的情况下只错误一个
                            lineScore = new BigDecimal(1.0 * lineScore / 2)
                                    .setScale(0, BigDecimal.ROUND_HALF_UP)
                                    .intValue();// 得分为填写个数的一半(四舍五入)
                        } else if (errorCount >= 2) {
                            lineScore = 0;
                        }
                    } else {// 最后一行没有一个空格，表示正常填写完成
                        for (int row = 0; row < BinaryNumberEntity.rows; row++) {
                            if (!(question.get(row).equals(answer.get(row)))) {
                                errorCount++;
                            }
                        }

                        if (errorCount == 1) {
                            lineScore = BinaryNumberEntity.LINE_ONE_ERROR_SCORE;
                        } else if (errorCount >= 2) {
                            lineScore = 0;
                        }
                    }
                } else {
                    for (int row = 0; row < BinaryNumberEntity.rows; row++) {
                        if (!(question.get(row).equals(answer.get(row)))) {
                            errorCount++;
                        }
                    }

                    if (errorCount == 1) {
                        lineScore = BinaryNumberEntity.LINE_ONE_ERROR_SCORE;
                    } else if (errorCount >= 2) {// 完全写满但两个错处（或漏空）及以上的一行得 0 分
                        lineScore = 0;
                    }
                }

                Log.i(TAG, line + ", lineScore::" + lineScore);
                totalScore += lineScore;
                Log.i(TAG, "totalScore::" + totalScore);
            }
            // 计算答题成绩————end

            this.showAnswerResult(takeTime, totalScore);
        } else {
            this.showAnswerError();
        }
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
    private void showAnswerResult(int takeTime, int scores) {
        //mStubShade.setVisibility(View.GONE);// 隐藏遮罩层

//		DialogConfirm dialogConfirm = new DialogConfirm(mActivity,
//				mActivity.getString(R.string.game_over, takeTime, scores),
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_binary_0:
            case R.id.ibtn_binary_1:
                String text = BinaryNumberEntity.TEXT_ONE;
                if (R.id.ibtn_binary_0 == view.getId()) {
                    text = BinaryNumberEntity.TEXT_ZERO;
                }
                this.setGridText(text);
                break;
            case R.id.btn_vibrate:
                Vibrator vibrator = (Vibrator) mActivity.getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(new long[]{100, 10, 100, 1000}, -1);//设备震动
                Animation shakeAnim = AnimationUtils.loadAnimation(mActivity, R.anim.shake);
                mParentLayout.startAnimation(shakeAnim); //控件震动的动画效果
                break;
            case R.id.btn_delete:
                this.deleteGirdText();
                break;
            case R.id.btn_go_top:
                mScrollAnswerView.smoothScrollTo(0, 0);
                break;
            case R.id.listen_keyboard_0:
            case R.id.listen_keyboard_1:
            case R.id.listen_keyboard_2:
            case R.id.listen_keyboard_3:
            case R.id.listen_keyboard_4:
            case R.id.listen_keyboard_5:
            case R.id.listen_keyboard_6:
            case R.id.listen_keyboard_7:
            case R.id.listen_keyboard_8:
            case R.id.listen_keyboard_9:
                this.setGridText(mNumberArray.get(view.getId()));
                break;
            case R.id.listen_keyboard_delete:
                this.deleteGirdText();
                break;
            default:
                break;
        }
    }

    /**
     * 设置格子中的数字
     *
     * @param numberText 数字文字
     */
    private void setGridText(String numberText) {
        if (mCursorPosition >= mTotalNumbers) {
            //ToastUtil.showToast("您已填满所有空白");
            return;
        }

        mNumberRememoryLayout.setGridText(mCursorPosition++, numberText);

        int lineIndex = mCursorPosition / mRows;
        if ((lineIndex % 8 == 0) && lineIndex / 8 == mScrollCount) {// 每8行mScrollAnswerView向上移动一次
            mScrollAnswerView.smoothScrollTo(0,
                    (App.screenHeight / 2 - 35) * (mScrollCount++));
        }

        //快速随机、马拉松数字项目的回忆界面需要显示光标
        if (CommonActivity.CompeteItem == R.id.main_lf_message) {
            mNumberRememoryLayout.setCursorPosition(mCursorPosition);
        }

    }

    /**
     * 删除格子中的数字
     */
    private void deleteGirdText() {
        --mCursorPosition;
        if (mCursorPosition < 0) {
            mCursorPosition = 0;
            return;
        }
        mNumberRememoryLayout.setGridText(mCursorPosition, "");

        //快速随机、马拉松数字项目的回忆界面需要显示光标
        if (CommonActivity.CompeteItem == R.id.main_lf_message) {
            mNumberRememoryLayout.setCursorPosition(mCursorPosition);
        }
    }

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
        //mStubShade.setVisibility(View.VISIBLE);
    }

    @Override
    public void numberKey(String keyValue) {
        this.setGridText(keyValue);
    }

    /**
     * 光标的位置，从0开始，0表示第一个，如果存在光标则默认在第一个显示
     */
    private int mCursorPosition = 0;

    @Override
    public void upKey() {
        int tempPosition = mCursorPosition;
        mCursorPosition -= mRows;
        if (mCursorPosition >= 0) {
            mNumberRememoryLayout.setCursorPosition(mCursorPosition);
        } else {
            mCursorPosition = tempPosition;
            ToastUtil.showToast(R.string.first_line);
        }
    }

    @Override
    public void downKey() {
        int tempPosition = mCursorPosition;
        mCursorPosition += mRows;
        if (mCursorPosition < mTotalNumbers) {
            mNumberRememoryLayout.setCursorPosition(mCursorPosition);
        } else {
            mCursorPosition = tempPosition;
            ToastUtil.showToast(R.string.last_line);
        }
    }

    @Override
    public void leftKey() {
        --mCursorPosition;
        if (mCursorPosition >= 0) {
            mNumberRememoryLayout.setCursorPosition(mCursorPosition);
        } else {
            mCursorPosition = 0;
        }
    }

    @Override
    public void rightKey() {
        ++mCursorPosition;
        if (mCursorPosition < mTotalNumbers) {
            mNumberRememoryLayout.setCursorPosition(mCursorPosition);
        } else {
            mCursorPosition = mTotalNumbers - 1;
        }
    }

    @Override
    public void deleteKey() {
        this.deleteGirdText();
    }

}
