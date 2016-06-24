package com.unipad.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Activity收集器
 * Created by Wbj on 2016/4/7.
 */
public class ActivityCollector {

    private static List<Activity> mActivityList = new ArrayList<>();

    /**
     * 向集合中添加Activity
     */
    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 从集合中移除Activity
     */
    public static void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }

    /**
     * 清除集合中所有的Activity
     */
    public static void finishAllActivity() {
        for (Activity activity : mActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
