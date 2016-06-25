package com.unipad.common.widget;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.utils.HidRelTimer;


/**
 * 通用对话框 功能： 1 去除头 2 增加定时关闭功能 3 屏蔽硬按键 4 自动关闭上一次弹出框 5 有固定对话框样式 6 打开永远显示对话框（下一个对话框打开时不关闭该对话框）
 * 
 * @author zKF26628
 * @since
 */
public class HIDDialog  extends Dialog
{
    /** The logging tag used by this class with com.huawei.hid.util.log.HLog. */
    private static final String TAG = "HIDDialog";

    /** Context context */
    protected Context mContext;

    /** 自动关闭弹出框定时器 */
    private DismissTimer timer;

    /** 对话框样式 */
    private ENUM_DIALOG_VIEW enumTypeView = null;

    /** 对话框View */
    private View viewDialog = null;

    /** 上一次正在显示的Dialog */
    private static HIDDialog lastDialog = null;

    /** 永远显示对话框的列表 */
    // private static LinkedList<HIDDialog> foreverDialogList = new
    // LinkedList<HIDDialog>();

    /** 对话框的map管理 */
    private static Map<String, HIDDialog> dlgList = new ConcurrentHashMap<String, HIDDialog>();

    /** 对话框的ID */
    protected String dialogId = "";

    /**
     * 对话框样式
     * 
     * <pre>
     * -------------------------------
     * | 标题                                              关闭   | 
     * |                             | 
     * |          内容文字                            | 
     * |                             | 
     * |     按钮                         按钮               |
     * |                             | 
     * -------------------------------
     * </pre>
     * 
     * @author zKF26628
     */
    public enum ENUM_DIALOG_VIEW {
        NO_BUTTON_VIEW(R.layout.dialog_view_0bt), ONE_BUTTON_VIEW(R.layout.dialog_view_1bt), TWO_BUTTON_VIEW(
                R.layout.dialog_view_2bt),THREE_BUTTON_VIEW(R.layout.dialog_view_3bt);

        /** 对话框样式 */
        private int viewRes;

        private ENUM_DIALOG_VIEW(int viewRes)
        {
            this.viewRes = viewRes;
        }
    }

