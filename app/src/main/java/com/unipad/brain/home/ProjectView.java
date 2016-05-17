package com.unipad.brain.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.ProjectBean;
import com.unipad.brain.home.iview.ISidebar;
import com.unipad.common.BaseFragment;
/**
 * @描述：  项目容器
 * @author gongjiebin
 *
 */
public class ProjectView extends BaseFragment {
	
	private ISidebar iSidebar;
	
	private int position;
	
	private String strMemory; 
	
	private String recall ; 
	
	private String num;

	private ProjectBean projectBean;

	public void  setProjectView(Context context,ISidebar iSidebar,int position,ProjectBean projectBean) {
		this.iSidebar = iSidebar;
		this.position = position;
		this.projectBean = projectBean;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View homeView = inflater.inflate(R.layout.fragment_project_layout, container,false);
		ViewUtils.inject(this, homeView);
		initView(homeView);
		createView();
		setProbjectUi();
		return homeView;
	}
	
	
	
	@ViewInject(R.id.txt_pname)
	private TextView txt_pname;
	
	// 目标
	@ViewInject(R.id.txt_target)
	private TextView txt_target;
	
	//-------------- 城市赛start --- 
	@ViewInject(R.id.txt_city_memory)
	private TextView  txt_city_memory;
	
	@ViewInject(R.id.txt_city_recall)
	private TextView txt_city_recall;
	
	@ViewInject(R.id.txt_city_num)
	private TextView txt_city_num;
	////// -----------  城市赛 end ----
	
	
	// -------------- 国家赛 start ---
	@ViewInject(R.id.txt_china_memory)
	private TextView txt_china_memory;
	
	@ViewInject(R.id.txt_china_recall)
	private TextView txt_china_recall;
	
	@ViewInject(R.id.txt_china_num)
	private TextView txt_china_num;
	
	////// ------------- 国家赛  end -------
	
	
	//------------- 世界赛 start
	
	@ViewInject(R.id.txt_world_memory)
	private TextView txt_world_memory;
	
	@ViewInject(R.id.txt_world_recall)
	private TextView txt_world_recall;
	
	@ViewInject(R.id.txt_world_num)
	private TextView txt_world_num;
	///// --------- 世界赛 end

	public void createView() {
		strMemory = getResources().getString(R.string.memory);
		recall = getResources().getString(R.string.recall);
		num = getResources().getString(R.string.num);
	}
	
	@OnClick(R.id.img_phelp)
	public void onHelpClick(View view){
		if(null != iSidebar)
			iSidebar.onClickHelpButton(true,position);
	}
	
	@OnClick(R.id.btn_apple)
	public void onAppleClick(View view){
		Intent intent = new Intent(this.getActivity(),CompetitionListActivity.class);
		intent.putExtra("projectBean", projectBean);
		this.startActivity(intent);
	}
	
	/**
	 *  设置本界面的UI,  实现动态加载数据
	 */
	private void setProbjectUi(){
		if(null == projectBean)
			return;
		txt_pname.setText(projectBean.getName());
		String target  = getResources().getString(R.string.target)+ projectBean.getTarget();
		SpannableString ss = new SpannableString(target);
		ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.main_1)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(Color.BLACK), 2, target.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		txt_target.setText(ss);
		String [] memorys = projectBean.getMemorysDate();
		String [] recalls = projectBean.getRecallsDate();
		String[] memorysNum = projectBean.getMemorysNum();
		if(null != memorys){
			// 记忆时间
			txt_city_memory.setText(strMemory + memorys[0]);
			txt_china_memory.setText(strMemory + memorys[1]);
			txt_world_memory.setText(strMemory + memorys[2]);
		}
		
		if(null != recalls){
			// 回忆时间
			txt_city_recall.setText(recall + recalls[0]);
			txt_china_recall.setText(recall + recalls[1]);
			txt_world_recall.setText(recall + recalls[2]);
		}
		
		if(null != memorysNum){
			txt_city_num.setText(num + memorysNum[0]);
			txt_china_num.setText(num + memorysNum[1]);
			txt_world_num.setText(num + memorysNum[2]);
		}
	}
}
