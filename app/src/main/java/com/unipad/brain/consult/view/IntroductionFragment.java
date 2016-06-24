package com.unipad.brain.consult.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.entity.AdPictureBean;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.bean.NewsOperateBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.BaseFragment;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;

import com.unipad.observer.IDataObserver;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 推荐
 * Created by jianglu on 2016/6/20.
 */
public class IntroductionFragment extends MainBasicFragment implements IDataObserver {


    private ListView mListViewTab;
    private List<NewEntity> newsDatas = new ArrayList<NewEntity>();
    private List<NewsOperateBean> newsOperateDatas = new ArrayList<NewsOperateBean>();
    private List<AdPictureBean> newsAdvertDatas = new ArrayList<AdPictureBean>();
    private NewsService service;
    private View view;
    private MyAdapter mNewsAdapter;

    private int postion ;
    private PopupWindow mPopupWindows;
    private ScaleAnimation sa;
    private View mPopupView;
    private EditText et_commment;
    private Button btn_commit;
    private NewEntity newEntity;
       //用于主界面的  接口回调
    private OnSwitchPagerButtonOnClick switchPagerClickButton;
    private View mAdvertPager;
    private TextView tv_title;
    private TextView tv_detail;
    private AdViewPagerAdapter adAdapter;
    private ViewPager mAdvertLuobo;
    //当无网络的时候 默认显示4个 广告轮播图
    private static final int DEFAULUPAGER = 4;
    private BitmapUtils bitmapUtils;
    private PlayLunboTask lunTask;
    private LinearLayout ll_point;
    //记录viewpager上面点击在那个画面;
    private int selectPicIndex;



    private void getNews(String contentType,String title,int page,int size ){
        service.getNews(contentType,title,page,size );
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
        //初始化弹出窗体
        initPopupWindows();
        getNews("00001",null,1,10);
        //初始化点的个数;
        initLunboPoint();
        //默认显示 第一个点;
        setPointSelect(selectPicIndex);
        //开发播放
        lunTask.startLunbo();
    }

    private void initData() {
        //进行bitmap初始化
        bitmapUtils = new BitmapUtils(mActivity);
        //播放轮播图 初始化
        lunTask = new PlayLunboTask();
        mListViewTab = (ListView) getView().findViewById(R.id.lv_introduction_listview);
        //广告轮播图;
        mAdvertLuobo = (ViewPager) getView().findViewById(R.id.vp_advert_luopo_pager);
        //轮播图的点的视图;
        View view_point = View.inflate(mActivity, R.layout.advert_luobo_picture, null);
        //轮播图的 view组件
        ll_point = (LinearLayout) view_point.findViewById(R.id.ll_advert_point);

        adAdapter = new AdViewPagerAdapter();
        mAdvertLuobo.setAdapter(adAdapter);
//        //广告标题
//        tv_title = (TextView) mAdvertPager.findViewById(R.id.tv_lunbo_title);
//        //广告介绍
//        tv_detail = (TextView) mAdvertPager.findViewById(R.id.tv_lunbo_detail);

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_NEWS, this);


        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);

        service.registerObserver(HttpConstant.NOTIFY_GET_ADVERT, this);

        mNewsAdapter = new MyAdapter(mActivity, newsDatas, R.layout.item_listview_introduction);
        mListViewTab.setAdapter(mNewsAdapter);
        //将轮播图放在listview 的头文件
