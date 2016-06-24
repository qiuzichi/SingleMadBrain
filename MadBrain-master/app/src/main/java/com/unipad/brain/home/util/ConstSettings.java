package com.unipad.brain.home.util;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;

import com.unipad.brain.R;
import com.unipad.brain.home.bean.User;


@SuppressLint("UseSparseArrays")
public class ConstSettings {
	/**
	 * 判断程序是否为第一次运行
	 * 
	 * @return true 是 false 否
	 */
	public static Boolean isfirstRun(Context context,
			SharedPreferencesUtil sharedPreferencesUtil) {
		boolean isFirstRun = sharedPreferencesUtil.getBoolean("isFirstRun",true);
		if (isFirstRun) {
			sharedPreferencesUtil.saveBoolean("isFirstRun", false);
			return true;
		} else {
			return false;
		}
	}

	private static Map<Integer, String> mapValues;
	
	private static Map<Integer, String> projectValues;

	
	/*
	 * 得到后台提供的 提示信息
	 */
	public static Map<Integer, String> getValues() {
		if (mapValues == null) {
			mapValues = new HashMap<Integer, String>();
			mapValues.put(SUCCESS, "处理成功");
			mapValues.put(SYSTEM_BUSY, "系统繁忙");
			mapValues.put(SESSION_INVALID, "尚未登录/回话超时");
			mapValues.put(PARAMETER_INCORRENT, "请求参数有误");
			mapValues.put(ACCOUNT_EXIST, "帐户已注册");
			mapValues.put(ACCOUNT_OR_PASSWORD_WRONG, "帐号或者密码错误");
			mapValues.put(ACCOUNT_EMAIL_EXIST, "邮箱已注册");
			mapValues.put(ACCOUNT_PHONE_EXIST, "手机已注册");
			mapValues.put(CAPTCHA_INCORRENT, "验证码错误");
			mapValues.put(TOKEN_INCORRECT, "TOKEN失效");
			mapValues.put(ACCOUNT_PHONE_NOT_EXIST, "手机尚未注册");
			mapValues.put(WRONG_JOSN_FORMAT, "错误的json格式");
			mapValues.put(DEVICE_ID_INCORRENT, "设备ID不正确（非法）");
			mapValues.put(DEVICE_ALREADY_ACTIVATION, "设备已激活");
			mapValues.put(DEVICE_NOT_ONLINE, "设备尚未链接网络");
			mapValues.put(DEVICE_UN_ACTIVATION, "设备未激活");
			mapValues.put(DEVICE_ACTIVATION_BY_YOU, "设备已被您激活过");
			mapValues.put(USER_NOT_EXIST, "用户不存在");
			mapValues.put(USER_IS_NOT_OWNER, "用户不是群主");
			mapValues.put(USER_ALREADY_OTHER_GROUP, "用户已加入其他群");
			mapValues.put(CANT_KICK_OWN_SELF, "不能自己踢自己");
			mapValues.put(GROUP_IS_FULL, "群成员已满");
			mapValues.put(GROUP_IS_DISMISS, "群已解散");
			mapValues.put(UNAUTHORIZED, "未授权");
			mapValues.put(ALREADY_AUTH, "已经登录");
			mapValues.put(NOT_SUPPORT_IE, "不支持IE提示");
			mapValues.put(REQUEST_FAIL, "网络请求超时");
			mapValues.put(NO_RESULT, "服务器没有查询到相关数据");
			mapValues.put(RE_ADD, "信息已存在，不能重复添加");
			mapValues.put(RE_FRIEND, "已是好友,请查看即时通信联系人");
			mapValues.put(TWICE_LIMIT, "提问失败，加急次数已达上限（每月两次）。");
			mapValues.put(NOT_DATA, "查询不到更多数据");
		}
		return mapValues;
	}
	
	
	/*
	 */
	public static Map<Integer, String> getProjectValues(Context context) {
		if (projectValues == null) {
			projectValues = new HashMap<Integer, String>();
			projectValues.put(0, context.getResources().getString(R.string.project_1));
			projectValues.put(1, context.getResources().getString(R.string.project_2));
			projectValues.put(2, context.getResources().getString(R.string.project_3));
			projectValues.put(3, context.getResources().getString(R.string.project_4));
			projectValues.put(4, context.getResources().getString(R.string.project_5));
			projectValues.put(5, context.getResources().getString(R.string.project_6));
			projectValues.put(6, context.getResources().getString(R.string.project_7));
			projectValues.put(7, context.getResources().getString(R.string.project_8));
			projectValues.put(8, context.getResources().getString(R.string.project_9));
			projectValues.put(9, context.getResources().getString(R.string.project_10));
		}
		return projectValues;
	}
		
	
	
	
	// 
	public static boolean isLogin = true;
	
	// 请求网络失败
	public final static int REQUEST_FAIL = 101;

	// 处理成功
	public final static int SUCCESS = 0;
	
	public final static int RELOGIN_SUCCESS = 0;

	// 系统繁忙
	public final static int SYSTEM_BUSY = -1;

	// 尚未登录/回话超时
	public final static int SESSION_INVALID = 40001;

	// 请求参数有误
	public final static int PARAMETER_INCORRENT = 40002;

	// 帐户已注册
	public final static int ACCOUNT_EXIST = 40003;

	// 帐号或者密码错误
	public final static int ACCOUNT_OR_PASSWORD_WRONG = 40004;

	// 邮箱已注册
	public final static int ACCOUNT_EMAIL_EXIST = 40005;

	// 手机已注册
	public final static int ACCOUNT_PHONE_EXIST = 40006;

	// 验证码错误
	public final static int CAPTCHA_INCORRENT = 40007;

