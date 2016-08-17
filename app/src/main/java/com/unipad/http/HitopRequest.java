package com.unipad.http;


import android.util.Log;

import com.unipad.brain.App;
import com.unipad.common.widget.HIDDialog;
import com.unipad.utils.LogUtil;
import com.unipad.utils.NetWorkUtil;
import com.unipad.utils.ToastUtil;

import org.json.JSONException;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.Map;

public abstract class HitopRequest<T>{
    //the default over due  is one hour; 
    public static final String TAG = "HitopRequest";

    protected String url = HttpConstant.url;

    protected RequestParams mParams = null;

    private T mResult = null;

    protected String path;

    public  abstract String buildRequestURL();
    
    public abstract T handleJsonData(String json) throws JSONException;




    public HitopRequest(String path){
        url = url+path;
        mParams = new RequestParams(url);
    }
    public void get(){
        if(!NetWorkUtil.isNetworkAvailable(App.getContext())) {
            ToastUtil.showToast("请检查网络");
            return ;
        }

        x.http().get(mParams, new ResultCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("","result:"+result);
                try {
                    handleJsonData(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                try {
                    handleJsonData(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                HIDDialog.dismissAll();
                if (throwable instanceof ConnectException) {
                    ToastUtil.showToast("服务器异常");
                }else if (throwable instanceof SocketTimeoutException){
                    ToastUtil.showToast("请求超时");
                }else {
                    ToastUtil.showToast("IO异常");
                }
                LogUtil.e("","请求异常："+throwable);
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
    public void UpLoadFile(Map<String,File> map){
        if(!NetWorkUtil.isNetworkAvailable(App.getContext())) {
            ToastUtil.showToast("请检查网络");
            return ;
        }
        if (null == mParams) {
            mParams = new RequestParams(buildRequestURL());
//            PostMe
        }

        if(null!=map){
            for(Map.Entry<String, File> entry : map.entrySet()){
               // mParams.addParameter(entry.getKey(), entry.getValue());
                mParams.addBodyParameter(entry.getKey(), (File)entry.getValue());
            }
        }
         mParams.setMultipart(true);
        x.http().post(mParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("request","result = "+result);
                try {
                    handleJsonData(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                new Exception(throwable).printStackTrace();
                ToastUtil.showToast("请求异常");
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
    public  <T> Callback.Cancelable post(Callback.CommonCallback<T> callback){
        return x.http().post(mParams,callback);
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
