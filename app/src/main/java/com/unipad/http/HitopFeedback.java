package com.unipad.http;

import com.unipad.observer.GlobleObserService;

/**
 * Created by gongjiebin on 2016/6/21.
 */
public class HitopFeedback extends HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopFeedback(String path) {
        super(path);
    }

    public HitopFeedback() {
        super(HttpConstant.SUBMIT_FEED_HTTP);
    }

    @Override
    public String buildRequestURL() {
        //this.sevice.noticeDataChange(HttpConstant.UOLOAD_AUTH_FILE, uploadFileBean);
        return null;
    }

    @Override
    public Object handleJsonData(String json) {
        this.sevice.noticeDataChange(HttpConstant.SUBMIT_FEEDBACK, json);
        return null;
    }


    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }

}
