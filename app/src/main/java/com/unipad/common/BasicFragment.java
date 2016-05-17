package com.unipad.common;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

public abstract class BasicFragment extends Fragment implements
        View.OnClickListener, CommonFragment.ICommunicate {
    protected CommonActivity mActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (CommonActivity) getActivity();
        mActivity.getCommonFragment().setICommunicate(this);
    }

    @Override
    public void exitActivity() {
        mActivity.finish();
    }

}
