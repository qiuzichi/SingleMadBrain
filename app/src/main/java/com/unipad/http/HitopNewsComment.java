package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.consult.entity.CommentBean;

import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongkan on 2016/6/12.
 */
public class HitopNewsComment extends HitopRequest<List<CommentBean>>{

    private String articleId;
    private int page;
    private int size;

    public HitopNewsComment(String path) {
        super(path);
    }
    public HitopNewsComment(String articleId,int page, int size) {
        super(HttpConstant.GET_NEWS_COMMENT);
        mParams.addBodyParameter("article_id", articleId);
        mParams.addBodyParameter("page", "" + page);
        mParams.addBodyParameter("size",""+size);

    }

    public String buildRequestURL() {

        return null;
    }

    @Override
    public List<CommentBean> handleJsonData(String json) {
        List<CommentBean> newsList = null;
        JSONObject jsObj = null;

        try {
            jsObj = new JSONObject(json);

            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
        Log.d("comment", json);
                    JSONArray jsonArray = jsObj.getJSONArray("lists");
                    int iSize = jsonArray.length();
                    newsList = new ArrayList<>();
                    for(int i=0; i<iSize; i++){
                        JSONObject jsonObj2 = jsonArray.getJSONObject(i);

                        CommentBean bean = new CommentBean();
                        bean.setId(jsonObj2.getString("id"));
                        bean.setUserId(jsonObj2.getString("userId"));
                        bean.setUserName(jsonObj2.getString("userName"));
                        bean.setContent(jsonObj2.getString("content"));
                        bean.setCreateDate(jsonObj2.getString("createDate"));
                        newsList.add(bean);
                    }


                }
            }
        }catch (Exception e) {
            return null;
        }

        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(HttpConstant.NOTIFY_GET_COMMENT,newsList);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
