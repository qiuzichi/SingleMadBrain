package com.unipad.common;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import com.unipad.IOperateGame;
import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.io.mina.SocketThreadManager;


import java.util.Map;

public abstract class BasicCommonFragment extends Fragment implements
        View.OnClickListener, CommonFragment.ICommunicate ,IOperateGame{
    protected CommonActivity mActivity;
    protected ViewGroup mViewParent;
    protected int memoryTime;
    private App.AppHandler handler;
    private static final int MSG_PROGRESS = 0;
    protected int progress = 100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewParent = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        if (isMatchMode()) {
            handler = new App.AppHandler(new App.HandlerCallback(){
                @Override
                public void dispatchMessage(Message msg) {
                    mActivity.progressGame(progress);
                    if (progress == 100 || progress == 200){

                    }else {
                        handler.sendEmptyMessageDelayed(MSG_PROGRESS,10000);
                    }
                }
            });
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
        progress = 101;
        handler.sendEmptyMessage(MSG_PROGRESS);
    }

    @Override
    public void reStartGame() {

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
        this.memoryTime = memoryTime;
        handler.sendEmptyMessage(MSG_PROGRESS);
    }


}
