package com.unipad.singlebrain.home.iview;

import android.widget.BaseAdapter;



public interface ICompetition extends BaseView{

	// 设置适配器
	public void setAdapter(BaseAdapter baseAdapter);
	
	// 返回当前页面的索引
	public String getCompetitionIndex();
}