//        mListViewTab.addHeaderView(mAdvertPager);

    }

    private void initEvent(){
        mAdvertLuobo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPicIndex = position;
                setPointSelect(selectPicIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initPopupWindows(){
        mPopupView = View.inflate(mActivity, R.layout.popup_windows_comment, null);
        //评论内容
        et_commment = (EditText) mPopupView.findViewById(R.id.et_popup_windows_input);
        //提交评论按钮
        btn_commit = (Button) mPopupView.findViewById(R.id.btn_popup_window_commit);

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击提交关闭窗体
                //用户评论内容
                String user_comment = et_commment.getText().toString().trim();
                if(TextUtils.isEmpty(user_comment)){
                    Toast.makeText(mActivity, "内容为空 请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交评论内容到服务器
                service.getNewsOperate(newEntity.getId(), "3", null, user_comment, 0);
                //清空输入的内容
                et_commment.setText("");
                //关闭弹出窗体
                closePopup();
        }
        });

        mPopupWindows = new PopupWindow(mPopupView, 300, -2, true);

        mPopupWindows.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //动画效果;
        sa = new ScaleAnimation(0f, 1f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(300);
    }

    private void showPopupWindows(View parent , int x, int y){
        closePopup();

        mPopupView.startAnimation(sa);
        mPopupWindows.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, x, y);


    }

    //关闭弹出窗体
    private void closePopup(){

        if(mPopupWindows != null && mPopupWindows.isShowing()){
            mPopupWindows.dismiss();
        }
    }

    //初始化点的个数;
    private void initLunboPoint(){
        //先清空 所有的点
        ll_point.removeAllViews();
        for(int i=0; i<newsAdvertDatas.size(); i++){
            View point = new View(mActivity);

            point.setBackgroundResource(R.drawable.advert_point_select);
            //默认为false;
            point.setEnabled(false);

            //点的大小属性;
            int px = 10;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px,px);
            params.leftMargin = 2* px;

            ll_point.addView(point, params);
        }
    }
    private void setPointSelect(int pointSelectIndex){

        for(int i=0; i<newsAdvertDatas.size(); i++){
           //其他点 都是 灰色  选择点  是红色
            ll_point.getChildAt(i).setSelected(i==pointSelectIndex);
        }
    }

   private void clear(){
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

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends CommonAdapter<NewEntity> {
        private Boolean isFavorite; //标示每个item 进行操作方式
        private Boolean isZan; //标示每个item 进行点赞方式



        public MyAdapter(Context context, List<NewEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(final ViewHolder holder, final NewEntity newEntity) {
            //设置  缩略图
            final ImageView iv_picture =  (ImageView) holder.getView(R.id.iv_item_introduction_icon);



//            new HttpUtils().send(HttpRequest.HttpMethod.GET, newEntity.getThumbUrl(), new RequestCallBack<Bitmap>() {
//                @Override
//                public void onSuccess(ResponseInfo<Bitmap> responseInfo) {
//                    // 请求成功
//                    iv_picture.setImageBitmap(responseInfo.result);
//                }
//
//                @Override
//                public void onFailure(HttpException e, String s) {
//                   //请求失败  默认图片
//                    iv_picture.setImageResource(R.drawable.set_headportrait);
//                }
//            });

           //设置标题
            ((TextView) holder.getView(R.id.tv_item_introduction_news_title)).setText(newEntity.getTitle());
            //设置更新时间
            ((TextView) holder.getView(R.id.tv_item_introduction_updatetime)).setText(newEntity.getPublishDate());
            //分割线
             View view_line_split = (View)holder.getView(R.id.view_line_item_introduction);

            //喜欢的imagebutton
            ImageView iv_pager_favorite  = (ImageView) holder.getView(R.id.iv_item_introduction_favorite);
            //点赞的imagebutton
            ImageView iv_pager_zan  = (ImageView) holder.getView(R.id.iv_item_introduction_zan);
            //评论
            final ImageView iv_pager_comment  = (ImageView) holder.getView(R.id.iv_item_introduction_comment);
            //分享的imagebutton
            ImageView iv_pager_share  = (ImageView) holder.getView(R.id.iv_item_introduction_share);
//            //查看详情
//            TextView tv_checkDetail  = (TextView) holder.getView(R.id.tv_item_introduction_detail);
            //查看详情的 relative
            RelativeLayout rl_checkDetail =  holder.getView(R.id.rl_item_introduction_detail);


            //点击收藏
            iv_pager_favorite.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //发送网络请求 数据

                    if(!isFavorite){
                        //更新界面图标
                        service.getNewsOperate(newEntity.getId(), "0", "true", "0", 0);
//                        new AsyncTask<String, Void, Boolean>() {
//
//                            @Override
//                            protected Boolean doInBackground(String... params) {
//
//                                return  service.getNewsOperate(newEntity.getId(), "0", "false", "0", 0);
//                            }
//
//                            @Override
//                            protected void onPostExecute(Boolean success) {
//                                if (success) {
//
//                                }
//
//                            }
//                        }.execute();


//                      iv_pager_favorite.setImageResource(R.drawable.favorite_introduction_check);
                        isFavorite = true;

                    }else {

//                      iv_pager_favorite.setImageResource(R.drawable.favorite_introduction_normal);
                        isFavorite = false;
                    }
                    postion = holder.getPosition();



                }
            });
            //点赞按钮 点击事件
            iv_pager_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            //评论的点击事件
            iv_pager_comment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //先做弹出窗体  然后 输入文本信息;
                    int[] location  = new int[2];
                    iv_pager_comment.getLocationInWindow(location);

                    showPopupWindows(iv_pager_comment, location[0] + 10, location[1]);
                    setNewEntity(newEntity);
                }
            });
            //查看详情点击
            rl_checkDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看详情的界面
