package com.unipad.common;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipad.brain.R;

import java.util.List;



/**
 * 通用Activity，所有Activity的父
 */
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BaseActivity extends Activity implements IBaseActivity,View.OnClickListener {

	public static final int ERROR = 10001;
	public static final int COOKIE_INVILD = 10002;

	static public int screenWidth = 0;
	static public int screenHeight = 0;


	public TextView title_txt;
	public Button button_left;
	public Button button_right;
	
	// title 容器  .  可根据不同的需求填充不同 view  默认为 TextView
	public LinearLayout ll_title_vessel;
	
	/**
	 * 设置左边的图片与文字
	 * @param id 
	 * @param text
	 */
	public void setLeft(int id, String text) {
		button_left.setVisibility(View.VISIBLE);
		button_left.setText(text);
		setIcon(id, button_left);
	}


	/**
	 * 设置左边图片
	 * @param id
	 */
	public void setLeft(int id) {
		button_left.setVisibility(View.VISIBLE);
		setIcon(id, button_left);
	}

	/**
	 * 设置右边的图片
	 * @param id 
	 * @param
	 */
	public void setRight(int id) {
		button_right.setVisibility(View.VISIBLE);
		setIcon(id, button_right);
	}

	/**
	 * 设置右边的图片与文字
	 * @param id 
	 * @param text
	 */
	public void setRight(int id, String text) {
		button_right.setVisibility(View.VISIBLE);
		button_right.setText(text);
		setIcon(id, button_right);
	}
	
	/**
	 * 加载title 图片
	 * @param id
	 * @param param
	 */
	private void setIcon(int id, Button param) {
		if (id == -1) {
			return;
		}
		Drawable img_off;
		Resources res = getResources();
		img_off = res.getDrawable(id);
		img_off.setBounds(0, 0, img_off.getMinimumWidth(),img_off.getMinimumHeight());
		param.setCompoundDrawables(img_off, null, null, null); // 设置左图标
	}


	public void setRight(String text) {
//		button_right.setText(text);
	}

	public void setTitle(String text) {
		title_txt.setText(text);
	}

	protected void onStart() {
		super.onStart();

	}

	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initView() {
		title_txt = (TextView) findViewById(R.id.tvTitle);
		button_left = (Button) findViewById(R.id.title_Left);
		button_right = (Button) findViewById(R.id.title_right);
		ll_title_vessel = (LinearLayout) findViewById(R.id.ll_title_vessel);
	}

	
	/**
	 * @描述：  填充titleView
	 * @param view
	 */
	public void fillTitleView(View view){
		if(null != ll_title_vessel){
			ll_title_vessel.removeAllViews();
			ll_title_vessel.addView(view);
		}
	}
	
	// / 初始化事件
	public void initListener() {
		this.button_left.setOnClickListener(this);
	}



	protected void onStop() {
		super.onStop();
		if (!isAppOnForeground()) {
			// 程序进入后台			
//			app.setActive(false);
			// MainService.isActive = false;

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
//		 if (!app.isActive) {
//		 // 程序 从后台唤醒，进入前台
//			 app.setActive(true);
//			 /*
//			 if(MainService.msgThread != null)
//			 {
//				 synchronized (MainService.msgThread) {
//						try {
//							MainService.msgThread.wait();
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//			 }
//			 */
//		 }

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//setTranslucentStatus(true);
//		}
		
//		SystemBarTintManager mTintManager = new SystemBarTintManager(this);
//
//		mTintManager.setStatusBarTintEnabled(false);
//		mTintManager.setStatusBarTintResource(R.color.white);
	}

	
	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);

	}
	
	private void setscrollsize() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	/**
	 * 程序是否在前台运�?	 * 
	 * @return
	 */
	public boolean isAppOnForeground() {
		// Returns a list of application processes that are running on the
		// device
//		String packageName = getApplicationContext().getPackageName();
//
//		List<RunningAppProcessInfo> appProcesses = SApplication.activityManager
//				.getRunningAppProcesses();
//		if (appProcesses == null)
//			return false;
//
//		for (RunningAppProcessInfo appProcess : appProcesses) {
//			// The name of the process that this object is associated with.
//			if (appProcess.processName.equals(packageName)
//					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//				return true;
//			}
//		}
		return false;
	}


	@Override
	public void onClick(View v) {	
		switch (v.getId()) {
		case R.id.title_Left:
			this.finish();
			break;
		default:
			break;
		}
	}
}
