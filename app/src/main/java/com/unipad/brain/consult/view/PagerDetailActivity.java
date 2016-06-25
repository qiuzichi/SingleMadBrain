package com.unipad.brain.consult.view;

import android.os.Bundle;
import android.view.View;

import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

/**
 * Created by jiangLu on 2016/6/23.
 */
public class PagerDetailActivity extends BasicActivity implements IDataObserver {

    private NewsService service;

    private void getNews(String contentType, String title, int page, int size ){
        service.getNews(contentType,title,page,size );
    }


    public void  initData(){
        //获取pagerId  数据
//        mActivity.get
        setContentView(R.layout.activity_pagerdetail);

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_DETAIL, this);



    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void update(int key, Object o) {

    }



    @Override
    public void onClick(View v) {

    }
}
