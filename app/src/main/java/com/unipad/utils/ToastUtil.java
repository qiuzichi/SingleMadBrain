package com.unipad.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.home.bean.RuleGame;
import com.unipad.common.widget.HIDDialog;

public class ToastUtil {

    private static Toast toast;

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), text,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void showToast(int textId) {
        String text = App.getContext().getResources().getString(textId);
        showToast(text);
    }

    public static HIDDialog createOnlyConfigDlg(Context mContext, String key, View.OnClickListener listener) {
        final HIDDialog dialog = new HIDDialog(mContext, key);
        dialog.setContentView(HIDDialog.ENUM_DIALOG_VIEW.ONE_BUTTON_VIEW);
        dialog.setText("");
        dialog.setFirstBTListener(listener);
        return dialog;
    }

    /**
     * 创建等待对话框
     *
     * @param mContext
     * @param message  显示信息
     * @return HIDDialog
     */
    public static HIDDialog createWaitingDlg(Context mContext, String message, String id) {
        HIDDialog WaitingDialog = new HIDDialog(mContext, R.style.dialog_wait, id);
        if (null == message) {
            message = mContext.getString(R.string.ui_please_wait);
        }

        WaitingDialog.setContentView(R.layout.ui_waiting,
                (int) mContext.getResources().getDimension(R.dimen.wait_dialog_width), (int) mContext.getResources()
                        .getDimension(R.dimen.wait_dialog_height));

        // 必须放在设置 view之后,自定义的view是不能设置该方法的，注释掉
        ((TextView) WaitingDialog.findViewById(R.id.dialog_text)).setText(message);
        Window win = WaitingDialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.CENTER_VERTICAL);

        win.setAttributes(lp);
        // HLog.v(TAG, "~~~~~~~~create waite dialog...the dialog id:" + id + "~~~the message:" + message);
        return WaitingDialog;
    }

    public static HIDDialog createRuleDialog(Context mContext, String id,RuleGame rule)
    {
        HIDDialog.dismissDialog(id);
        HIDDialog WaitingDialog = new HIDDialog(mContext, R.style.dialog_wait, id);
        WaitingDialog.setContentView(HIDDialog.ENUM_DIALOG_VIEW.NO_BUTTON_VIEW,
                (int) mContext.getResources().getDimension(R.dimen.wait_dialog_width),
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ((TextView) WaitingDialog.findViewById(R.id.dialog_text)).setText(rule.getMemeryTip());
        Window win = WaitingDialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();

        lp.y = lp.y + mContext.getResources().getDimensionPixelOffset(R.dimen.toast_gravity_yoffset);
        win.setAttributes(lp);
        return WaitingDialog;
    }
}
