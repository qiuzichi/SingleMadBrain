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

    private String path;
    public HitopAdList(String path) {
        super(HttpConstant.GET_NEWS_ADVERTPIC);
        this.path = path;
        mParams.addBodyParameter("positionId", path);
    }

    @Override
    public String buildRequestURL() {

        return null;
    }

    @Override
    public List<AdPictureBean> handleJsonData(String json) {
        List<AdPictureBean> adList = null;
        JSONObject jsObj = null;
        try {
            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONArray jsonArray = jsObj.getJSONArray("lists");
                    int iSize = jsonArray.length();
                    adList = new ArrayList<AdPictureBean>();
                    if (iSize != 0) {
                        for (int i = 0; i < iSize; i++) {
                            AdPictureBean bean = new AdPictureBean();
                            JSONObject jsonObj2 = jsonArray.getJSONObject(i);
                            bean.setAdvertName(jsonObj2.getString("advertName"));
                            bean.setAdvertId(jsonObj2.getString("id"));
                            bean.setJumpType(jsonObj2.getString("jumpType"));
                            bean.setJumpUrl(jsonObj2.getString("jumpUrl"));
                            bean.setAdvertPath(HttpConstant.PATH_FILE_URL + jsonObj2.getString("path"));
                            adList.add(bean);
                        }
                    }
                }
            }
        }catch (Exception e) {
            return null;
        }
        int key =HttpConstant.NOTIFY_GET_ADVERT;
        if ("00001".equals(path)){
            key = HttpConstant.NOTIFY_GET_ADVERT;
        } else if ("00002".equals(path)) {
            key = HttpConstant.NOTIFY_GET_HOTADVERT;
        }
        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(key,adList);
        return null;
    }
}
