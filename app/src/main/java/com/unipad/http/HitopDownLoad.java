package com.unipad.http;

import android.util.Log;

import com.lidroid.xutils.http.ResponseInfo;
import com.unipad.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by gongkan on 2016/6/21.
 */
public class HitopDownLoad extends HitopRequest<File>{

    public HitopDownLoad(String path) {
        super(path);
    }

    public HitopDownLoad(){
        super(HttpConstant.DOWNLOAD_QUESTION);
    }
    @Override
    public String buildRequestURL() {
        return null;
    }

    public void downLoad(String savaPath){

        //设置断点续传
        mParams.setAutoResume(true);
        mParams.setSaveFilePath(savaPath);
      x.http().get(mParams, new Callback.ProgressCallback<File>() {


          @Override
          public void onSuccess(File file) {
              Log.e("","success");
              ToastUtil.showToast("下载成功！");
          }

          @Override
          public void onError(Throwable throwable, boolean b) {
              if (throwable instanceof HttpException) {
                  ToastUtil.showToast("网络错误");
              }
          }

          @Override
          public void onCancelled(CancelledException e) {
              Log.e("","onCancelled");

          }

          @Override
          public void onFinished() {
              Log.e("","onFinished");
          }

          @Override
          public void onWaiting() {
              Log.e("","onWaiting");
          }

          @Override
          public void onStarted() {
              Log.e("","onStarted");
          }

          @Override
          public void onLoading(long total, long current, boolean isDownloading) {
              Log.e("","total = "+total+",current="+current+",isDownloading="+isDownloading);
              if (total != current) {

              }
          }
      });

    }
    @Override
    public File handleJsonData(String json) {
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
