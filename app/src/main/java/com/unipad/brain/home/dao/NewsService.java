package com.unipad.brain.home.dao;

import com.unipad.ICoreService;
import com.unipad.http.HitopNewsList;
import com.unipad.observer.GlobleObserService;

/**
 * Created by gongkan on 2016/6/13.
 */
public class NewsService extends GlobleObserService implements ICoreService{

    public void getNews(String contentType,String title,int page,int size ){
        HitopNewsList newsList = new HitopNewsList(contentType,title,page,size);
        newsList.post();
    }
    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }


}
