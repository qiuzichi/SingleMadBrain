package com.unipad.singlebrain.number.view;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.unipad.singlebrain.R;

public class KeyboardDialog extends Dialog implements View.OnClickListener {
    private KeyboardClickListener mKeyboardClickListener;
    private static SparseArray<String> mNumberArray = new SparseArray<>();

    static {
        mNumberArray.put(R.id.wbj_keyboard_0, "0".trim());
        mNumberArray.put(R.id.wbj_keyboard_1, "1".trim());
        mNumberArray.put(R.id.wbj_keyboard_2, "2".trim());
        mNumberArray.put(R.id.wbj_keyboard_3, "3".trim());
        mNumberArray.put(R.id.wbj_keyboard_4, "4".trim());
        mNumberArray.put(R.id.wbj_keyboard_5, "5".trim());
        mNumberArray.put(R.id.wbj_keyboard_6, "6".trim());
        mNumberArray.put(R.id.wbj_keyboard_7, "7".trim());
        mNumberArray.put(R.id.wbj_keyboard_8, "8".trim());
        mNumberArray.put(R.id.wbj_keyboard_9, "9".trim());
    }

    public KeyboardDialog(Context context) {
        super(context, R.style.DialogStyle);
        View dialogView = View.inflate(context, R.layout.keyboard_dialog, null);
        setContentView(dialogView);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.type = WindowManager.LayoutParams.TYPE_TOAST;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 0.7f;
        lp.x = 10;
        lp.y = 10;
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        initViews(dialogView);
    }

    private void initViews(View view) {
        for (int id = R.id.wbj_keyboard_1; id <= R.id.wbj_keyboard_right; ++id) {
            view.findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (mKeyboardClickListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.wbj_keyboard_0:
            case R.id.wbj_keyboard_1:
            case R.id.wbj_keyboard_2:
            case R.id.wbj_keyboard_3:
            case R.id.wbj_keyboard_4:
            case R.id.wbj_keyboard_5:
            case R.id.wbj_keyboard_6:
            case R.id.wbj_keyboard_7:
            case R.id.wbj_keyboard_8:
            case R.id.wbj_keyboard_9:
                mKeyboardClickListener.numberKey(mNumberArray.get(v.getId()));
                break;
            case R.id.wbj_keyboard_up:
                mKeyboardClickListener.upKey();
                break;
            case R.id.wbj_keyboard_down:
                mKeyboardClickListener.downKey();
                break;
            case R.id.wbj_keyboard_left:
                mKeyboardClickListener.leftKey();
                break;
            case R.id.wbj_keyboard_right:
                mKeyboardClickListener.rightKey();

                break;
            case R.id.wbj_keyboard_delete:
                mKeyboardClickListener.deleteKey();

                break;
            default:
                break;
        }
    }

    public void setKeyboardClickListener(KeyboardClickListener keyboardClickListener) {
        this.mKeyboardClickListener = keyboardClickListener;
    }

    public interface KeyboardClickListener {
        /**
         * 数字键
         *
         * @param keyValue 键值
         */
        void numberKey(String keyValue);

        /**
         * 上键
         */
        void upKey();

        /**
         * 下键
         */
        void downKey();

        /**
         * 左键
         */
        void leftKey();

        /**
         * 右键
         */
        void rightKey();

        /**
         * 删除键
         */
        void deleteKey();
    }

}
