package com.lswd.youpin.weixin.utils;

import java.util.Arrays;

/**
 * Created by liruilong on 2017/4/12.
 */
public class CheckUtil {

    private static final String token = "weixin0128";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuffer str = new StringBuffer();
        for (String s : arr) {
            str.append(s);
        }
        String res = Sha1Utils.encode(String.valueOf(str));
        return res.equals(signature);
    }
}
