package com.lswd.youpin.processor;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.commons.LsAppException;
import com.lswd.youpin.request.Request;
import com.lswd.youpin.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * Created by liruilong on 16/7/31.
 */
public abstract class AbstractProcessor<S extends Request, T extends Response> {

	/**
	 * 使用指定类初始化日志对象
	 * 在日志输出的时候，可以打印出日志信息所在类
	 * 如：Logger logger = LoggerFactory.getLogger(com.Book.class);
	 *  	logger.debug("日志信息");
	 *  将会打印出: com.Book : 日志信息
	 */
    protected final Logger log = LoggerFactory.getLogger(AbstractProcessor.class);
    protected final Logger latencyLog = LoggerFactory.getLogger("latencyLog");


    @Value("${contextPath}")
    protected String contextPath;

    //S代表request T代表response
    //此处使用的是模版设计模式的模版方法
    public T execute(S request){
    	//利用反射记录日志
        Class tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.info("{} is being executed. request = {}", tClass.getSimpleName(), JSON.toJSON(request));

        try {
            // 校验,失败时抛异常
            validate(request);
            // 不要抛出异常,信息封装到response中
            Long startTime = System.currentTimeMillis();
            T response = executeRequest(request);
            latencyLog.info("{}'s latency is {} ms", this.getClass().getSimpleName(), System.currentTimeMillis()-startTime);
            log.info("{} is executed. response = {}", tClass.getSimpleName(), JSON.toJSON(response));
            return response;
        } catch (LsAppException e) {
            log.error(e.getMessage(), e);
            // 利用反射动态创建相应的response,返回错误信息
            String responseName = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
            try {
                Class responseClass = Class.forName(responseName);
                T responseIns = (T) responseClass.newInstance();
                Method setAsFailureMethod = responseClass.getMethod("setAsFailure", null);
                Method setErrorCodeMethod = responseClass.getMethod("setErrorCode", new Class[]{String.class});
                Method setMessageMethod = responseClass.getMethod("setMessage", new Class[]{String.class});
                setAsFailureMethod.invoke(responseIns, null);
                setErrorCodeMethod.invoke(responseIns, e.getCodeMessage().getCode());
                setMessageMethod.invoke(responseIns, e.getCodeMessage().getMsg());//设置异常消息
                return responseIns;
            } catch (Exception e1) {
                //TODO 反射异常做处理,返回给前端

                log.error(e.getMessage(), e1);
            }
        }
        return null;
    }
    
    protected abstract void validate(S request) throws LsAppException;

    protected abstract T executeRequest(S request);

}
