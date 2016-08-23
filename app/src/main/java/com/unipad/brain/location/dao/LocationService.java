package com.unipad.brain.location.dao;

import com.unipad.AppContext;
import com.unipad.ICoreService;
import com.unipad.http.HitopApplyGame;
import com.unipad.http.HitopGetCityGame;
import com.unipad.http.HitopGetCityList;
import com.unipad.http.HitopGetProvinceList;

import com.unipad.observer.GlobleObserService;

/**
 * Created by gongjiebin on 2016/6/22.
 */
public class LocationService extends GlobleObserService implements ICoreService {

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void clear() {

    }

    /**
     *  得到省份列表
     */
    public void getProvinceList(){
        HitopGetProvinceList getProvinceList = new HitopGetProvinceList();
        getProvinceList.setSevice(this);
        getProvinceList.post();
    }

    /**
     *  得到
     * @param provinceId 省份ID
     */
    public void getCityList(String provinceId){
        HitopGetCityList getCityList = new HitopGetCityList();
        getCityList.buildRequestParams("province_id",provinceId);
        getCityList.setSevice(this);
        getCityList.post();
    }

    /**
     * 根据城市Id 获取赛事列表
     * @param cityId 城市ID
     */
    public void getCompetitionList(String cityId){
        HitopGetCityGame getCityGame = new HitopGetCityGame();
        getCityGame.buildRequestParams("cityId",cityId);
        getCityGame.buildRequestParams("userId", AppContext.instance().loginUser.getUserId());
        getCityGame.setSevice(this);
        getCityGame.post();
    }

    /**报名 赛事
     * @param userId 用户id
     * @param key  请求key
     * @param matchId 赛事matchid
     * @param projectId
     * @param gradeId
     * @param isPay  是否支付
     */

    public void getApplyCompetition(String userId, int key, String matchId, String projectId, String gradeId, int isPay) {
        HitopApplyGame applyGame = new HitopApplyGame(userId, key, matchId, projectId, gradeId, isPay);
        applyGame.get();
    }
}
