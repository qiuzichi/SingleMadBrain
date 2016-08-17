package com.unipad.brain.number;

import android.view.View;

import com.unipad.brain.R;
import com.unipad.brain.number.view.NumberMemoryLayout;
import com.unipad.common.AbsMatchActivity;
import com.unipad.common.CommonActivity;
import com.unipad.common.Constant;

/**
 * Created by gongkan on 2016/7/4.
 */
public class BinaryRightFragment extends NumberRightFragment{


    @Override
    public boolean isNeedShowCurrent() {
        return false;
    }

    @Override
    public String getCompeteItem() {
        return Constant.getProjectName(((AbsMatchActivity) getActivity()).getProjectId());
    }

    @Override
    public void initAnswerView() {
        View.inflate(getActivity(), R.layout.binary_v_bottom, mLayoutBottom);
        mLayoutBottom.findViewById(R.id.ibtn_binary_0).setOnClickListener(this);
        mLayoutBottom.findViewById(R.id.ibtn_binary_1).setOnClickListener(this);
        mLayoutBottom.findViewById(R.id.btn_vibrate).setOnClickListener(this);
        mLayoutBottom.findViewById(R.id.btn_delete).setOnClickListener(this);
        mLayoutBottom.findViewById(R.id.btn_go_top).setOnClickListener(this);
    }

    @Override
    public void initMemoryView() {
        frameLayout.removeAllViews();
        frameLayout.addView(new NumberMemoryLayout(getActivity(), service.lineNumbers));
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        super.rememoryTimeToEnd(answerTime);
        getAnswer();
    }
}
