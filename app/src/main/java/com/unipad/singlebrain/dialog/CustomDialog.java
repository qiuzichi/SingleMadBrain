package com.unipad.singlebrain.dialog;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unipad.singlebrain.R;

public class CustomDialog extends Dialog implements
		View.OnClickListener {

	/** 布局文件 **/
	int layoutRes;
	/** 上下文对象 **/
	Context context;
	/** 显示的文本 **/
	String text;
	/** 确定按钮 **/
	public Button confirmBtn;
	/** 取消按钮 **/
	public Button cancelBtn;
	private TextView textview;
	/** Toast时间 **/
	public static final int TOAST_TIME = 1000;

	public CustomDialog(Context context, String text) {
		super(context);
		this.context = context;
		this.text = text;
	}

	/**
	 * 自定义布局的构造方法
	 * 
	 * @param context
	 * @param resLayout
	 */
	public CustomDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 自定义主题及布局的构造方法
	 * 
	 * @param context
	 * @param theme
	 * @param
	 */
	public CustomDialog(Context context, int theme, String text) {
		super(context, theme);
		this.context = context;
		this.text = text;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 指定布局
		this.setContentView(R.layout.customdialog);

		// 根据id在布局中找到控件对象
		confirmBtn = (Button) findViewById(R.id.confirm_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		textview = (TextView) findViewById(R.id.text);

		// 设置按钮的文本颜色
		cancelBtn.setTextColor(android.graphics.Color.parseColor("#646464"));
		confirmBtn.setTextColor(0xff1E90FF);
		textview.setText(text);

		// 为按钮绑定点击事件监听器
		/* confirmBtn.setOnClickListener(this); */
		cancelBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel_btn:
			dismiss();
			break;
		default:
			break;
		}
	}

}