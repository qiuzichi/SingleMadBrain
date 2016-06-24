package com.unipad.io;

import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.util.SparseArray;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by gongkan on 2016/6/6.
 */
public class XmlUtil {

    //private static String CODE_HEAD = "<?xml version="1.0<?xml version="1.0" encoding="GBK"?>" encoding="GBK"?>"
    public static String buildXmlString(String headType,Map<String,String> body)
    {
        StringBuilder build = new StringBuilder("<?xml version=\'1.0\' encoding=\'GBK\'?><TRX> <HEAD>");
        if (headType != null) {
            Calendar calendar = Calendar.getInstance();
            String date = DateFormat.format("yyyyMMdd kkmmss",
                    calendar.getTime()).toString();

            Log.e("",date);

            build.append("<TRXCODE>").append(headType).append("</TRXCODE>").append("<TRXDATE>")
                    .append(date.replace(" ", "</TRXDATE><TRXTIME>")).append("</TRXTIME>");
            //for (Map.Entry<String, String> entry : head.entrySet()) {
             //   build.append("<"+entry.getKey()+">").append(entry.getValue()).append("</"+entry.getKey()+">");
            //}
        }
        build.append("</HEAD>").append("<BODY>");
        if (body != null) {
            for (Map.Entry<String, String> entry : body.entrySet()) {
                build.append("<"+entry.getKey()+">").append(entry.getValue()).append("</"+entry.getKey()+">");
            }
        }
        build.append("</BODY>").append("</TRX>");
        return build.toString();
    }
    private static String format(int temp){
        if (temp<10) {
            return "0"+temp;
        }else {
            return temp+"";
        }
    }
}
