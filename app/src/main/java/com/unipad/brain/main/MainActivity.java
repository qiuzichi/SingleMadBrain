package com.unipad.brain.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.unipad.brain.BasicActivity;

import com.unipad.brain.R;
import com.unipad.brain.home.HomeLeftFragment;
import com.unipad.brain.personal.PersonalActivity;

/**
 * Created by Wbj on 2016/4/7.
 */
public class MainActivity extends BasicActivity {
    private static final int MSG_LOCATION = 0x100;
    private TextView mTextLocation;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private HomeLeftFragment mHomeFragment = new HomeLeftFragment();
    private Fragment mCurrentFrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_aty);
    }

    @Override
    public void initData() {
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.c_rfg_container, mHomeFragment);
        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
        mCurrentFrg = mHomeFragment;

        mTextLocation = (TextView) findViewById(R.id.main_lf_text_locating);
        findViewById(R.id.main_lf_home).setOnClickListener(this);
        findViewById(R.id.main_lf_user).setOnClickListener(this);
        findViewById(R.id.main_lf_location).setOnClickListener(this);
        findViewById(R.id.main_lf_message).setOnClickListener(this);
        findViewById(R.id.main_lf_setting).setOnClickListener(this);

        mHandler.sendEmptyMessageDelayed(MSG_LOCATION, 5000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_lf_home:
                if (mCurrentFrg != mHomeFragment) {
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.hide(mCurrentFrg).show(mHomeFragment);
                    mCurrentFrg = mHomeFragment;
                }
                break;
            case R.id.main_lf_user:
                this.openPersonalActivity();
                break;
            default:
                break;
        }
    }

    private void openPersonalActivity() {
        Intent intent = new Intent(this, PersonalActivity.class);
        startActivity(intent);
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case MSG_LOCATION:
                findViewById(R.id.pb_locating).setVisibility(View.GONE);

                mTextLocation.setVisibility(View.VISIBLE);
                mTextLocation.setText("乌鲁木齐");
                mTextLocation.setSelected(true);
                break;
            default:
                break;
        }
    }

}
