package com.unipad.http;

/**
 * Created by gongkan on 2016/6/17.
 */
public class HitopGetRule  extends HitopRequest<Object>{


    public HitopGetRule(String path) {
        super(path);
    }

    public HitopGetRule(String matchId,String path) {
        super(HttpConstant.GET_RULE);
        mParams.addQueryStringParameter("matchId",matchId);
    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public Object handleJsonData(String json) {
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

}
