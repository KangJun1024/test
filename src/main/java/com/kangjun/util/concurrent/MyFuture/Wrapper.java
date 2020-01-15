package com.kangjun.util.concurrent.MyFuture;

/**
 * @author kangjun
 * @version 1.0
 * 包装器
 */
public class Wrapper {
    private Object param;
    private Worker worker;
    private Listener listener;
 
    public Object getParam() {
        return param;
    }
 
    public void setParam(Object param) {
        this.param = param;
    }
 
    public Worker getWorker() {
        return worker;
    }
 
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
 
    public Listener getListener() {
        return listener;
    }
 
    public void setListener(Listener listener) {
        this.listener = listener;
    }
}