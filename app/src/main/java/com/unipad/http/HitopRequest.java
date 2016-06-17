package com.unipad.http;


import android.util.Log;

import com.unipad.brain.App;
import com.unipad.utils.NetWorkUtil;
import com.unipad.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Locale;
import java.util.Map;

public abstract class HitopRequest<T>{
    //the default over due  is one hour; 
    public static final String TAG = "HitopRequest";

    protected String url = "http://192.168.0.104:8090/crazybrain-mng";

    protected RequestParams mParams = null;

    private T mResult = null;

    protected String path;

    public  abstract String buildRequestURL();
    
    public abstract T handleJsonData(String json);

    public abstract void buildRequestParams();


    public HitopRequest(String path){
        url = url+path;
        mParams = new RequestParams(url);
    }
    public void get(){
        if(!NetWorkUtil.isNetworkAvailable(App.getContext())) {
            ToastUtil.showToast("请检查网络");
            return ;
        }
        buildRequestParams();
        x.http().get(mParams, new ResultCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                handleJsonData(result);

            }
        });
    }
    public void post(){
        if(!NetWorkUtil.isNetworkAvailable(App.getContext())) {
            ToastUtil.showToast("请检查网络");
            return ;
        }
        buildRequestURL();
        x.http().post(mParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("",result);
                handleJsonData(result);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {


            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 上传文件
     */
    public void UpLoadFile(Map<String,Object> map){
        if(!NetWorkUtil.isNetworkAvailable(App.getContext())) {
            ToastUtil.showToast("请检查网络");
            return ;
        }
        if (null == mParams) {
            mParams = new RequestParams(buildRequestURL());
//            PostMe
        }

        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
               // mParams.addParameter(entry.getKey(), entry.getValue());
                mParams.addBodyParameter(entry.getKey(), (File)entry.getValue());
            }
        }
         mParams.setMultipart(true);
        x.http().post(mParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("request","result = "+result);
                handleJsonData(result);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

             }
         });
    }

    /**
     * 下载文件
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(String url,String filepath,Callback.CommonCallback<T> callback){
        RequestParams params=new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
}








    private static final String VERSION_CODE = "versionCode";


    public void buildRequestParams(String key,String value){

        mParams.addQueryStringParameter(key, value);
    }

    /*
     添加文件
     */
    public void buildRequestParams(String key,File value) {

        mParams.addBodyParameter(key, value);
    }

    protected String getHost() {

        return url;
    }
    protected String getLanguageCountryCode() {
        String languageCode = Locale.getDefault().getLanguage();
        String countryCode = Locale.getDefault().getCountry();
        String code = null;
        if ("".equals(languageCode) || "".equals(countryCode)) {
            code = "en_US";
        } else {
            code = languageCode + "_" + countryCode;
        }
        return code;
    }
    
    //for now ,we just support payed theme/font in china and have pay service , hw id in rom

}
