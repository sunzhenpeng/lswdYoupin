package com.lswd.youpin.shiro.kit;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by liruilong on 2017/6/27.
 */
public class ShiroKit {

    public static String md5(String originPassword,String salt){
        return new Md5Hash(originPassword,salt).toString();
    }
    public static void main(String args[]){
        System.out.println(md5("qdyx123","admin"));
        System.out.println(md5("root","654321"));
        System.out.println(md5("root","654321"));
    }
}
