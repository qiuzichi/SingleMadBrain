package com.unipad.brain.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.ProjectBean;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.brain.home.competitionpj.CompetitionListPresenter;
import com.unipad.brain.home.dao.HomeGameHandService;
import com.unipad.brain.home.iview.ICompetitionList;
import com.unipad.common.BaseFragmentActivity;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

/**
 * @描述：  赛事列表
 * @author gongjiebin
 *
 */
public class CompetitionListActivity extends BaseFragmentActivity implements ICompetitionList,OnPageChangeListener,IDataObserver {

	@ViewInject(R.id.viewPager_MessageFragment)
	private ViewPager viewPager;
	
	private CompetitionListPresenter competitionListPresenter;
	
	// 自定义 titleView
	private RelativeLayout titleView;
	
	
	@ViewInject(R.id.message_title_china)
	private TextView txt_title_china;  // 中國賽
	
	@ViewInject(R.id.message_title_city)
	private TextView txt_title_city;  // 城市賽
	
	@ViewInject(R.id.message_title_world)
	private TextView txt_title_world; // 世界賽
	
	private TextView[] titleTxts;
	
	private ProjectBean projectBean;

	private HomeGameHandService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_competition_list_layout);
		ViewUtils.inject(this);
		projectBean = (ProjectBean) getIntent().getSerializableExtra("projectBean");
		service = (HomeGameHandService) AppContext.instance().getService(Constant.HOME_GAME_HAND_SERVICE);
		service.registerObserver(HttpConstant.GET_RULE_NOTIFY,this);
		initView(null);
		changeTitleUI(0);
		
	}

	public void getGameList(String groupId,int page,int size) {
		service.sendMsgGetGame(projectBean.getProjectId(),groupId,page,size);
	}

	@Override
	public void initView(View view) {
		super.initView(view);
		competitionListPresenter = new CompetitionListPresenter(this);
		setLeft(R.drawable.back, getResources().getString(R.string.str_back));
		this.initListener();
		titleView = (RelativeLayout) View.inflate(this, R.layout.title_competition_layout, null);
		// 填充容器
		fillTitleView(titleView);
		ViewUtils.inject(this, titleView);
		viewPager.setOnPageChangeListener(this);
		titleTxts = new TextView[]{txt_title_city,txt_title_china,txt_title_world};
		txt_title_city.performClick();
	}


	
	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void request(String jsonStr, int flag) {
		
	}

	@Override
	public void showToast(String checkResult) {

	}

	@Override
	public void loadingDialog(long total, long current, boolean isUploading) {
		
	}

	@Override
	public void showDialog(boolean isOpen) {
		
	}

	@Override
	public void setAdapter(FragmentStatePagerAdapter fragmentStatePagerAdapter) {
		viewPager.setAdapter(fragmentStatePagerAdapter);
	}
	
	
	
	@OnClick({R.id.message_title_china,R.id.message_title_city,R.id.message_title_world})
	public void onTitleClick(View view){
		//
		if(viewPager != null)
			viewPager.setCurrentItem(view.getId() == R.id.message_title_city ? 0 : view.getId() == R.id.message_title_china ? 1 : 2);
	}

	
	/**
	 * @描述：  改变title UI的颜色状态
	 * @param position  当前页面下标位置
	 */
	public void changeTitleUI(int position){
		TextView thisTxt = titleTxts[position];
		for(int i = 0 ; i < titleTxts.length; i ++ ) {
			if(titleTxts[i].getId() == thisTxt.getId()){
				// 选中
				titleTxts[i].setTextColor(getResources().getColor(R.color.black));
				titleTxts[i].setBackgroundColor(getResources().getColor(R.color.white));
			} else {
				// 没选中
				titleTxts[i].setTextColor(getResources().getColor(R.color.white));
				titleTxts[i].setBackgroundColor(getResources().getColor(R.color.transparent));
			}
		}
	}

	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		service.unregistDataChangeListenerObj(this);
	}

	@Override
	public void onPageSelected(int arg0) {
		changeTitleUI(arg0);
	}


	@Override
	public ProjectBean getProjectBean() {
		return projectBean;
	}

	@Override
	public void update(int key, Object o) {
		switch (key){
			case HttpConstant.GET_RULE_NOTIFY:
				ToastUtil.createRuleDialog(this,Constant.SHOW_RULE_DIG,(RuleGame)o).show();
				break;
			default:
				break;
		}
	}
}
