package com.unipad.common;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BasicCommonFragment extends Fragment implements
        View.OnClickListener, CommonFragment.ICommunicate {
    protected CommonActivity mActivity;
    protected ViewGroup mViewParent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewParent = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        return mViewParent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (CommonActivity) getActivity();
        mActivity.getCommonFragment().setICommunicate(this);
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
}
