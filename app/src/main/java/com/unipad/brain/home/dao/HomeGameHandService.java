package com.unipad.brain.home.dao;

import com.unipad.AppContext;
import com.unipad.ICoreService;
import com.unipad.http.HitopGetRule;
import com.unipad.http.HittopGetUserGame;
import com.unipad.observer.GlobleObserService;

/**
 * Created by gongkan on 2016/6/15.
 */
public class HomeGameHandService extends GlobleObserService implements ICoreService {

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }

    public void sendMsgGetGame(String projectId,String group,int page,int size){
        HittopGetUserGame httpGame = new HittopGetUserGame(AppContext.instance().loginUser.getUserId(), projectId,group,page,size);
        httpGame.post();
    }

    public void getRule(String id){
        HitopGetRule httpGetRule = new HitopGetRule(id,null);
        httpGetRule.post();
    }

}
