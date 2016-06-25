package com.unipad.io.bean;

import android.text.TextUtils;
import android.util.Log;

import com.unipad.io.IPack;
import com.unipad.io.IWrite;
import com.unipad.io.XmlUtil;

import org.apache.mina.core.session.IoSession;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by gongkan on 2016/5/31.
 */
public class Request implements IWrite{



    private Map<String,String> body;

    /** 请求码*/
    private String textCode;


    public Request (String textCode,Map<String,String> body){
        this.textCode = textCode;
        this.body = body;
    }

    public void addBodyParamter(String key,String value) {
        if (body != null) {
            body.put(key,value);
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
    @Override
    public boolean write(String data) {
        return false;
    }





}