	// TOKEN失效
	public final static int TOKEN_INCORRECT = 40008;
	// 手机尚未注册
	public final static int ACCOUNT_PHONE_NOT_EXIST = 40009;
	// 错误的json格式
	public final static int WRONG_JOSN_FORMAT = 40050;
	// 设备ID不正确（非法）
	public final static int DEVICE_ID_INCORRENT = 40051;
	// 设备已激活
	public final static int DEVICE_ALREADY_ACTIVATION = 40052;
	// 设备尚未链接网络
	public final static int DEVICE_NOT_ONLINE = 40053;
	// 设备未激活
	public final static int DEVICE_UN_ACTIVATION = 40054;
	// 设备已被您激活过
	public final static int DEVICE_ACTIVATION_BY_YOU = 40055;

	// 用户不存在
	public final static int USER_NOT_EXIST = 40060;
	// 用户不是群主
	public final static int USER_IS_NOT_OWNER = 40061;
	// 用户已加入其他群
	public final static int USER_ALREADY_OTHER_GROUP = 40062;
	// 不能自己踢自己
	public final static int CANT_KICK_OWN_SELF = 40063;
	// 群成员已满
	public final static int GROUP_IS_FULL = 40064;
	// 群已解散
	public final static int GROUP_IS_DISMISS = 40065;

	// 未授权
	public final static int UNAUTHORIZED = 50001;

	// 已经登录
	public final static int ALREADY_AUTH = 50002;

	// 不支持IE提示
	public final static int NOT_SUPPORT_IE = 50003;
	
	// 没有查询到数据
	public final static int NO_RESULT=50004;
	
	public final static int RE_ADD = 50008; // 重复添加了
	
	public final static int RE_FRIEND = 60003;
	
	public final static int TWICE_LIMIT=50009; // 用户当月提问加急已经2次
	
	// 重新登录
	public final static int RELOGIN = 999;
	
	public final static int NOT_DATA = 122;
	
	public final static boolean isDeBug = false;
	
	
	/**
	 * 得到 用户信息
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static User getUserInfo(String json) throws JSONException {
		
		return new User();
	}
	
	
	/**
	 *  描述 ： 做横竖屏 适配时，  保存当前界面的数据。 此方法 是将数据转换为string[]
	 * @param param
	 * @return
	 */
	public static String[] saveDataToStrs(Object...param){
		String[] strs = null;
		if(param != null){
			strs = new String[param.length];
			for(int i = 0; i < param.length; i ++ ) {
				strs[i] = param[i].toString();
			}
		}
		return strs;
	}
	
	/*------------------推送type------------------*/
	
	/**
	 * 发送文本
	 */
	public final static String TYPE_TEXT = "text";
	
	/**
	 * 发送图片
	 */
	public final static String TYPE_IMAGE = "image";
	
	/**
	 * 发送音频
	 */
	public final static String TYPE_AUDIO = "audio";
	
	/**
	 * 发送文件
	 */
	public final static String TYPE_FILE = "file";
	
	/**
	 *  添加好友
	 */
	public final static String TYPE_ADD = "add";
	
	/**
	 * 同意添加好友
	 */
	public final static String TYPE_AGREE = "agree";
	
	/**
	 * 拒绝添加好友
	 */
	public final static String TYPE_REJECT ="reject";
	
	/**
	 *  删除好友
	 */
	public final static String TYPE_DELETE = "delete";
	
	/**
	 *  问题被解答了
	 */
	public final static String TYPE_Q_RESP = "q_resp";
	
	/**
	 * 07-09 07:47:12.470: I/Mytest(2462): Socket连接正常{"action":"message","content":"9090","createDate":1436442812926,"msgId":"dd77010ab2214442b4bbe1a10f6b00ff","needPersistence":true,"receiverId":"1","senderId":"eae3081ecad848d3a31d340c12af94c0","senderName":"李萌","status":0,"timestamp":1436442812926,"type":"q_exa"}

	 */
	public final static String TYPE_Q_EXA = "q_exa";
	
	/**
	 *  问题转移了
	 */
	public final static String TYPE_Q_TRANS = "q_trans";
	
	/**
	 * 群文字
	 */
	public final static String TYPE_GROUP_TEXT = "group_text";
	
	/**
	 * 群图片
	 */
	public final static String TYPE_GROUP_IMG = "group_img";
	
	/**
	 * 群语音
	 */
	public final static String TYPE_GROUP_AUDIO = "group_audio";
	/**--------------------好友状态------------------------*/
	
	/**
	 * 08-10 23:36:46.580: I/Mytest(841): Socket连接正常{"action":"message","content":"123@@http://10.191.18.3:8080/asrpms/upload/uploadimg/9fa88c4bf4a44a1da6c277ca7b511a50.jpg","createDate":1439264208821,"groupId":"6e130b8b5c3d4a0dba908b5e97c91159","msgId":"bf5ee67be5c0429f923a71545c72cb00","needPersistence":true,"receiverId":"3","senderId":"a2408b1818fc4cd18dadf2068ae67713","senderName":"admin","status":0,"timestamp":1439264208821,"type":"subscribe_push"}
subscribe_push
	 */
	public final static String  TYPE_S_PUSH = "subscribe_push";
	
	/**
	 * 拉人入群
	 */
	public final static String TYPE_JOIN_GROUP = "join_group";
	
	
	
	/**
	 * 互为好友
	 */
	public static final int STATUS_EFFECT = 0;
	
	/**
	 * 添加中，待审核
	 */
	public static final int STATUS_ADD = 1;
	
	/**
	 * 已拒绝
	 */
	public static final int STATUS_REJECT = 2;
	
	/**
	 * 双方不是好友
	 */
	public static final int STATUS_NO_Friend = 3;
	
}
