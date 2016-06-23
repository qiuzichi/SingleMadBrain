package com.unipad.http;

/**
 * Created by gongkan on 2016/5/30.
 */
public class HttpConstant {

    public static final String LOGIN = "/api/user/login";
    public static final String GET_USER_GAME_LIST = "/api/match/getMatchByUser";
    public static final String FORGET_PWD = "/api/user/forget";
    public static final String DOWNLOAD_QUESTION ="/api/file/download" ;
    public static final String GET_QUESTION = "/api/match/getQuestion";
    public static String Regist = "/api/user/regist";
    public static String UPLOAD = "/api/file/upload";
    public static String GET_NEWS_LIST = "/api/news/list";
    // 实名认证
    public static String AUTH_PATH = "/api/user/auth";
    // 调取实名认证信息 ----在用户已经实名认证之后 调取。
    public static String AUTH_INFO_PATH = "/api/user/data";

    public static String GET_RULE = "/api/match/getRule";
    /**
     * 申请报名
     */
    public static String APPLY_GAME = "/api/match/apply";
    // 获取图片路径 http://192.168.0.104:8090/crazybrain-mng
    public static String PATH_FILE_URL = "http://192.168.0.104:8090/crazybrain-mng/image/getFile?filePath=";
    // 更新用户信息
    public static String UPDATE_USERINFO = "/api/user/modify";
    // 得到用户已经报名列表
    public static String USER_APPLYED_HTTP= "/api/user/myApply";
    // 进入比赛时间判断
    public static String USER_IN_GAME_HTTP = "/api/match/checkMatchStart";
    // 修改用户密码
    public static String UPDATA_PWD_HTTP = "/api/user/modifyPassword";
    // 意见提交
     public static String SUBMIT_FEED_HTTP = "/api/user/feedback";


    public static int JSON_ERREO = -2;
    public static final int LOGIN_UPDATE_UI = 0x10000;
    public static final int LOGIN_WRONG_MSG = 0x10001;


    public static final int CITY_GET_HOME_GAME_LIST = 0x10002;
    public static final int CHINA_GET_HOME_GAME_LIST = 0x10003;
    public static final int WORD_GET_HOME_GAME_LIST = 0x10004;

    public static final int CITY_APPLY_GAME = 0x10005;
    public static final int CHINA_APPLY_GAME = 0x10006;
    public static final int WORD_APPLY_GAME = 0x10007;
    public static final int GET_RULE_NOTIFY = 0x10008;


    // 实名认证
    public static final int USER_AUTH=0x10100;
    // 上传文件
    public static final int UOLOAD_AUTH_FILE =0x10200;
    // 获取用户基本信息
    public static final int PERSONALDATA=0x10300;
    // 获取用户认证信息
    public static final int USER_AUTH_INFO=0x10400;
    // 获取用户已经报名的信息
    public static final int USER_APPLYED = 0x10500;
    // 进入比赛时间判断
    public static final int USER_IN_GAEM = 0x10600;
    // 修改登录密码
    public static final int UPDATA_LOGIN_PWD = 0x10700;
    // 提交意见
    public static final int SUBMIT_FEEDBACK=0x10800;


    public static final int REGIST_OK = 0x18000;
    public static final int REGIST_FILED = 0x18001;
    public static final int MODIFY_OK = 0x18002;
    public static final int MODIFY_FILED = 0x18003;

}
