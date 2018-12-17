package com.lswd.youpin.weixin.loader;

import com.lswd.youpin.weixin.model.base.AccessToken;

/**
 * accessToken加载接口
 * liruilong
 * Date: 10/11/15
 * @since 1.3.0
 */
public interface AccessTokenLoader {

    /**
     * 获取accessToken
     * @return accessToken，""或NULL会重新从微信服务器获取，并进行refresh
     */
    String get();

    /**
     * 刷新accessToken，实现时需要保存一段时间，以免频繁从微信服务器获取
     * @param token 从微信服务器获取AccessToken
     */
    void refresh(AccessToken token);
}
