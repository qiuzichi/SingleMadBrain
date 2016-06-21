package com.unipad.brain.home.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil
{
	private SharedPreferences mSharedPreferences;

	public SharedPreferencesUtil(Context context)
	{
		mSharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
	}

	// 保存字符串数据
	public void saveString(String key, String value)
	{
		mSharedPreferences.edit().putString(key, value).commit();
	}

	// 获取字符串数据
	public String getString(String key, String... defValue)
	{
		if (defValue.length > 0)
			return mSharedPreferences.getString(key, defValue[0]);
		else
			return mSharedPreferences.getString(key, "");
	}

	// 保存整型数据
	public void saveInt(String key, int value)
	{
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	// 获取整型数据
	public int getInt(String key, int defValue)
	{
		return mSharedPreferences.getInt(key, defValue);

	}

	// 保存布尔值数据
	public void saveBoolean(String key, Boolean value)
	{
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	// 获取布尔值数据
	public Boolean getBoolean(String key, Boolean... defValue)
	{
		if (defValue.length > 0)
			return mSharedPreferences.getBoolean(key, defValue[0]);
		else
			return mSharedPreferences.getBoolean(key, false);

	}

	// 保存整型数据
	public void saveLong(String key, long value)
	{
		mSharedPreferences.edit().putLong(key, value).commit();
	}

	// 获取整型数据
	public long getLong(String key, long defValue)
	{
		return mSharedPreferences.getLong(key, defValue);

	}
	// 保存float型数据
	public void saveFloat(String key, float value)
	{
		mSharedPreferences.edit().putFloat(key, value).commit();
	}
	// 获取float型数据
	public float getFloat(String key, float defValue)
	{
		return mSharedPreferences.getFloat(key, defValue);

	}

	public boolean remove(String key)
	{
		return mSharedPreferences.edit().remove(key).commit();
	}
	
	/**
	 *  清除
	 * @return
	 */
	public boolean clear(){
		return mSharedPreferences.edit().clear().commit();
	}
}
