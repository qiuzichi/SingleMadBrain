package com.unipad.brain.quickPoker.view;

import java.util.ArrayList;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.quickPoker.adapter.DragAdapter;
import com.unipad.brain.quickPoker.adapter.OtherAdapter;
import com.unipad.brain.quickPoker.dao.QuickCardService;
import com.unipad.brain.quickPoker.entity.ChannelItem;
import com.unipad.brain.quickPoker.entity.PokerEntity;
import com.unipad.brain.quickPoker.view.widget.DragGrid;
import com.unipad.brain.quickPoker.view.widget.OtherGridView;
import com.unipad.brain.quickPoker.view.widget.QuickPokerBrowseHorizontalView;
import com.unipad.brain.quickPoker.view.widget.QuickPokerBrowseVerticalView;
import com.unipad.common.BasicCommonFragment;
import com.unipad.common.Constant;
import com.unipad.common.widget.HIDDialog;
import com.unipad.utils.LogUtil;
import com.unipad.utils.ToastUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class QuickPokerRightFragment extends BasicCommonFragment implements
        View.OnClickListener, OnItemClickListener {
    private static final String TAG = QuickPokerRightFragment.class
            .getSimpleName();

    private ImageButton mIBtnBrowseMode;
    private View mRightLayout, mHorizontalLayout, mBrowseLayout;
    private ViewStub mStubMutiBrowse, mStubAnswer, mStubAnswerShade;
    private QuickPokerBrowseHorizontalView mSingleLineLayout;// 单行浏览模式
    private QuickPokerBrowseVerticalView mMUtiLineLayout;// 多行浏览模式
    /**
     * 是否处于单行浏览模式，默认为true
     */
    private boolean isSingleLineBrowse = true;
    /**
     * 是否处已经显示过扑克牌正面，默认为false
     */
    private boolean isSingleLineShowPokerFace, isMutiLineShowedPokerFace;
    /**
     * 是否处已经开始处于记忆模式
     */
    private boolean isInMemoryMode;

    /**
     * 顶部GRIDVIEW
     */
    private DragGrid userGridView;
    /**
     * 底部GRIDVIEW
     */
    private OtherGridView otherGridView;
    /**
     * 顶部GRIDVIEW对应的适配器，可以拖动
     */
    DragAdapter userAdapter;
    /**
     * 底部GRIDVIEW对应的适配器
     */
    OtherAdapter otherAdapter;
    /**
     * 顶部扑克牌列表
     */
    ArrayList<ChannelItem> userChannelList;
    /**
     * 底部扑克牌列表
     */
    ArrayList<ChannelItem> otherChannelList;
    /**
     * 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。
     */
    boolean isMove = false;
    private TextView leftCards;
    private QuickCardService service;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.e(TAG, "******************onActivityCreated");
        mIBtnBrowseMode = (ImageButton) mViewParent
                .findViewById(R.id.ibtn_browse_mode);
        mIBtnBrowseMode.setOnClickListener(this);
        mRightLayout = mViewParent.findViewById(R.id.right_layout);
        mHorizontalLayout = mViewParent.findViewById(R.id.browse_proker_hlayout);
        mViewParent = (RelativeLayout) mViewParent.findViewById(R.id.parent_layout);
        mBrowseLayout = mViewParent.findViewById(R.id.browse_layout);
        mStubMutiBrowse = (ViewStub) mViewParent
                .findViewById(R.id.browse_proker_muti_stub);
        mStubAnswer = (ViewStub) mViewParent.findViewById(R.id.answer_layout);
        mStubAnswerShade = (ViewStub) mViewParent.findViewById(R.id.view_shade_answer);
        mSingleLineLayout = (QuickPokerBrowseHorizontalView) mViewParent
                .findViewById(R.id.browse_proker_single_mode);
        service = (QuickCardService) AppContext.instance().getGameServiceByProject(Constant.GAME_QUICKIY_POCKER);
    }

    @Override
    public int getLayoutId() {
        return R.layout.quick_poker_frg_right;
    }





    /**
     * 进入记忆模式
     */
    public void inMemoryMode() {
        isInMemoryMode = true;

        if (isSingleLineBrowse) {
            mSingleLineLayout.showPokerFace();
            isSingleLineShowPokerFace = true;
        } else {
            mMUtiLineLayout.showPokerFace();
            isMutiLineShowedPokerFace = true;
        }
    }

    /**
     * 进入回忆模式：答题
     */
    private void inAnswerMode() {
        try {
            mViewParent.removeView(mBrowseLayout);// 先移除浏览界面的视图

            this.layoutAnswerView();
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString(), new Exception());
        }
    }

    /**
     * 初始化数据
     */
    private void layoutAnswerView() {
        View viewAnswer = mStubAnswer.inflate();
        userGridView = (DragGrid) viewAnswer.findViewById(R.id.userGridView);
        otherGridView = (OtherGridView) viewAnswer
                .findViewById(R.id.otherGridView);
        leftCards = (TextView) viewAnswer.findViewById(R.id.remain_poker_nums);
        userAdapter = new DragAdapter(mActivity, userChannelList);

        userGridView.setAdapter(userAdapter);
        otherChannelList = service.getBottomCards();
        otherAdapter = new OtherAdapter(mActivity, otherChannelList);
        otherGridView.setAdapter(this.otherAdapter);
        leftCards.setText(" " + otherAdapter.getChannnelLst().size() + " ");

        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
    }

    /**
     * 结束答题
     */
    public void endAnswerMode(int time) {

        ArrayList<ChannelItem> oringe = PokerEntity.getInstance().getPokerSortArray();
        int answer = 0;
        String errorText = "";
        if (userAdapter.getChannelList() != null
                && userAdapter.getChannelList().size() != 0) {

            for (int i = 0; i < userAdapter.getChannelList().size(); i++) {
                if (userAdapter.getChannelList().get(i).resId != oringe
                        .get(i).resId) {
                    errorText = ",第"+i+"张应为"+oringe.get(i).name+",而您的是"+userAdapter.getChannelList().get(i).name;
                    break;
                } else {
                    answer = i + 1;
                }
            }
        }
        String content = "您用时 " + time + "秒,正确牌数" + answer + "张"+errorText;
        ToastUtil.createOkAndCancelDialog(mActivity, Constant.COMMIT_POCKER_GAME_DLG, content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HIDDialog.dismissDialog(Constant.COMMIT_POCKER_GAME_DLG);
            }
        }).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_browse_mode:
                if (isSingleLineBrowse) {// 切换扑克浏览模式
                    this.mutiLineBrowseMode();
                } else {
                    this.singleLineBrowseMode();
                }
                isSingleLineBrowse = !isSingleLineBrowse;
                break;
            default:
                break;
        }
    }

    /**
     * 单行浏览模式
     */
    private void singleLineBrowseMode() {
        mIBtnBrowseMode.setImageDrawable(null);
        mIBtnBrowseMode.setImageResource(R.drawable.ibtn_browse_muti_line);
        if (isInMemoryMode) {
            if (!isSingleLineShowPokerFace) {
                mSingleLineLayout.showPokerFace();
                isSingleLineShowPokerFace = true;
            }
        }
        mHorizontalLayout.setVisibility(View.VISIBLE);
        mStubMutiBrowse.setVisibility(View.GONE);
        mRightLayout.setVisibility(View.GONE);
    }

    /**
     * 多行浏览模式
     */
    private void mutiLineBrowseMode() {
        mIBtnBrowseMode.setImageDrawable(null);
        mIBtnBrowseMode.setImageResource(R.drawable.ibtn_browse_one_line);
        mHorizontalLayout.setVisibility(View.GONE);

        if (mMUtiLineLayout == null) {
            View view = mStubMutiBrowse.inflate();
            mMUtiLineLayout = (QuickPokerBrowseVerticalView) view
                    .findViewById(R.id.quick_poker_browse_vertical);
        } else {
            mStubMutiBrowse.setVisibility(View.VISIBLE);
        }

        if (isInMemoryMode) {
            if (!isMutiLineShowedPokerFace) {
                mMUtiLineLayout.showPokerFace();
                isMutiLineShowedPokerFace = true;
            }
        }
        mRightLayout.setVisibility(View.VISIBLE);
    }

    /**
     * GRIDVIEW对应的ITEM点击监听接口
     */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view,
                            final int position, long id) {
        // 如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if (isMove) {
            return;
        }
        switch (parent.getId()) {
            case R.id.userGridView:
                // position为 0，1 的不可以进行任何操作
                // if (position != 0 && position != 1) {
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    ImageView newTextView = (ImageView) view
                            .findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((DragAdapter) parent.getAdapter())
                            .getItem(position);// 获取点击的频道内容
                    otherAdapter.setVisible(false);
                    // 添加到最后一个
                    otherAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                // 获取终点的坐标
                                otherGridView.getChildAt(
                                        otherGridView.getLastVisiblePosition())
                                        .getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation,
                                        channel, userGridView);
                                userAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            case R.id.otherGridView:
                final ImageView moveImageView2 = getView(view);
                if (moveImageView2 != null) {
                    ImageView newTextView = (ImageView) view
                            .findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((OtherAdapter) parent.getAdapter())
                            .getItem(position);
                    userAdapter.setVisible(false);
                    // 添加到最后一个
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                // 获取终点的坐标
                                userGridView.getChildAt(
                                        userGridView.getLastVisiblePosition())
                                        .getLocationInWindow(endLocation);
                                MoveAnim(moveImageView2, startLocation,
                                        endLocation, channel, otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 点击ITEM移动动画
     *
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation,
                          int[] endLocation, final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        // 获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        // 得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView,
                initLocation);
        // 创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(50L);// 动画时间
        // 动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
                leftCards.setText(" " + otherAdapter.getChannnelLst().size()
                        + " ");
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) mActivity.getWindow()
                .getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(mActivity);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(mActivity);
        iv.setImageBitmap(cache);
        return iv;
    }

    @Override
    public void rememoryTimeToEnd(int answerTime) {
        endAnswerMode(answerTime);

    }

    @Override
    public void memoryTimeToEnd(int memoryTime) {
        super.memoryTimeToEnd(memoryTime);
        inAnswerMode();
    }
    @Override
    public void initDataFinished() {
        super.initDataFinished();
        mStubAnswerShade.setVisibility(View.VISIBLE);
    }
    @Override
    public void startGame() {
        super.startGame();
        mStubAnswerShade.setVisibility(View.GONE);
        inMemoryMode();
    }

    @Override
    public void pauseGame() {
        super.pauseGame();
        mStubAnswerShade.setVisibility(View.VISIBLE);
    }

    @Override
    public void reStartGame() {
        super.reStartGame();
        mStubAnswerShade.setVisibility(View.GONE);
    }
}
