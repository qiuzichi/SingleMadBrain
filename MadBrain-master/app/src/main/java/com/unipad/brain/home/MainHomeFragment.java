package com.unipad.brain.home;

import android.os.Bundle;
import android.view.View;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.http.HitopNewsList;
import com.unipad.io.bean.Request;
import com.unipad.io.mina.LongTcpClient;
import com.unipad.io.w.SocketThreadManager;
import com.unipad.observer.IDataObserver;

import java.util.HashMap;
import java.util.Map;

public class MainHomeFragment extends MainBasicFragment implements IDataObserver{

    private NewsService service;
    @Override
    public int getLayoutId() {
        return R.layout.main_home_fragment;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        HitopNewsList newsList = new HitopNewsList("00001","",1,10);
        //newsList.post();
        Map<String,String> body = new HashMap<String,String>();
        body.put("USERID",AppContext.instance().loginUser.getUserId());
        body.put("SCHEDULEID","6BD96887E062405EB2762BEBD2B7EE84");
        Request request = new Request("10001",body);
        SocketThreadManager.sharedInstance().sendMsg(request);
    }

    @Override
    public void update(int key, Object o) {

    }
}
