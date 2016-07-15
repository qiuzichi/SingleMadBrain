package com.unipad.brain.dialog;


import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.unipad.brain.R;


/**
 * 提示dialog，只显示一段文本，用户确认和取消；
 * @author 张超
 */
public class ConfirmDialog extends BaseConfirmDialog {
	
	/**
	 * 显示一段文本，用户确认和取消
	 * @param context
	 * @param hint 提示信息文本
	 * @param leftlabel 左边按钮文本
	 * @param rightlabel 右边按钮文本
	 * @param listener 点击事件
	 */
    public ConfirmDialog(Context context, String hint,String leftlabel, String rightlabel,
    		BaseConfirmDialog.OnActionClickListener listener) {
        super(context, hint,leftlabel,rightlabel,listener);
    }

 	@Override
	protected int getDialogLayoutId() {
		return R.layout.d_confirm;
	}

	@Override
	protected TextView getHintVersionView() {
		return null;
	}

	@Override
	protected TextView getTitleView() {
		return (TextView) findViewById(R.id.hint_content);
	}

	@Override
	protected Button getLeftButton() {
		return (Button) findViewById(R.id.btn_left);
	}

	@Override
	protected Button getRightButton() {
		return (Button) findViewById(R.id.btn_right);
	}
	
}
