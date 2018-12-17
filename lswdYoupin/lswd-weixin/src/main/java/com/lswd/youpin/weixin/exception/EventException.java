package com.lswd.youpin.weixin.exception;

import com.lswd.youpin.weixin.model.message.receive.event.RecvEventType;

/**
 * 微信事件异常，接收微信消息时有可能抛出
 *liruilong
 * Date: 5/11/15
 * @see RecvEventType#from
 * @since 1.4.0
 */
public class EventException extends RuntimeException {

    public EventException() {
        super();
    }

    public EventException(String message) {
        super(message);
    }

    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventException(Throwable cause) {
        super(cause);
    }

    protected EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
