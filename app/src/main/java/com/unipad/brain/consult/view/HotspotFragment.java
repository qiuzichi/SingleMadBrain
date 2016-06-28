package com.unipad.brain.consult.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.unipad.brain.R;
import com.unipad.brain.home.MainBasicFragment;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.entity.AdPictureBean;
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

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 热点
 * Created by jiangLu on 2016/6/20.
 */

public class HotspotFragment extends MainBasicFragment implements IDataObserver {

    private NewsService service;

    private List<NewEntity> newsDatas = new ArrayList<NewEntity>();

    private List<AdPictureBean> newsLunboDatas = new ArrayList<AdPictureBean>();
    private ImageOptions imageOptions;
    //广告轮播图adapter
    private NewsViewPagerAdapter newsAdapter;
    //listview adapter
    private HotspotAdapter mHotspotAdapter;


    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_HOTSPOT:
                //发送网络请求 获取热点页面数据
                newsDatas.addAll((List<NewEntity>) o);
                mHotspotAdapter.notifyDataSetChanged();
                break;

            case HttpConstant.NOTIFY_GET_HOTADVERT:
                //获取轮播图数据
                newsLunboDatas.clear();
                newsLunboDatas.addAll((List<AdPictureBean>) o);

                newsAdapter.notifyDataSetChanged();
                break;
        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_hotspot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        getNews("00003", null, 1, 10);

        //获取广告的数据
        service.getAdverts("00002");
    }



    private void initData(){
        //注册服务；
        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_HOTSPOT, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_HOTADVERT, this);

        //初始化 轮播图
        initNewsLunBo();
        //播放轮播图；
        startPlayLunPic();

        //初始化新闻条目；
        ListView mListView = (ListView) getView().findViewById(R.id.lv_hotspot_listview);
        mHotspotAdapter = new HotspotAdapter(getActivity(), newsDatas, R.layout.item_listview_hotspot );
        mListView.setAdapter(mHotspotAdapter);

    }

    private void initNewsLunBo(){
        //轮播图;
        RecommendGallery mNewsLuobo = (RecommendGallery) getView().findViewById(R.id.point_gallery_hotspot);
        //轮播图的点的视图;
        RecommendPot adPotView = (RecommendPot) getView().findViewById(R.id.hotspot_ad_pot);
        newsLunboDatas.add(new AdPictureBean());
        newsLunboDatas.add(new AdPictureBean());

        adPotView.setIndicatorChildCount(newsLunboDatas.size());
        mNewsLuobo.initSelectePoint(adPotView);

        newsAdapter = new NewsViewPagerAdapter(getActivity(),newsLunboDatas, R.layout.ad_gallery_item);
        mNewsLuobo.setAdapter(newsAdapter);
    }

    private class NewsViewPagerAdapter extends CommonAdapter<AdPictureBean>{

        public NewsViewPagerAdapter(Context context, List<AdPictureBean> datas, int layoutId){
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, AdPictureBean adPictureBean) {
            ImageView imageView = holder.getView(R.id.ad_gallery_item);
            x.image().bind(imageView, adPictureBean.getAdvertPath(),imageOptions);
        }

    }
    //热点新闻条目的 adapter
    private class HotspotAdapter extends CommonAdapter<NewEntity> {

        public HotspotAdapter(Context context, List<NewEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, NewEntity newEntity) {
            //图片的
            ImageView iv_pic = holder.getView(R.id.iv_item_hotspot_icon);
            //设置标题
            ((TextView)holder.getView(R.id.tv_item_hotspot_news_title)).setText(newEntity.getTitle());
            //设置更新时间
            ((TextView) holder.getView(R.id.tv_item_hotspot_updatetime)).setText(newEntity.getPublishDate());
            //分割线
            View view_line_split = (View)holder.getView(R.id.view_line_item_hotspot);


            //点赞的imagebutton
            ImageView iv_pager_zan  = (ImageView) holder.getView(R.id.iv_item_hotspot_zan);

            //评论
            final ImageView iv_pager_comment  = (ImageView) holder.getView(R.id.iv_item_hotspot_comment);

            //查看详情的 relative
            RelativeLayout rl_checkDetail =  holder.getView(R.id.rl_item_hotspot_detail);

        }
    }

    private void getNews(String contentType,String title,int page,int size ){
        service.getNews(contentType,title,page,size );
    }

    private void clear(){
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_HOTSPOT, this);

        service.unRegisterObserve(HttpConstant.NOTIFY_GET_HOTADVERT, this);
    }

    //播放轮播图；
    private void startPlayLunPic(){

        imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType;
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.default_advert_pic)
                        //设置加载失败后的图片
                .setFailureDrawableId(R.drawable.default_advert_pic)
                        //设置使用缓存
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }
    @Override
    public void onClick(View v) {

    }
}
