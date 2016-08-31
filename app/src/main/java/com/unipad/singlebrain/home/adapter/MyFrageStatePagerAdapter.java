package com.unipad.singlebrain.home.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 *
 * @author gongjb
 * 
 * @描述:   fragment page
 *
 */
public class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

	 /** 
     * 页面集合 
     */  
    private List<Fragment> fragmentList;
    
	public MyFrageStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void setFragmentList(List<Fragment> fragmentList){
		this.fragmentList = fragmentList;
	}
   
	 @Override  
     public Fragment getItem(int position) {  
         return fragmentList.get(position);  
     }  

     @Override  
     public int getCount() {  
         return fragmentList.size();  
     }  
       
     /** 
      * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动 
      */  
     @Override  
     public void finishUpdate(ViewGroup container)  {  
         super.finishUpdate(container);//这句话要放在最前面，否则会报错  
         //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置  
     }  
}
