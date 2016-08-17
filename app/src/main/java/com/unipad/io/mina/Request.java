package com.unipad.io.mina;

import android.util.Log;


import com.unipad.utils.LogUtil;
import com.unipad.utils.ToastUtil;

import org.apache.mina.core.future.DefaultWriteFuture;
import org.apache.mina.core.session.AbstractIoSession;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteToClosedSessionException;
import org.apache.mina.util.ExceptionMonitor;

import java.io.UnsupportedEncodingException;
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
            String xmlLength = null;
            try {
                xmlLength = df.format( xmlString.getBytes("GBK").length);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.e("request", xmlLength + xmlString);
            Throwable e =  session.write(xmlLength + xmlString).getException();
                if (null != e){
                    LogUtil.e("", "请求失败。。。", new Exception(e));
                    AbstractIoSession s = (AbstractIoSession)session;
                    try {
                        s.getHandler().exceptionCaught(s,e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
        }else {
            Log.e("uipad","send msg err! session is null");
        }
    }






}
