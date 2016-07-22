package com.unipad.brain.consult.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.AdapterView;
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
import com.unipad.brain.consult.entity.AdPictureBean;

import com.unipad.brain.consult.listener.DividerDecoration;
import com.unipad.brain.consult.widget.RecommendGallery;
import com.unipad.brain.consult.widget.RecommendPot;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 * Created by jianglu on 2016/6/20.
 */
public class IntroductionFragment extends MainBasicFragment implements IDataObserver {

    private List<NewEntity> newsDatas;
    private List<AdPictureBean> newsAdvertDatas ;
    //默认加载第一页  的数据
    private int requestPagerNum = 1;
    private final int primaryDataNumber = 10;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private NewsService service;
    private PopupWindow mPopupWindows;
    private AdViewPagerAdapter adAdapter;
    private RecommendGallery mAdvertLuobo;
    private boolean isGetData;
    private RecommendPot adPotView;
    private ImageOptions imageOptions;
    private BitmapUtils biutmapUtils;

    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    //是否是 最后一页
    private boolean isLastPage = false;

//    private AnimRFRecyclerView mRecyclerView;

    private void getNews(String contentType,String title,int page,int size ){
        service.getNews(contentType, title, page, size);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsDatas = new ArrayList<NewEntity>();
        newsAdvertDatas = new ArrayList<AdPictureBean>();

        //初始化轮播图
        initLunPic();
        initData();
        initRecycler();
        //播放轮播广告
        startLunPic();

    }

    private void initData() {
        biutmapUtils = new BitmapUtils(mActivity);
        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_NEWS, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_ADVERT, this);

//        mNewsAdapter = new NewsListAdapter(mActivity, newsDatas, R.layout.item_listview_introduction);
//        mRecyclerView.setAdapter(mNewsAdapter);

    }
    private void initRecycler(){
//        // 自定义的RecyclerView, 也可以在布局文件中正常使用
//        mRecyclerView = (AnimRFRecyclerView) getView().findViewById(R.id.lv_introduction_recyclerview);
//        // 头部
//        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.recycle_header_layout, null);
//        // 脚部
//        View footerView = LayoutInflater.from(mActivity).inflate(R.layout.recycler_footer_layout, null);
//
//        // 使用重写后的线性布局管理器
//        AnimRFLinearLayoutManager manager = new AnimRFLinearLayoutManager(mActivity);
//        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, manager.getOrientation(), true));
//        // 添加头部和脚部，如果不添加就使用默认的头部和脚部
////        mRecyclerView.addHeaderView(headerView);
////            // 设置头部的最大拉伸倍率，默认1.5f，必须写在setHeaderImage()之前
////            mRecyclerView.setScaleRatio(1.7f);
////            // 设置下拉时拉伸的图片，不设置就使用默认的
//        mRecyclerView.setHeaderImage((ImageView) headerView.findViewById(R.id.iv_hander));
//        mRecyclerView.addFootView(footerView);
//        // 设置刷新动画的颜色 进度条   以及 背景色
//        mRecyclerView.setColor(getResources().getColor(R.color.refresh_progress_bar_bg), Color.RED);
//        // 设置头部恢复动画的执行时间，默认500毫秒
//        mRecyclerView.setHeaderImageDurationMillis(300);
//        // 设置拉伸到最高时头部的透明度，默认0.5f
//        mRecyclerView.setHeaderImageMinAlpha(0.6f);
//        // 设置适配器
//        mRecyclerViewAdapter = new MyRecyclerViewAdapter();
//
//        mRecyclerView.setAdapter(mRecyclerViewAdapter);
//
//        // 设置刷新和加载更多数据的监听，分别在onRefresh()和onLoadMore()方法中执行刷新和加载更多操作
//        mRecyclerView.setLoadDataListener(new AnimRFRecyclerView.LoadDataListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        newsDatas.clear();
//                        requestPagerNum = 1;
//                        service.getNews("00001", null, requestPagerNum, primaryDataNumber);
//                        mRecyclerView.getAdapter().notifyDataSetChanged();
//                        // 刷新完成后调用，必须在UI线程中
//                        mRecyclerView.refreshComplate();
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onLoadMore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 加载更多完成后调用，必须在UI线程中
//                        service.getNews("00001", null, requestPagerNum, primaryDataNumber);
//                        mRecyclerView.getAdapter().notifyDataSetChanged();
//                        mRecyclerView.loadMoreComplate();
//                    }
//                }, 3000);
//            }
//        });
        // 刷新
//        mRecyclerView.setRefresh(true);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.lv_introduction_recyclerview);
//        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.light_blue2,
                R.color.red,
                R.color.stroke_color,
                R.color.black
        );
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(24));
        mSwipeRefreshLayout.setRefreshing(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        newsDatas.clear();
                        requestPagerNum = 1;
                        service.getNews("00001", null, requestPagerNum, primaryDataNumber);
                    }
                }, 1000);
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerDecoration(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setRefreshing(false);

//        mRecyclerViewAdapter = new MyRecyclerViewAdapterTest(mActivity, mRecyclerView, newsDatas);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
//        mRecyclerViewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                newsDatas.add(null);
//                mRecyclerViewAdapter.notifyItemInserted(newsDatas.size() - 1);
//                loadMoreData(true);
//                mRecyclerViewAdapter.setLoading(true);
//            }
//        });
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            private int lastVisibleItem;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView,
//                                             int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItem + 1 == mRecyclerViewAdapter.getItemCount()) {
//                    // 此处在现实项目中，请换成网络请求数据代码，加载更多数据
//                    service.getNews("00001", null, requestPagerNum, primaryDataNumber);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
//            }
//        });
    }

    private void initLunPic(){
        //广告轮播图;
        mAdvertLuobo = (RecommendGallery) getView().findViewById(R.id.point_gallery);
        //轮播图的点的视图;
        adPotView = (RecommendPot) getView().findViewById(R.id.ad_pot);
        newsAdvertDatas.add(new AdPictureBean());
        newsAdvertDatas.add(new AdPictureBean());
        adPotView.setIndicatorChildCount(newsAdvertDatas.size());
        mAdvertLuobo.initSelectePoint(adPotView);
        mAdvertLuobo.setOnItemClickListener(mOnItemClickListener);

        adAdapter = new AdViewPagerAdapter(getActivity(),newsAdvertDatas,R.layout.ad_gallery_item);
        mAdvertLuobo.setAdapter(adAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        //默认加载第一个页面的时候 设置可见
        setUserVisibleHint(true);
    }

    //对于用户不可见 与 不可见  会被调用；
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {

            if(!isGetData){
                service.getNews("00001", null, requestPagerNum, primaryDataNumber);
                service.getAdverts("00001");
                Log.d("introduction visit ", "获取消息 界面可见");

                isGetData = true;
            }

        } else if (!isVisibleToUser) {
            super.onPause();
        }
    }

    private void startLunPic(){
        //开始播放
        imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.default_advert_pic)
                        //设置加载失败后的图片
                .setFailureDrawableId(R.drawable.default_advert_pic)
                        //设置使用缓存
                .build();
    }
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AdPictureBean bean = newsAdvertDatas.get(position);
            if (bean.getAdvertPath() != null) {
                if (bean.getJumpType().equals("0")) {
                    //本页面打开 发送意图
                    Intent intent = new Intent(mActivity, PagerDetailActivity.class);
                    intent.putExtra("pagerId", bean.getJumpUrl());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setData(Uri.parse(bean.getJumpUrl()));
                    startActivity(intent);
                }
            }
        }
    };

    //同时弹出 隐藏键盘 弹出框
    private void popupInputMethodWindow() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }, 0);
    }

   private void clear() {
       service.unRegisterObserve(HttpConstant.NOTIFY_GET_NEWS, this);
       service.unRegisterObserve(HttpConstant.NOTIFY_GET_OPERATE, this);
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_ADVERT, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    @Override
    public int getLayoutId() {
        return  R.layout.fragment_introduction;
    }
    //回调数据到搜索栏
    public List<String> getNewsDatas() {
        if(newsDatas.size() != 0){
            List<String> mTipList= new ArrayList<String>();
            for(int i=0; i<newsDatas.size(); i++){
                String title =  newsDatas.get(i).getTitle();
                mTipList.add(title);
            }
            return  mTipList;
        }
        return null;
    }
    private void loadMoreData(final Boolean isLoading){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoading){
                    mRecyclerViewAdapter.setLoading(false);
                    newsDatas.remove(newsDatas.size() - 1);
                    mRecyclerViewAdapter.notifyItemRemoved(newsDatas.size());

                    mRecyclerViewAdapter.notifyItemChanged(newsDatas.size());
                    if(!isLastPage){
                        service.getNews("00001", null, requestPagerNum, primaryDataNumber);
                    }else {
                        int lastVisibleItem =((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                        ToastUtil.showToast("最后一页  已经没有新的数据了" + lastVisibleItem);

                    }

                }
            }
        },3000);
    }
    @Override
    public void onClick(View v) {
    }


    //RecyclerView  的 adapter
    class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private boolean isLoadMoreData = false;
        private LayoutInflater mLayoutInflater;
        private int totalItemCount;
        private int lastVisibleItem;
