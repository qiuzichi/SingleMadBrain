package com.unipad.common;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.number.NumberRightFragment;
import com.unipad.brain.portraits.view.HeadPortraitFragment;
import com.unipad.brain.virtual.VirtualRightFragment;
import com.unipad.brain.words.WordRightFragment;

/**
 * Created by Wbj on 2016/4/7.
 */
public class CommonActivity extends BasicActivity {
    public static final String COMPETE_TYPE = "CompeteType";
    public static final int COMPETE_TYPE_DEFAULT = -1;
    private CommonFragment mCommonFragment = new CommonFragment();
    /**
     * 比赛项目
     */
    public static int CompeteItem = COMPETE_TYPE_DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_aty);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        CompeteItem = intent.getIntExtra(COMPETE_TYPE, COMPETE_TYPE_DEFAULT);
        if (CompeteItem == COMPETE_TYPE_DEFAULT) {
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.common_lfg_container, mCommonFragment);
        if (CompeteItem == 1 || CompeteItem == 2 || CompeteItem == 4 || CompeteItem == 8) {
            fragmentTransaction.replace(R.id.common_rfg_container, new NumberRightFragment());
        } else if (CompeteItem == 5) {
            fragmentTransaction.replace(R.id.common_rfg_container, new VirtualRightFragment());
        } else if (CompeteItem == 7) {
            fragmentTransaction.replace(R.id.common_rfg_container, new WordRightFragment());
        } else if (CompeteItem == 0 || CompeteItem == 3) {
            fragmentTransaction.replace(R.id.common_rfg_container, new HeadPortraitFragment());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }


    public CommonFragment getCommonFragment() {
        return mCommonFragment;
    }

}
