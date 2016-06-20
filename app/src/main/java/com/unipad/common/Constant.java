package com.unipad.common;

import android.content.Context;

import com.unipad.brain.App;
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
    public static final String SHOW_RULE_DIG = "com.unipad.SHOW_RULE_DLG";

    public static final String HOME_GAME_HAND_SERVICE = "com.unipad.brain.home.dao.HomeGameHandService";

    public static final  String CITY_GAME  = "00001";

    public static final  String CHIMA_GAME = "00002";

    public static final  String WORD_GAME = "00003";

    private static  Map<String,String>  projectNames;

    private static Map<String,String> projectIds;


    public static String getProjectName(String projectId){
        if(null == projectNames){
            projectNames = new HashMap<String,String>();
            projectNames.put("00001", App.getContext().getString(R.string.project_1));
            projectNames.put("00002",App.getContext().getString(R.string.project_2));
            projectNames.put("00003",App.getContext().getString(R.string.project_3));
            projectNames.put("00004",App.getContext().getString(R.string.project_4));
            projectNames.put("00005",App.getContext().getString(R.string.project_5));
            projectNames.put("00006",App.getContext().getString(R.string.project_6));
            projectNames.put("00007",App.getContext().getString(R.string.project_7));
            projectNames.put("00008",App.getContext().getString(R.string.project_8));
            projectNames.put("00009",App.getContext().getString(R.string.project_9));
            projectNames.put("00010", App.getContext().getString(R.string.project_10));
        }
        return projectNames.get(projectId);
    }


    public static String getProjectId(String projectName){
        if(null == projectIds){
            projectIds = new HashMap<String,String>();
            projectIds.put(App.getContext().getString(R.string.project_1),"00001");
            projectIds.put(App.getContext().getString(R.string.project_2),"00002");
            projectIds.put(App.getContext().getString(R.string.project_3),"00003");
            projectIds.put(App.getContext().getString(R.string.project_4),"00004");
            projectIds.put(App.getContext().getString(R.string.project_5),"00005");
            projectIds.put(App.getContext().getString(R.string.project_6),"00006");
            projectIds.put(App.getContext().getString(R.string.project_7),"00007");
            projectIds.put(App.getContext().getString(R.string.project_8),"00008");
            projectIds.put(App.getContext().getString(R.string.project_9),"00009");
            projectIds.put(App.getContext().getString(R.string.project_10),"00010");
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
