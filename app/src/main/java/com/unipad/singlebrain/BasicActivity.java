package com.unipad.singlebrain;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.unipad.utils.ActivityCollector;

/**
 * Created by Wbj on 2016/4/7.
 */
public abstract class BasicActivity extends FragmentActivity implements View.OnClickListener, App.HandlerCallback {
    protected App.AppHandler mHandler = new App.AppHandler(this);
    private boolean mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mFlag) {
            mFlag = true;
            this.initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void dispatchMessage(Message msg) {

    }

    /**
     * 为了编码的规范，此方法用于初始化变量(包括初始化控件)
     */
    public abstract void initData();


}
