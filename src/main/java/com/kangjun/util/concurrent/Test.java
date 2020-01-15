package com.kangjun.util.concurrent;

import java.util.concurrent.*;
 
/**
 * @author kangjun
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(new Task());
        //这一步get会阻塞当前线程
        System.out.println(future.get());
 
        executor.shutdown();
    }
 
    private static class Task implements Callable<Integer> {
 
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(2000);
            return 1;
        }
 
    }
 
}