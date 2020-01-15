package com.kangjun.util.concurrent.MyFuture;

/**
 * @author kangjun
 * @version 1.0
 * 异步工作线程
 */
public interface Worker {
    //耗时任务
    String action(Object object);
}