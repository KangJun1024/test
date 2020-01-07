package com.kangjun.threadpool.lock;

/**
 *  模拟死锁案例
 *
 *  死锁：至少两个线程占用对方需要的锁不释放
 */
public class DeadLock implements Runnable{
    private int flag = 1;
    static Object a = new Object();
    static Object b = new Object();

    @Override
    public void run() {
        System.out.println("flag: " + flag);
        if (1 == flag){
            synchronized (a){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    System.out.println(1);
                }
            }
        }
        if (0 == flag){
            synchronized (b){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    System.out.println(0);
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock deadLockA = new DeadLock();
        DeadLock deadLockB = new DeadLock();
        deadLockA.flag = 1;
        deadLockB.flag = 0;
        new Thread(deadLockA).start();
        new Thread(deadLockB).start();

    }
}
