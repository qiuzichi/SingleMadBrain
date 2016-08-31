package com.unipad.singlebrain.home.iview;

import android.support.v4.app.FragmentStatePagerAdapter;

import com.unipad.singlebrain.home.bean.ProjectBean;


/**
 * 
 * @author gongjiebin
 *
 */
public interface ICompetitionList extends BaseView {
	
	
	public void setAdapter(FragmentStatePagerAdapter fragmentStatePagerAdapter);
	
	/**
	 *  得到项目对象
	 * @return
	 */
	public ProjectBean getProjectBean();
}
