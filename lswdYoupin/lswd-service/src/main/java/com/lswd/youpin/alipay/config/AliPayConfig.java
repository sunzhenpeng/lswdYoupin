package com.lswd.youpin.alipay.config;

/**
 * Created by liruilong on 2017/7/4.
 */
public class AliPayConfig {

    //新版支付宝需要的参数

    //支付宝网关地址
    public final static String URL = "https://openapi.alipay.com/gateway.do";

    public final static String APP_ID = "2017072907945794";

    public final static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDhoAQGKs0g346VUXv9fQUq/UkL33yd8ty6i0QVVqMNaTd7X290Gwc6Xgfc9Owio7jDSsc1RfrQPDGfBYj7ACfA0+dTcQE30tpaIHZ6fnKPwR13R9ONM4JwQTMcRAjvtpr3N/frOzeEOCh5oJ3Bif3QYe43UZUuA7W6ZQ+d7UAXF4eUmdu+F7dEbZ2Efw2jlnoKvROae0nrhIXMjdSY5jEaDdjAOk3MnY8L+eVAzsIMybqs4eO4s5sNu/a0tY/z/4ja3AL4OrMxafSdMpc7IVgQW/DaiHCgpr8No+/1+0cANsPj8x22wTuB6qQdok2A9A8ONgRN4NrSgeVsF+hZUkcPAgMBAAECggEAT/6v7o/WUsVwhDhzwZhF8MwEzhTa1hV5dRpTdU1WWjbn2PIpTf0XPvZ/yzN7VXy+0V796kF5f8MXxM3tlzCi55c5+TejuusjR7kiyKN0csuBav0oYHPW134GZQsqL/OKVgSd/eTs9hADKbxssmX+e5d9IlfmkH34PLIAK3omswiWFk6L0jCXwto8e60Zfu9XfXiNSINEvwKmTQlv2Ur33h84UVy5OkkXsWKccsMTR6xsTDg9kY2BYtIwhkGqTxvGGblfwpr58QhYZcrGHX7aaX0ze/6R0Uc4PHn0kBHjSP9gTv2JC0urwxRCig5dvHnDJG0Vv7gcU/JTbnVzkq2lwQKBgQD6LiFFFGOJ6hf90mBgwGA8tIE+9CQHTdrzjEZbc8lcO7CxljbHw+BvO+ZFqQ72KozfO2bi62Ld2pr9E1J9Efcep/Js1qkddf3IFMnLkP9JXL+g6yMKNX0g1mLYVJM6QzNYoqXucNJT1FWuz8ZE1zSA9502v4ZJLXr+unfCo2H/dwKBgQDm36fDeTkGfVkGRMQOMVaEDzy5zLgnwJ2/B3n3mDD1YUKvM00MD3/6XtYrlir+wPfuyGEz5yZ88VZwTgcBmWIgisr8X2mFB5f2nLwLQQQ5Lk3yJFhV5OpRRhThDNItJEh2KIOcNDWaWYp+867ormzXODgttPt3YOkHYoLRlJvLKQKBgQCONobHA+4JSb7VlC8hEHokR99ob9PDKyiZZVXhSpJeEbhBWshNCjWcRYqCcrWEjW+CvfugZnnO7yNHVSKA4iN9k66k2+lKS/Y1NEv5obTceRKdYMyMUxeMARIzMvB2IBkh+2MjhZKqAHusYYOtQkAgR6Fgq8Kj+mx+PHUzeTiNxQKBgQDH8m7ilbvTaP+OtENvXz9kQ6X969SZaPG1HY595eqQuNj9ztotVccqWLP3L6iw1QJkZFn6j3f/wStxO8JJmkMnl8xQYXmLYpYzWG5q+LXlp7qNuPhf+08LcDxo46eqvL+cxV2mDHlX4bBWXkje7yEIC4Jr40/dhaqLYL+XLrL2cQKBgFngzoS/4DrGuYuDgBUbmznk4hjBlKLUEGxGV/oK2wXDRuGM3MWGRnJ0EjgbNKYmrP4U6PzoJUctW1MCZLzZhTy+iTQCtvcg3MOnowpQllmiZ7Coj9nPzYhRmKUxAtzUlSzhY2uN198ZvJXcDQCmmsqk9/z/uwsFDNHQY3sTU+CI";

    public final static String FORMAT = "json";

    public final static String CHARSET = "UTF-8";

    //应用公钥
    // public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4aAEBirNIN+OlVF7/X0FKv1JC998nfLcuotEFVajDWk3e19vdBsHOl4H3PTsIqO4w0rHNUX60DwxnwWI+wAnwNPnU3EBN9LaWiB2en5yj8Edd0fTjTOCcEEzHEQI77aa9zf36zs3hDgoeaCdwYn90GHuN1GVLgO1umUPne1AFxeHlJnbvhe3RG2dhH8No5Z6Cr0TmntJ64SFzI3UmOYxGg3YwDpNzJ2PC/nlQM7CDMm6rOHjuLObDbv2tLWP8/+I2twC+DqzMWn0nTKXOyFYEFvw2ohwoKa/DaPv9ftHADbD4/MdtsE7geqkHaJNgPQPDjYETeDa0oHlbBfoWVJHDwIDAQAB";

    //支付宝公钥
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqX/oDVFyzXQ33ciObpntkOyeSFjmtG1B0YikoaIDQ4WdCMCOxxXRpfRhnKOG1fnKSYZBLZFZ1QEjp3DbiLi4KSLTxw0cj9ifySxe+Kuey+u2p5p323RRV2MXfemcPuV2oVrWvFv7TgKhILJnFsuhgjFgbygezifUo1HW3Ff6XAPlsjIKGg9Onr99qLMsDjaPG06ntOsav061wi82Hbrym++kyMFFInVzQP8fHlYJAXxZNc846zLU0Ra6XFIcuu+9PAIYC8x/8eHezYkEZ2uZHLyUcvuTjx3Enei1pcE9hHU4CvuXIVRfO85apWTlzIwxH/LIP/JFXbu2aJqUzyEeRwIDAQAB";

    public final static String NEW_SIGN_TYPE = "RSA2";

    //异步授权回调地址，对应的是支付宝平台的应用网网关地址
    public final static String NOTIFY_URL = "https://web.lsypct.com/lswd-web/pay/aliPay/notifyUrl";

    //授权回调地址
    public final static String RETURN_URL = "https://web.lsypct.com/lswd-web/pay/aliPay/returnUrl";

    public final static String PRODUCT_CODE = "QUICK_WAP_WAY";

    //--------------------------------------------------------------------------------------------------------------------
    // 老版支付宝支付需要的参数
    public final static String PARTNER = "2088721424228431";

    public final static String MD5_KEY = "gjhkk60j72alb406huv6uiwu9wduf1em";

    public final static String INPUT_CHARSET = "utf-8";

    public final static String OLD_SIGN_TYPE = "MD5";

    public final static String PAYMENT_TYPE = "1";

    // 网站商品的展示地址，不允许加?id=123这类自定义参数
    public final static String SHOW_URL = "";
    //-----------------------------------------------------------------------------------------------------------------------


}
