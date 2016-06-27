package com.unipad.http;
import android.util.Log;

import com.unipad.AppContext;
import com.unipad.brain.home.bean.MyFollow;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 深圳冰凌信息科技有限公司 黄祖嘉 on 2016/6/27 0027.
 */
public class HitopFollow extends HitopRequest<List<MyFollow>>{

    public HitopFollow(String path) {
        super(path);
    }
    public HitopFollow(){
        super(HttpConstant.MY_FOLLOW);
    }
    @Override
    public String buildRequestURL() {
        return null;
    }

    @Override
    public List<MyFollow> handleJsonData(String json) throws JSONException {
        List<MyFollow> myFollows=null;
        JSONObject jsObj=null;
        String response = null;
        try {
            response=new String(json.getBytes(),"utf-8");
            jsObj=new JSONObject(response);
            Log.e("",""+json);
            if (jsObj!=null&&jsObj.toString().length()!=0)
                if (jsObj.getInt("ret_code")==0){
                    ((PersonCenterService)AppContext.instance().getService(Constant.PERSONCENTER)).
                            noticeDataChange(HttpConstant.MYFOLLOW_OK,null);
                    JSONObject data=new JSONObject(jsObj.getString("date"));
                    JSONArray jsonArray=data.getJSONArray("resultList");
                    int iSize=jsonArray.length();
                    if (!(iSize==0)){
                        myFollows=new ArrayList<>();
                    }
                    for (int i=0;i<iSize;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        MyFollow bean=new MyFollow();
                        bean.setMatch(jsonObject.getString("match"));
                        bean.setNews(jsonObject.getString("news"));
                        myFollows.add(bean);
                        Log.e("",""+json);
                    }

                }
        }
        catch (Exception e){
           return null;
        }
        return null;
    }

    @Override
    public void buildRequestParams() {

    }
}
