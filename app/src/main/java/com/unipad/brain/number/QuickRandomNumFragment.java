package com.unipad.brain.number;

import android.view.View;

import com.unipad.brain.number.view.KeyboardDialog;
import com.unipad.brain.number.view.NumberMemoryLayout;
import com.unipad.common.Constant;
import com.unipad.common.widget.HIDDialog;
import com.unipad.utils.ToastUtil;

/**
 * Created by gongkan on 2016/7/18.
 */
public class QuickRandomNumFragment extends  NumberRightFragment{
    private KeyboardDialog mKeyboardDialog;
    @Override
    public boolean isNeedShowCurrent() {
        return true;
    }

    @Override
    public String getCompeteItem() {
        return Constant.getProjectName(mActivity.getProjectId());
    }

    @Override
    public void initAnswerView() {
        mKeyboardDialog = new KeyboardDialog(mActivity);
        mKeyboardDialog.setKeyboardClickListener(this);
    }

    @Override
    public void initMemoryView() {
        frameLayout.removeAllViews();
        frameLayout.addView(new NumberMemoryLayout(mActivity, service.lineNumbers));
    }

    @Override
    public void startRememory() {
        super.startRememory();
        if (mKeyboardDialog != null) {
            mKeyboardDialog.show();
        }
    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        if (mKeyboardDialog != null) {
            mKeyboardDialog.dismiss();
        }
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        super.rememoryTimeToEnd(answerTime);

        mKeyboardDialog.dismiss();
        if (isMatchMode()) {
            getAnswer();
            if (service.isLastRound()) {

            } else {
                ToastUtil.createTipDialog(mActivity, Constant.SHOW_GAME_PAUSE, "开始准备下一轮").show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        service.parseDataByNextRound();
                    }
                }.start();
            }
        }else{
            showAnswer();
        }
    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);
        //第一轮的时候，会自动发一个消息给管控端，告诉管控端已结束记忆，准备开始答题
        //进度需要初始化。
        if (service.round != 1){
            progress = 100;
            sendMsgToPreper();
        }
    }
}
