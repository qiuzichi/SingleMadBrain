package com.unipad.brain.consult.view;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.adapter.MyRecyclerAdapter;
import com.unipad.brain.consult.entity.AdPictureBean;
import com.unipad.brain.consult.entity.ConsultTab;
import com.unipad.brain.consult.listener.DividerDecoration;
import com.unipad.brain.consult.listener.OnLoadMoreListener;
import com.unipad.brain.consult.listener.OnShowUpdateDialgo;
import com.unipad.brain.consult.widget.RecommendGallery;
import com.unipad.brain.consult.widget.RecommendPot;
import com.unipad.brain.dialog.BaseConfirmDialog;
import com.unipad.brain.dialog.ConfirmUpdateDialog;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.bean.VersionBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐 新闻
 * Created by jianglu on 2016/6/20.
 */
public class IntroductionFragment extends MainBasicFragment implements IDataObserver {

    private List<NewEntity> newsDatas;
    private List<AdPictureBean> newsAdvertDatas ;
    //默认加载第一页  的数据 标记为最后一页的页数
    private int requestPagerNum = 1;
    private final int primaryDataNumber = 10;
    private NewsService service;
    private AdViewPagerAdapter adAdapter;
    private RecommendGallery mAdvertLuobo;
    private boolean isGetData;
    private RecommendPot adPotView;
    private ImageOptions imageOptions;
    private MyRecyclerAdapter mRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    //总页数 大小
    private int totalPager = 0;
    //返回的服务器 apk版本信息
    private VersionBean versionBean;
    private ConfirmUpdateDialog mConfirmDialog;
    private RelativeLayout mRelativeLayoutVersion;

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
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.lv_introduction_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_widget);
        mRelativeLayoutVersion = (RelativeLayout) getView().findViewById(R.id.rl_reminder_version);
        ((ImageView) getView().findViewById(R.id.update_version_imgview)).setOnClickListener(this);

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_NEWS, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_ADVERT, this);
        service.registerObserver(HttpConstant.NOTIFY_DOWNLOAD_APK, this);

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
//
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
//
//                        mRecyclerView.loadMoreComplate();
//                    }
//                }, 3000);
//            }
//        });
        // 刷新
//        mRecyclerView.setRefresh(true);


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

