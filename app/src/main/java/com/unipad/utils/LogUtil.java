package com.unipad.utils;

/**
 * Created by hasee on 2016/6/22.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 *	日志打印工具类
 */
public class LogUtil {

    private static boolean isPrint = true;
    private static boolean isDebug = false;

    public static final String TAG = "niiwooTuanDaiApp";
    public static final String MSG = "log msg is null.";

    private static List<String> logList;

    public static void v(String tag, String msg) {
        print(Log.VERBOSE, tag, msg);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String tag, String msg) {
        print(Log.DEBUG, tag, msg);
        print(isDebug, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(Object msg) {
        d(TAG, msg  == null ? "null" :msg.toString());
    }

    public static void i(String tag, String msg) {
        print(Log.INFO, tag, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(Object msg) {
        i(TAG, msg==null ? "null":msg.toString());
    }

    public static void w(String tag, String msg) {
        print(Log.WARN, tag, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String tag, String msg) {
        print(Log.ERROR, tag, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    private static void print(int mode, final String tag, String msg) {
        if (!isPrint) {
            return;
        }
        if (msg == null) {
            Log.e(tag, MSG);
            return;
        }
        switch (mode) {
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            default:
                Log.d(tag, msg);
                break;
        }
    }

    private static void print(boolean flag, String msg) {
        if (flag && logList != null) {
            logList.add(msg);
        }
    }
    public static void e(String tag, String msg, Exception exception) {
            Log.e(tag, msg, exception);
    }
    public static void setState(boolean flag) {
        if (flag) {
            if (logList == null) {
                logList = new ArrayList<String>();
            } else {
                logList.clear();
            }
        } else {
            if (logList != null) {
                logList.clear();
                logList = null;
            }
        }

        isDebug = flag;
    }

    public static void printFile(InputStream is, String path) {
        FileOutputStream fos = null;
        byte[] temp = null;
        try {
            if (isPrint) {
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                temp = new byte[1024];
                int i = 0;
                while ((i = is.read(temp)) > -1) {
                    if (i < temp.length) {
                        byte[] b = new byte[i];
                        System.arraycopy(temp, 0, b, 0, b.length);
                        fos.write(b);
                    } else {
                        fos.write(temp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
            temp = null;
        }
    }

    public static  String getLogUtilsTag(Class<? extends Object> clazz) {
        return LogUtil.TAG + "." + clazz.getSimpleName();
    }
}