//        private Context mContext;
//        private List<NewEntity> mData;

        public MyRecyclerViewAdapter() {
//            this.mContext = context;
//            this.mData = data;
            this.mLayoutInflater = LayoutInflater.from(mActivity);

            if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                       int count = mRecyclerViewAdapter.getItemCount();
                        if (!isLoadMoreData && newState == RecyclerView.SCROLL_STATE_IDLE
                                && lastVisibleItem + 1 == count) {
                            newsDatas.add(null);
                            mRecyclerViewAdapter.notifyItemInserted(newsDatas.size() - 1);
                            loadMoreData(true);
                            mRecyclerViewAdapter.setLoading(true);

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
                biutmapUtils.display(((ItemViewHolder) holder).iv_picture, bean.getThumbUrl());

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
                        startActivity(intent);
                    }
                });

            }else if(holder instanceof FooterViewHolder){
                ((FooterViewHolder) holder).pr_moreData.setIndeterminate(true);
                ((FooterViewHolder) holder).text_loadMore.setText("正在加载数据...");
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



    }


    //广告轮播图的 adapter
    class AdViewPagerAdapter extends CommonAdapter<AdPictureBean>{

        public AdViewPagerAdapter(Context context, List<AdPictureBean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, final AdPictureBean adPictureBean) {
            ImageView imageView = holder.getView(R.id.ad_gallery_item);
            x.image().bind(imageView, adPictureBean.getAdvertPath(), imageOptions);
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

    //用于网络请求数据 key 是网页的id   o是解析后的list数据
    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_NEWS:
                //获取新闻页面数据
                List<NewEntity> databean = (List<NewEntity>) o;

                if(databean.size() != primaryDataNumber){
                    isLastPage = true;
                }else{
                    requestPagerNum++;
                }
                newsDatas.addAll(databean);
                mRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case HttpConstant.NOTIFY_GET_OPERATE:
                //获取喜欢 点赞 评论 信息
                mRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case HttpConstant.NOTIFY_GET_ADVERT:
                //获取轮播图数据
                newsAdvertDatas.clear();
                newsAdvertDatas.addAll((List<AdPictureBean>) o);

                adAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }



}
