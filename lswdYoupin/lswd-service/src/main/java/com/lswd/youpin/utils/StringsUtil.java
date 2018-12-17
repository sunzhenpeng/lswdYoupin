package com.lswd.youpin.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 字符串工具类
 * create by zhenguanqi 2017/11/20
 */
public class StringsUtil {

    /**
     * 用户操作卡号的，读取卡号为24BE84A4 将其转化成 A484BE24 存到数据库
     * @param str
     * @return
     */
    public static String getOppositeTwo(String str){
        StringBuilder stringBuilder = new StringBuilder();
        if (str != null && !str.equals("")){
            for (int i = 0; i < str.length(); i+=2 ) {
                String sb = str.substring(str.length() - i - 2,str.length() - i);
                stringBuilder.append(sb);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 用户操作卡号的，读取卡号为24BE84A4 将其转化成 4A48EB42 ,目前系统采用的是上面的转换方法
     * @param str
     * @return
     */
    public static String getOppositeOne(String str){
        StringBuilder stringBuilder = new StringBuilder();
        if (str != null && !str.equals("")){
            for (int i = 0; i < str.length(); i++ ) {
                String sb = str.substring(str.length() - i - 1,str.length() - i);
                stringBuilder.append(sb);
            }
        }
        return stringBuilder.toString();
    }

    public static String encodingChange(String str) throws UnsupportedEncodingException {
        String newStr = "" ;
        if (str != null && !"".equals(str)){
            newStr = new String(str.getBytes("iso8859-1"),"utf-8");
        }
        return newStr;
    }
}
