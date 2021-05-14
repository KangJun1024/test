package com.kangjun.util.redislockdemo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Redis实现分布式锁
 */
public class RedisLock {

    /**
     * 进程获得锁
     * @param key  方法
     * @param timeout  超时时间
     * @return UUID
     */
    public String getLock(String key,int timeout){
        try{
            Jedis jedis = RedisManager.getJedis();
            String value = UUID.randomUUID().toString();
            //模拟锁阻塞状态  （多路复用）
            long end = System.currentTimeMillis() + timeout;
            while (System.currentTimeMillis() < end ){
                if(jedis.setnx(key,value) == 1){
                    //设置key失效时间 （模拟超时释放锁）
                    jedis.expire(key,timeout);
                    return value;
                }
                Thread.sleep(1000);
                //双重判断 是否设置过期时间
                if(jedis.ttl(key) == -1){
                    jedis.expire(key,timeout);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 进程释放锁 删除key
     * @param key  方法
     * @param value  UUID
     * @return
     */
    public boolean releaseLock(String key,String value){
        try {
            Jedis jedis = RedisManager.getJedis();
            while (true){
                jedis.watch(key); //监控key
                if(value.equals(jedis.get(key))){
                    //开启事务
                    Transaction transaction = jedis.multi();
                    jedis.del(key);
                    List<Object> list = transaction.exec();
                    //删除失败 循环删除
                    if(list == null){
                        continue;
                    }
                    return true;
                }
                jedis.unwatch();
                break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
