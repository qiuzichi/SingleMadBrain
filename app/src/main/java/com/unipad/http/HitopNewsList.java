package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.CompetitionBean;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongkan on 2016/6/12.
 */
public class HitopNewsList extends HitopRequest<List<NewEntity>>{

    private int page;

    private int size;

    private String title;
    private  String userId;
    private String contenttype;

    public HitopNewsList(String path) {
        super(path);
    }
    public HitopNewsList(String contentType,String title,int page,int size) {
        super(HttpConstant.GET_NEWS_LIST);
        this.size = size;
        this.page= page;
        this.title = title;
        this.userId = AppContext.instance().loginUser.getUserId();
        this.contenttype = contentType;

    }
    @Override
    public String buildRequestURL() {
        mParams.addQueryStringParameter("contenttype_id",contenttype);
        if (null != title) {
            mParams.addQueryStringParameter("title", title);
        }
        mParams.addQueryStringParameter("userId",""+ userId);
        mParams.addQueryStringParameter("page",""+page);
        mParams.addQueryStringParameter("size",""+size);
        return null;
    }

    @Override
    public List<NewEntity> handleJsonData(String json) {
        List<NewEntity> newsList = null;
        JSONObject jsObj = null;

        try {
            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject data = new JSONObject(jsObj.getString("data"));
                    JSONArray jsonArray = data.getJSONArray("resultList");
                    int iSize = jsonArray.length();
                    newsList = new ArrayList<>();
                    for (int i = 0; i < iSize; i++) {
                        NewEntity bean = new NewEntity();
                        JSONObject jsonObj2 = jsonArray.getJSONObject(i);
                        bean.setBrief(jsonObj2.getString("brief"));
                        bean.setThumbUrl(HttpConstant.PATH_FILE_URL + jsonObj2.getString("pictureUrl"));
                        bean.setPublishDate(jsonObj2.getString("createDate"));
                        bean.setTitle(jsonObj2.getString("title"));
                        bean.setId(jsonObj2.getString("id"));
                        bean.setIsLike(jsonObj2.getInt("isLike")==1?true:false);
                        newsList.add(bean);
                    }
                }
            }
        }catch (Exception e) {
            return null;
        }
        int key =HttpConstant.NOTIFY_GET_NEWS;

        if ("00001".equals(contenttype)){
            key = HttpConstant.NOTIFY_GET_NEWS;
        } else if ("00002".equals(contenttype)){
            key = HttpConstant.NOTIFY_GET_COMPETITION;
        }else if ("00003".equals(contenttype)) {
            key = HttpConstant.NOTIFY_GET_HOTSPOT;
        }
        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(key,newsList);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
