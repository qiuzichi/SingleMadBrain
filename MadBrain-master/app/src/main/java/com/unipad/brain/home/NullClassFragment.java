package com.unipad.brain.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.unipad.brain.R;
import com.unipad.common.BaseFragment;

/**
 * @描述： 首页初始化调用
 * @author gongjiebin
 *
 */
public class NullClassFragment extends BaseFragment {
	
	final public static String TAG = "NullClassFragment";
	
	private static NullClassFragment nullClassFragment;
	
	public static NullClassFragment getNullClassFragment(){
		if( null == nullClassFragment)
			return nullClassFragment = new NullClassFragment();
		return nullClassFragment;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View homeView = inflater.inflate(R.layout.fragment_null_class, container,false);
		ViewUtils.inject(this, homeView);
		initView(homeView);
		return homeView;
	}
}
