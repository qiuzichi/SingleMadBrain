package com.unipad.common;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.IOperateGame;
import com.unipad.brain.AbsBaseGameService;

import java.util.Map;

public abstract class BasicCommonFragment extends Fragment implements
        View.OnClickListener, CommonFragment.ICommunicate ,IOperateGame{
    protected CommonActivity mActivity;
    protected ViewGroup mViewParent;
    protected int memoryTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewParent = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        //mViewParent.setVisibility(View.GONE);
        return mViewParent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (CommonActivity) getActivity();
        mActivity.getCommonFragment().setICommunicate(this);
        mViewParent.setVisibility(View.GONE);
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
        mViewParent.setVisibility(View.GONE);
    }

    @Override
    public void startGame() {
        mViewParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void reStartGame() {
        mViewParent.setVisibility(View.VISIBLE);
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
    }
}
