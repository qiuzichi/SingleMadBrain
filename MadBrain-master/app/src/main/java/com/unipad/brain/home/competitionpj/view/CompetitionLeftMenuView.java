package com.unipad.brain.home.competitionpj.view;

import android.content.Context;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.event.OnClick;
import com.unipad.brain.R;
import com.unipad.brain.home.competitionpj.icompetition.OnLMenu;


/**
 * @描述：  比赛 练习左边的菜单
 * @author gongjiebin
 *
 */
public class CompetitionLeftMenuView extends RelativeLayout {

	private Context context;
	
	private OnLMenu onLMenu;
	
	private View view;
	
	public void setIlMenu(OnLMenu onLMenu) {
		this.onLMenu = onLMenu;
	}

	public CompetitionLeftMenuView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public CompetitionLeftMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CompetitionLeftMenuView(Context context) {
		super(context);
		this.context = context;
		createView();
	}
	
	private void createView(){
		view = View.inflate(context, R.layout.competition_left_menu_layout, this);
		//ViewUtils.inject(this, view);
	}
	
	
	@OnClick({R.id.img_bg1,R.id.img_bg2,R.id.img_bg3})
	public void onClickChangeBg(View view){
		if(null == onLMenu)
			return;
		switch (view.getId()) {
		case R.id.img_bg1:
			onLMenu.changeBg(context.getResources().getColor(R.color.competition_bg1));
			break;
		case R.id.img_bg2:
			onLMenu.changeBg(context.getResources().getColor(R.color.competition_bg2));
			break;
		case R.id.img_bg3:
			onLMenu.changeBg(context.getResources().getColor(R.color.competition_bg3));
			break;

		default:
			break;
		}
	}
	
	@OnClick({R.id.txt_exit,R.id.btn_end})
	public void OnExitClick(View view){
		onLMenu.onMenuClick(view.getId());
	}
}
