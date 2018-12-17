package com.lswd.youpin.commons;

/**
 * Created by liruilong on 2017/7/19.
 */
public enum LoginType {
    USER("User"),  ASSOCIATOR("Associator"),WxUser("WxUser"),CounterUser("CounterUser");


    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
