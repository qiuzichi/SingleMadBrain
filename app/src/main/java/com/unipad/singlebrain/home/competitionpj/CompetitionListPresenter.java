package com.unipad.singlebrain.home.competitionpj;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import com.unipad.singlebrain.home.ChinaCompetitionFragment;
import com.unipad.singlebrain.home.CityCompetitionFragment;
import com.unipad.singlebrain.home.WorldCompetitionFragment;
import com.unipad.singlebrain.home.adapter.MyFrageStatePagerAdapter;
import com.unipad.singlebrain.home.iview.ICompetitionList;
import com.unipad.common.BaseFragmentActivity;


/**
 * @描述：  赛事列表中间件
 * @author gongjiebin
 *
 */
public class CompetitionListPresenter {

	private ICompetitionList iCompetitionList;

	private List<Fragment> arrayFm;
	
	private CityCompetitionFragment cityCompetitionFragment;
	
	private ChinaCompetitionFragment chinaCompetitionFragment;
	
	private WorldCompetitionFragment worldCompetitionFragment;
	
	public CompetitionListPresenter(ICompetitionList iCompetitionList) {
		super();
		this.iCompetitionList = iCompetitionList;
		
		arrayFm = new ArrayList<Fragment>();
		// 城市赛
		cityCompetitionFragment = CityCompetitionFragment.getCityCompetitionFragment();
		chinaCompetitionFragment = ChinaCompetitionFragment.getChinaCompetitionFragment();
		worldCompetitionFragment =WorldCompetitionFragment.getWorldCompetitionFragment();
		
		addFmView();
		MyFrageStatePagerAdapter frageStatePagerAdapter = new MyFrageStatePagerAdapter(((BaseFragmentActivity)iCompetitionList).getSupportFragmentManager());
		frageStatePagerAdapter.setFragmentList(arrayFm);
		this.iCompetitionList.setAdapter(frageStatePagerAdapter);
	}
	

//	/**
//	 *  添加view
//	 */
	private void addFmView(){
		arrayFm.add(cityCompetitionFragment);
		arrayFm.add(chinaCompetitionFragment);
		arrayFm.add(worldCompetitionFragment);
	}
	
}
