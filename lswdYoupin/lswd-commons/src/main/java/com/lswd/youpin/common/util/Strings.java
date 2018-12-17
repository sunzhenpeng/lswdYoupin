package com.lswd.youpin.common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *liruilong
 * Date: 17/04/11
 */
public class Strings {

    /**
     * 判断字符串是否为null或""
     * @param s 字符串
     * @return 若为null或""返回true，反之false
     */
    public static Boolean isNullOrEmpty(String s){
        return s == null || "".equals(s);
    }


    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String urlHandle(String url) throws UnsupportedEncodingException {

        String[] urls = url.split("\\/");
        String prefix = urls[3];
        return prefix;
    }
    public static String paramHandle(String param) throws UnsupportedEncodingException {

        if (param != null && !(param.equals(""))) {
            // String tmp = URLDecoder.decode(keyword, "UTF-8");
            param = new String(param.getBytes("UTF-8"), "UTF-8");
        }else {
            param = "";
    }
        return param;
    }

    public static String threeNumberToString(String pre,int i){

        String b=String.valueOf(i);//转换成string类型
        b = (b.length() == 1 ? "000" + b : b.length() == 2 ? "00" + b: b.length() == 3 ? "0" + b : b);
        b=pre+""+b;
        System.out.println(b);//输出结果为：B0001
        return b;
    }

    public static void main(String[] args) {
        System.out.printf(threeNumberToString("ls",0));
    }

}
