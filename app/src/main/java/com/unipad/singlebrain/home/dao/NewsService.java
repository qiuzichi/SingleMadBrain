package com.unipad.singlebrain.home.dao;

import com.unipad.ICoreService;
import com.unipad.http.HitopAdList;
import com.unipad.http.HitopApplyGame;
import com.unipad.http.HitopNewsComment;
import com.unipad.http.HitopNewsList;
import com.unipad.http.HitopNewsOperate;
import com.unipad.http.HitopNewsVersion;
import com.unipad.http.HittopGetUserGame;
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

    public void getSearchNews(String key , String contentType,String title,int page,int size ){
        HitopNewsList newsList = new HitopNewsList(key ,contentType,title,page,size);
        newsList.post();
    }

    public void getNewCompetition(String userId, String projectId, String gradeId, int page, int size) {
        HittopGetUserGame newsList = new HittopGetUserGame(userId, projectId, gradeId, page, size);
        newsList.get();
    }

    public void getApplyCompetition(String userId, int key, String matchId, String projectId, String gradeId, int isPay) {
        HitopApplyGame applyGame = new HitopApplyGame(userId, key, matchId, projectId, gradeId, isPay);
        applyGame.get();
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

    public void getComment(String pagerId, int page, int size){
        HitopNewsComment advertList = new HitopNewsComment(pagerId, page, size);
        advertList.get();
    }

    public void getApkVersion(){
        HitopNewsVersion newVersion = new HitopNewsVersion();
        newVersion.get();
    }

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }


}
