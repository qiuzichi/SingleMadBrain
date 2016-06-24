package com.unipad.brain.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.unipad.brain.home.competitionpj.view.HomeFragment;
import com.unipad.brain.home.iview.MainActivityView;

/**
 *  中间件 ，  main
 * @author gongjiebin
 *
 */
public class MainPresenter {

	private MainActivityView activityView;
	

	
	/*// 用户信息
	private UserInfoFragment userInfoFragment;
	
	// 位置信息
	private PositionFragment positionFragment;
	
	// 消息
	private MessageFragment messageFragment;
	
	// 帮助
	private HelpFragment helpFragment;
	
	// 设置
	private SetFragment setFragment;*/
	
	public String currentContentFragmentTag;
	
	private FragmentManager fragmentManager;
	

	
	/*public UserInfoFragment getUserInfoFragment() {
		return userInfoFragment;
	}

	public PositionFragment getPositionFragment() {
		return positionFragment;
	}

	public MessageFragment getMessageFragment() {
		return messageFragment;
	}

	public HelpFragment getHelpFragment() {
		return helpFragment;
	}

	public SetFragment getSetFragment() {
		return setFragment;
	}*/

	public MainPresenter(MainActivityView activityView) {
		super();
		this.activityView = activityView;
		/*userInfoFragment = UserInfoFragment.getUserInfoFragment();
		positionFragment = PositionFragment.getPositionFragment();
		messageFragment = MessageFragment.getHomeFragment();
		helpFragment = HelpFragment.getHelpFragment();
		setFragment = SetFragment.getHelpFragment();*/
	}

	
	public void switchFragment(int id, Fragment fragment,String tag) {
		if(tag.equals(currentContentFragmentTag)){
			return;
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
	}
}
