package com.unipad.brain.home;

import android.os.Bundle;
import android.view.View;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.http.HitopNewsList;
import com.unipad.observer.IDataObserver;

public class MainHomeFragment extends MainBasicFragment implements IDataObserver{

    private NewsService service;
    @Override
    public int getLayoutId() {
        return R.layout.main_home_fragment;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        HitopNewsList newsList = new HitopNewsList("00001","",1,10);
        newsList.post();
    }

    @Override
    public void update(int key, Object o) {

    }
}
