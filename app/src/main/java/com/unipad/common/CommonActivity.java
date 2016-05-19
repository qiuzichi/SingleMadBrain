package com.unipad.common;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.absPic.view.AbsFigureFragment;
import com.unipad.brain.number.NumberRightFragment;
import com.unipad.brain.portraits.view.HeadPortraitFragment;
import com.unipad.brain.virtual.VirtualRightFragment;
import com.unipad.brain.words.WordRightFragment;

/**
 * Created by Wbj on 2016/4/7.
 */
public class CommonActivity extends BasicActivity {
    public static final String COMPETE_TYPE = "CompeteType";
    private CommonFragment mCommonFragment = new CommonFragment();
    /**
     * 比赛项目
     */
    private String mCompeteItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_aty);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mCompeteItem = intent.getStringExtra(COMPETE_TYPE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.common_lfg_container, mCommonFragment);
        if (mCompeteItem.equals(getString(R.string.project_2)) || mCompeteItem.equals(getString(R.string.project_3))
                || mCompeteItem.equals(getString(R.string.project_5)) || mCompeteItem.equals(getString(R.string.project_9))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new NumberRightFragment());
        } else if (mCompeteItem.equals(getString(R.string.project_6))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new VirtualRightFragment());
        } else if (mCompeteItem.equals(getString(R.string.project_8))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new WordRightFragment());
        } else if (mCompeteItem.equals(getString(R.string.project_1))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new HeadPortraitFragment());
        } else if (mCompeteItem.equals(getString(R.string.project_4))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new AbsFigureFragment());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }


    public CommonFragment getCommonFragment() {
        return mCommonFragment;
    }

    public String getCompeteItem() {
        return mCompeteItem;
    }

}
