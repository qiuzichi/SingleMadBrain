package com.unipad.io.mina;

/**
 * Created by gongkan on 2016/6/6.
 */
public class IOConstant {
    /**选手签到	10001		移动端	管控端	选手在移动端进入比赛，发送签到报文到管控端
     试题下发	10002		管控端	移动端	管控端发起试题下载通知
     试题下载结果	10021		移动端	管控端	客户端发起下载完成交易请求
     开始比赛	10003		管控端	移动端	管控端发起开始比赛请求
     暂停比赛	10004		管控端	移动端	管控端发起暂停比赛请求
     恢复比赛	10005		管控端	移动端	管控端发起恢复比赛请求
     结束比赛	10006		移动端	管控端	客户端主动发起结束比赛请求
     结束比赛	10061		管控端	移动端	管控端发起全场结束比赛请求
     */

    /**
     * 选手在移动端进入比赛，发送签到报文到管控端
     */
    public static String ATHLETE_SIGN_IN = "10001";

    /**
     * 管控端发起试题下载通知
     */
    public static String SEND_QUESTIONS = "10002";

    /**
     * 管控端发起开始比赛请求
     */
    public static String GAME_START = "10003";
    /**
     * 管控端发起暂停比赛请求
     */
    public static String GAME_PAUSE = "10004";
    /**
     * 管控端发起恢复比赛请求
     */
    public static String GAME_RESTART = "10005";
    /**
     * 客户端主动发起结束比赛请求
     */
    public static String END_GAME_BY_Client = "10006";
    /**
     * 客户端的比赛进度
     */
    public static String PROGRESS_GAME = "10007";
    /**
     * 管控端发起全场结束比赛请求
     */
    public static String END_GAME_BY_SERVER = "10061";

    /**
     * 客户端发起下载完成交易请求
     */
    public static String LOAD_QUSETION_END = "10021";

    /**
     * 代码	代码含义
     0000	成功
     9999	未知错误
     2101	报文传输错误
     2102	报文格式错误
     2103	不存在的交易
     */
    /**
     * 成功
     */
    public static int RESULT_OK = 0000;

    /**
     * 未知错误
     */
    public static int RESULT_UNKANOW_ERR = 9999;

    /**
     * 报文传输错误
     */
    public static int RESULT_TRANSPORT_ERR = 2101;

    /**
     * 报文格式错误
     */
    public static int MSG_FORMAT_ERR = 2102;

    /**
     * 报文格式错误
     */
    public static int MSG_NOT_EXIST = 2103;
}
