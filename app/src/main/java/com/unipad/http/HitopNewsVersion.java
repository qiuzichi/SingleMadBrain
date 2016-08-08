package com.unipad.http;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.NewEntity;
import com.unipad.brain.home.bean.VersionBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongkan on 2016/6/12.
 */
public class HitopNewsVersion extends HitopRequest<VersionBean>{

    public HitopNewsVersion(String path) {
        super(path);
    }
    public HitopNewsVersion() {
        super(HttpConstant.GET_NEWS_VERSION);

    }

    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public VersionBean handleJsonData(String json) {
        VersionBean versionBean = null;
        JSONObject jsObj = null;
        try {
            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    JSONObject data = new JSONObject(jsObj.getString("data"));
                    versionBean = new VersionBean();
                    versionBean.setVersion(data.getString("version"));
                    versionBean.setUpdateTime(data.getString("uploadDate"));
                    versionBean.setPath(HttpConstant.PATH_FILE_URL + data.getString("path"));
                    versionBean.setInfoDescription(data.getString("info"));
                }
            }
        }catch (Exception e) {
            return null;
        }
        ((NewsService)AppContext.instance().getService(Constant.NEWS_SERVICE)).noticeDataChange(HttpConstant.NOTIFY_DOWNLOAD_APK,versionBean);

        return null;
    }
}
