package com.kangjun.algorithm.mytest;

/**
 *  lambda 用法
 */
public class lambdaTest {
    public static void main(String[] args) {
        //利用lambda表达式创建线程
        new Thread(()-> System.out.println()).start();

    }

}
