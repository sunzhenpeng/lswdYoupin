package com.lswd.youpin.web.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by liruilong on 2016/10/17.
 */
@Aspect
@Component
public class AOPHttpStatusCode {

    protected final Logger log = LoggerFactory.getLogger(AOPHttpStatusCode.class);

    private static final String METHOD_GET = "GET";

    public static final int CODE_503 = 503;

   /* @Pointcut("within(com.lswd.youpin.web.controller.*)")
    private void pointCut(){}

    @AfterReturning(pointcut="pointCut()", returning="returnValue")
    public void after(JoinPoint joinPoint, Object returnValue){

    }*/

}
