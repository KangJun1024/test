package com.kangjun.threadpool.lock;

public class TestDeadLock implements Runnable {
    public int flag = 1;
    static Object resourceA = new Object();
    static Object resourceAB = new Object();

    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag==1){
            synchronized (resourceA) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (resourceAB){
                    System.out.println("1");
                }
            }
        }

        if (flag==0){
            synchronized (resourceAB){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resourceA){
                    System.out.println("0");
                }
            }
        }
    }
    public static void main(String[] args) {
        TestDeadLock t1 = new TestDeadLock();
        TestDeadLock t2 = new TestDeadLock();
        t1.flag = 1;
        t2.flag = 0;
        new Thread(t1).start();
        new Thread(t2).start();
    }
}