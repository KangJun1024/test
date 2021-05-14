package com.kangjun.util.redislockdemo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisPool pool = new JedisPool(config, "localhost",);
 */
public class RedisManager {

    static JedisPool jedisPool = null;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(2);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.100.5",6379);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
