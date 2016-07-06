package com.unipad.brain.consult.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛事
 * Created by Administrator on 2016/6/20.
 */
public class OccasionsFragment extends MainBasicFragment implements IDataObserver {
    private String TAG;

    private ListView mListViewTab;
    private List<NewEntity> occasionData = new ArrayList<NewEntity>();
    private NewsService service;
    private MyNewsListAdapter mNewsAdapter;
    //用来记录第一次获取数据
    private  boolean isGetData;


    private void getNews(String contentType,String title,int page,int size ){
        service.getNews(contentType, title, page, size);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    //对于用户不可见 与 不可见  会被调用；该方法可能会多次调用
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {

            if(!isGetData){
                getNews("00002", null, 1, 10);
                Log.d(tag(), "获取消息 界面可见");
                isGetData = true;
            }


        } else if (!isVisibleToUser) {
            super.onPause();
        }
    }

    private void initData() {

        mListViewTab = (ListView) getView().findViewById(R.id.lv_occasions_listview);

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_COMPETITION, this);

        mNewsAdapter = new MyNewsListAdapter(mActivity, occasionData, R.layout.item_listview_occasions);
        mListViewTab.setAdapter(mNewsAdapter);
    }

    private void clear(){
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_COMPETITION, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    @Override
    public int getLayoutId() {
        Log.d(tag(), "occasion fragme  ,/   nt is run");
        return  R.layout.fragment_occasions;
    }

    @Override
    public void onClick(View v) {

    }

    class MyNewsListAdapter extends CommonAdapter<NewEntity> {

        public MyNewsListAdapter(Context context, List<NewEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(final com.unipad.common.ViewHolder holder, final NewEntity newEntity) {
            //设置  缩略图
            final ImageView iv_picture =  (ImageView) holder.getView(R.id.iv_item_occasion_icon);
            //设置标题
            ((TextView) holder.getView(R.id.tv_item_occasion_news_title)).setText(newEntity.getTitle());
            //设置更新时间
            ((TextView) holder.getView(R.id.tv_item_occasion_updatetime)).setText(newEntity.getPublishDate());
            //分割线
            View view_line_split = (View)holder.getView(R.id.view_line_item_occasion);


            //点赞的imagebutton
            ImageView iv_pager_zan  = (ImageView) holder.getView(R.id.iv_item_occasion_zan);
//            //查看详情
//            TextView tv_checkDetail  = (TextView) holder.getView(R.id.tv_item_occasion_detail);
            //查看详情的 relative
            RelativeLayout rl_checkDetail =  holder.getView(R.id.rl_item_occasion_detail);


            //点赞按钮 点击事件
            iv_pager_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //查看详情点击
            rl_checkDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //查看详情的界面
                Intent intent = new Intent(mActivity, PagerDetailActivity.class);
                intent.putExtra("pagetId",newEntity.getId());
                }
            });
        }

    }


    //用于网络请求数据 key 是网页的id   o是json数据
    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_COMPETITION:
                //发送网络请求 获取赛事页面数据
                occasionData.addAll((List<NewEntity>) o);
                mNewsAdapter.notifyDataSetChanged();
                break;

            case HttpConstant.NOTIFY_GET_OPERATE:

                break;

        }
    }
    private String tag(){
        if(TextUtils.isEmpty(TAG)){
            TAG = this.getClass().getSimpleName();
        }
        return TAG;
    }

}
