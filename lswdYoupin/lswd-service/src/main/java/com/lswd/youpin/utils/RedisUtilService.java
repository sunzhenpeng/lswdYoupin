package com.lswd.youpin.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by liuhao on 2017/6/16.
 */
public class RedisUtilService {
   private static String ADDR = "127.0.0.1";//redis服务器ip
    //private static String ADDR = "114.215.108.23";//redis服务器ip
    private static int PORT = 6379;//redis服务端口
    private static int MAX_ACTIVE = 1024;//可连接的最大实例数
    private static int MAX_IDLE = 200;//控制一个pool最多有多少个空闲的jedis实例
    private static int MAX_WAIT = 10000;//等待链接的最大时间，单位毫秒
    private static int TIMEOUT = 10000;
    private static boolean TEST_ON_BORROW = true;//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

}
