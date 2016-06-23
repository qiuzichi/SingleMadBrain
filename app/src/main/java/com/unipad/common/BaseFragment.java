package com.unipad.common;


import java.util.List;

import android.annotation.TargetApi;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipad.brain.R;
import com.unipad.brain.absPic.bean.SystemBarTintManager;
import com.unipad.brain.home.SApplication;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BaseFragment extends Fragment implements FragmentCallback,View.OnClickListener {

	public TextView title_txt;
	public Button button_left;
	public Button button_right;
//	public View titleView;
	// title 容器  .  可根据不同的需求填充不同 view  默认为 TextView
	public LinearLayout ll_title_vessel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);

	}
	
	@Override
	public void initView(View view) {
		title_txt = (TextView) view.findViewById(R.id.tvTitle);
		button_left = (Button) view.findViewById(R.id.title_Left);
		button_right = (Button) view.findViewById(R.id.title_right);
		ll_title_vessel = (LinearLayout) view.findViewById(R.id.ll_title_vessel);
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
	

	@Override
	public void initListener() {
		button_left.setOnClickListener(this);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public Context getContext() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * 设置标题
	 * 
	 * @param text
	 */
	public void setTitle(String text) {
		title_txt.setText(text);
	}

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

	@Override
	public void dialogControySure(Object... param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dialogControyCancel() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		super.onResume();

	}
	
	@Override
	public void onStop() {
		super.onStop();

	}
	
	/**
	 * 程序是否在前台运�?
	 * 
	 * @return
	 */
	public boolean isAppOnForeground() {
//		// Returns a list of application processes that are running on the
//		// device
//		String packageName = app.getPackageName();
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
			break;
		default:
			break;
		}
	}

}
