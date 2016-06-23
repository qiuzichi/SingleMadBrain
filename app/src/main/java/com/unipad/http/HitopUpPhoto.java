package com.unipad.http;

import com.unipad.brain.personal.bean.UploadFileBean;
import com.unipad.observer.GlobleObserService;

import org.json.JSONObject;

/**
 * Created by gongjiebin on 2016/6/22.
 */
public class HitopUpPhoto  extends HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopUpPhoto(String path) {
        super(path);
    }
    public HitopUpPhoto() {
        super(HttpConstant.UPLOAD);
    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public Object handleJsonData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            UploadFileBean uploadFileBean = new UploadFileBean(jsonObject.optString("data"),jsonObject.optInt("ret_code"));
            this.sevice.noticeDataChange(HttpConstant.UOLOAD_PHOTO_FILE,uploadFileBean);
        }catch (Exception e){
            UploadFileBean uploadFileBean = new UploadFileBean("",HttpConstant.JSON_ERREO);
            this.sevice.noticeDataChange(HttpConstant.UOLOAD_PHOTO_FILE,uploadFileBean);
        }
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }
}

