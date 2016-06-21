package com.unipad.brain.home.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
/**
 * 
 * @author 张超
 *
 */
public abstract class BaseConfirmDialog extends BaseDialog {

	String hintTitle; // 需要提示的文本信息；
	String leftBtnLabel;  //左侧按钮文本；
	String rightBtnLabel; //右侧按钮文本；
	OnActionClickListener itemClickListener; // item点击事件处理器
	
	
	public BaseConfirmDialog(Context context, String hint,String leftbtn, String rightbtn,
			OnActionClickListener listener) {
		super(context);
		hintTitle = hint;
		leftBtnLabel=leftbtn;
		rightBtnLabel=rightbtn;
		itemClickListener = listener;
	}

	/**
	 * 获取dialog的layout文件resid，必须为有效resource，否则会导致dialog出错；
	 * @return
	 */
	protected abstract int getDialogLayoutId();

	/**
	 * 获取顶部title对应的view，如果为null，表示不显示；
	 * 
	 * @return
	 */
	protected abstract TextView getTitleView();

	/**
	 * 获取底部左侧按钮，如果为null，表示不显示；
	 * 
	 * @return
	 */
	protected abstract TextView getLeftButton();

	/**
	 * 获取底部右侧按钮，如果为null，表示不显示；
	 * 
	 * @return
	 */
	protected abstract TextView getRightButton();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化contentview
		setContentView(getDialogLayoutId());
		
		//提取标题栏，并捆绑提示内容信息
		TextView titleView=getTitleView();
		if (titleView!=null){
			titleView.setText(hintTitle);
		}

		//定义按钮点击事件监听
		View.OnClickListener clickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (itemClickListener != null) {
					if (v == getLeftButton()) {
						// 左侧按钮被点击
						itemClickListener.onActionLeft(BaseConfirmDialog.this);
					} else if (v == getRightButton()) {
						// 右侧按钮被点击
						itemClickListener.onActionRight(BaseConfirmDialog.this);
					}
				}
				
				//关闭对话框
				dismiss();
			}
		};

		// 捆绑左侧按钮的点击事件
		TextView btnLeft = getLeftButton();
		if (btnLeft != null) {
			if (TextUtils.isEmpty(leftBtnLabel)){
				btnLeft.setVisibility(View.GONE);
			}
			else{
				btnLeft.setVisibility(View.VISIBLE);
			}
			btnLeft.setText(leftBtnLabel);
			btnLeft.setOnClickListener(clickListener);
		}

		// 捆绑左侧按钮的点击事件
		TextView btnRight = getRightButton();
		if (btnRight != null) {
			if (TextUtils.isEmpty(rightBtnLabel)){
				btnRight.setVisibility(View.GONE);
			}
			else{
				btnRight.setVisibility(View.VISIBLE);
			}
			btnRight.setText(rightBtnLabel);
			btnRight.setOnClickListener(clickListener);
		}
	}
	
	
	/**
	 * 设置事件监听
	 * @param listener
	 */
	public void setOnActionClickListener(OnActionClickListener listener){
		itemClickListener=listener;
	}

	/** 定义对话框的点击事件，v表示事件触发的控件 */
	public interface OnActionClickListener {
		public void onActionRight(BaseConfirmDialog dialog);
		public void onActionLeft(BaseConfirmDialog dialog);
	}

}
