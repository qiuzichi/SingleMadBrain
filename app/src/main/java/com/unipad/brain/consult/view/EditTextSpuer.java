package com.unipad.brain.consult.view;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by hasee on 2016/7/11.
 */
public class EditTextSpuer extends EditText {
    public EditTextSpuer(Context context) {
        super(context);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER ) {
            Log.v("xpf", "--------------------+onKeyDown---------enter");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public boolean onKeyPreIme (int keyCode, KeyEvent event){

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
            //调用了fragment中的静态方法，关闭mpopupWindow和键盘，这里最为关键。
//            IntroductionFragment.hiddenpopupWindow();
            Log.v("xpf","--------------------+onKeyPreIme---------back");
            return true;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
