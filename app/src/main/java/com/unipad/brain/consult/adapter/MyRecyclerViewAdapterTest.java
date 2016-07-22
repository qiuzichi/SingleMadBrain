package com.unipad.brain.consult.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.listener.OnLoadMoreListener;
import com.unipad.brain.consult.view.PagerDetailActivity;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

import org.xutils.common.Callback;

import java.util.List;

//RecyclerView  的 adapter
public class MyRecyclerViewAdapterTest extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDataObserver {
    private boolean isLoadMoreData = false;
    private LayoutInflater mLayoutInflater;
    private int lastVisibleItem;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private List<NewEntity> newsDatas;
    private PopupWindow mPopupWindows;
    private OnLoadMoreListener onLoadMoreListener;
    private NewsService service;

    public MyRecyclerViewAdapterTest(Activity mActivity, final RecyclerView mRecyclerView, List<NewEntity> datas) {
        this.mActivity = mActivity;
        this.newsDatas = datas;
        this.mRecyclerView = mRecyclerView;
        this.mLayoutInflater = LayoutInflater.from(mActivity);


        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!isLoadMoreData && newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItem + 1 == mRecyclerView.getAdapter().getItemCount()) {
                        onLoadMoreListener.onLoadMore();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                }
            });
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = View.inflate(mActivity,
                    R.layout.item_listview_introduction, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view);
        }  if(viewType == TYPE_FOOTER){
            return new FooterViewHolder(mLayoutInflater.inflate(R.layout.recycler_footer_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            final NewEntity bean = newsDatas.get(position);

            ((ItemViewHolder) holder).text_title.setText(bean.getTitle());
            ((ItemViewHolder) holder).text_updatetime.setText(bean.getPublishDate());
            new BitmapUtils(mActivity).display(((ItemViewHolder) holder).iv_picture, bean.getThumbUrl());

            final ImageView iv_zan = ((ItemViewHolder) holder).iv_pager_zan;
            if (bean.getIsLike()) {
                iv_zan.setImageResource(R.drawable.favorite_introduction_check);
            } else {
                //默认情况下
                iv_zan.setImageResource(R.drawable.favorite_introduction_normal);
            }

            //点赞  点击事件
            iv_zan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Log.e("", "dianzao kai shi !!!!");
                    service.getNewsOperate(bean.getId(), "1", String.valueOf(!bean.getIsLike()), "0", 0,
                            new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    bean.setIsLike(!bean.getIsLike());
                                    if (bean.getIsLike()) {
                                        //点击之后 变为check
                                        iv_zan.setImageResource(R.drawable.favorite_introduction_check);
                                    } else {
                                        iv_zan.setImageResource(R.drawable.favorite_introduction_normal);
                                    }
                                }

                                @Override
                                public void onError(Throwable throwable, boolean b) {

                                }

                                @Override
                                public void onCancelled(CancelledException e) {

                                }

                                @Override
                                public void onFinished() {

                                }

                            });
                }
            });
            final ImageView iv_comment = (ImageView)((ItemViewHolder) holder).iv_pager_comment;
            //评论的点击事件
            iv_comment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //初始化弹出窗体
                    initPopupWindows(bean);
                    showPopupWindows(iv_comment, 0, 0);
                }
            });
            final RelativeLayout rl_checkDetail =  ((ItemViewHolder) holder).rl_checkDetail;
            //查看详情点击
            rl_checkDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看详情的界面
                    Intent intent = new Intent(mActivity, PagerDetailActivity.class);
                    intent.putExtra("pagerId", bean.getId());
                    mActivity.startActivity(intent);
                }
            });

        }else if(holder instanceof FooterViewHolder){
            ((FooterViewHolder) holder).pr_moreData.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return newsDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
//            if (position   == getItemCount()) {
//                return TYPE_FOOTER;
//            } else {
//                return TYPE_ITEM;
//            }
        return newsDatas.get(position) != null ? super.getItemViewType(position) : TYPE_FOOTER;
    }

    public void setLoading(Boolean isLoading) {
        isLoadMoreData = isLoading;
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView text_loadMore;
        ProgressBar pr_moreData;
        public FooterViewHolder(View view) {
            super(view);
            text_loadMore = (TextView) view.findViewById(R.id.tv_item_introduction_news_title);
            pr_moreData = (ProgressBar) view.findViewById(R.id.progressbar_loadmore_footer);
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text_title;
        TextView text_updatetime;
        ImageView iv_pager_zan;
        ImageView iv_pager_comment;
        ImageView iv_picture;
        RelativeLayout rl_checkDetail;
        public ItemViewHolder(View view) {
            super(view);
            text_title = (TextView) view.findViewById(R.id.tv_item_introduction_news_title);
            text_updatetime = (TextView) view.findViewById(R.id.tv_item_introduction_updatetime);
            iv_picture = (ImageView) view.findViewById(R.id.iv_item_introduction_icon);
            iv_pager_zan = (ImageView) view.findViewById(R.id.iv_item_introduction_zan);
            iv_pager_comment = (ImageView) view.findViewById(R.id.iv_item_introduction_comment);
            rl_checkDetail = (RelativeLayout) view.findViewById(R.id.rl_item_introduction_detail);
        }
    }


    private void initPopupWindows(final NewEntity newEntity ){
        View mPopupView = View.inflate(mActivity, R.layout.comment_commit_popup, null);
        //评论内容
        final EditText et_commment = (EditText) mPopupView.findViewById(R.id.et_popup_comment_input);

        //监听返回键事件
        et_commment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if(mPopupWindows !=null && mPopupWindows.isShowing()){
                        mPopupWindows.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        //提交评论按钮
        ((Button) mPopupView.findViewById(R.id.btn_comment_commit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击提交关闭窗体  用户评论内容
                final String user_comment = et_commment.getText().toString().trim();
                if (TextUtils.isEmpty(user_comment)) {
                    Toast.makeText(mActivity, "内容为空 请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交评论内容到服务器
                service.getNewsOperate(newEntity.getId(), "2", null, user_comment, 0, new Callback.CommonCallback<String>() {

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Toast.makeText(mActivity, "网络原因 提交失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                //清空输入的内容
                et_commment.setText("");
                //关闭弹出窗体
                closePopup();
            }
        });

        mPopupWindows = new PopupWindow(mPopupView, -1, 100, true);
        mPopupWindows.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindows.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindows.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //动画效果;
        ScaleAnimation sa = new ScaleAnimation(0f, 1f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(300);
        mPopupView.startAnimation(sa);

    }

    private void showPopupWindows(View parent , int x, int y){
        closePopup();
        popupInputMethodWindow();
        mPopupWindows.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

    }

    //关闭弹出窗体
    private void closePopup(){
        if(mPopupWindows != null && mPopupWindows.isShowing()){
            mPopupWindows.dismiss();
            popupInputMethodWindow();
        }
    }

    //同时弹出 隐藏键盘 弹出框
    private void popupInputMethodWindow() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) mActivity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }, 0);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }



    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_OPERATE:
                //获取喜欢 点赞 评论 信息
                mRecyclerView.getAdapter().notifyDataSetChanged();
                break;
        }
    }

}