    public void showColseButton(){
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        Button closeDlg = (Button) viewDialog.findViewById(R.id.dialog_close);
        closeDlg.setVisibility(View.VISIBLE);
        closeDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public HIDDialog(Context context, String ID)
    {
        super(context, R.style.dialog);
        mContext = context;
        dialogId = ID;
        setCanceledOnTouchOutside(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public HIDDialog(Context context, int theme, String ID)
    {
        super(context, theme);
        mContext = context;
        dialogId = ID;
        setCanceledOnTouchOutside(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    // ***********************提供给外部的方法 start******************************* //
    /**
     * 设置对话框内容的icon
     * @param resId
     */
    public void setContentIcon(int resId)
    {
        ((ImageView) findViewById(R.id.dlg_contenticon)).setBackgroundResource(resId);
    }

    /**
     * 设置对话框内容的icon
     * @param
     */
    @SuppressWarnings("unchecked")
    public void setContentIcon(Drawable drawable)
    {
        ((ImageView) findViewById(R.id.dlg_contenticon)).setBackground(drawable);
    }

    /**
     * 设置关闭按钮是否显示，默认为显示
     */
    public void setIsShowCloseBT(boolean isShow)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        int visibility;
        if (isShow)
        {
            visibility = View.VISIBLE;
        }
        else
        {
            visibility = View.GONE;
        }
    }

    /**
     * 设置标题文字
     */
    public void setTitle(String title)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        ((TextView) viewDialog.findViewById(R.id.dialog_title)).setText(title);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        ((TextView) viewDialog.findViewById(R.id.dialog_title)).setText(title);
    }

    @Override
    public void setTitle(int titleRes)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        ((TextView) viewDialog.findViewById(R.id.dialog_title)).setText(titleRes);
    }

    /**
     * 设置内容文字
     */
    public void setText(String text)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        viewDialog.findViewById(R.id.dialog_textview_scroll_area).setVisibility(View.VISIBLE);
        viewDialog.findViewById(R.id.dialog_content_area).setVisibility(View.GONE);
        ((TextView) viewDialog.findViewById(R.id.dialog_text)).setText(text);
    }

    /**
     * 设置内容View,在调用了setContentView设置对话框模式后才能调用 该View会展现在对话框的title和按钮的中间
     * @param view View
     */
    public void setContentWithEnumType(View view)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        viewDialog.findViewById(R.id.dialog_content_area).setVisibility(View.VISIBLE);
        viewDialog.findViewById(R.id.dialog_textview_scroll_area).setVisibility(View.GONE);
        ((LinearLayout) (viewDialog.findViewById(R.id.dialog_content_area))).removeAllViews();
        ((LinearLayout) (viewDialog.findViewById(R.id.dialog_content_area))).addView(view, new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

    /**
     * 设置对话框背景样式
     */
    public void setContentView(ENUM_DIALOG_VIEW enumTypeView)
    {
        viewDialog = getLayoutInflater().inflate(enumTypeView.viewRes, null);
        setContentView(viewDialog,
                new LayoutParams((int) mContext.getResources().getDimension(R.dimen.dialog_width),
                        (int) mContext.getResources().getDimension(R.dimen.dialog_height)));
        this.enumTypeView = enumTypeView;

    }

    /**
     * 设置对话框背景样式
     */
    public void setContentView(ENUM_DIALOG_VIEW enumTypeView, int width, int height)
    {
        viewDialog = getLayoutInflater().inflate(enumTypeView.viewRes, null);
        setContentView(viewDialog, new LayoutParams(width, height));
        this.enumTypeView = enumTypeView;

    }
    
    /**
     * 设置第一个按钮的监听事件， 该方法需要使用固定样式setContentView(ENUM_DIALOG_VIEW)才能使用
     */
    public void setThirdBTListener(View.OnClickListener listener)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            new IllegalStateException("This dialog style can't use this method!").printStackTrace();
            return;
        }
        switch (enumTypeView)
        {
            case NO_BUTTON_VIEW:
                new IllegalStateException("This dialog style can't use this method!").printStackTrace();
                break;
            case ONE_BUTTON_VIEW:
            case TWO_BUTTON_VIEW:
			    break;
            case THREE_BUTTON_VIEW:
                ((Button) viewDialog.findViewById(R.id.dialog_third_button)).setOnClickListener(listener);
                break;
        }
    }

