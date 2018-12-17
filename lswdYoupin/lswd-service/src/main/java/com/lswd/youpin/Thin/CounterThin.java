package com.lswd.youpin.Thin;

import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/11/16.
 */
public interface CounterThin {

    LsResponse getBingCounterUserList(String keyword,String counterId);

    LsResponse getCounterUserLinkedList(String counterId,String keyword);
}
