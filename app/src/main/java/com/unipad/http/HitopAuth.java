package com.unipad.http;


import com.unipad.observer.GlobleObserService;

/**
 * Created by gongjiebin on 2016/6/8.
 */
public class HitopAuth extends HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopAuth(String path) {
        super(path);
    }

    public HitopAuth(){
        super(HttpConstant.AUTH_PATH);
    }

    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public Object handleJsonData(String json) {
        // 处理返回结果
        this.sevice.noticeDataChange(HttpConstant.USER_AUTH,json);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }
}
