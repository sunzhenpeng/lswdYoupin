package com.lswd.youpin.weixin.exception;

import java.util.Map;

/**
 * 微信异常，微信服务器返回错误时抛出的异常
 *liruilong
 * Date: 5/11/15
 * @since 1.0.0
 */
public class WechatException extends RuntimeException {

    /**
     * 微信返回的errcode
     */
    private Integer code;

    public WechatException(Map<String, ?> errMap) {
        super("[" + errMap.get("errcode") + "]" + errMap.get("errmsg"));
        code = (Integer)errMap.get("errcode");
    }

    public WechatException() {
        super();
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatException(Throwable cause) {
        super(cause);
    }

    protected WechatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return code;
    }
}
