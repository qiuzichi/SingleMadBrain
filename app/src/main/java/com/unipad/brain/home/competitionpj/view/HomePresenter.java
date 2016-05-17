package com.unipad.brain.home.competitionpj.view;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.slidingmenu.lib.SlidingMenu;
import com.unipad.brain.R;
import com.unipad.brain.home.ProjectView;
import com.unipad.brain.home.bean.HomeBean;
import com.unipad.brain.home.bean.ProjectBean;
import com.unipad.brain.home.iview.IHome;
import com.unipad.brain.home.iview.ISidebar;
import com.unipad.brain.home.util.CommomAdapter;
import com.unipad.brain.home.util.ConstSettings;
import com.unipad.common.BaseFragment;
import com.unipad.common.BaseFragmentActivity;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;

/**
 * @描述：  项目首页
 * @author gongjiebin
 *
 */
public class HomePresenter implements ISidebar {

	private IHome iHome;

	private BaseAdapter homeAdapter;
	
	private List<HomeBean> homeBeans;
	
	private List<ProjectBean> projectBeans;
	
	private SlidingMenu menu;
	
	private Resources resources;
	
	private String next;
	
     public String currentContentFragmentTag;
	
	private FragmentManager fragmentManager;
	
	public HomePresenter(IHome iHome) {
		super();
		this.iHome = iHome;
		fragmentManager = ((BaseFragment)iHome).getFragmentManager();
		resources = iHome.getContext().getResources();
		next = resources.getString(R.string.next);
		initData();
		initProjects();
		setAdapter();
	}
	
	/*
	 * 初始化项目容器
	 */
	private void initProjects(){
		projectBeans = new ArrayList<ProjectBean>();
		for(int i = 0; i < homeBeans.size() ;i ++ ){
			homeBeans.get(i).name = ConstSettings.getProjectValues(iHome.getContext()).get(i);
			homeBeans.get(i).projectBean.setName(ConstSettings.getProjectValues(iHome.getContext()).get(i));
			projectBeans.add(homeBeans.get(i).projectBean);
		}
	}
	
	
	/**
	 * 此方法  填充项目内容
	 * @param position  代表项目
	 */
	public void setProjectContent(int position){
			// 需要展示的视图
		ProjectView projectView = new ProjectView();
		projectView.setProjectView(iHome.getContext(), this, position,projectBeans.get(position));
		// 显示视图
		switchFragment(R.id.fl_project, projectView, position+"");
	}
	
	
	public Fragment switchFragment(int id, Fragment fragment,String tag) {
		if(tag.equals(currentContentFragmentTag)){
			return null;
		}
		
		
		FragmentTransaction tr = fragmentManager.beginTransaction();
		if (currentContentFragmentTag != null) {
			final Fragment currentFragment = fragmentManager.findFragmentByTag(currentContentFragmentTag);
			if (currentFragment != null) {
				tr.hide(currentFragment);// 将当前的Frament隐藏到后台去
			}
		}
		
		if (fragment != null && fragment.isAdded()) {
			tr.show(fragment);// 显示要显示的frament
		} else {
			tr.add(id, fragment, tag);// 如果没添加，就添加进去并且会显示出来
		}
		currentContentFragmentTag = tag;
		tr.commitAllowingStateLoss();
		return fragment;
	}
	

