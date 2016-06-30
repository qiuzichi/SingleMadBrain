package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.consult.entity.NewDetails;
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
public class HitopNewsDetails extends HitopRequest<List<NewDetails>>{

    private String contenttype;

    public HitopNewsDetails(String path) {
        super(path);
    }
    public HitopNewsDetails(String pagerId, String contentType) {
        super(HttpConstant.GET_NEWS_DETAIL);
        mParams.addBodyParameter("id", pagerId);
        this.contenttype = contentType;
    }

    public String buildRequestURL() {
        return null;
    }

    @Override
    public List<NewDetails> handleJsonData(String json) {
//        List<NewDetails> newsList = null;
//        JSONObject jsObj = null;

//        try {
//            jsObj = new JSONObject(json);
//            if (jsObj != null && jsObj.toString().length() != 0) {
//                if (jsObj.getInt("ret_code") == 0) {
//        Log.d("details" , json);
//                    JSONObject jsonObj2 = new JSONObject(jsObj.getString("data"));
//                    NewDetails bean = new NewDetails();
//                    bean.setBrief(jsonObj2.getString("brief"));
//                    bean.setPictureUrl(HttpConstant.PATH_FILE_URL + jsonObj2.getString("pictureUrl"));
//                    bean.setPublishDate(jsonObj2.getString("createDate"));
//                    bean.setTitle(jsonObj2.getString("title"));
//                    bean.setId(jsonObj2.getString("id"));
//                    bean.setContent(jsonObj2.getString("content"));
//
//                    newsList.add(bean);
//
//                }
//            }
//        }catch (Exception e) {
//            return null;
//        }
//        int key =HttpConstant.NOTIFY_GET_NEWS;
//
//        if ("00001".equals(contenttype)){
//            key = HttpConstant.NOTIFY_GET_NEWS;
//        } else if ("00002".equals(contenttype)){
//            key = HttpConstant.NOTIFY_GET_COMPETITION;
//        }else if ("00003".equals(contenttype)) {
//            key = HttpConstant.NOTIFY_GET_HOTSPOT;
//        }
        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(HttpConstant.NOTIFY_GET_DETAIL,json);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
