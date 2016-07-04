package com.unipad.brain.consult.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.consult.entity.CommentBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jiangLu on 2016/6/23.
 */
public class PagerDetailActivity extends BasicActivity implements IDataObserver, View.OnClickListener {

    private NewsService service;

    private List<CommentBean> mCommentDatas =  new ArrayList<CommentBean>();
//    private CommentAdapter mCommentAdapter;
    private View mPopupView;
    //资讯的id
    private String articleId;
    private ScaleAnimation sa;
    private PopupWindow mPopupWindows;
    private EditText et_commment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagerdetail);
        articleId = getIntent().getStringExtra("pagerId");

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
//        service.registerObserver(HttpConstant.NOTIFY_GET_COMMENT, this);
//        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);
//        //获取数据
////        getComment(articleId, 1, 10);

    }

    private void getComment(String pagerId, int page, int size) {
        service.getComment(pagerId, 1, 10);
}


    public void initData() {
        String  id = getIntent().getStringExtra("pagerId");
        String htmlDatas = "http://192.168.0.104:8090/crazybrain-mng" +HttpConstant.GET_NEWS_DETAIL + "?id=" +
                getIntent().getStringExtra("pagerId") + "&userId=" + AppContext.instance().loginUser.getUserId();
        Log.d("pager" , htmlDatas);
        //新闻部分的 webview
        WebView web_detail = (WebView) findViewById(R.id.pager_detail_webview);



       //设置返回
        ((TextView)findViewById(R.id.title_back_text)).setOnClickListener(this);
        WebSettings web_set = web_detail.getSettings();
        web_detail.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        web_set.setSupportZoom(true); // 支持缩放
        web_set.setUseWideViewPort(true);
        web_set.setBlockNetworkImage(false);
        web_set.setJavaScriptEnabled(true);//支持jscrip
        web_set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//内容大小填充
        web_detail.loadUrl(htmlDatas);

//        mCommentAdapter = new CommentAdapter(getApplicationContext(), mCommentDatas, R.layout.item_comment_listview);
//
//        mListComment.setAdapter(mCommentAdapter);
    }
//    //评论条目的 adapter
//    private class CommentAdapter extends CommonAdapter<CommentBean>{
//
//        public CommentAdapter(Context context, List<CommentBean> datas, int layoutId) {
//            super(context, datas, layoutId);
//        }
//
//        @Override
//        public void convert(ViewHolder holder, CommentBean commentBean) {
//            //用户头像；
//            ImageView iv_icon = (ImageView)holder.getView(R.id.iv_item_comment_userid);
//            String user_photo = AppContext.instance().loginUser.getPhoto();
////    new BitmapUtils(getApplicationContext()).display(iv_icon, commentBean.get);
//            //用户名
//            ((TextView) holder.getView(R.id.tv_item_username_textview)).setText(commentBean.getUserName());
//            //更新时间
//            ((TextView)holder.getView(R.id.tv_item_comment_updatetime)).setText(commentBean.getCreateDate());
//            // 评论内容
//            ((TextView)holder.getView(R.id.tv_item_comment_content)).setText(commentBean.getContent());
//        }
//    }

    @Override
    public void update(int key, Object o) {
        switch (key) {
//            case HttpConstant.NOTIFY_GET_COMMENT:
//
//                break;
//            case HttpConstant.NOTIFY_GET_OPERATE:
//

            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_text:
                //直接返回
                finish();
                break;

        }
    }

//    //关闭弹出窗体
//    private void closePopup(){
//
//        if(mPopupWindows != null && mPopupWindows.isShowing()){
//            mPopupWindows.dismiss();
//        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear() {

//        service.unRegisterObserve(HttpConstant.NOTIFY_GET_OPERATE, this);

    }


}
