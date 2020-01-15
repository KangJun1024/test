package com.kangjun.util.concurrent.MyFuture;

/**
 * @author kangjun
 * @version 1.0
 * 回调器
 * result：worker执行结果
 */
public interface Listener {
    void result(Object result);
}