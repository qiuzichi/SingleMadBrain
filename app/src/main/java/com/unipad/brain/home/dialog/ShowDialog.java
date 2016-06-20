package com.unipad.brain.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.unipad.brain.R;


/**
 * 
 * @author 张超
 *
 */
public class ShowDialog {
	private Dialog dialog;

	public Dialog getDialog(){
		return dialog;
	};

	private Context context;
	/**
	 * TYPE_CENTER :dialog居中显示
	 * */
	public final static int TYPE_CENTER = 1;
	/**
	 * TYPE_CENTER :dialog居底部显示
	 * */
	public final static int TYPE_BOTTOM = 0;
	
	public final static int TYPE_RIGHT = 2;

	// 返回Dialog
	public Dialog getAlertDialog() {
		return dialog;
	}

	/* 构造方法 */
	public ShowDialog(Context argActivity) {
		this.context = argActivity;
	}

	
	/**
	 *  显示自定义屏幕宽高的  dialog
	 * @param v  需要显示的视图
	 * @param type  位置
	 * @param m 屏幕管理器
	 * @param h dialog 显示的高度 0.0f：全屏
	 * @param w dialog 显示的宽度 0.0f: 全屏
	 */
	public void showDialog(View v, int type,WindowManager m,float h,float w) {
		h = h <= 0.0f ? 1.0f : h;
		
		w = w <= 0.0f ? 1.0f : w;
		dialog = new Dialog(context, R.style.DialogTheme);
		Window window = dialog.getWindow();
		if (TYPE_BOTTOM == type) {
			window.setGravity(Gravity.BOTTOM); // dialog底部显示
			//window.setWindowAnimations(R.style.animation); // 动漫显示dialog
			window.setWindowAnimations(R.style.mystyle);
		} else if(TYPE_RIGHT == type){
			window.setGravity(Gravity.RIGHT); // dialog底部显示
			//window.setWindowAnimations(R.style.animation); // 动漫显示dialog
			window.setWindowAnimations(R.style.mystyle);
		} else {
			window.setGravity(Gravity.CENTER); // dialog居中显示
			//window.setWindowAnimations(R.style.animation); // 动漫显示dialog
			window.setWindowAnimations(R.style.mystyle);
		}
		dialog.setContentView(v);
		dialog.show();
		
		
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
		p.height = (int) (d.getHeight() * h );   //高度设置为屏幕的
		p.width = (int) (d.getWidth() * w );    //宽度设置为屏幕的
		dialog.getWindow().setAttributes(p);     //设置生效  
	}
	
	
	/**
	 *  显示自定义屏幕宽高的  dialog
	 * @param v  需要显示的视图
	 * @param type  位置
	 * @param m 屏幕管理器
	 * @param w dialog 显示的宽度 0.0f: 全屏
	 */
	public void showDialog(View v, int type,WindowManager m,float w) {
		
		w = w <= 0.0f ? 1.0f : w;
		dialog = new Dialog(context, R.style.DialogTheme);
		Window window = dialog.getWindow();
		if (TYPE_BOTTOM == type) {
			window.setGravity(Gravity.BOTTOM); // dialog底部显示
			window.setWindowAnimations(R.style.animation); // 动漫显示dialog
		} else if(TYPE_RIGHT == type){
			window.setGravity(Gravity.RIGHT); // dialog底部显示
			//window.setWindowAnimations(R.style.animation); // 动漫显示dialog
			window.setWindowAnimations(R.style.mystyle);
		} else {
			window.setGravity(Gravity.CENTER); // dialog居中显示
			//window.setWindowAnimations(R.style.animation); // 动漫显示dialog
			window.setWindowAnimations(R.style.mystyle);
		}
		dialog.setContentView(v);
		dialog.show();
		
		
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
		p.height = WindowManager.LayoutParams.WRAP_CONTENT;   //高度设置为屏幕的
		
		p.width = (int) (d.getWidth() * w );    //宽度设置为屏幕的
		dialog.getWindow().setAttributes(p);     //设置生效  
	}
	
	
	
