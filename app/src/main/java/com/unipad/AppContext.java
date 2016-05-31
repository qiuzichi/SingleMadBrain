package com.unipad;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.unipad.brain.absPic.dao.FigureService;
import com.unipad.brain.portraits.control.HeadService;
import com.unipad.brain.words.dao.WordsService;
import com.unipad.common.Constant;

import org.xutils.DbManager;

/**
 * 整个应用的全局上下文
 *
 * @author y42579
 * @2011-8-24
 */
public class AppContext {
    /**
     * The logging tag used by this class with com.huawei.hid.util.log.HLog.
     */
    protected static final String TAG = "AppContext";

    private static volatile AppContext instance = null;

    /**
     * 所有service的集合
     */

    public HashMap<String, ICoreService> serviceList = new HashMap<String, ICoreService>();



    /**
     * 升级地址的 URL
     */
    public String updateUrl = "hid/HID.apk";

    /**
     * 当前升级包的版本
     */
    public String updateVersion = "";

    /**
     * 是否该类已经实例化完成
     */
    public static boolean isInstanceed = false;

    /**
     * 屏的像素宽和高
     */
    public int[] disPlay = {1280, 800};


    private DbManager.DaoConfig daoConfig;

    private AppContext() {
    }

    private void init() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("unistrong_db")//创建数据库的名称
                .setDbVersion(0)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作

    }

    /**
     * 获取AppContext的单例
     *
     * @return AppContext的实例
     */
    public static AppContext instance() {
        // 如果为空或是没有实例化完成，则等待
        if (instance == null || !isInstanceed) {
            synchronized (AppContext.class) {
                if (instance == null) {
                    instance = new AppContext();
                    instance.init();
                    isInstanceed = true;
                }
            }
        }

        return instance;
    }

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    public ICoreService getService(String key) {
        ICoreService service = null;
        if (!TextUtils.isEmpty(key)) {
            service = serviceList.get(key);
            if (service != null) {

            } else if (key.equals(Constant.HEADSERVICE)) {
                service = new HeadService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.ABS_FIGURE)) {
                service = new FigureService();
                service.init();
                serviceList.put(key, service);
            } else if (key.equals(Constant.WORDS_SERVICE)) {
                service = new WordsService();
                service.init();
                serviceList.put(key, service);
            }
        }
        return service;
    }



    /**
     * 初始化屏幕的像素参数
     *
     * @param activity
     */
    public void initDisplay(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        disPlay[0] = metric.widthPixels; // 屏幕宽度（像素）
        disPlay[1] = metric.heightPixels; // 屏幕高度（像素）
    }

    public boolean initServiceState() {


        return true;
    }

    public void clear(String key) {
        if (!TextUtils.isEmpty(key)) {
            ICoreService service = serviceList.get(key);
            if (service != null) {
                service.clear();
                serviceList.remove(key);
            }
        }
    }

    /**
     * 销毁该全局处理器
     */
    public void destory() {
        if (null != instance) {
            Set keys = serviceList.keySet();
            if (keys != null) {
                Iterator iterator = keys.iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    ICoreService service = serviceList.get(key);
                    service.clear();
                }
            }
            instance = null;
        }
        isInstanceed = false;
    }

}
