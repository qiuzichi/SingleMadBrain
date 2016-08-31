package com.unipad.singlebrain.personal.view;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

import com.unipad.singlebrain.R;

/**
 * 折线图
 */
public class BrokenLineView extends LinearLayout {
    private static final String TAG = BrokenLineView.class.getSimpleName();
    /**
     * 折线图(不包括Y轴刻度线和柱体名称)的顶边距
     */
    private static final int HV_MARGIN_TOP = 100;
    /**
     * 折线图(不包括Y轴刻度线和柱体名称)的底边距
     */
    private static final int HV_MARGIN_BOTTOM = HV_MARGIN_TOP;
    /**
     * 折线图(不包括左边标题)的左边距
     */
    private static final int HV_MARGIN_LEFT = 96;
    /**
     * 刻度分隔线的长度
     */
    private static final int SCALE_DIVIDE_LINE_LENGTH = 15;
    /**
     * 字体大小
     */
    private static final int PAINT_TEXT_SIZE = 22;
    /**
     * Y轴的最大刻度
     */
    private int mYPivotMaxScale = 100;
    /**
     * Y轴的刻度数量
     */
    private int mYPivotScaleNum = 10;
    /**
     * Y轴坐标刻度刻度之间的间隔
     */
    private float mYPivotScalesMargin;
    /**
     * Y轴的高度
     */
    private int mYPivotHeight;
    /**
     * Y轴所在布局的高度
     */
    private int mHorizontalLayoutHeight;
    /**
     * mHistogramViewWidth：HistogramView的宽度；mHistogramViewHeight：
     * HistogramView的高度
     */
    private int mHistogramViewWidth, mHistogramViewHeight;
    /**
     * 柱状图的右边距
     */
    private int mHvMarginRight = 30;
    /**
     * x轴每一格的宽度，也就是网格的宽度，默认是100
     */
    private int mGridWidth = 60;
    private Resources mResources;
    private Canvas mLeftCancas;
    private Bitmap mBitmapLeftPart, mBitmapGridHori, mBitmapHistogram;
    /**
     * 占位：负责显示左边刻度
     */
    private ImageView mIVShowLeftPart;
    /**
     * 占位：负责显示折线图网格横向线和背景
     */
    private ImageView mIVShowGridHori;
    /**
     * 占位：负责显示折线图网格纵向线和柱体
     */
    private ImageView mIVShowGridPoti;
    private LinearLayout mHorizontalLayout;
    private RelativeLayout histogramScrollContainer;
    private ArrayList<String> mHistogramEntityList;

    private Paint mPaintPivotLine, mPaintGridLine;
    private Paint mPaintRect, mPaintBrokenLine;
    private Paint mPaintTextPivot, mPaintTextCity;

    /**
     * @param context             上下文对象
     * @param histogramEntityList 柱体属性实体的集合
     */
    public BrokenLineView(Context context, ArrayList<String> histogramEntityList) {
        super(context);
        initView(context);
        mHistogramEntityList = histogramEntityList;

        mPaintPivotLine = this.getPaint(R.color.paint_pivot, 3.0f);
        mPaintGridLine = this.getPaint(R.color.histogram_view_grid_line, 1.0f);
        mPaintTextPivot = this.getPaint(R.color.black, PAINT_TEXT_SIZE);
        mPaintTextCity = this.getPaint(R.color.record_city, PAINT_TEXT_SIZE);
        mPaintRect = this.getPaint(R.color.record_city);
        mPaintBrokenLine = this.getPaint(R.color.record_city, 2.0f);
    }

