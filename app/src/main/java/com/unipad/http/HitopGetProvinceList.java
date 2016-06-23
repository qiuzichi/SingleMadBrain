package com.unipad.http;

import android.text.TextUtils;
import android.util.Log;

import com.unipad.brain.location.bean.ProvinceBean;
import com.unipad.observer.GlobleObserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongjiebin on 2016/6/22.
 * @描述：  获取省份列表
 */
public class HitopGetProvinceList extends   HitopRequest<Object>{

    private GlobleObserService sevice;

    public HitopGetProvinceList(String path){
        super(path);
    }

    public HitopGetProvinceList(){
        super(HttpConstant.GET_PROVINCE_HTTP);
    }

    @Override
    public String buildRequestURL(){
        return getHost();
    }

    @Override
    public Object handleJsonData(String json){
       // Log.e(this.getClass().getSimpleName(), json);
       // {"lists":[{"isHot":0,"parentId":1,"regionCode":"110000","regionId":2,"regionName":"北京市","regionNameEn":"Beijing Shi","regionShortNameEn":"BJ"},{"isHot":0,"parentId":1,"regionCode":"120000","regionId":3,"regionName":"天津市","regionNameEn":"Tianjin Shi","regionShortNameEn":"TJ"},{"isHot":0,"parentId":1,"regionCode":"130000","regionId":4,"regionName":"河北省","regionNameEn":"Hebei Sheng","regionShortNameEn":"HE"},{"isHot":0,"parentId":1,"regionCode":"140000","regionId":5,"regionName":"山西省","regionNameEn":"Shanxi Sheng ","regionShortNameEn":"SX"},{"isHot":0,"parentId":1,"regionCode":"150000","regionId":6,"regionName":"内蒙古自治区","regionNameEn":"Nei Mongol Zizhiqu","regionShortNameEn":"NM"},{"isHot":0,"parentId":1,"regionCode":"210000","regionId":7,"regionName":"辽宁省","regionNameEn":"Liaoning Sheng","regionShortNameEn":"LN"},{"isHot":0,"parentId":1,"regionCode":"220000","regionId":8,"regionName":"吉林省","regionNameEn":"Jilin Sheng","regionShortNameEn":"JL"},{"isHot":0,"parentId":1,"regionCode":"230000","regionId":9,"regionName":"黑龙江省","regionNameEn":"Heilongjiang Sheng","regionShortNameEn":"HL"},{"isHot":0,"parentId":1,"regionCode":"310000","regionId":10,"regionName":"上海市","regionNameEn":"Shanghai Shi","regionShortNameEn":"SH"},{"isHot":0,"parentId":1,"regionCode":"320000","regionId":11,"regionName":"江苏省","regionNameEn":"Jiangsu Sheng","regionShortNameEn":"JS"},{"isHot":0,"parentId":1,"regionCode":"330000","regionId":12,"regionName":"浙江省","regionNameEn":"Zhejiang Sheng","regionShortNameEn":"ZJ"},{"isHot":0,"parentId":1,"regionCode":"340000","regionId":13,"regionName":"安徽省","regionNameEn":"Anhui Sheng","regionShortNameEn":"AH"},{"isHot":0,"parentId":1,"regionCode":"350000","regionId":14,"regionName":"福建省","regionNameEn":"Fujian Sheng ","regionShortNameEn":"FJ"},{"isHot":0,"parentId":1,"regionCode":"360000","regionId":15,"regionName":"江西省","regionNameEn":"Jiangxi Sheng","regionShortNameEn":"JX"},{"isHot":0,"parentId":1,"regionCode":"370000","regionId":16,"regionName":"山东省","regionNameEn":"Shandong Sheng ","regionShortNameEn":"SD"},{"isHot":0,"parentId":1,"regionCode":"410000","regionId":17,"regionName":"河南省","regionNameEn":"Henan Sheng","regionShortNameEn":"HA"},{"isHot":0,"parentId":1,"regionCode":"420000","regionId":18,"regionName":"湖北省","regionNameEn":"Hubei Sheng","regionShortNameEn":"HB"},{"isHot":0,"parentId":1,"regionCode":"430000","regionId":19,"regionName":"湖南省","regionNameEn":"Hunan Sheng","regionShortNameEn":"HN"},{"isHot":0,"parentId":1,"regionCode":"440000","regionId":20,"regionName":"广东省","regionNameEn":"Guangdong Sheng","regionShortNameEn":"GD"},{"isHot":0,"parentId":1,"regionCode":"450000","regionId":21,"regionName":"广西壮族自治区","regionNameEn":"Guangxi Zhuangzu Zizhiqu","regionShortNameEn":"GX"},{"isHot":0,"parentId":1,"regionCode":"460000","regionId":22,"regionName":"海南省","regionNameEn":"Hainan Sheng","regionShortNameEn":"HI"},{"isHot":0,"parentId":1,"regionCode":"500000","regionId":23,"regionName":"重庆市","regionNameEn":"Chongqing Shi","regionShortNameEn":"CQ"},{"isHot":0,"parentId":1,"regionCode":"510000","regionId":24,"regionName":"四川省","regionNameEn":"Sichuan Sheng","regionShortNameEn":"SC"},{"isHot":0,"parentId":1,"regionCode":"520000","regionId":25,"regionName":"贵州省","regionNameEn":"Guizhou Sheng","regionShortNameEn":"GZ"},{"isHot":0,"parentId":1,"regionCode":"530000","regionId":26,"regionName":"云南省","regionNameEn":"Yunnan Sheng","regionShortNameEn":"YN"},{"isHot":0,"parentId":1,"regionCode":"540000","regionId":27,"regionName":"西藏自治区","regionNameEn":"Xizang Zizhiqu","regionShortNameEn":"XZ"},{"isHot":0,"parentId":1,"regionCode":"610000","regionId":28,"regionName":"陕西省","regionNameEn":"Shanxi Sheng ","regionShortNameEn":"SN"},{"isHot":0,"parentId":1,"regionCode":"620000","regionId":29,"regionName":"甘肃省","regionNameEn":"Gansu Sheng","regionShortNameEn":"GS"},{"isHot":0,"parentId":1,"regionCode":
        if(!TextUtils.isEmpty(json)){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray arrayList = jsonObject.optJSONArray("lists");
                int length = arrayList.length();
                List<ProvinceBean> provinceBeans = new ArrayList<ProvinceBean>();
                ProvinceBean provinceBean;
                for(int i = 0; i < length; i ++ ) {
                    provinceBean = new ProvinceBean();
                    JSONObject provinceObj = arrayList.optJSONObject(i);
                    provinceBean.provinceId = provinceObj.optString("regionId");
                    provinceBean.ProvinceName = provinceObj.optString("regionName");
                    provinceBeans.add(provinceBean);
                }
                sevice.noticeDataChange(HttpConstant.GET_PROVINCE,provinceBeans);
            } catch (JSONException e) {
                sevice.noticeDataChange(HttpConstant.GET_PROVINCE,"JSON 格式错误");
                e.printStackTrace();
            }
        } else {
            sevice.noticeDataChange(HttpConstant.GET_PROVINCE,"数据为空");
        }
        return null;
    }

    @Override
    public void buildRequestParams(){

    }

    public void setSevice(GlobleObserService sevice){
        this.sevice=sevice;
    }
}