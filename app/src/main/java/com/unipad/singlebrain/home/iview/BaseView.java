package com.unipad.singlebrain.home.iview;

import android.content.Context;

public interface BaseView {
	Context getContext(); // 得到上下文

	void request(String jsonStr, int flag); // 接口返回结果

	void showToast(String checkResult); // Toast 客户端验登录证结果

	/**
	 * 正在请求中 (有进度)
	 * 
	 * @param total
	 *            总数进度
	 * @param current
	 *            现在的进度
	 * @param isUploading
	 *            是否正在上传
	 */
	void loadingDialog(long total, long current, boolean isUploading);

	/**
	 * 
	 * @param isOpen
	 *            true 打开 ， false 关闭
	 */
	void showDialog(boolean isOpen);
}
