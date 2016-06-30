package com.unipad.brain.home.dao;

import com.unipad.ICoreService;
import com.unipad.http.HitopAdList;
import com.unipad.http.HitopNewsDetails;
import com.unipad.http.HitopNewsList;
import com.unipad.http.HitopNewsOperate;
import com.unipad.observer.GlobleObserService;

import org.xutils.common.Callback;

/**
 * Created by gongkan on 2016/6/13.
 */
public class NewsService extends GlobleObserService implements ICoreService{

    public void getNews(String contentType,String title,int page,int size ){
        HitopNewsList newsList = new HitopNewsList(contentType,title,page,size);
        newsList.post();
}
    public void getAdverts(String positionId ){
        HitopAdList advertList = new HitopAdList(positionId);
        advertList.get();
    }
    public boolean getNewsOperate(String articleId, String method, String methodType, String content, int contentType){
        HitopNewsOperate newsOperate = new HitopNewsOperate(articleId,method,methodType,content, contentType);
        newsOperate.get();
        return newsOperate.getResonse();
    }

    public void getNewsOperate(String articleId, String method, String methodType, String content, int contentType,Callback.CommonCallback<String> callback){
        HitopNewsOperate newsOperate = new HitopNewsOperate(articleId,method,methodType,content, contentType);
        newsOperate.post(callback);
    }

    public void getDetail(String pagerId, String contentType){
        HitopNewsDetails advertList = new HitopNewsDetails(pagerId, contentType);
        advertList.get();
    }

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }


}
