package com.kangjun.util.concurrent.MyFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BootstrapNew {
 
    public static void main(String[] args) {
        BootstrapNew bootstrap = new BootstrapNew();
 
        Worker worker = bootstrap.newWorker();
 
        Wrapper wrapper = new Wrapper();
        wrapper.setWorker(worker);
        wrapper.setParam("hello");
        //添加结果回调器
        wrapper.setListener(new Listener() {
            @Override
            public void result(Object result) {
                System.out.println(Thread.currentThread().getName());
                System.out.println(result);
            }
        });
 
        CompletableFuture future = CompletableFuture.supplyAsync(() -> bootstrap.doWork(wrapper));
        try {
            future.get(1800, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            //超时了
            wrapper.getListener().result("time out exception");
        }
        System.out.println(Thread.currentThread().getName());
    }
 
    private Wrapper doWork(Wrapper wrapper) {
        Worker worker = wrapper.getWorker();
        String result = worker.action(wrapper.getParam());
        wrapper.getListener().result(result);
 
        return wrapper;
    }
 
    private Worker newWorker() {
        return new Worker() {
            @Override
            public String action(Object object) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return object + " world";
            }
        };
    }
 
}