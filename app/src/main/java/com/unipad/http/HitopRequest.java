package com.unipad.http;


import android.util.Log;

import java.util.Locale;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public abstract class HitopRequest<T>{
    //the default over due  is one hour; 
    public static final String TAG = "HitopRequest";


    protected String url = "http://221.5.109.34/crazybrain-mng";

    protected RequestParams mParams = null;

    private String mResult = "";

    protected String path;

    public  abstract String buildRequestURL();
    
    public abstract T handleJsonData(String json);

    public abstract void buildRequestParams();
    public HitopRequest(String path){
        url = url+path;
        mParams = new RequestParams(url);
    }
    public T get(){
        buildRequestParams();
        x.http().get(mParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mResult = result;
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
        return handleJsonData(mResult);
    }

    public T post(){

        if (null == mParams) {
            mParams = new RequestParams(buildRequestURL());
        }

        x.http().post(mParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                mResult = result;
                Log.e("",result);
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
        return handleJsonData(mResult);
    }











    private static final String VERSION_CODE = "versionCode";


    public void buildRequestParams(String key,String value){

        mParams.addQueryStringParameter(key, value);
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