	/**
	 *  显示自定义屏幕宽高的  dialog
	 * @param v  需要显示的视图
	 * @param type  位置
	 * @param m 屏幕管理器
	 */
	public void showDialog(View v, int type,WindowManager m) {
		dialog = new Dialog(context,R.style.DialogTheme);
		Window window = dialog.getWindow();
		if (TYPE_BOTTOM == type) {
			window.setGravity(Gravity.BOTTOM); // dialog底部显示
			window.setWindowAnimations(R.style.animation); // 动漫显示dialog
		} else {
			window.setGravity(Gravity.CENTER); // dialog居中显示
			window.setWindowAnimations(R.style.animation); // 动漫显示dialog
		}
		dialog.setContentView(v);
		dialog.show();
		
		
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
		p.height = WindowManager.LayoutParams.WRAP_CONTENT;
		p.width=d.getWidth()-24;
		dialog.getWindow().setAttributes(p);     //设置生效  
	}
	
	
	/**
	 * 显示自定义视图
	 * @param v  需要显示的视图
	 * @param type  位置
	 */
	public void showDialog(View v, int type) {
		dialog = new Dialog(context, R.style.DialogTheme);
		Window window = dialog.getWindow();
		if (TYPE_BOTTOM == type) {
			window.setGravity(Gravity.BOTTOM); // dialog底部显示
			window.setWindowAnimations(R.style.animation); // 动漫显示dialog
		} else {
			window.setGravity(Gravity.CENTER); // dialog居中显示
			window.setWindowAnimations(R.style.animation); // 动漫显示dialog
		}
		dialog.setContentView(v);
		dialog.show();
	}
	


	// 关闭dialog
	public void dismiss() {
		if(dialog!=null && dialog.isShowing())dialog.dismiss();
	}
	public boolean isShowing(){
		return null!=dialog&&dialog.isShowing();
	}
	public void setCancelable(boolean cancelable,boolean canceledOnTouchOutside){
		if(null!=dialog){
			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		}
	}
	/**
	 *  弹出带 确定键的 dialog
	 * @param str  需要显示的文本
	 * @param context 
	 */
	public static void showErrorDialog(String str,Context context){
		Dialog dialog =  new ConfirmDialog(context, str, "确定",
				"",new BaseConfirmDialog.OnActionClickListener() {
					@Override
					public void onActionRight(BaseConfirmDialog dialog) {

					}
					@Override
					public void onActionLeft(BaseConfirmDialog dialog) {
						dialog.dismiss();
					}
				});
//		dialog.setOnKeyListener(keylistener); // 点击返回键不让dialog 消失
		dialog.setCanceledOnTouchOutside(false);  // 触摸屏幕外让 dialog 消失
		dialog.show();
	 }
	
	/**
	 *   描述 ：   点击返回键不让dialog 消失.
	 *         让返回键失效
	 */
	static OnKeyListener keylistener = new OnKeyListener() {
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				return true;
			} else {
				return false;
			}
		}
	};

	/**
	 *  绑定一个View 控件的点击事件
	 */
	public void bindOnClickListener(View view,int[] ids){
		if(ids == null)
			return;
		for(int i = 0 ; i < ids.length;i++) {
			view.findViewById(ids[i]).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(null != onShowDialogClick)
						onShowDialogClick.dialogClick(v.getId());
				}
			});
		}
	}

	/**
	 *
	 *
	 *   此接口  返回自定义dialog中所有的点击事件
	 */
	public interface OnShowDialogClick{
		 void dialogClick(int id);
	}

	private OnShowDialogClick onShowDialogClick;

	public void setOnShowDialogClick( OnShowDialogClick onShowDialogClick){
		this.onShowDialogClick = onShowDialogClick;
	}


}
