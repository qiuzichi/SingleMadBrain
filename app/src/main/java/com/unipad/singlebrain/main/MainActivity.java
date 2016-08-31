package com.unipad.singlebrain.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.unipad.AppContext;
import com.unipad.baiduservice.LocationUtils;
import com.unipad.singlebrain.BasicActivity;

import com.unipad.singlebrain.R;
import com.unipad.singlebrain.dialog.ShowDialog;
import com.unipad.singlebrain.home.MainBasicFragment;
import com.unipad.singlebrain.home.MainCompeteFragment;
import com.unipad.singlebrain.home.MainHomeFragment;
import com.unipad.singlebrain.location.LocationActivity;
import com.unipad.singlebrain.personal.PersonalActivity;

/**
 * Created by Wbj on 2016/4/7.
 */
public class MainActivity extends BasicActivity implements  ShowDialog.OnShowDialogClick,LocationUtils.OnLocationListener  {
    private static final int MSG_LOCATION = 0x100;
    private TextView mTextLocation;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private MainHomeFragment mHomeFragment = new MainHomeFragment();
    private MainCompeteFragment mCompeteFragment = new MainCompeteFragment();
    private MainBasicFragment mCurrentFrg;
    private View mCurrentView;
    private ShowDialog showDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_aty);
    }

    @Override
    public void initData() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.c_rfg_container, mHomeFragment);
        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
        mCurrentFrg = mHomeFragment;

        View view = findViewById(R.id.main_lf_home);
        view.setOnClickListener(this);
        mCurrentView = view;
        mCurrentView.setSelected(true);

        mTextLocation = (TextView) findViewById(R.id.main_lf_text_locating);
        findViewById(R.id.main_lf_user).setOnClickListener(this);
        findViewById(R.id.main_lf_compete).setOnClickListener(this);
        findViewById(R.id.main_lf_location).setOnClickListener(this);

        if(AppContext.instance().loginUser.getAuth() == 0 || AppContext.instance().loginUser.getAuth() == 3){
            View dialogView = View.inflate(this,R.layout.first_login_dialog,null);
            TextView txt_msg = (TextView)dialogView.findViewById(R.id.txt_msg);
            txt_msg.setText(AppContext.instance().loginUser.getAuth() == 0 ? this.getString(R.string.auth_hint) : this.getString(R.string.auth_fail_hint));
            showDialog = new ShowDialog(this);
            showDialog.showDialog(dialogView, ShowDialog.TYPE_CENTER,getWindowManager(),0.4f,0.5f);
            showDialog.setOnShowDialogClick(this);
            showDialog.bindOnClickListener(dialogView,new int[]{R.id.img_close});
        }

       // mHandler.sendEmptyMessageDelayed(MSG_LOCATION, 5000);
        LocationUtils locationUtils = new LocationUtils(this);
        locationUtils.setOnLocationListener(this);
        locationUtils.startLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_lf_home:
            case R.id.main_lf_compete:
                this.switchFragment(v);
                break;
            case R.id.main_lf_user:
                this.openPersonalActivity();
                break;
            case R.id.main_lf_location:
                this.openLocationActivity();
                break;
            default:
                break;
        }
    }

    private void switchFragment(View v) {
        if (mCurrentFrg == null || mCurrentView == v) {
            return;
        }

        if (mCurrentView != null) {
            mCurrentView.setSelected(false);
        }
        v.setSelected(true);
        mCurrentView = v;

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.hide(mCurrentFrg);

        if (v.getId() == R.id.main_lf_home) {
            mCurrentFrg = mHomeFragment;
            mFragmentTransaction.show(mHomeFragment);
        } else if (v.getId() == R.id.main_lf_compete) {
            if (mCompeteFragment == null) {
                return;
            }
            mCurrentFrg = mCompeteFragment;

            if (!mCompeteFragment.isAdded()) {//如果没有添加Fragment，则先添加
                mFragmentTransaction.add(R.id.c_rfg_container, mCompeteFragment);
            } else {//如果已经添加Fragment，则显示
                mFragmentTransaction.show(mCompeteFragment);
            }
        }

        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    private void openPersonalActivity() {
        Intent intent = new Intent(this, PersonalActivity.class);
        startActivity(intent);
    }

    private void openLocationActivity() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case MSG_LOCATION:

                break;
            default:
                break;
        }
    }

    @Override
    public void dialogClick(int id) {
        if(null != showDialog && showDialog.isShowing()){
            showDialog.dismiss();
        }
    }

    @Override
    public void sendPostion(BDLocation location) {
        AppContext.instance().location = location;
        findViewById(R.id.pb_locating).setVisibility(View.GONE);
        mTextLocation.setVisibility(View.VISIBLE);
        mTextLocation.setText(location.getCity());
        mTextLocation.setSelected(true);
    }


}
