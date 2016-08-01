package com.unipad.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.unipad.brain.App;
import com.unipad.brain.home.SApplication;

public class SharepreferenceUtils {
	public static final String THEME_NAME = "gameSetting";

	public static void writeString(String key, String value, String sharedFile) {
		SharedPreferences preferences = App.getContext()
				.getSharedPreferences(sharedFile, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static boolean getBoolean(String key) {
		SharedPreferences preferences = App.getContext()
				.getSharedPreferences(SharepreferenceUtils.THEME_NAME,
						Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	public static String getString(String key, String defaultString) {
		SharedPreferences preferences = App.getContext()
				.getSharedPreferences(THEME_NAME, Context.MODE_PRIVATE);
		return preferences.getString(key, defaultString);
	}

	public static void writeLong(String key, long value) {
		SharedPreferences preferences = App.getContext()
				.getSharedPreferences(SharepreferenceUtils.THEME_NAME,
						Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public static void writeLong(String key, long value, String sharedFile) {
		SharedPreferences preferences = App.getContext()
				.getSharedPreferences(sharedFile, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public static long readLong(String key) {
		return readLong(key, 0);
	}

	public static long readLong(String key, long defaultValue) {
		SharedPreferences preferences = App.getContext()
				.getSharedPreferences(THEME_NAME, Context.MODE_PRIVATE);
		return preferences.getLong(key, defaultValue);
	}

	public static void writeBoolean(String key, boolean value) {
		SharedPreferences preferences = App.getContext()
				.getSharedPreferences(SharepreferenceUtils.THEME_NAME,
						Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	/**
	 * Remove key from sharedFile
	 * 
	 * @param //Context
	 *            The context. key The name of the preference to remove.
	 *            sharedFile The preference file name
	 */
	public static void removeKey(Context context, String key, String sharedFile) {
		SharedPreferences preference = context.getSharedPreferences(sharedFile,
				Context.MODE_PRIVATE);

		Editor editor = preference.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * Remove all data int sharedFile
	 * 
	 * @param //Context
	 *            The context. sharedFile The preference file name
	 */
	public static void removeAll(Context context, String sharedFile) {
		SharedPreferences preference = context.getSharedPreferences(sharedFile,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.clear();
		editor.commit();
	}
}
