package com.unipad.http;

import com.unipad.singlebrain.personal.bean.UploadFileBean;
import com.unipad.observer.GlobleObserService;

import org.json.JSONObject;

/**
 * Created by gongjiebin on 2016/6/12.
 *
 *  @描述：  实名认证上次图片
 */
public class HitopAuthUploadFile extends HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopAuthUploadFile(String path) {
        super(path);
    }
    public HitopAuthUploadFile() {
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
            this.sevice.noticeDataChange(HttpConstant.UOLOAD_AUTH_FILE,uploadFileBean);
        }catch (Exception e){
            UploadFileBean uploadFileBean = new UploadFileBean("",HttpConstant.JSON_ERREO);
            this.sevice.noticeDataChange(HttpConstant.UOLOAD_AUTH_FILE,uploadFileBean);
        }
        return null;
    }


    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }
}
