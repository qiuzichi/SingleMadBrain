package com.unipad.io.mina;

import android.util.Log;


import org.apache.mina.core.session.IoSession;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by gongkan on 2016/5/31.
 */
public class Request {



    private Map<String,String> body;

    /** 请求码*/
    private String textCode;


    public Request (String textCode,Map<String,String> body){
        this.textCode = textCode;
        this.body = body;
    }

    public void addBodyParamter(String key,String value) {
        if (body != null) {
            body.put(key, value);
        } else {

        }
    }


    public void sendMsg(IoSession session) {
        if (session != null) {
            String xmlString = XmlUtil.buildXmlString(textCode, body);
            DecimalFormat df = new DecimalFormat("00000000");
            String xmlLength = df.format( xmlString.getBytes().length);
            Log.e("request",xmlLength+xmlString);
            session.write(xmlLength+xmlString);
        }else {
            Log.e("uipad","send msg err! session is null");
        }
    }






}
