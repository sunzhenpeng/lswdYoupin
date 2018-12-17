package com.lswd.youpin.web.utils;

import com.google.common.base.Strings;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liruilong on 17/4/11
 */
public class CookieUtils {

//    private static final String COOKIE_NAME = "AppLoginID";


    /**
     * 根据cookie名称获取cookie值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request,String cookieName) {
        String value = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        if (Strings.isNullOrEmpty(cookieName)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                value = cookie.getValue();
                break;
            }
        }
        return value;
    }

    /**
     * 设置cookie
     * @param response
     * @param cookieName  名称
     * @param cookieValue  值
     * @param domain   域名
     * @param path   路径
     * @param expiry  有效时长(单位:秒)
     * @param isHttpOnly
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue,String domain,String path,int expiry,boolean isHttpOnly) {
        if (response == null) {
            return;
        }
        if (Strings.isNullOrEmpty(cookieName)) {
            return;
        }
        Cookie cookie = new Cookie(cookieName, cookieValue);
//        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(expiry);
//        cookie.setHttpOnly(isHttpOnly);// TODO 待测试
        response.addCookie(cookie);
    }

    public static boolean removeCookie(HttpServletResponse response, String cookieName) {

        if (response == null) {
            return false;
        }
        if (Strings.isNullOrEmpty(cookieName)) {
            return false;
        }
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return true;
    }


}
