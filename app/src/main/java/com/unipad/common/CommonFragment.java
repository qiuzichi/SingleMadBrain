package com.unipad.common;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unipad.brain.R;
import com.unipad.common.bean.CompeteItemEntity;
import com.unipad.utils.CountDownTime;

/**
 * Created by Wbj on 2016/4/7.
 */
public class CommonFragment extends Fragment implements View.OnClickListener, CountDownTime.TimeListener {
    private static final int[] COLORS = {R.color.bg_one, R.color.bg_two, R.color.bg_three};
    private CommonActivity mActivity;
    private RelativeLayout mParentLayout;
    private TextView mTextName, mTextAgeAds, mTextTime, mTextCompeteProcess;
    private Button mBtnCompeteMode;
    private CountDownTime mCountDownTime;
    /**
     * 是否处于回忆模式，只有两种模式且先记忆再回忆；默认为false，即处于记忆模式；
     */
    private boolean isRememoryStatus;
    private ICommunicate mICommunicate;
    private SparseArray mColorArray = new SparseArray();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentLayout = (RelativeLayout) inflater.inflate(R.layout.common_frg_left, container, false);
        return mParentLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (CommonActivity) getActivity();

        mTextName = (TextView) mParentLayout.findViewById(R.id.user_name);
        mTextAgeAds = (TextView) mParentLayout.findViewById(R.id.user_age_ads);
        mTextTime = (TextView) mParentLayout.findViewById(R.id.text_time);
        mTextCompeteProcess = (TextView) mParentLayout.findViewById(R.id.memory_stats);
        mBtnCompeteMode = (Button) mParentLayout.findViewById(R.id.btn_compete_process);
        mBtnCompeteMode.setOnClickListener(this);
        mParentLayout.findViewById(R.id.text_exit).setOnClickListener(this);

        mTextAgeAds.setSelected(true);
        mTextName.setSelected(true);
        this.getBgColorArray(mParentLayout);

        mCountDownTime = new CountDownTime(CompeteItemEntity.getInstance().getMemoryTime(), true);
        mCountDownTime.setTimeListener(this);
        mTextTime.setText(mCountDownTime.getTimeString());

        if (CompeteItemEntity.getInstance().getCompeteItem().equals(getString(R.string.project_9))) {
            mTextCompeteProcess.setText(R.string.playing_voice);
        }
    }

    /**
     * 获取背景颜色集合并设置示例图片背景
     */
    private void getBgColorArray(View view) {
        ImageView viewImg;
        for (int i = 0; i < COLORS.length; i++) {
            viewImg = (ImageView) view.findViewById(R.id.text_change_layout_img_bg1 + i);
            viewImg.setBackgroundColor(mActivity.getResources().getColor(COLORS[i]));
            viewImg.setOnClickListener(this);

            mColorArray.put(R.id.text_change_layout_img_bg1 + i, COLORS[i]);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mCountDownTime.resumeCountTime();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCountDownTime.pauseCountTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCountDownTime.stopCountTime();
        mColorArray.clear();
    }

    /**
     * 切换比赛进程：记忆模式-->回忆模式-->提交答案
     */
    private void switchCompeteProcess() {
        if (!isRememoryStatus) {//切换到回忆模式
            isRememoryStatus = true;
            mTextCompeteProcess.setText(R.string.rememorying);
            mTextTime.setText(mCountDownTime.setNewSeconds(CompeteItemEntity.getInstance().getRememoryTime()));
            mBtnCompeteMode.setText(R.string.commit_answer);

            if (mICommunicate != null) {
                mICommunicate.memoryTimeToEnd();
            }
        } else {//回忆模式下才可以提交答案
            this.commitAnswer();
        }
    }

    /**
     * 提交答案
     */
    private void commitAnswer() {
        mCountDownTime.stopCountTime();

        if (mICommunicate != null) {
            mICommunicate.rememoryTimeToEnd();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_compete_process:
                this.switchCompeteProcess();
                break;
            case R.id.text_change_layout_img_bg1:
            case R.id.text_change_layout_img_bg2:
            case R.id.text_change_layout_img_bg3:
                if (mICommunicate != null) {
                    mICommunicate.changeBg(mActivity.getResources().getColor((Integer) mColorArray.get(v.getId())));
                }
                break;
            case R.id.text_exit:
                if (mICommunicate != null) {
                    mICommunicate.exitActivity();
                }
            default:
                break;
        }
    }

    @Override
    public void endOfTime() {
        if (isRememoryStatus) {
            mTextTime.setText(R.string.time_end);
        }
        this.switchCompeteProcess();
    }

    @Override
    public void getTime(String hour, String minute, String second) {
        mTextTime.setText(mCountDownTime.getTimeString());
    }

    public void setICommunicate(ICommunicate iCommunicate) {
        mICommunicate = iCommunicate;
    }

    /**
     * CommonFragment对外通讯接口
     */
    public interface ICommunicate {
        /**
         * 更换背景
         *
         * @param color 颜色值
         */
        void changeBg(int color);

        /**
         * 当记忆时间到时调用的方法
         */
        void memoryTimeToEnd();

        /**
         * 当回忆时间到时调用的方法
         */
        void rememoryTimeToEnd();

        /**
         * 退出当前Activity
         */
        void exitActivity();
    }

}
