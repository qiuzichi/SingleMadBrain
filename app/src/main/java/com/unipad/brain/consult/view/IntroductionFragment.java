package com.unipad.brain.consult.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.entity.AdPictureBean;
import com.unipad.brain.consult.widget.RecommendGallery;
import com.unipad.brain.consult.widget.RecommendPot;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.bean.NewsOperateBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
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

    private PopupWindow mPopupWindows;
    private ScaleAnimation sa;
    private View mPopupView;
    private NewsListAdapter mNewsAdapter;
    private AdViewPagerAdapter adAdapter;
    private RecommendGallery mAdvertLuobo;

    private RecommendPot adPotView;
    private ImageOptions imageOptions;
    private BitmapUtils biutmapUtils;

    private void getNews(String contentType,String title,int page,int size ){
        service.getNews(contentType, title, page, size);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        service.getNews("00001", null, 1, 10);
        service.getAdverts("00001");
        //播放轮播广告
        startLunPic();
    }

    private void initData() {
        //初始化轮播图
        initLunPic();
        biutmapUtils = new BitmapUtils(mActivity);

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_NEWS, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_ADVERT, this);

        mListViewTab = (ListView) getView().findViewById(R.id.lv_introduction_listview);
        mNewsAdapter = new NewsListAdapter(mActivity, newsDatas, R.layout.item_listview_introduction);
        mListViewTab.setAdapter(mNewsAdapter);

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

        adAdapter = new AdViewPagerAdapter(getActivity(),newsAdvertDatas,R.layout.ad_gallery_item);
        mAdvertLuobo.setAdapter(adAdapter);
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

    private void initPopupWindows(final NewEntity newEntity ){
        mPopupView = View.inflate(mActivity, R.layout.popup_windows_comment, null);
        //评论内容
        final EditText et_commment = (EditText) mPopupView.findViewById(R.id.et_popup_windows_input);
        //提交评论按钮
        Button btn_commit = (Button) mPopupView.findViewById(R.id.btn_popup_window_commit);

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击提交关闭窗体  用户评论内容

                String user_comment = et_commment.getText().toString().trim();
                if(TextUtils.isEmpty(user_comment)){
                    Toast.makeText(mActivity, "内容为空 请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                //提交评论内容到服务器
                service.getNewsOperate(newEntity.getId(), "3", null, user_comment, 0, new Callback.CommonCallback<String>(){

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

        mPopupWindows = new PopupWindow(mPopupView, 600, -2, true);

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
    //listview  的 adapter
    private class NewsListAdapter extends CommonAdapter<NewEntity> {


        public NewsListAdapter(Context context, List<NewEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(final ViewHolder holder, final NewEntity newEntity) {
            //设置  缩略图
           final ImageView iv_picture = (ImageView) holder.getView(R.id.iv_item_introduction_icon);
            biutmapUtils.display(iv_picture, newEntity.getThumbUrl());

            //设置标题
            ((TextView) holder.getView(R.id.tv_item_introduction_news_title)).setText(newEntity.getTitle());
            //设置更新时间
            ((TextView) holder.getView(R.id.tv_item_introduction_updatetime)).setText(newEntity.getPublishDate());
            //分割线
            View view_line_split = (View) holder.getView(R.id.view_line_item_introduction);

            //点赞的imagebutton
            final ImageView iv_pager_zan = (ImageView) holder.getView(R.id.iv_item_introduction_zan);
            //评论
            final ImageView iv_pager_comment = (ImageView) holder.getView(R.id.iv_item_introduction_comment);

//            TextView tv_checkDetail  = (TextView) holder.getView(R.id.tv_item_introduction_detail);
            //查看详情的 relative
            RelativeLayout rl_checkDetail = holder.getView(R.id.rl_item_introduction_detail);

            if (newEntity.getIsLike()) {
                iv_pager_zan.setImageResource(R.drawable.favorite_introduction_check);
            } else {
                //默认情况下
                iv_pager_zan.setImageResource(R.drawable.favorite_introduction_normal);
            }


            //点赞  点击事件
            iv_pager_zan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

    Log.e("", "dianzao kai shi !!!!");
                service.getNewsOperate(newEntity.getId(), "1", String.valueOf(!newEntity.getIsLike()), "0", 0,
                        new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String s) {
                                newEntity.setIsLike(!newEntity.getIsLike());
                                if (newEntity.getIsLike()) {
                                    //点击之后 变为check
                                    iv_pager_zan.setImageResource(R.drawable.favorite_introduction_check);
                                } else {
                                    iv_pager_zan.setImageResource(R.drawable.favorite_introduction_normal);
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


            //评论的点击事件
            iv_pager_comment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //初始化弹出窗体
                    initPopupWindows(newEntity);
                    //先做弹出窗体  然后 输入文本信息;
                    int[] location = new int[2];
                    iv_pager_comment.getLocationInWindow(location);
                    //弹出窗体位置
                    showPopupWindows(iv_pager_comment, location[0] + 30, location[1] + 30);

                }
            });
            //查看详情点击
            rl_checkDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看详情的界面
                    Intent intent = new Intent(mActivity, PagerDetailActivity.class);
                    intent.putExtra("pagerId", newEntity.getId());
                    intent.putExtra("contentType", "00001");
                    startActivity(intent);
                }
            });


        }
    }
    //广告轮播图的 adapter
    class AdViewPagerAdapter extends CommonAdapter<AdPictureBean>{

        public AdViewPagerAdapter(Context context, List<AdPictureBean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, AdPictureBean adPictureBean) {
            ImageView imageView = holder.getView(R.id.ad_gallery_item);
            x.image().bind(imageView, adPictureBean.getAdvertPath(),imageOptions);
        }
    }



    //用于网络请求数据 key 是网页的id   o是解析后的list数据
    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_NEWS:
                //获取新闻页面数据
                newsDatas.addAll((List<NewEntity>) o);
                mNewsAdapter.notifyDataSetChanged();
                break;

            case HttpConstant.NOTIFY_GET_OPERATE:
                //获取喜欢 点赞 评论 信息
                mNewsAdapter.notifyDataSetChanged();
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
