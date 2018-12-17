package com.lswd.youpin.service.test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * Created by guolanrui on 16/10/3.
 */
public class GuavaTests {

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private static LoadingCache<String, String> build = null;
    static {
        build = CacheBuilder.newBuilder()
                .refreshAfterWrite(2L, TimeUnit.SECONDS)//自动刷新缓存
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue = "Hello " + key + " ! time = " + sdf.format(new Date());
                        return strProValue;
                    }
                });
    }
    @Test
    public void loadingCacheTest() throws Exception {

//        LoadingCache<String, String> build = CacheBuilder.newBuilder()
//                .refreshAfterWrite(2L, TimeUnit.SECONDS)//自动刷新缓存
//                .build(new CacheLoader<String, String>() {
//            @Override
//            public String load(String key) throws Exception {
//                String strProValue = "Hello " + key + " ! time = " + sdf.format(new Date());
//                return strProValue;
//            }
//        });

        build.put("test1", "value1");
        System.out.println("1 =====" + build.get("test1"));
        Thread.sleep(1000L);
        System.out.println("2 =====" + build.get("test1"));
        Thread.sleep(2000L);
        System.out.println("3 =====" + build.get("test1"));
        Thread.sleep(2000L);
        System.out.println("4 =====" + build.get("test1"));

    }



}
