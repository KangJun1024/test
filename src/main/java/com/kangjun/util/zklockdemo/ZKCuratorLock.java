package com.kangjun.util.zklockdemo;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class ZKCuratorLock {
    //从配置类中获取分布式锁对象
    private static InterProcessMutex lock =  ZKCuratorManager.getLock();
    //加锁
    public static boolean acquire(){
        try {
            lock.acquire();
            System.out.println(Thread.currentThread().getName() + " acquire success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    //锁的释放
    public static void release(){
        try {
            lock.release();
            System.out.println(Thread.currentThread().getName() + " release success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
