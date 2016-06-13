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
    // 获取图片路径
    public static String PATH_FILE_URL = "http://221.5.109.34/crazybrain-mng/image/getFile?filePath=";

    public static int JSON_ERREO = -2;

    public static final int LOGIN_UPDATE_UI = 0x10000;
    public static final int LOGIN_WRONG_MSG = 0x10001;

    public static final int USER_AUTH=0x10100;
    public static final int UOLOAD_AUTH_FILE =0x10200;
    public static final int PERSONALDATA=0x10300;
}
