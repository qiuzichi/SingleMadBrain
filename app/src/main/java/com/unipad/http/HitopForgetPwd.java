package com.unipad.http;
import android.util.Log;

import com.unipad.AppContext;
import com.unipad.singlebrain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.utils.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class HitopForgetPwd extends HitopRequest<Object>{
    public HitopForgetPwd(String path) {
        super(path);
    }
public HitopForgetPwd(){
    super(HttpConstant.FORGET_PWD);
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
                    ((PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER)).
                            noticeDataChange(HttpConstant.MODIFY_OK,null);
                    ToastUtil.showToast("密码修改成功！");
                    return null;
                }
                else {
                    ToastUtil.showToast(jsObj.getString("ret_msg"));
                    ((PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER)).
                            noticeDataChange(HttpConstant.MODIFY_FILED,jsObj.getString("ret_msg"));
                }

            }

        }catch (Exception e){

        }
        ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).
                noticeDataChange(HttpConstant.REGIST_FILED,null);
        return null;
    }

    @Override
    protected boolean isNeedToken() {
        return false;
    }
}
