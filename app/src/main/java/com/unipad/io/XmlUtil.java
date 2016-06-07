package com.unipad.io;

import android.text.format.Time;
import android.util.SparseArray;

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
            Time time = new Time("GMT+8");
            time.setToNow();
            int year = time.year;
            int month = time.month+1;
            int day = time.monthDay;
            int minute = time.minute;
            int hour = time.hour;
            int sec = time.second;

            String date = year+format(month)+format(day);
            String texTime = format(hour)+format(minute)+format(sec);

            build.append("<TRXCODE>").append(headType).append("</TRXCODE>").append("<TRXDATE>")
                    .append(date).append("</TRXDATE>").append("<TRXTIME>").append(texTime).append("</TRXTIME>");
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
