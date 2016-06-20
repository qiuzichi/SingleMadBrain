package com.unipad.http;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;

import java.util.List;

/**
 * Created by gongkan on 2016/6/12.
 */
public class HitopNewsList extends HitopRequest<List<NewEntity>>{

    private int page;

    private int size;

    private String title;

    private String contenttype;

    public HitopNewsList(String path) {
        super(path);
    }
    public HitopNewsList(String contentType,String title,int page,int size) {
        super(HttpConstant.GET_NEWS_LIST);
        this.size = size;
        this.page= page;
        this.title = title;
        this.contenttype = contentType;

    }
    @Override
    public String buildRequestURL() {
        mParams.addQueryStringParameter("contenttype_id",contenttype);
        mParams.addQueryStringParameter("title",title);
        mParams.addQueryStringParameter("page",""+page);
        mParams.addQueryStringParameter("size",""+size);
        return null;
    }

    @Override
    public List<NewEntity> handleJsonData(String json) {
        List<NewEntity> newsList = null;
        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(HttpConstant.NOTIFY_GET_NEWS,newsList);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
