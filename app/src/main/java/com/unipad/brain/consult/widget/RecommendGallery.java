package com.unipad.brain.consult.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;

import static android.support.v7.widget.ViewUtils.isLayoutRtl;

@SuppressWarnings("deprecation")
public class RecommendGallery extends Gallery implements OnItemSelectedListener{
    private static final int DELAY_TIME = 3000;
    private static final int SELECTE_MSG = 1;
    private RecommendPot mRecommendPot;
    private ViewPager mViewPager;
    private float startX;


    public RecommendGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnItemSelectedListener(this);
    }

    public void initSelectePoint(RecommendPot recommendPot) {
        mRecommendPot = recommendPot;
    }
    
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        if(null != mRecommendPot){
            mRecommendPot.setIndicatorChildCount(getCount());
            mRecommendPot.setCurrentScreen(position);
        }
     
        mUIHander.removeMessages(SELECTE_MSG);
        mUIHander.sendEmptyMessageDelayed(SELECTE_MSG, DELAY_TIME);
    }
    
    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        mUIHander.removeMessages(SELECTE_MSG);
        super.onDetachedFromWindow();
    }
/* DTS2014072211265   qiuhongwei/00227094 20140722 begin */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return  super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // the first time ,viewpager send motionEvent to gallery,gallery consume
        // this event, and request motionevent next.
        if (mViewPager != null) {
            mViewPager.requestDisallowInterceptTouchEvent(true);

        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        /*super.onFling(e1, e2, velocityX, velocityY);
        int count = getCount();
        if(e1.getX() - e2.getX() > FLIP_DISTANCE) {
            if(getSelectedItemPosition() == count -1) {
                setSelection(0);
            }
        }
        
        if(e2.getX() - e1.getX() > FLIP_DISTANCE) {
            if(getSelectedItemPosition() == 0) {
                setSelection(count -1);
            }
        }
        return true;*/
       /** if(e1.getX() - e2.getX() > 30) {
            if (velocityX < 0) { 
                    super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null); 
                 } else { 
                     super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null); 
                 } 
        } else {
            if (velocityX > 0) { 
                    super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null); 
                 } else { 
                     super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null); 
                 } 
        }
        */
        //do not slip circularly,because it is bad performance.
//        e1 = startEvent;+v
        int count = getCount();
        int position = getSelectedItemPosition();
Log.d("onfiling",  "运行onfiling  滑动事件");


        if(e1.getX() > e2.getX()) {
           if(position == count -1) {
               position = 0;

            } else {
               position++;
           }
        }

        if(e2.getX() > e1.getX() ) {
            if(position == 0) {
                position = count -1;
            }else {
                position--;
            }
        }
        setSelection(position);
     return true;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:   //按下的时候
                //按下 停止播放
                startX = event.getX();
                mUIHander.removeMessages(SELECTE_MSG);

            case MotionEvent.ACTION_MOVE:


                Log.d("gallery", "移动 " + event.getX() + "==={" +startX   );
                break;

            case MotionEvent.ACTION_UP:  //松开

                mUIHander.sendEmptyMessageDelayed(SELECTE_MSG, DELAY_TIME);
                break;
        }

        return true;
    }

    private Handler mUIHander = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case SELECTE_MSG:
                int position = getSelectedItemPosition();
                if (position + 1 >= getCount()) {
                    position = 0;
                } else{
                    position += 1;
                }
                
//                setSelection(position,true);//alway crash.
                setSelection(position);
                break;
            default:
                break;
            }
        };
    };
    
    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }
}
