package com.lswd.youpin.shiro.filter;

/**
 * Created by liruilong on 2017/6/22.
 */

import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.SerializeUtils;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/********
 * 会话超时控制过滤器
 *
 * @author liruilong
 *         <p/>
 *         类功能： 用于ajax和普通请求，会话超时
 */

public class
SessionExpireFilter extends AdviceFilter {

    @Autowired
    private RedisManager redisManager;

    private static final Logger log = LoggerFactory.getLogger(SessionExpireFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info("进行token验证SessionExpireFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        boolean flag = false;
        if ("OPTIONS".equals(req.getMethod())) {
            rep.setHeader("Access-Control-Allow-Origin", "*");
            rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            rep.setHeader("Access-Control-Max-Age", "3600");
            rep.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,token,type,canteenId");
            return flag;
        }
        try {
            String token = req.getHeader("token");
            flag = false;
            if (token != null && !"".equals(token)) {
                Object value = SerializeUtils.deserialize(redisManager.get(token.getBytes()));
                if (value != null) {
                    flag = true;
                    redisManager.setKeyExpire(token.getBytes(), ConstantsCode.SESSION_EXPIRE);
                } else {
                    log.info("token超时");
                    rep.addHeader("flag", "token已失效");
                    PrintWriter out = rep.getWriter();
                    out.print("token out time");
                }
            } else {
                log.info("请求中没有token值");
                PrintWriter out = rep.getWriter();
                out.print("header no token");
            }
        } catch (Exception e) {
            log.info("token校验抛出异常=============：={}", e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }


}