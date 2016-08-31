package com.unipad.http;

import com.unipad.AppContext;
import com.unipad.singlebrain.consult.entity.AdPictureBean;
import com.unipad.singlebrain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by gongkan on 2016/6/12.
 */
public class HitopQuitLogin extends HitopRequest<List<AdPictureBean>> {

    public HitopQuitLogin() {
        super(HttpConstant.USER_QUIT_APPLICATION);
        mParams.addBodyParameter("user_id", AppContext.instance().loginUser.getUserId());
    }

    @Override
    public String buildRequestURL() {

        return null;
    }

    @Override
    public List handleJsonData(String json) {
        JSONObject jsObj = null;
        int result = -1;
        try {
            jsObj = new JSONObject(json);
            if (jsObj != null && jsObj.toString().length() != 0) {
                if (jsObj.getInt("ret_code") == 0) {
                   result = 0;
                }
            }
        } catch (Exception e) {
            return null;
        }

        ((PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER)).noticeDataChange(HttpConstant.QUIT_APPLICATION, result);
        return null;
    }
}