    /**
     * @param paintColor 画笔颜色
     * @return Paint对象
     */
    private Paint getPaint(int paintColor) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);// 抗(不显示)锯齿，让绘出来的物体更清晰
        paint.setColor(mResources.getColor(paintColor));// 设置画笔的颜色
        return paint;
    }

    /**
     * @param paintColor 画笔颜色
     * @param width      画笔宽度
     * @return Paint对象
     */
    private Paint getPaint(int paintColor, float width) {
        Paint paint = this.getPaint(paintColor);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(width);
        return paint;
    }

    /**
     * @param paintColor 画笔颜色
     * @param textSize   字体大小
     * @return Paint对象
     */
    private Paint getPaint(int paintColor, int textSize) {
        Paint paint = this.getPaint(paintColor);
        paint.setTextSize(textSize);
        return paint;
    }

    public BrokenLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(Context context) {
        mResources = context.getResources();
        setOrientation(VERTICAL);
        setBackgroundColor(mResources.getColor(R.color.hv_container_bg));

        mHorizontalLayout = new LinearLayout(context);
        mHorizontalLayout.setOrientation(HORIZONTAL);

        mIVShowLeftPart = new ImageView(context);
        mIVShowLeftPart.setLayoutParams(new LayoutParams(HV_MARGIN_LEFT,
                LayoutParams.MATCH_PARENT));
        mHorizontalLayout.addView(mIVShowLeftPart);

        histogramScrollContainer = new RelativeLayout(context);

        mIVShowGridHori = new ImageView(context);
        mIVShowGridHori.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mIVShowGridHori.setBackgroundColor(Color.WHITE);// 设置网格的背景为白色
        histogramScrollContainer.addView(mIVShowGridHori);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);

        mIVShowGridPoti = new ImageView(context);
        mIVShowGridPoti.setLayoutParams(params);
        mIVShowGridPoti.setScaleType(ScaleType.MATRIX);

        // 定义一个水平滚动控件用于包裹各个柱子
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        horizontalScrollView.setHorizontalScrollBarEnabled(true);
        horizontalScrollView.setLayoutParams(params);
        horizontalScrollView.addView(mIVShowGridPoti);// 把mIVShowGridPoti加入水平滚动控件中

        histogramScrollContainer.addView(horizontalScrollView);// 把水平滚动控件加入histogramScrollContainer中
        mHorizontalLayout.addView(histogramScrollContainer);

        addView(mHorizontalLayout);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
        Log.i(TAG, "HistogramView-->dispatchDraw");
        if (mHistogramViewWidth == 0) {
            mHistogramViewWidth = this.getWidth();
            mHistogramViewHeight = this.getHeight();
            Log.i(TAG, "mHistogramViewWidth = " + mHistogramViewWidth
                    + ", mHistogramViewHeight = " + mHistogramViewHeight);
            mHorizontalLayoutHeight = mHistogramViewHeight - HV_MARGIN_TOP;
            mYPivotHeight = mHorizontalLayoutHeight - HV_MARGIN_BOTTOM;
            mHorizontalLayout.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, mHorizontalLayoutHeight));
            histogramScrollContainer.setLayoutParams(new LayoutParams(
                    mHistogramViewWidth - HV_MARGIN_LEFT - mHvMarginRight,
                    mHorizontalLayoutHeight));
            this.drawHistogramView();
        }
    }

    private void drawHistogramView() {
        // 绘制左边标题
        mBitmapLeftPart = Bitmap.createBitmap(HV_MARGIN_LEFT,
                mHorizontalLayoutHeight, Bitmap.Config.ARGB_8888);
        mIVShowLeftPart.setImageBitmap(mBitmapLeftPart);
        mLeftCancas = new Canvas();
        mLeftCancas.setBitmap(mBitmapLeftPart);

        // 绘制白色背景
        mBitmapGridHori = Bitmap.createBitmap(mHistogramViewWidth
                - HV_MARGIN_LEFT - mHvMarginRight, mHorizontalLayoutHeight
                - HV_MARGIN_BOTTOM, Bitmap.Config.ARGB_8888);
        mIVShowGridHori.setImageBitmap(mBitmapGridHori);

        // 绘制Y轴刻度
        Canvas horizontalGridLineCanvas = new Canvas();
        horizontalGridLineCanvas.setBitmap(mBitmapGridHori);
        int yScaleValue = mYPivotMaxScale / mYPivotScaleNum;// 算出刻度值
        mYPivotScalesMargin = mYPivotHeight / mYPivotMaxScale;
        for (int scale = 0; scale <= mYPivotMaxScale; scale += yScaleValue) {
            // 减一是为了让最下面一根的横向网格线显示并与零刻度对齐(①Ⅰ)
            float startY = mYPivotHeight - scale * mYPivotScalesMargin - 1;

            // 绘制Y轴刻度值
            String scaleValue = String.valueOf(scale);
            float textWidth = mPaintTextPivot.measureText(scaleValue) + 2;
            mLeftCancas.drawText(scaleValue, HV_MARGIN_LEFT
                    - SCALE_DIVIDE_LINE_LENGTH - textWidth + 8, startY + 8, mPaintTextPivot);

            // 绘制中间的横向网格线
            horizontalGridLineCanvas.drawLine(0, startY,
                    mHistogramViewWidth - HV_MARGIN_LEFT - mHvMarginRight,
                    startY, mPaintGridLine);
        }

        // 绘制最下面一根的横向网格线
        horizontalGridLineCanvas.drawLine(0, mYPivotHeight,
                mHistogramViewWidth - HV_MARGIN_LEFT - mHvMarginRight,
                mYPivotHeight, mPaintPivotLine);

        // 绘制Y轴刻度线
        mLeftCancas.drawLine(HV_MARGIN_LEFT, 0, HV_MARGIN_LEFT, mYPivotHeight, mPaintPivotLine);

        this.drawBrokenLine();
    }

    /**
     * 绘制折线
     */
    private void drawBrokenLine() {
        if (mHistogramEntityList == null || mHistogramEntityList.size() <= 0) {
            return;
        }

        int listSize = mHistogramEntityList.size();
        int xPivotLen = listSize * mGridWidth > mHistogramViewWidth ? listSize
                * mGridWidth + mGridWidth : mHistogramViewWidth;
        mBitmapHistogram = Bitmap.createBitmap(xPivotLen,
                mHorizontalLayoutHeight, Bitmap.Config.ARGB_8888);
        Canvas histogramCanvas = new Canvas();
        histogramCanvas.setBitmap(mBitmapHistogram);

        Path path = new Path();
        boolean moveTo = true;
        for (int i = 0; i < listSize; i++) {
            // 绘制柱体;(mYPivotHeight / mYPivotMaxScale)为单位刻度值占有多少的高度
            float startX = i * mGridWidth;
            float startY = (mYPivotHeight - mYPivotHeight / mYPivotMaxScale
                    * new Random().nextInt(101)) - 1;// 减1是因为(①Ⅰ)处减了1

            // 绘制纵向网格线，不包括最左边的Y轴刻度线
            histogramCanvas.drawLine(startX, mYPivotHeight, startX, 0, mPaintGridLine);


            // 绘制折线上的转折点(小正方形)
            histogramCanvas.drawRect(startX - 5, startY - 5, startX + 5, startY + 5,
                    mPaintRect);
            // 设置折线路径走向
            if (moveTo) {
                path.moveTo(startX, startY);
                moveTo = false;
            } else {
                path.lineTo(startX, startY);
            }

            // 绘制柱体上的数值文字
            String histogramValue = String.valueOf(mHistogramEntityList.get(i));
            histogramCanvas.drawText(histogramValue, startX + 2, startY, mPaintTextCity);

            // 绘制柱体名称
            String histogramName = mHistogramEntityList.get(i);
            if (!TextUtils.isEmpty(histogramName)) {
                float textWidth = mPaintTextPivot.measureText(histogramName);
                float y = startX - textWidth / 2;
                if (i == 0) {
                    y += 16;
                }
                // 沿着路径画字
                Path textPath = new Path();
                textPath.moveTo(y + 24, mYPivotHeight + 70);
                textPath.lineTo(y + textWidth, mYPivotHeight + 10);
                mPaintTextPivot.getTextBounds(histogramName, 0, histogramName.length(), new Rect());
                histogramCanvas.drawTextOnPath(histogramName, textPath, 0, 0, mPaintTextPivot);
            }
        }

        histogramCanvas.drawPath(path, mPaintBrokenLine);// 绘制折线图

        mIVShowGridPoti.setImageBitmap(mBitmapHistogram);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseResource(mBitmapLeftPart);
        releaseResource(mBitmapGridHori);
        releaseResource(mBitmapHistogram);

        mLeftCancas = null;
    }

    private void releaseResource(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 当数据改变时，调用此方法刷新柱状图
     */
    public void refreshHistogramView() {
        this.drawBrokenLine();
    }

}
