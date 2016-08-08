package com.unipad.http;

import android.text.TextUtils;
import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.brain.location.bean.CityBean;
import com.unipad.brain.location.bean.ProvinceBean;
import com.unipad.observer.GlobleObserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gongjiebin on 2016/6/22.
 */
public class HitopGetCityList extends  HitopRequest<Object>{

    private GlobleObserService sevice;

    public HitopGetCityList(String path){
        super(path);
    }

    public HitopGetCityList(){
        super(HttpConstant.GET_CITY_HTTP);
    }

    @Override
    public String buildRequestURL(){
        return getHost();
    }

    @Override
    public Object handleJsonData(String json){
        Log.e(this.getClass().getSimpleName(),json);
        if(!TextUtils.isEmpty(json)){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray arrayList = jsonObject.optJSONArray("lists");
                int length = arrayList.length();
                LinkedList<CityBean> cityBeans = new LinkedList<CityBean>();
                CityBean cityBean;
                for(int i = 0; i < length; i ++ ) {
                    cityBean = new CityBean();
                    JSONObject provinceObj = arrayList.optJSONObject(i);
                    cityBean.cityId = provinceObj.optString("regionId");
                    cityBean.cityName = provinceObj.optString("regionName");
                    if(null != AppContext.instance().location){
                        if(AppContext.instance().location.getCity().contains(cityBean.cityName)){
//                            cityBean.isSel = true;
                            cityBeans.addFirst(cityBean);
                            continue;
                        }
                    }
                    cityBeans.add(cityBean);
                }
                sevice.noticeDataChange(HttpConstant.GET_CITY,cityBeans);
            } catch (JSONException e) {
                sevice.noticeDataChange(HttpConstant.GET_CITY, App.getContext().getString(R.string.string_json_error));
                e.printStackTrace();
            }
        } else {
            sevice.noticeDataChange(HttpConstant.GET_CITY,"数据为空");
        }
        return null;
    }


    public void setSevice(GlobleObserService sevice){
        this.sevice=sevice;
    }
}