    /**
     * 设置第三个按钮的监听事件， 该方法需要使用固定样式setContentView(ENUM_DIALOG_VIEW)才能使用
     */
    public void setFirstBTListener(View.OnClickListener listener)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        switch (enumTypeView)
        {
            case NO_BUTTON_VIEW:
                break;
            case ONE_BUTTON_VIEW:
            case TWO_BUTTON_VIEW:
            case THREE_BUTTON_VIEW:
                ((Button) viewDialog.findViewById(R.id.dialog_first_button)).setOnClickListener(listener);
                break;
        }
    }
    /** 设置第三个按钮字体大小 */
    public void setThirdBTTextSize(int textSize)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        if (enumTypeView == ENUM_DIALOG_VIEW.TWO_BUTTON_VIEW)
        {
            ((Button) viewDialog.findViewById(R.id.dialog_third_button)).setTextSize(textSize);
        }
    }
    
    /**
     * 设置第三个按钮的文字，默认为“取消” 该方法需要使用固定样式setContentView(ENUM_DIALOG_VIEW)才能使用
     */
    public void setThirdBTText(String text)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            new IllegalStateException("This dialog style can't use this method!").printStackTrace();
            return;
        }
        switch (enumTypeView)
        {
            case NO_BUTTON_VIEW:
            case ONE_BUTTON_VIEW:
                new IllegalStateException("This dialog style can't use this method!").printStackTrace();
                break;
            case TWO_BUTTON_VIEW:
                break;
            case THREE_BUTTON_VIEW:
                ((Button) viewDialog.findViewById(R.id.dialog_third_button)).setText(text);
                break;
        }
    }

    /** 设置第二个按钮字体大小 */
    public void setFirstBTTextSize(int textSize)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        if (enumTypeView == ENUM_DIALOG_VIEW.TWO_BUTTON_VIEW)
        {
            ((Button) viewDialog.findViewById(R.id.dialog_first_button)).setTextSize(textSize);
        }
    }

    /**
     * 设置第二个按钮的监听事件 该方法需要使用固定样式setContentView(ENUM_DIALOG_VIEW)才能使用
     */
    public void setSecondBTListener(View.OnClickListener listener)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        switch (enumTypeView)
        {
            case NO_BUTTON_VIEW:
            case ONE_BUTTON_VIEW:
                break;
            case TWO_BUTTON_VIEW:
            case THREE_BUTTON_VIEW:
                ((Button) viewDialog.findViewById(R.id.dialog_second_button)).setOnClickListener(listener);
                break;
        }
    }

    /**
     * 设置第一个按钮的文字，默认为“确定” 该方法需要使用固定样式setContentView(ENUM_DIALOG_VIEW)才能使用
     */
    public void setFirstBTText(String text)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        switch (enumTypeView)
        {
            case NO_BUTTON_VIEW:
                break;
            case ONE_BUTTON_VIEW:
            case TWO_BUTTON_VIEW:
            case THREE_BUTTON_VIEW:
                ((Button) viewDialog.findViewById(R.id.dialog_first_button)).setText(text);
                break;

        }
    }

    /**
     * 设置第二个按钮的文字，默认为“取消” 该方法需要使用固定样式setContentView(ENUM_DIALOG_VIEW)才能使用
     */
    public void setSecondBTText(String text)
    {
        if (enumTypeView == null || viewDialog == null)
        {
            return;
        }
        switch (enumTypeView)
        {
            case NO_BUTTON_VIEW:
            case ONE_BUTTON_VIEW:
                break;
            case TWO_BUTTON_VIEW:
            case THREE_BUTTON_VIEW:
                ((Button) viewDialog.findViewById(R.id.dialog_second_button)).setText(text);
                break;
        }
    }

    /**
     * 显示对话框 如果正在显示则重新计时重新显示
     * 
     * @param second 弹出框将在time秒后自动消失
     */
    public void show(int second)
    {
        lastDialog = this;
        dlgList.put(dialogId, this);

        destroyTimer();
        timer = new DismissTimer("dlg_DismissTimer");
        timer.schedule(second);
        setCanceledOnTouchOutside(false);
        super.show();
    }

    /**
     * 显示对话框
     */
    @Override
    public void show()
    {
        lastDialog = this;
        dlgList.put(dialogId, this);
        setCanceledOnTouchOutside(false);
        super.show();
    }

    /**
     * 永远显示该对话框 在下一个对话框显示时不关闭该对话框 升级对话框需要使用
     */
    public void showForever()
    {
        dlgList.put(dialogId, this);
        setCanceledOnTouchOutside(false);
        super.show();
    }

    /**
     * 消失对话框
     */
    @Override
    public void dismiss()
    {
        lastDialog = null;
        destroyTimer();
        if (null != dlgList.get(dialogId) && dlgList.get(dialogId) == this)
        {
            dlgList.remove(dialogId);
        }
        super.dismiss();
    }

    private void dimissInList()
    {
        lastDialog = null;
        destroyTimer();
        super.dismiss();
    }

    /**
     * 删除正在显示的所有对话框
     */
    public synchronized static void dismissAll()
    {
        if (lastDialog != null)
        {
            lastDialog.dismiss();
        }

        Iterator<Entry<String, HIDDialog>> dlgIters = dlgList.entrySet().iterator();

        while (dlgIters.hasNext())
        {
            Entry<String, HIDDialog> entry = (Entry<String, HIDDialog>) dlgIters.next();
            HIDDialog dialog = entry.getValue();
            if (dialog != null)
            {
                dialog.dimissInList();
            }
        }
        dlgList.clear();
    }

    /**
     * dismiss 指定的对话框
     * @param id 对话框的id
     */
    public synchronized static void dismissDialog(String id)
    {
        HIDDialog dialog = dlgList.get(id);
        if (null != dialog)
        {
            try
            {
                dialog.dismiss();

            }
            catch (Exception e)
            {
            }
        }
        else
        {
        }
    }

    /**
     * dismiss 指定的对话框
     * @param id 对话框的id
     * @param inSecends 等待时间
     */
    public synchronized static void dismissDialogAfterSecends(final String id, int inSecends)
    {
        if (!isDialogExist(id))
        {
            return;
        }
        if (inSecends == 0)
        {
            dismissDialog(id);
            return;
        }

        AppContext.instance().globleHandle.postDelayed(new Runnable() {

            @Override
            public void run() {
                HIDDialog dialog = dlgList.get(id);
                if (null != dialog) {
                    try {
                        dialog.dismiss();

                    } catch (Exception e) {
                        //HLog.e(TAG, e);
                    }
                } else {
                    // HLog.v(TAG, "dismiss dialog is not exist:" + id);
                }
            }
        }, inSecends * 1000);
    }

    /**
     * 判断指定的对话框是否存在
     * @param id 对话框的id
     */
    public synchronized static boolean isDialogExist(String id)
    {
        HIDDialog dialog = dlgList.get(id);
        if (null != dialog)
        {
            return true;
        }
        return false;
    }

    public synchronized static HIDDialog getExistDialog(String id)
    {
        return dlgList.get(id);
    }
    // /////////////////////////提供给外部的方法 end////////////////////////////////

    /**
     * 点击事件，屏蔽了硬按键事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
        // 按键为back
            case KeyEvent.KEYCODE_BACK:
                // 屏蔽call
            case KeyEvent.KEYCODE_CALL:
            case KeyEvent.KEYCODE_ENDCALL:
                // 屏蔽方向键
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event)
    {
        return true;
    }

    @Override
    public void onDetachedFromWindow()
    {
        lastDialog = null;
        destroyTimer();
        super.onDetachedFromWindow();
    }

    /**
     * 销毁定时器
     */
    public void destroyTimer()
    {
        if (timer != null)
        {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    /**
     * 弹出框定时消失计时器
     */
    private class DismissTimer extends HidRelTimer
    {
        /**
         * @param name
         */
        public DismissTimer(String name)
        {
            super(name);
        }

        private void schedule(int second)
        {
            schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    HIDDialog.this.dismiss();
                }
            }, second * 1000);
        }
    }

    /**
     * Set the screen content from a layout resource. The resource will be inflated, adding all top-level views to the
     * screen.
     * 
     * @param layoutResID Resource ID to be inflated.
     */
    public void setContentView(int layoutResID, int width, int height)
    {

        viewDialog = getLayoutInflater().inflate(layoutResID, null);
        setContentView(viewDialog, new LayoutParams(width, height));
    }

    /**
     * Set the screen content to an explicit view. This view is placed directly into the screen's view hierarchy. It can
     * itself be a complex view hierarhcy.
     * 
     * @param view The desired content to display.
     */
    public void setContentView(View view)
    {
        setContentView(viewDialog,
                new LayoutParams((int) mContext.getResources().getDimension(R.dimen.dialog_width),
                        (int) mContext.getResources().getDimension(R.dimen.dialog_height)));
    }

    /**
     * Set the screen content to an explicit view. This view is placed directly into the screen's view hierarchy. It can
     * itself be a complex view hierarhcy.
     * 
     * @param view The desired content to display.
     * @param params Layout parameters for the view.
     */
    public void setContentView(View view, LayoutParams params)
    {
        viewDialog = view;
        super.setContentView(viewDialog, params);
        enumTypeView = null;
    }

    /**
     * 屏幕明暗控制
     * @param brightness
     */
    public void setScreenBrightness(int brightness)
    {
       // HLog.v(TAG, "HIDDialog setScreenBrightness:" + brightness);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = (float) (brightness / 255.0);
        getWindow().setAttributes(lp);
    }

    /**
     * 重新设置对话框的ID，在对话框Show之前显示，对话框显示后不生效。
     * @param id
     */
    public void setDialogID(String id)
    {
        dialogId = id;
    }
}
