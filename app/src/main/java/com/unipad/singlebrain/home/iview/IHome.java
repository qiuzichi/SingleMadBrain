package com.unipad.singlebrain.home.iview;

import android.widget.BaseAdapter;

import com.unipad.singlebrain.home.bean.ProjectBean;


/**
 * @描述：  首页接口
 * @author gongjiebin
 *
 */
public interface IHome extends BaseView {
	
   public void setAdapter(BaseAdapter baseAdapter);
   
   // 设置侧边栏的文字
   public void setSlidingMenuTxt(ProjectBean projectBean);
}