	/**
	 *  此方法，  初始化home页左边菜单。
	 */
	private List<HomeBean>  initData(){
		homeBeans = new ArrayList<HomeBean>();
		//人名头像记忆
		ProjectBean npPj = new ProjectBean("","" , new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean npBean = new HomeBean(R.drawable.sel_np,R.drawable.nor_np,npPj,false);
		//二进制数字
		ProjectBean ejzPj = new ProjectBean("", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean ejzBean = new HomeBean(R.drawable.sel_rjz,R.drawable.nor_rjz,ejzPj,false);
		//马拉松数字项目
		ProjectBean mlsNumPj = new ProjectBean("", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean mlsNumBean = new HomeBean(R.drawable.sel_mlsnum,R.drawable.nor_mlsnum,mlsNumPj,false);
		//抽象图形
		ProjectBean cxTxPj = new ProjectBean("", "", new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean cxTxBean = new HomeBean(R.drawable.sel_cx,R.drawable.nor_cx,cxTxPj,false);
		// 快速随机数字
		ProjectBean kssjPj = new ProjectBean("", resources.getString(R.string.project_5_target) ,  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},resources.getString(R.string.project_5_attention_content),resources.getString(R.string.project_5_memory_content),resources.getString(R.string.project_5_recall_content),resources.getString(R.string.project_5_jf_function_content));
		HomeBean kssjBean = new HomeBean(R.drawable.sel_kssj,R.drawable.nor_kssj,kssjPj,false);
		// 虚拟事件和日期
		ProjectBean xnsjPj = new ProjectBean("", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean xnsjBean = new HomeBean(R.drawable.sel_xlsj,R.drawable.nor_xlsj,xnsjPj,false);
		// 马拉松扑克记忆
		ProjectBean mlspkPj = new ProjectBean("", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean mlspkBean = new HomeBean(R.drawable.sel_mlspk,R.drawable.nor_mlspk,mlspkPj,false);
		// 随机词语记忆
		ProjectBean sjcyPj = new ProjectBean("", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean sjcyBean = new HomeBean(R.drawable.sel_sjcy,R.drawable.nor_sjcy,sjcyPj,false);
		//听记数字
		ProjectBean thnumPj = new ProjectBean("", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean thnumBean = new HomeBean(R.drawable.sel_tjnum,R.drawable.nor_tjnum,thnumPj,false);
		// 快速扑克记忆
		ProjectBean kspkPj = new ProjectBean("", "",  new String[]{"5min","5min","5min"}, new String[]{"15min","15min","15min"}, new String[]{"1" + next,"2" + next,"2" + next},"","","","");
		HomeBean kspkBean = new HomeBean(R.drawable.sel_kspk,R.drawable.nor_kspk,kspkPj,false);
		homeBeans.add(npBean);
		homeBeans.add(ejzBean);
		homeBeans.add(mlsNumBean);
		homeBeans.add(cxTxBean);
		homeBeans.add(kssjBean);
		homeBeans.add(xnsjBean);
		homeBeans.add(mlspkBean);
		homeBeans.add(sjcyBean);
		homeBeans.add(thnumBean);
		homeBeans.add(kspkBean);

		return homeBeans;
	}
	
	

	/**
	 *  设置适配器
	 */
	private void setAdapter(){
		iHome.setAdapter(homeAdapter = new CommomAdapter<HomeBean>(iHome.getContext(), initData(),R.layout.fragment_home_item) {
			public void convert(ViewHolder holder, HomeBean homeBean) {
				holder.setText(R.id.txt_name, homeBean.name);
				holder.setImageResource(R.id.img_photo, homeBean.isSelect ? homeBean.selImgId : homeBean.norImgId);
				holder.setTextColor(R.id.txt_name, homeBean.isSelect ? iHome.getContext().getResources().getColor(R.color.main_1) : iHome.getContext().getResources().getColor(R.color.black));

				/////////----- 以下两行代码表示 设置某个控件的点击事件-----////
//				holder.getView(R.id.btn_add).setTag(homeBean);
//				holder.getView(R.id.btn_add).setOnClickListener(addDoctorListener);
			}
		});
	}
	
	
	public void setMenuOpen(){
		if( null != menu){
			if(!menu.isShown()){
				menu.showMenu();
			} else {
				menu.toggle();
			}
		}
	}
	
	/*
	 * 设置侧边栏
	 */
	public View setSidebar() {
		menu = new SlidingMenu(iHome.getContext());
		// 设置左侧边栏
		menu.setMode(SlidingMenu.RIGHT);
		// 手指滑动全屏屏幕，弹出侧边栏
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置阴影图片的宽度
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// 设置阴影图片的宽度
		menu.setShadowDrawable(R.drawable.shadow);
		// SlidingMenu划出时主页面显示的剩余宽度
		//menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置SlidingMenu菜单的宽度
		//menu.setBehindWidth(550);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setBehindOffset(((BaseFragmentActivity)iHome.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 2);
		// SlidingMenu滑动时的渐变程度
		menu.setFadeDegree(0.55f);
		menu.attachToActivity((BaseFragmentActivity)iHome.getContext(), SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.sliding_menu_layout);
		// 得到View对象
		 View view = menu.getMenu();
		 // 设置点击事件
		 return view;
	}
	
	
	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	
	/**
	 * @描述：  此方法利用循环 改变列表中的控件状态
	 * @param arg2 当前选中的位置
	 */
	public void changeUI(int arg2) {
		for(int i = 0 ; i < homeBeans.size(); i ++){
			if(i == arg2){
				homeBeans.get(i).isSelect = true;
			} else {
				homeBeans.get(i).isSelect = false;
			}
		}
		homeAdapter.notifyDataSetChanged();
	}

	
	@Override
	public void onClickHelpButton(boolean isOpen, int position) {
		if(isOpen){
			setMenuOpen();
			iHome.setSlidingMenuTxt(projectBeans.get(position));
		}
	}
}
