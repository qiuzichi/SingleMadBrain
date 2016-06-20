package com.unipad.common;

import android.content.Context;

import com.unipad.brain.R;
import com.unipad.brain.home.util.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongkan on 2016/4/12.
 */
public class Constant {
    public static final  String HEADSERVICE = "headService";
    public static final String ABS_FIGURE ="absFigure" ;
    public static final String WORDS_SERVICE = "words_service";
    public static final String BINARYNUMSERVICE = "binarynumservice";
    public static final String PERSONCENTER = "personcenterservice";
    public static final String NEWS_SERVICE ="news_service" ;

    public static final String LOGIN_WAIT_DLG = "com.unipad.LOGIN_WAIT_DLG";

    public static final String HOME_GAME_HAND_SERVICE = "com.unipad.brain.home.dao.HomeGameHandService";

    public static final  String CITY_GAME  = "00001";

    public static final  String CHIMA_GAME = "00002";

    public static final  String WORD_GAME = "00003";

    private static  Map<String,String>  projectNames;

    private static Map<String,String> projectIds;

    /**
     *  根据项目ID 获取项目名字
     * @param context
     * @param projectId
     * @return
     */
    public static String getProjectName(Context context,String projectId){
        if(null == projectNames){
            projectNames = new HashMap<String,String>();
            projectNames.put("00001",context.getString(R.string.project_1));
            projectNames.put("00002",context.getString(R.string.project_2));
            projectNames.put("00003",context.getString(R.string.project_3));
            projectNames.put("00004",context.getString(R.string.project_4));
            projectNames.put("00005",context.getString(R.string.project_5));
            projectNames.put("00006",context.getString(R.string.project_6));
            projectNames.put("00007",context.getString(R.string.project_7));
            projectNames.put("00008",context.getString(R.string.project_8));
            projectNames.put("00009",context.getString(R.string.project_9));
            projectNames.put("00010", context.getString(R.string.project_10));
        }
        return projectNames.get(projectId);
    }


    /**
     *  根据项目ID 获取项目ID
     * @param context
     * @param projectName
     * @return
     */
    public static String getProjectId(Context context,String projectName){
        if(null == projectIds){
            projectIds = new HashMap<String,String>();
            projectIds.put(context.getString(R.string.project_1),"00001");
            projectIds.put(context.getString(R.string.project_2),"00002");
            projectIds.put(context.getString(R.string.project_3),"00003");
            projectIds.put(context.getString(R.string.project_4),"00004");
            projectIds.put(context.getString(R.string.project_5),"00005");
            projectIds.put(context.getString(R.string.project_6),"00006");
            projectIds.put(context.getString(R.string.project_7),"00007");
            projectIds.put(context.getString(R.string.project_8),"00008");
            projectIds.put(context.getString(R.string.project_9),"00009");
            projectIds.put(context.getString(R.string.project_10),"00010");
        }
        return projectIds.get(projectName);
    }


    /**
     * 判断程序是否为第一次运行
     *
     * @return true 是 false 否
     */
    public static Boolean isfirstRun(Context context,SharedPreferencesUtil sharedPreferencesUtil) {
        boolean isFirstRun = sharedPreferencesUtil.getBoolean("isFirstRun",true);
        if (isFirstRun) {
            sharedPreferencesUtil.saveBoolean("isFirstRun", false);
            return true;
        } else {
            return false;
        }
    }
}
