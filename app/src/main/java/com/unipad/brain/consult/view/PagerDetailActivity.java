package com.unipad.brain.consult.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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
import java.util.TimerTask;

/**
 * Created by jiangLu on 2016/6/23.
 */
public class PagerDetailActivity extends BasicActivity implements IDataObserver{



//    private List<CommentBean> mCommentDatas =  new ArrayList<CommentBean>();
//    private CommentAdapter mCommentAdapter;
//    private View mPopupView;
    //资讯的id
    private String articleId;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    //    private ScaleAnimation sa;
//    private PopupWindow mPopupWindows;
//    private EditText et_commment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagerdetail);
        articleId = getIntent().getStringExtra("pagerId");

        if(getIntent().getBooleanExtra("isAdvert", false)){
            //如果是广告的 我就隐藏详情；
             ((TextView) findViewById(R.id.current_time_text)).setVisibility(View.GONE);

        }
    }


    public void initData() {
        String htmlDatas = null;

        if(getIntent().getBooleanExtra("isAdvert", false)){
            htmlDatas = getIntent().getStringExtra("pagerId");
        }else {
            String  id = getIntent().getStringExtra("pagerId");
            htmlDatas = HttpConstant.url +HttpConstant.GET_NEWS_DETAIL + "?id=" + getIntent().getStringExtra("pagerId") + "&userId=" +
                    AppContext.instance().loginUser.getUserId() +
                    "&unipadId=" + AppContext.instance().loginUser.getUserId() + "&token=" + AppContext.instance().loginUser.getToken();
        }
        //新闻部分的 webview
        mWebView = (WebView) findViewById(R.id.pager_detail_webview);
        mProgressBar = (ProgressBar) findViewById(R.id.pager_load_progressBar);

       //设置返回
        ((TextView)findViewById(R.id.title_back_text)).setOnClickListener(this);
        WebSettings web_set = mWebView.getSettings();
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        web_set.setSupportZoom(true); // 支持缩放
        web_set.setUseWideViewPort(true);
        web_set.setBlockNetworkImage(false);
        web_set.setJavaScriptEnabled(true);//支持jscrip
        web_set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//内容大小填充
        mWebView.loadUrl(htmlDatas);

        initEvent();

//        mCommentAdapter = new CommentAdapter(getApplicationContext(), mCommentDatas, R.layout.item_comment_listview);
//
//        mListComment.setAdapter(mCommentAdapter);
    }

    private void initEvent(){
        //在本页面打开
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mProgressBar.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        //显示进度
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.GONE);
                    if(!mProgressBar.isShown()){
                        mWebView.setVisibility(View.VISIBLE);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

        });


    }
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();//退回到上一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void clear() {

//        service.unRegisterObserve(HttpConstant.NOTIFY_GET_OPERATE, this);

    }


}
