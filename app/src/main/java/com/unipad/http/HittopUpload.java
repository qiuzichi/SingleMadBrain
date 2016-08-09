package com.unipad.http;

/**
 * Created by gongkan on 2016/6/7.
 */
public class HittopUpload extends HitopRequest<Object>{

    public HittopUpload(String path) {
        super(path);
    }
    public HittopUpload() {
        super(HttpConstant.UPLOAD);
    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public Object handleJsonData(String json) {
        return null;
    }
}