//                    switchPagerClickButton.switchPagerFragment(newEntity.getId());
                    Intent intent = new Intent(mActivity, PagerDetailActivity.class);
                    intent.putExtra("pagetId",newEntity.getId());
                }
            });

        }

    }
    //广告轮播图的  adapter
    class AdViewPagerAdapter extends PagerAdapter{
        private AdPictureBean adPictureBean;
        @Override
        public int getCount() {
            //访问网络失败的时候
            if(newsAdvertDatas== null && newsAdvertDatas.size() == 0){
               return DEFAULUPAGER;
            }
            return newsAdvertDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv_lunbo = new ImageView(mActivity);
            iv_lunbo.setScaleType(ImageView.ScaleType.FIT_XY);

            //如果网络差  默认图片;
            iv_lunbo.setImageResource(R.drawable.common_google_signin_btn_text_dark_normal);

           adPictureBean =  newsAdvertDatas.get(position);
            //图片url path
            String imageUrl = adPictureBean.getAdvertPath();
            bitmapUtils.display(iv_lunbo, imageUrl);

            //由于点下  与松开 状态不同 所以是touch  而不是click
            iv_lunbo.setOnTouchListener(new ImageView.OnTouchListener() {

                private float downX;
                private float downY;
                private long starttime;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //停止轮播;
                            downX = event.getX();
                            downY = event.getY();
                            starttime = System.currentTimeMillis();

                            lunTask.stopLunbo();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            //停止轮播;
                            lunTask.stopLunbo();
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            //开始轮播;
                            lunTask.startLunbo();
                            break;
                        case MotionEvent.ACTION_UP:

                            float upX = event.getX();
                            float upY = event.getY();
                            if(upX == downX &&  upY == downY){
                                long endtime = System.currentTimeMillis();
                                if(endtime -starttime < 500){
                                    //点击事件;
                                    String adPath = adPictureBean.getJumpUrl();

System.out.println("被点击到了....." + adPath);
                                }
                            }
                            lunTask.startLunbo();
                            break;

                        default:
                            break;
                    }

                    return true;
                }
            });


            container.addView(iv_lunbo);

            return iv_lunbo;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
    //播放轮播图的  控制类
    private class PlayLunboTask extends Handler implements Runnable{
        private void startLunbo(){
            stopLunbo();
            postDelayed(this, 2000);
        }

        private void stopLunbo(){
            // 清理消息
            removeCallbacksAndMessages(null);
        }

        @Override
        public void run() {
            mAdvertLuobo.setCurrentItem(
                    (mAdvertLuobo.getCurrentItem() + 1) % mAdvertLuobo.getAdapter().getCount());
            //这步为什么要重复发送一次呢
            postDelayed(this, 2000);

        }
    }

    public void setOnSwitchPagerClick(OnSwitchPagerButtonOnClick switchPagerClickButton){
        this.switchPagerClickButton = switchPagerClickButton;
    }
    //在activity中  去修改 显示的fragment
    public interface OnSwitchPagerButtonOnClick{
        public void switchPagerFragment(String pagerId);
    }


    public void setNewEntity(NewEntity newEntity){
        //当前的newEntity;
        this.newEntity = newEntity;
    }

    //用于网络请求数据 key 是网页的id   o是json数据
    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_NEWS:
                //发送网络请求 获取新闻页面数据
                newsDatas.addAll((List<NewEntity>) o);
                mNewsAdapter.notifyDataSetChanged();
                break;

            case HttpConstant.NOTIFY_GET_OPERATE:
                //发送网络请求  获取喜欢 点赞 评论 信息
                mNewsAdapter.notifyDataSetChanged();
                break;
            case HttpConstant.NOTIFY_GET_ADVERT:
                //获取轮播图数据
                newsAdvertDatas.addAll((List<AdPictureBean>)o);
                mNewsAdapter.notifyDataSetChanged();
                break;
        }
    }

}
