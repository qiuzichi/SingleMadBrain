package com.unipad.common;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.absPic.view.AbsFigureFragment;
import com.unipad.brain.number.NumberRightFragment;
import com.unipad.brain.portraits.view.HeadPortraitFragment;
import com.unipad.brain.virtual.VirtualRightFragment;
import com.unipad.brain.words.view.WordRightFragment;
import com.unipad.common.bean.CompeteItemEntity;
import com.unipad.io.bean.Request;
import com.unipad.io.w.SocketThreadManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wbj on 2016/4/7.
 */
public class CommonActivity extends BasicActivity {
    private CommonFragment mCommonFragment = new CommonFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_aty);
        Map<String,String> body = new HashMap<String,String>();
        body.put("USERID", AppContext.instance().loginUser.getUserId());
        body.put("SCHEDULEID", "6BD96887E062405EB2762BEBD2B7EE84");
        Request request = new Request("10001",body);
        SocketThreadManager.sharedInstance().sendMsg(request);
    }

    @Override
    public void initData() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.common_lfg_container, mCommonFragment);
        String competeItem = CompeteItemEntity.getInstance().getCompeteItem();
        if (competeItem.equals(getString(R.string.project_2)) || competeItem.equals(getString(R.string.project_3))
                || competeItem.equals(getString(R.string.project_5)) || competeItem.equals(getString(R.string.project_9))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new NumberRightFragment());
        } else if (competeItem.equals(getString(R.string.project_6))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new VirtualRightFragment());
        } else if (competeItem.equals(getString(R.string.project_8))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new WordRightFragment());
        } else if (competeItem.equals(getString(R.string.project_1))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new HeadPortraitFragment());
        } else if (competeItem.equals(getString(R.string.project_4))) {
            fragmentTransaction.replace(R.id.common_rfg_container, new AbsFigureFragment());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }


    public CommonFragment getCommonFragment() {
        return mCommonFragment;
    }

}
