package com.unipad.brain.quickPoker.view.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.quickPoker.entity.ChannelItem;
import com.unipad.brain.quickPoker.entity.PokerEntity;
import com.unipad.common.Constant;
import com.unipad.utils.LogUtil;


public class QuickPokerBrowseHorizontalView extends HorizontalScrollView
		implements App.HandlerCallback {
	private static final String TAG = QuickPokerBrowseHorizontalView.class
			.getSimpleName();
	private static final int MSG_LAYOUT_POKER = 101;
	private Context mContext;
	private App.AppHandler mHandler = new App.AppHandler(this);
	/** 扑克牌所在的父布局 */
	private RelativeLayout mPokerLayout;
	/** 临时的View，用于计算扑克牌的宽度 */
	private ImageView mTempPokerIV;
	private float mScaleFactor = 1.0f;
	private ScaleGestureDetector mScaleGestureDetector;
	private boolean isFirst = true;

	public QuickPokerBrowseHorizontalView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		mScaleGestureDetector = new ScaleGestureDetector(context,
				new ScaleListener());
	}

	public QuickPokerBrowseHorizontalView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mScaleGestureDetector = new ScaleGestureDetector(context,
				new ScaleListener());
		this.initView(context);

	}

	private void initView(Context context) {
		mContext = context;
		mPokerLayout = new RelativeLayout(context);
		addView(mPokerLayout);

		mTempPokerIV = new ImageView(context);
		RelativeLayout.LayoutParams pokerLayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mTempPokerIV.setLayoutParams(pokerLayoutParams);
		mTempPokerIV.setVisibility(View.INVISIBLE);
		mTempPokerIV.setImageResource(R.drawable.poker_fangkuai_01);

		mPokerLayout.addView(mTempPokerIV);

		post(new Runnable() {
			@Override
			public void run() {
				int pokerWidth = mTempPokerIV.getWidth();

				PokerEntity.getInstance().setPokerWith(pokerWidth);
				Message msg = mHandler.obtainMessage();
				msg.arg1 = pokerWidth;
				mHandler.sendEmptyMessage(MSG_LAYOUT_POKER);
			}
		});

	}




	@Override
	public void dispatchMessage(Message msg) {
		switch (msg.what) {
		case MSG_LAYOUT_POKER:
			mPokerLayout.removeView(mTempPokerIV);// 先移除临时的View

			this.layoutPokerView(msg.arg1);
			break;
		default:
			break;
		}
	}

	private void layoutPokerView(int pokerWidth) {
// 用于排布扑克后，保存扑克的顺序

		int availableWidth = (int) (App.screenWidth * Constant.RIGHT_RATIO)
				- getPaddingLeft() - getPaddingRight();
		int basicMargin = (availableWidth - pokerWidth) / 20;

		int margin = 0;
		ImageView pokerImage = null;
		RelativeLayout.LayoutParams pokerLayoutParams = null;
		for (int i = 0; i < PokerEntity.pairNums * PokerEntity.pairs; i++) {

			pokerImage = new ImageView(mContext);
			pokerLayoutParams = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			pokerImage.setLayoutParams(pokerLayoutParams);
			pokerLayoutParams.leftMargin = margin;
			margin += basicMargin;
			pokerImage.setImageResource(R.drawable.bg_poker);
			mPokerLayout.addView(pokerImage);
		}


	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (isFirst) {
			super.onLayout(changed, l, t, r, b);
			return;
		}
		RelativeLayout view = (RelativeLayout) getChildAt(0);
		LayoutParams firstChildParams = (LayoutParams) view
				.getLayoutParams();
		view.layout((int) (firstChildParams.leftMargin * mScaleFactor),
				(int) (firstChildParams.topMargin * mScaleFactor),
				(int) ((firstChildParams.leftMargin + view.getMeasuredWidth()
						* mScaleFactor)),
				(int) ((firstChildParams.topMargin + view.getMeasuredHeight()
						* mScaleFactor)));
		int count = view.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = view.getChildAt(i);
			if (child.getVisibility() != GONE) {
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) child
						.getLayoutParams();
				child.layout(
						(int) (params.leftMargin * mScaleFactor),
						(int) (params.topMargin * mScaleFactor),
						(int) ((params.leftMargin + child.getMeasuredWidth()) * mScaleFactor),
						(int) ((params.topMargin + child.getMeasuredHeight()) * mScaleFactor));
			}
		}

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor();
			// Apply limits to the zoom scale factor:
			mScaleFactor = Math.max(0.6f, Math.min(mScaleFactor, 1.5f));
			invalidate();
			requestLayout();
			// scrollTo(x, y)
			return true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		isFirst = false;
		mScaleGestureDetector.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}

	/**
	 * 展示扑克牌面
	 */
	public void showPokerFace() {
		try {
			ImageView imageView = null;
			ArrayList<ChannelItem> pokerSortArray = PokerEntity.getInstance()
					.getPokerSortArray();
			if (pokerSortArray != null
					&& pokerSortArray.size() == PokerEntity.pairNums) {
				for (int index = 0; index < PokerEntity.pairNums; index++) {
					imageView = (ImageView) mPokerLayout.getChildAt(index);
					imageView.setImageDrawable(null);
					imageView.setImageResource(pokerSortArray.get(index).resId);
				}
			}
			scrollTo(0,0);
		} catch (Exception e) {
			LogUtil.e(TAG, e.toString());
		}
	}

}
