package com.unipad.brain.consult.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ViewUtils;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.consult.entity.NewDetails;
import com.unipad.brain.home.MainBasicFragment;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.BaseFragmentActivity;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangLu on 2016/6/23.
 */
public class PagerDetailActivity extends BasicActivity implements IDataObserver, View.OnClickListener {

    private NewsService service;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagerdetail);


        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
//        service.registerObserver(HttpConstant.NOTIFY_GET_DETAIL, this);
        //获取数据
//        getDetail(getIntent().getStringExtra("pagerId"));

    }
//
//    private void getDetail(String pagerId, String contentType) {
//        service.getDetail(pagerId, contentType);
//    }


    public void initData() {
        String  id = getIntent().getStringExtra("pagerId");
        String htmlDatas = "http://192.168.0.104:8090/crazybrain-mng" +HttpConstant.GET_NEWS_DETAIL + "?id=" +
                getIntent().getStringExtra("pagerId");
        //新闻部分的 webview
        WebView web_detail = (WebView) findViewById(R.id.pager_detail_webview);
       //设置返回
        ((TextView)findViewById(R.id.title_back_text)).setOnClickListener(this);
        //收藏点击

        WebSettings web_set = web_detail.getSettings();
        web_detail.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        web_set.setSupportZoom(true); // 支持缩放
        web_set.setUseWideViewPort(true);
        web_set.setBlockNetworkImage(false);
        web_set.setJavaScriptEnabled(true);//支持jscrip
        web_set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//内容大小填充
        web_detail.loadUrl(htmlDatas);

    }



    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_DETAIL:
                initData();
                break;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear() {
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_DETAIL, this);
    }


}
