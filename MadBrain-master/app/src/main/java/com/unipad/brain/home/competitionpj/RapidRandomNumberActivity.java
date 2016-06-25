package com.unipad.brain.home.competitionpj;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.home.competitionpj.icompetition.IRapidRandomNumber;
import com.unipad.brain.home.competitionpj.icompetition.OnLMenu;
import com.unipad.brain.home.competitionpj.view.CompetitionLeftMenuView;
import com.unipad.brain.home.util.MyTools;

/**
 * @描述： 快速随机数字  比赛界面
 * @author gongjiebin
 *
 */
public class RapidRandomNumberActivity extends BasicActivity implements OnLMenu,IRapidRandomNumber {

	// 界面左侧菜单类
	private CompetitionLeftMenuView competitionLeftMenuView;
	
	@ViewInject(R.id.fl_left_menu)
	private FrameLayout fl_left_menu;
	
	@ViewInject(R.id.lv_competition)
	private ListView lv_competition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rapid_random_number_layout);
		ViewUtils.inject(this);
		initData();
	}

	@Override
	public void initData() {
		competitionLeftMenuView = new CompetitionLeftMenuView(getContext());
		competitionLeftMenuView.setIlMenu(this);
		fl_left_menu.addView(competitionLeftMenuView);
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
		MyTools.showToast(getContext(), checkResult);
	}

	@Override
	public void loadingDialog(long total, long current, boolean isUploading) {
		
	}

	@Override
	public void showDialog(boolean isOpen) {
		
	}
	
	@Override
	public void onMenuClick(int id) {
		 switch (id) {
		 case R.id.txt_exit:
			this.finish();
			break;
		 case R.id.btn_end:
			// 结束回忆
			break;
		 default:
			break;
		}
	}
	
	private int olbColorId;

	@Override
	public void changeBg(int colorId) {
		 //  防止用户一直点击相同的按钮
		if(colorId != olbColorId){  
			// 改变颜色  color
			lv_competition.setBackgroundColor(colorId);
		}
		olbColorId = colorId;
	}
}
