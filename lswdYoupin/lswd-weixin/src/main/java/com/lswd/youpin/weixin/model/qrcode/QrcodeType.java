package com.lswd.youpin.weixin.model.qrcode;

/**
 *hanxiaoyu
 * Date: 9/11/15
 */
public enum QrcodeType {

    /**
     * 临时二维码，有过期时间，最长7天，604800s
     */
    QR_SCENE("QR_SCENE"),

    /**
     * 永久二维码，最多100000个
     */
    QR_LIMIT_SCENE("QR_LIMIT_SCENE"),
    
    /**
     * 永久二维码，字符串类型，长度限制为1到64
     */
    QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE");

    private String value;

    private QrcodeType(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
