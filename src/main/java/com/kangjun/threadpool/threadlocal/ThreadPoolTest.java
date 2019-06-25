package com.kangjun.threadpool.threadlocal;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 代码（1）创建了一个核心线程数和最大线程数为5的线程池，这个保证了线程池里面随时都有5个线程在运行。
 * 代码（2）创建了一个ThreadLocal的变量，泛型参数为LocalVariable，LocalVariable内部是一个Long数组。
 * 代码（3）向线程池里面放入50个任务
 * 代码（4）设置当前线程的localVariable变量，也就是把new的LocalVariable变量放入当前线程的threadLocals变量。
 * 由于没有调用线程池的shutdown或者shutdownNow方法所以线程池里面的用户线程不会退出，进而JVM进程也不会退出。
 */
public class ThreadPoolTest {

    static class LocalVariable {
        private Long[] a = new Long[1024 * 1024];
    }

    // (1)
    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>());
    // (2)
    final static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<LocalVariable>();

    public static void main(String[] args) throws InterruptedException {
        // (3)
        for (int i = 0; i < 50; ++i) {
            poolExecutor.execute(new Runnable() {
                public void run() {
                    // (4)
                    localVariable.set(new LocalVariable());
                    // (5)
                    System.out.println("use local varaible");
                    localVariable.remove();

                }
            });

            Thread.sleep(1000);
        }
        // (6)
        System.out.println("pool execute over");
    }
}