//                        if(mRecyclerViewAdapter.getIsVisibility()){
//                            newsDatas.add(0, new NewEntity("header"));
//                        }
                        requestPagerNum = 1;
                        service.getNews(ConsultTab.INTRODUCATION.getTypeId(), null, requestPagerNum, primaryDataNumber);

                    }
                }, 1000);
            }

        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerDecoration(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setRefreshing(false);


        mRecyclerViewAdapter = new MyRecyclerAdapter(mActivity, mRecyclerView, newsDatas, 0);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(requestPagerNum == totalPager){
                   /* 最后一页 直接吐司 不显示下拉加载*/
                    ToastUtil.showToast(getString(R.string.loadmore_null_data));
                    return;
                }

                /*禁用下拉刷新功能    */
                mSwipeRefreshLayout.setEnabled(false);
                newsDatas.add(null);
                mRecyclerViewAdapter.notifyItemInserted(newsDatas.size() - 1);
                loadMoreData(true);
                mRecyclerViewAdapter.setLoading(true);
                }
        });

        mRecyclerViewAdapter.setmOnShowUpdateDialgo(new OnShowUpdateDialgo() {
            @Override
            public void showDialogUpdate() {
                showUpdateVersionDialog(mActivity);
            }
        });

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

    private boolean checkVersionIsNew(){
//        PackageManager pm = mActivity.getPackageManager();
//        PackageInfo pi = null;
//        try {
//            pi = pm.getPackageInfo(mActivity.getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String versionName = pi.versionName;
//        String versioncode = pi.versionCode;
        String versionName = getString(R.string.versionName);

        if(versionName.equals(versionBean.getVersion())){
            return true;
        }
        ((TextView)getView().findViewById(R.id.text_update_version)).setText(getString(R.string.check_version) + versionBean.getVersion());
        return  false;
    }

    private BaseConfirmDialog.OnActionClickListener mDialogListener = new BaseConfirmDialog.OnActionClickListener() {
        @Override
        public void onActionRight(BaseConfirmDialog dialog) {
            mConfirmDialog.dismiss();
            //从网络下载文件
            downloadApkFile(versionBean.getPath());
            //点击确认更新 隐藏提示栏 删除header item
            mRelativeLayoutVersion.setVisibility(View.GONE);
        }

        @Override
        public void onActionLeft(BaseConfirmDialog dialog) {
            mConfirmDialog.dismiss();
        }
    };

    private void downloadApkFile(String path){
        Intent server = new Intent("com.loaddown.application");
        server.putExtra("loadPath", path);
        mActivity.startService(server);
//      LoadService.handler.obtainMessage(1,path).sendToTarget();
    }

    //显示dialog  以及 通知栏 有新版本
    private void showUpdateVersionDialog(Context context) {
        mConfirmDialog = new ConfirmUpdateDialog(context, getString(R.string.update_version_title) + versionBean.getVersion(),
                versionBean.getInfoDescription(), getString(R.string.cancel),  getString(R.string.confirm_update), mDialogListener);
        mConfirmDialog.show();
    }

    private void showNotification(){

        NotificationManager mNotificationManager = (NotificationManager) mActivity.getSystemService(mActivity.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mActivity);

        String title = getString(R.string.update_version_title) + versionBean.getVersion();
        mBuilder
                .setContentTitle(title)//设置通知栏标题
                .setContentText(versionBean.getInfoDescription())
                .setContentIntent(getDefalutIntent(versionBean.getPath())) //设置通知栏点击意图
//                .setNumber(number) //设置通知集合的数量
                .setTicker(getString(R.string.update_version_title)) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//                .setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                        //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON

        Notification mNotification  = mBuilder.build();
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(11, mNotification);
    }

    private PendingIntent getDefalutIntent(String path){
//        Intent intent = new Intent(mActivity, MainHomeFragment.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Intent server = new Intent("com.loaddown.application");
        server.putExtra("loadPath", path);
        PendingIntent pendingIntent= PendingIntent.getService(mActivity, 1, server, PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }


    //对于用户不可见 与 不可见  会被调用；
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {

            if(!isGetData){
                service.getNews(ConsultTab.INTRODUCATION.getTypeId(), null, requestPagerNum, primaryDataNumber);
                service.getAdverts(ConsultTab.INTRODUCATION.getTypeId());
                service.getApkVersion();
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
                    intent.putExtra("isAdvert", true);
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

   private void clear() {
       service.unRegisterObserve(HttpConstant.NOTIFY_GET_NEWS, this);
       service.unRegisterObserve(HttpConstant.NOTIFY_GET_OPERATE, this);
       service.unRegisterObserve(HttpConstant.NOTIFY_GET_ADVERT, this);
       service.unRegisterObserve(HttpConstant.NOTIFY_GET_ADVERT, this);
       service.unRegisterObserve(HttpConstant.NOTIFY_DOWNLOAD_APK, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //第一个节目  当节目可见 获取数据
        setUserVisibleHint(true);
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
    @Override
    public List<String> getNewsDatas() {
        if(newsDatas.size() != 0){
            List<String> mTipList= new ArrayList<String>();
            for(int i=0; i<newsDatas.size(); i++){
                String title =  newsDatas.get(i).getTitle();
                if(TextUtils.isEmpty(title)){
                    continue;
                }
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
                    if (isLoading) {
                        mRecyclerViewAdapter.setLoading(false);
                        newsDatas.remove(newsDatas.size() - 1);
                        mRecyclerViewAdapter.notifyItemRemoved(newsDatas.size());

                        if (requestPagerNum != totalPager) {
                            service.getNews(ConsultTab.INTRODUCATION.getTypeId(), null, requestPagerNum, primaryDataNumber);
                        } else {
                            mRecyclerViewAdapter.notifyItemChanged(newsDatas.size());
                            //重新加载adapter 不然不更新数据
                            mRecyclerView.setAdapter(mRecyclerViewAdapter);
                            ToastUtil.showToast(getString(R.string.loadmore_null_data));
                        }
                         /*允许下拉刷新    */
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_version_imgview:
                //
                showUpdateVersionDialog(mActivity);
                break;
            default:
                break;
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



    //用于网络请求数据 key 是网页的id   o是解析后的list数据
    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_NEWS:
                //获取新闻页面数据
                List<NewEntity> databean = (List<NewEntity>) o;

                if(requestPagerNum == 1 && databean.size() != 0){
                    totalPager = databean.get(0).getTotalPager();
                }

                if(requestPagerNum != totalPager){
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


            case HttpConstant.NOTIFY_DOWNLOAD_APK:
                versionBean = (VersionBean) o;
                if(versionBean == null){
                    //没有网络 网络异常的时候 直接返回什么都不做
                    return;
                }


                if(mRecyclerViewAdapter != null && !checkVersionIsNew()) {
                    //版本不一致的时候 显示textview
                    mRelativeLayoutVersion.setVisibility(View.VISIBLE);
                    showNotification();
                }
//                if(mRecyclerViewAdapter != null && !checkVersionIsNew()){
//                    mRecyclerViewAdapter.setHeadVisibility(true);
//                }

                break;
            default:
                break;
        }
    }
}
