package com.unipad.common;

import com.unipad.utils.LogUtil;

/**
 * Created by liuxiang on 2016/6/22.
 */
public class AppGlobalManager {
    private static final long VIEW_CLICKED_TIME_LIMIT = 600;
    private static long lastClickTime;
    private static int lastButtonId;

    /**
     * 判断两次点击的间隔，如果小于600毫秒，则认为是多次无效点击
     *
     * 这个方法不是万能的，如果用户点击一个按钮后，迅速点击其他按钮，
     * 在再次点击该按钮，或者用户同时点击多个按钮，都可能导致该方法失效
     *
     * 暂时使用这个判断，这个方法在以后再进行优化2015/11/6 11:20
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < VIEW_CLICKED_TIME_LIMIT) {
            LogUtil.v("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}
