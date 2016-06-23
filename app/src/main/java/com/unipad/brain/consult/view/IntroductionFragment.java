package com.unipad.brain.consult.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.unipad.AppContext;
import com.unipad.brain.R;
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
import com.unipad.io.XmlUtil;
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


    private void getNews(String contentType,String title,int page,int size ){
        service.getNews(contentType,title,page,size );
    }
    //获取评论



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        //初始化弹出窗体
        initPopupWindows();
        getNews("00001",null,1,10);


    }

    private void initData() {

        mListViewTab = (ListView) getView().findViewById(R.id.lv_introduction_listview);
        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_NEWS, this);


        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);

        mNewsAdapter = new MyAdapter(mActivity, newsDatas, R.layout.item_listview_introduction);
        mListViewTab.setAdapter(mNewsAdapter);
        //获取网络json 数据


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


   private void clear(){
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_NEWS, this);
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_OPERATE, this);
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

    class MyAdapter extends CommonAdapter<NewEntity> {
        private Boolean isFavorite; //标示每个item 进行操作方式
        private Boolean isZan; //标示每个item 进行点赞方式



        public MyAdapter(Context context, List<NewEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(final com.unipad.common.ViewHolder holder, final NewEntity newEntity) {
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
                    //查看详情的界面  关闭自己

                }
            });

        }

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
        }
    }

}
