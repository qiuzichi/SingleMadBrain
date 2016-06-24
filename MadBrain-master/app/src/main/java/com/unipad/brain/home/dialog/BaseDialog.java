package com.unipad.brain.home.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.unipad.brain.R;


/**
 * 
 * @author gongjb
 *
 */
public class BaseDialog extends Dialog {
	
	private final static float DEFAULT_DIM_AMOUNT=0.4f;
	
	public BaseDialog(Context context) {
		super(context, R.style.DialogTheme);
		setCanceledOnTouchOutside(true);
	}	

	/**
	 * 设置dialog在屏幕中的布局参数；

	 */
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window w = getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.width = getDialogWidth();
		lp.height = getDialogHeight();
		lp.gravity=getDialogGravity();
		lp.format = PixelFormat.TRANSLUCENT;
		lp.dimAmount = getDimAmount();
		lp.x=getOffsetX();
		lp.y=getOffsetY();
		w.setAttributes(lp);
	}

	/**
	 * 获取dialog的宽度，缺省为fill_parent
	 * @return
	 */
	protected int getDialogWidth(){
		return WindowManager.LayoutParams.FILL_PARENT;
	}	
	
	/**
	 * 获取dialog的高度，缺省为wrap_content
	 * @return
	 */
	protected int getDialogHeight(){
		return WindowManager.LayoutParams.WRAP_CONTENT;
	}
	
	/**
	 * 获取dialog的背景透明度
	 * @return
	 */
	protected float getDimAmount(){
		return DEFAULT_DIM_AMOUNT;
	}
	
	/**
	 * 获取dialog的布局位置，缺省为居中
	 * @return
	 */
	protected int getDialogGravity(){
		return Gravity.CENTER;
	}
	
	/**
	 * 获取dialog的x方向相对于基准边的偏移量，如果dialog左对齐、右对齐时有效
	 * @return
	 */
	protected int getOffsetX(){
		return 0;
	}

	/**
	 *  获取dialog的y方向相对于基准边的偏移量，如果dialog顶对齐、底对齐时有效
	 * @return
	 */
	protected int getOffsetY(){
		return 0;
	}	

}
