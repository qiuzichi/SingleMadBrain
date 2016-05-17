package com.unipad.brain.words;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.unipad.brain.R;
import com.unipad.common.BasicFragment;

/**
 * liupeng
 */
public class WordRightFragment extends BasicFragment {
    /**
     * Fragment界面父布局
     */
    private RelativeLayout mParentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentLayout = (RelativeLayout) inflater.inflate(R.layout.word_frg_right, container,
                false);
        return mParentLayout;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void changeBg(int color) {
        mParentLayout.setBackgroundColor(color);
    }

    @Override
    public void memoryTimeToEnd() {
    }

    @Override
    public void rememoryTimeToEnd() {
    }

}
