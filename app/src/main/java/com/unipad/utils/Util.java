package com.unipad.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.TextView;

import com.unipad.IcoreTimeChange;
import com.unipad.brain.R;


public class Util {
	
	public static AlertDialog createSetting(final Context context,
			final String key) {
		return createSetting(context, key, null);
	}

	public static AlertDialog createSetting(final Context context,
			final String key, final IcoreTimeChange callback) {

		Builder build = new Builder(context);
		build.setCancelable(false);
		final AlertDialog dialog = build.create();


		View contentView = LayoutInflater.from(context).inflate(
				R.layout.num_picker, null);

		Display d = ((Activity)context).getWindow().getWindowManager().getDefaultDisplay();  //为获取屏幕宽、高
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的
		p.width = (int) (d.getWidth() * 0.5 );    //宽度设置为屏幕的
		dialog.getWindow().setAttributes(p);     //设置生效

		final NumberPicker hourPicker = (NumberPicker) contentView
				.findViewById(R.id.hour_picker);
		final NumberPicker minutePicker = (NumberPicker) contentView
				.findViewById(R.id.minute_picker);
		final NumberPicker secondPicker = (NumberPicker) contentView
				.findViewById(R.id.second_icker);


		contentView.findViewById(R.id.btn_ok).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						long value = secondPicker.getValue() + 60
								* minutePicker.getValue() + 3600
								* hourPicker.getValue();
//						SharepreferenceUtils.writeLong(key, value);
						dialog.dismiss();
						if (callback != null) {
							callback.callback(value);
						}
					}
				});
		contentView.findViewById(R.id.btn_cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});

		Formatter formatter = new Formatter() {

			@Override
			public String format(int value) {
				String tmpStr = String.valueOf(value);
				if (value < 10) {
					tmpStr = "0" + tmpStr;
				}
				return tmpStr;
			}
		};
		long value = SharepreferenceUtils.readLong(key, 300L);

		Log.e("gongkan", "value:" + value);
		hourPicker.setFormatter(formatter);
		hourPicker.setMaxValue(23);
		hourPicker.setMinValue(0);
		int hour = (int) (value / 3600);
		hourPicker.setValue(hour);
		minutePicker.setFormatter(formatter);
		minutePicker.setMaxValue(59);
		minutePicker.setMinValue(0);
		int minute = (int) value % 3600 / 60;
		minutePicker.setValue(minute);
		secondPicker.setFormatter(formatter);
		secondPicker.setMaxValue(59);
		secondPicker.setMinValue(0);
		int second = (int) value % 3600 % 60;
		secondPicker.setValue(second);
		((TextView) contentView.findViewById(R.id.time))
				.setText(dateFormat(hour) + ":" + dateFormat(minute) + ":"
						+ dateFormat(second));
		dialog.setView(contentView);

		return dialog;
	}

	public static String dateFormat(long value) {
		int hour = (int) (value / 3600);
		int minute = (int) value % 3600 / 60;
		int second = (int) value % 3600 % 60;
		return dateFormat(hour) + ":" + dateFormat(minute) + ":"
				+ dateFormat(second);
	}


	/**
	 * 格式化时间变量
	 *
	 * @param
	 * //传递过来的时间变量
	 *            (String类型)yyyyMMddHHmmss
	 * @return 返回指定的类型.yyyy.MM.dd HH:mm
	 */
	public static String dateFormat(int value) {
		String tmpStr = String.valueOf(value);
		if (value < 10) {
			tmpStr = "0" + tmpStr;
		}
		return tmpStr;
	}
}
