package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.CardPayThin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liruilong on 2017/8/11.
 */
@Component
public class CardPayThinImpl implements CardPayThin {
    @Value("${password.yan}")//会员密码加盐
    private String passwordYan;
}