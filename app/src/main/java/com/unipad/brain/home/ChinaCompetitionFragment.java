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

/**
 * @描述：  中国赛
 * @author gongjiebin
 *
 */
public class ChinaCompetitionFragment extends BaseFragment implements ICompetition {

	
	final public static String TAG = "ChinaCompetitionFragment";
	
	private static ChinaCompetitionFragment chinaCompetitionFragment;
	
	@ViewInject(R.id.lv_competition)
	private ListView lv_competition;
	
	private CompetitionPersenter competitionPersenter;
	
	private ProjectBean projectBean;
	
	public static ChinaCompetitionFragment getChinaCompetitionFragment(){
		if( null == chinaCompetitionFragment)
			return chinaCompetitionFragment = new ChinaCompetitionFragment();
		return chinaCompetitionFragment;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View homeView = inflater.inflate(R.layout.fragment_competition_layout, container,false);
		ViewUtils.inject(this, homeView);
		initView(homeView);
		return homeView;
	}

	
	@Override
	public void initView(View view) {
		super.initView(view);
		projectBean = ((CompetitionListActivity)getActivity()).getProjectBean();
		competitionPersenter = new CompetitionPersenter(this,projectBean);
	}

	@Override
	public Context getContext() {
		return getActivity();
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
