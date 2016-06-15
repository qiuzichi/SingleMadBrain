package com.unipad.brain.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.ProjectBean;
import com.unipad.brain.home.iview.ICompetition;
import com.unipad.brain.home.util.MyTools;
import com.unipad.common.BaseFragment;
import com.unipad.common.Constant;

/**
 * @描述： 城市赛 页面 帧
 * @author gongjiebin
 *
 */
public class CityCompetitionFragment extends BaseFragment implements ICompetition {

   final public static String TAG = "CityCompetitionFragment";

	private static CityCompetitionFragment cityCompetitionFragment;

	@ViewInject(R.id.lv_competition)
	private ListView lv_competition;

	private CompetitionPersenter competitionPersenter;

	private ProjectBean projectBean;

	private CompetitionListActivity activity;

	public static CityCompetitionFragment getCityCompetitionFragment(){
		if( null == cityCompetitionFragment)
			return cityCompetitionFragment = new CityCompetitionFragment();
		return cityCompetitionFragment;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View homeView = inflater.inflate(R.layout.fragment_competition_layout, container,false);
		ViewUtils.inject(this, homeView);
		initView(homeView);
		return homeView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (CompetitionListActivity) getActivity();
		activity.getGameList(Constant.CITY_GAME, 1, 10);
	}

	@Override
	public void initView(View view) {
		super.initView(view);
		projectBean = ((CompetitionListActivity)getActivity()).getProjectBean();
		competitionPersenter = new CompetitionPersenter(this,projectBean);
	}


	@Override
	public Context getContext() {
		return this.getActivity();
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
	public void setAdapter(BaseAdapter baseAdapter) {
		lv_competition.setAdapter(baseAdapter);
	}


	@Override
	public String getCompetitionIndex() {
		return TAG;
	}
}
