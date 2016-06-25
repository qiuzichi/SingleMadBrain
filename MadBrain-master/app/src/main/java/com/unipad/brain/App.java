package com.unipad.brain;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;

import com.unipad.io.mina.LongTcpClient;

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
            }

        }
    }

    public File getAppFile() {
        return mAppFile;
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
