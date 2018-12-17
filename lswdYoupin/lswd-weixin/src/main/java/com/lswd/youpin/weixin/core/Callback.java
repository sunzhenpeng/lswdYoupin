package com.lswd.youpin.weixin.core;

/**
 *liruilong
 * Date: 17/4/8
 * @since 1.4.0
 **/
public interface Callback<T> {

    /**
     * 成功时回调
     * @param t 结果类型
     */
    void onSuccess(T t);

    /**
     * 失败时回调
     * @param e Exception
     */
    void onFailure(Exception e);
}
