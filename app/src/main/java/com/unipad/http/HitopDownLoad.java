package com.unipad.http;

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

          }

          @Override
          public void onFinished() {

          }

          @Override
          public void onWaiting() {

          }

          @Override
          public void onStarted() {

          }

          @Override
          public void onLoading(long total, long current, boolean isDownloading) {
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
