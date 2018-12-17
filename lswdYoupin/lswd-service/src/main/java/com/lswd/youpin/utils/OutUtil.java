package com.lswd.youpin.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by liruilong on 2017/7/11.
 */
public class OutUtil {
    public static void out(ServletResponse servletResponse, Map<String, String> resultMap) {
        PrintWriter out = null;
        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json");
            out = servletResponse.getWriter();
            out.println(JSONObject.toJSON(resultMap).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
