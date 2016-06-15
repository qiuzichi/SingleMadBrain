package com.unipad.http;

import com.unipad.AuthEntity;
import com.unipad.observer.GlobleObserService;
import com.unipad.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gongjiebin on 2016/6/14.
 *
 * @描述：  获取实名认证
 */
public class HitopAuthInfo extends HitopRequest<Object> {

    private GlobleObserService sevice;

    public HitopAuthInfo(String path) {
        super(path);
    }

    public HitopAuthInfo(){
        super(HttpConstant.AUTH_INFO_PATH);
    }

    @Override
    public String buildRequestURL() {
        return getHost();
    }

    @Override
    public Object handleJsonData(String json) {
        // 处理返回结果
        AuthEntity authEntity = new AuthEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int ret_code = jsonObject.optInt("ret_code");
            if(ret_code == 0 ){
                JSONObject authObj = jsonObject.optJSONObject("data");
                authEntity.setAuth(authObj.optInt("auth"));
                authEntity.setIdFrontUrl(authObj.optString("onphoto"));
                authEntity.setIdReverseUrl(authObj.optString("idephoto"));
                authEntity.setIdentity(authObj.optString("identity"));
                authEntity.setName(authObj.optString("reaname"));
                authEntity.setType(authObj.optString("roleId"));
                authEntity.setSex(authObj.optString("sex"));
                String[] icons = authObj.optString("gradcert").split(",");
                authEntity.setRating_certificate1(icons[0]);
                authEntity.setRating_certificate2(icons[1]);
                this.sevice.noticeDataChange(HttpConstant.USER_AUTH_INFO, authEntity);
            } else {
                this.sevice.noticeDataChange(HttpConstant.USER_AUTH_INFO,json);
            }
        } catch (JSONException e) {
            this.sevice.noticeDataChange(HttpConstant.USER_AUTH_INFO,"JSON 格式错误");
            e.printStackTrace();
        }
//{"data":{"auth":1,"gradcert":"\\api\\20160613\\93552CEC07A046F78FA9229C882D83AD.jpg,\\api\\20160613\\FC62CFEDBB8941A1AB6FFF038952CFF4.jpg","id":"15857BD2D334481FA47A3BB7F6B1F718","identity":"430923199203184132","idephoto":"\\api\\20160613\\9E64E5D6B28E499FBA8FC970DD47DB5A.jpg","onphoto":"\\api\\20160613\\9E64E5D6B28E499FBA8FC970DD47DB5A.jpg","reaname":"gongjb","roleId":"00001"},"ret_code":"0"}
        return null;
    }

    @Override
    public void buildRequestParams() {

    }

    public void setSevice(GlobleObserService sevice) {
        this.sevice = sevice;
    }
}
