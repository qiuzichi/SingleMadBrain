package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.consult.entity.AdPictureBean;
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
public class HitopAdList extends HitopRequest<List<AdPictureBean>>{


    public HitopAdList(String path) {
        super(HttpConstant.GET_NEWS_ADVERTPIC);
        mParams.addBodyParameter("positionId", path);
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
    public List<AdPictureBean> handleJsonData(String json) {
        List<AdPictureBean> adList = null;
        JSONObject jsObj = null;

        try {

            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
//                    JSONObject data = new JSONObject(jsObj.getString("list"));
                if( jsObj.getInt("ret_code") == 0) {
                    Log.i("hitopadlist", json);

                    JSONArray jsonArray = jsObj.getJSONArray("lists");
                    int iSize = jsonArray.length();
                    adList = new ArrayList<AdPictureBean>();
                    for (int i = 0; i < iSize; i++) {
                        AdPictureBean bean = new AdPictureBean();
                        JSONObject jsonObj2 = jsonArray.getJSONObject(i);
                        bean.setAdvertName(jsonObj2.getString("advertName"));
                        bean.setAdvertId(jsonObj2.getString("id"));
                        bean.setJumpType(jsonObj2.getString("jumpType"));
                        bean.setJumpUrl(jsonObj2.getString("jumpUrl"));
                        bean.setAdvertPath(HttpConstant.PATH_FILE_URL+jsonObj2.getString("path"));
                        adList.add(bean);
        Log.i("hitopadlist", adList.size()+ "  " + bean.getAdvertPath());
                    }
                }
            }
        }catch (Exception e) {
            return null;
        }
        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(HttpConstant.NOTIFY_GET_ADVERT,adList);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
