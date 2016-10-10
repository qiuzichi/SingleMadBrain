package com.unipad.singlebrain;

import android.app.Application;
import android.app.Service;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;

import com.tencent.bugly.crashreport.CrashReport;
import com.unipad.baiduservice.LocationService;

import org.xutils.x;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Wbj on 2016/4/7.
 */
public class App extends Application {
    private static App mContext;
    private File mAppFile, mTakePhotoFile;
    public static int screenWidth = 0, screenHeight = 0;
    public LocationService locationService;
    public Vibrator mVibrator;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = (App) getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        // 获取屏幕的宽和高
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        // 初始化 百度定位
        //locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        //SDKInitializer.initialize(getApplicationContext());

        CrashReport.initCrashReport(getApplicationContext(), "31a8102803", false);

        this.createDirs();
    }

    private void createDirs() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            mAppFile = new File(Environment.getExternalStorageDirectory(), "MadBrain");
            if (!mAppFile.exists()) {
                mAppFile.mkdir();
            }

            if (mAppFile.exists() && mAppFile.isDirectory()) {
                mTakePhotoFile = new File(mAppFile, "TakePhoto");
                mTakePhotoFile.mkdir();
                File gameFile = new File(mAppFile,"game");
                gameFile.mkdir();
            }

        }
    }

    public String getAppFilePath() {
        return mAppFile.getAbsolutePath();
    }

    public File getTakePhotoFile() {
        return mTakePhotoFile;
    }

    public static App getContext() {
        return mContext;
    }

    public static class AppHandler extends Handler {
        private WeakReference<HandlerCallback> mHandlerCallback;

        public AppHandler(HandlerCallback handlerCallback) {
            mHandlerCallback = new WeakReference<>(handlerCallback);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerCallback callback = mHandlerCallback.get();
            if (callback != null) {
                callback.dispatchMessage(msg);
            }
        }
    }

    public interface HandlerCallback {
        /**
         * 分发消息以刷新界面
         */
        void dispatchMessage(Message msg);
    }



}
