package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.NewsOperateBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongkan on 2016/6/12.
 */
public class HitopNewsOperate extends HitopRequest<List<NewsOperateBean>>{

    private boolean isOK;
    public HitopNewsOperate(String path) {
        super(path);
    }
    public HitopNewsOperate(String articleId, String method, String methodType, String content, int contentType) {
        super(HttpConstant.GET_NEWS_OPERATE);

        mParams.addBodyParameter("user_id",AppContext.instance().loginUser.getUserId());
        mParams.addBodyParameter("article_id","" + articleId);
        mParams.addBodyParameter("role_id",AppContext.instance().loginUser.getRoleId());

        mParams.addBodyParameter("method","" + method);
        mParams.addBodyParameter("method_type","" + methodType);
        mParams.addBodyParameter("content","" + content);
        mParams.addBodyParameter("content_type", "" + contentType);
    }
    @Override
    public String buildRequestURL() {
//        mParams.addQueryStringParameter("contenttype_id",contenttype);
//        if (null != title) {
//            mParams.addQueryStringParameter("title", title);
//        }
//        mParams.addQueryStringParameter("page",""+page);
//        mParams.addQueryStringParameter("size",""+size);
        return null;
    }

    @Override
    public List<NewsOperateBean> handleJsonData(String json) {
        List<NewsOperateBean> newsList = null;
        JSONObject jsObj = null;
Log.i("hitopOperater", json);
        try {

            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    isOK = true;
                }
            }
        }catch (Exception e) {
            return null;
        }
        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(HttpConstant.NOTIFY_GET_OPERATE,newsList);
        return null;
    }
    public boolean getResonse() {
        return isOK;
    }
}
