package com.kangjun.threadpool;

import com.kangjun.threadpool.impl.DefaultThreadPool;

public class ThreadPoolTest {
    public static void main(String[] args) {

        ThreadPool threadPool = new DefaultThreadPool();
        threadPool.execute(new Job());
        System.out.println("阻塞队列个数:" + threadPool.getJobSize());
        threadPool.shutdown();

    }

    //定义工作任务
    static class Job implements Runnable {

        public void run() {
            System.out.println("我被康俊写的线程池执行啦，哈哈哈");
        }
    }
}
