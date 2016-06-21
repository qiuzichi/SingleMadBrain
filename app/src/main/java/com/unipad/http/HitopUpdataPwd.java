package com.unipad.http;

import com.unipad.observer.GlobleObserService;

/**
 * Created by gongjiebin on 2016/6/20.
 */
public class HitopUpdataPwd extends HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopUpdataPwd(String path) {
        super(path);
    }

    public HitopUpdataPwd(){
        super(HttpConstant.UPDATA_PWD_HTTP);
    }
    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public Object handleJsonData(String json) {
        this.sevice.noticeDataChange(HttpConstant.UPDATA_LOGIN_PWD,json);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }
}
