package com.unipad.http;

/**
 * Created by gongkan on 2016/5/30.
 */
public class HttpConstant {
    public static final String LOGIN = "/api/user/login";

    public static String Regist = "/api/user/regist";
    public static String UPLOAD = "/api/file/upload";
    // 实名认证
    public static String AUTH_PATH = "/api/user/auth";
    // 调取实名认证信息 ----在用户已经实名认证之后 调取。
    public static String AUTH_INFO_PATH = "/api/user/data";
    // 获取图片路径 http://192.168.0.104:8090/crazybrain-mng
    public static String PATH_FILE_URL = "http://192.168.0.104:8090/crazybrain-mng/image/getFile?filePath=";
    // 更新用户信息
    public static String UPDATE_USERINFO = "/api/user/modify";
    // 得到用户已经报名列表
    public static String USER_APPLYED_HTTP= "/api/user/myApply";

    public static int JSON_ERREO = -2;
    public static final int LOGIN_UPDATE_UI = 0x10000;
    public static final int LOGIN_WRONG_MSG = 0x10001;
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
}
