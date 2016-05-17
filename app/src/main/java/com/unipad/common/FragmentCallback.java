package com.unipad.common;

import android.content.Context;
import android.view.View;

public interface FragmentCallback {
   
	void initView(View view);// 初始化界面数�?

	void initListener(); // / 初始化事�?

	void refresh(Object... param);// 数据回调刷新
	

    void dialogControySure(Object... param);

	
	 void dialogControyCancel();
	
}
