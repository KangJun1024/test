package com.kangjun.zklockdemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZKCuratorManager {
    private static InterProcessMutex lock;
    private static CuratorFramework cf;
    private static String zkAddr = "127.0.0.1:2181";
    private static String lockPath = "/distribute-lock";
    static {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        cf = CuratorFrameworkFactory.builder()
                .connectString(zkAddr)
                .sessionTimeoutMs(2000)
                .retryPolicy(retryPolicy)
                .build();
        cf.start();
    }

    public static InterProcessMutex getLock(){
        lock = new InterProcessMutex(cf, lockPath);
        return lock;
    }
}
