package com.kangjun.redislockdemo;

import java.util.UUID;
import java.util.concurrent.CyclicBarrier;

/**
 * 使用CyclicBarrier模拟并发获取分布式锁
 */
public class CucTest {
	public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++){
        	new Writer(barrier).start();
        }
    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
 
        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
            String key = "test123";
            //加锁
            RedissonLock.acquire(key);
            System.out.println("线程"+ Thread.currentThread().getName() +"获得分布式锁");
            try {
                Thread.sleep(2000);
                RedissonLock.release(key);
                System.out.println("线程"+Thread.currentThread().getName()+"释放分布式锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("END");
        }
    }
}