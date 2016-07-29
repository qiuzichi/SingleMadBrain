package com.unipad.brain;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.unipad.brain.R;

/**
 * Created by gongkan on 2016/7/27.
 */
public class ImageRadioButton extends RadioButton{
    private int mDrawableWith;

    private int mDrawableHeight;

    public ImageRadioButton(Context context) {
        super(context);
    }

    public ImageRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ImageRadioButton);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.ImageRadioButton_drawableHeight:
                    mDrawableHeight = a.getDimensionPixelSize(R.styleable.ImageRadioButton_drawableHeight, 35);
                    break;
                case R.styleable.ImageRadioButton_drawableWith:
                    mDrawableWith = a.getDimensionPixelSize(R.styleable.ImageRadioButton_drawableWith, 30);
                break;
                case R.styleable.ImageRadioButton_drawableTop:
                    drawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.ImageRadioButton_drawableBottom:
                    drawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.ImageRadioButton_drawableRight:
                    drawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.ImageRadioButton_drawableLeft:
                    drawableLeft = a.getDrawable(attr);
                    break;
                default :
                    break;
            }
        }
        a.recycle();

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableWith, mDrawableHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableWith, mDrawableHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableWith, mDrawableHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableWith, mDrawableHeight);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
    public ImageRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
}
