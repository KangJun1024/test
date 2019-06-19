package com.kangjun.redislockdemo;

public class DistributedLockTest {
    public static void main(String[] args) {
        //使用栅栏模拟并发情况
        String key = "test123";
        //加锁
        RedissonLock.acquire(key);
        //执行具体业务逻辑
        System.out.println("第一次获得分布式锁");
        //加锁
        RedissonLock.acquire(key);
        //执行具体业务逻辑
        System.out.println("第二次次获得分布式锁");
        RedissonLock.acquire(key);
        RedissonLock.acquire(key);
        RedissonLock.acquire(key);
        RedissonLock.acquire(key);


        //释放锁
        RedissonLock.release(key);



    }


}
