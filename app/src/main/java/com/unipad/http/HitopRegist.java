package com.unipad.http;

import android.util.Log;

import com.unipad.AppContext;
import com.unipad.UserDetailEntity;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.io.w.Const;
import com.unipad.utils.ToastUtil;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by gongkan on 2016/5/30.
 */
public class HitopRegist extends HitopRequest<Object>{

    public HitopRegist(String path) {
        super(path);
    }
    public HitopRegist() {
        super(HttpConstant.Regist);
    }
    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public Object handleJsonData(String json) {
        JSONObject jsObj = null;
        String response = null;
        try {
            response = new String(json.getBytes(), "utf-8");
            jsObj = new JSONObject(response);
            Log.e("", "" + json);
            Log.e("", "" + json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                    ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).
                            noticeDataChange(HttpConstant.REGIST_OK,null);
                    ToastUtil.showToast("注册成功！");
                    return null;
                }
                else {
                    ToastUtil.showToast(jsObj.getString("ret_msg"));
                    ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).
                            noticeDataChange(HttpConstant.REGIST_FILED,jsObj.getString("ret_msg"));
                }
            }

        }catch (Exception e){

        }
        ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).
                noticeDataChange(HttpConstant.REGIST_FILED,null);
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
