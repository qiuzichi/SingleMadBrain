package com.unipad.http;

import com.unipad.observer.GlobleObserService;

/**
 * Created by gongjiebin on 2016/6/15.
 * @描述：  更新用户信息
 */
public class HitopUserInfoUpdate extends HitopRequest<Object> {
    private GlobleObserService sevice;

    public HitopUserInfoUpdate(String path) {
        super(path);
    }

    public HitopUserInfoUpdate(){
        super(HttpConstant.UPDATE_USERINFO);
    }

    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public Object handleJsonData(String json) {
        // 处理返回结果
        this.sevice.noticeDataChange(HttpConstant.PERSONALDATA,json);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }
}
