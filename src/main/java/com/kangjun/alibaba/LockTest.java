package com.kangjun.alibaba;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        try {
            boolean flag = lock.tryLock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}