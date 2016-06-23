package com.unipad.utils;

public class StringUtil {

    /**
     * @param value             需要在前面添加零的数字
     * @param expectStringToLen 期望字符串达到的长度
     * @return String
     */
    public static String addZero(int value, int expectStringToLen) {
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(value));
        while (stringBuffer.length() < expectStringToLen) {
            stringBuffer.insert(0, "0");
        }
        return stringBuffer.toString();
    }

}
