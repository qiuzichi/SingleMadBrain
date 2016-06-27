package com.unipad.brain.consult.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.consult.entity.AdPictureBean;
import com.unipad.brain.consult.widget.RecommendGallery;
import com.unipad.brain.consult.widget.RecommendPot;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.BaseFragment;
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
    private NewsViewPagerAdapter newsAdapter;


    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_HOTSPOT:
                //发送网络请求 获取热点页面数据
                newsDatas.addAll((List<NewEntity>) o);
//                mNewsAdapter.notifyDataSetChanged();
                break;

            case HttpConstant.NOTIFY_GET_ADVERT:
                //获取轮播图数据
//                newsLunboDatas.clear();
//                newsLunboDatas.addAll((List<AdPictureBean>) o);

//                newsAdapter.notifyDataSetChanged();
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
//        service.getAdverts("00001");
    }



    private void initData(){
        //注册服务；
        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_HOTSPOT, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_ADVERT, this);
        //初始化 轮播图
        initNewsLunBo();

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

    class NewsViewPagerAdapter extends CommonAdapter<AdPictureBean>{

        public NewsViewPagerAdapter(Context context, List<AdPictureBean> datas, int layoutId){
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, AdPictureBean adPictureBean) {
            ImageView imageView = holder.getView(R.id.ad_gallery_item);
            x.image().bind(imageView, adPictureBean.getAdvertPath(),imageOptions);
        }
    }

    private void getNews(String contentType,String title,int page,int size ){
        service.getNews(contentType,title,page,size );
    }
    private void clear(){
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_HOTSPOT, this);

        service.unRegisterObserve(HttpConstant.NOTIFY_GET_ADVERT, this);
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
