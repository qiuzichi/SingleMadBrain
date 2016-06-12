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

    public static final int LOGIN_UPDATE_UI = 0x10000;
    public static final int LOGIN_WRONG_MSG = 0x10001;
    public static final int USER_AUTH=0x10100;
}
