package com.unipad.common;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.IOperateGame;
import com.unipad.utils.LogUtil;
import com.unipad.utils.ToastUtil;

import java.util.Map;

public abstract class BasicCommonFragment extends Fragment implements
        View.OnClickListener, CommonFragment.ICommunicate ,IOperateGame{
    protected CommonActivity mActivity;
    protected ViewGroup mViewParent;
    private Handler handler;
    private static final int MSG_PROGRESS = 0;
    protected int progress = 100;
    /** 记忆结束时，弹出dialog，等待裁判开始统一回忆，如果这个时候裁判点击暂停，那个dialog消失，出现
     * 暂停的dialog，恢复开始的时候，原来等待裁判开始的dialog又得显示出来，倒计时不能开始*/
    private boolean isNeedStartGame;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewParent = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        if (isMatchMode()) {
            handler = new Handler(){
                @Override
                public void dispatchMessage(Message msg) {
                    mActivity.progressGame(progress);
                    if (progress == 100 || progress == 200){

                    }else {
                        LogUtil.e("","Rememory progress :"+progress);
                        handler.sendEmptyMessageDelayed(MSG_PROGRESS,10000);
                    }
                }
            };
        }
        return mViewParent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (CommonActivity) getActivity();
        mActivity.getCommonFragment().setICommunicate(this);;

    }
    /**
     * 是否是比赛模式
     * */
    protected boolean isMatchMode(){
        return true;
    }
    @Override
    public void changeBg(int color) {
        mViewParent.setBackgroundColor(color);
    }

    @Override
    public void exitActivity() {
        mActivity.finish();
    }

    public abstract int getLayoutId();
    @Override
    public void pauseGame() {

    }

    @Override
    public void startMemory() {

    }

    @Override
    public void startRememory() {
        isNeedStartGame = false;
        progress = 101;
        handler.sendEmptyMessage(MSG_PROGRESS);
    }

    @Override
    public void reStartGame() {
        if (isNeedStartGame) {
            ToastUtil.createTipDialog(mActivity, Constant.SHOW_GAME_PAUSE, "等待裁判开始答题").show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(MSG_PROGRESS);
    }

    @Override
    public void initDataFinished() {

    }

    @Override
    public void finishGame() {

    }

    @Override
    public void downloadingQuestion(Map<String,String> map) {

    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {


    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        progress = 200;
        handler.sendEmptyMessage(MSG_PROGRESS);
    }

    public  void sendMsgToPreper(){
        isNeedStartGame = true;
        ToastUtil.createTipDialog(mActivity, Constant.SHOW_GAME_PAUSE, "等待裁判开始答题").show();
        handler.sendEmptyMessage(MSG_PROGRESS);
    }

    public boolean isNeedStartGame() {
        return isNeedStartGame;
    }
}
