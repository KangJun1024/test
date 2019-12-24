package com.kangjun.algorithm.Heap;

/**
 *  栈上分配对象内存空间
 */
public class HeapOptimize {

    public static void main(String[] args) {

    }

    static StringBuilder getStringBuilder1(String a, String b) {
        StringBuilder builder = new StringBuilder(a);
        builder.append(b);
        return builder;   // builder通过方法返回值逃逸到外部
    }

    static String getStringBuilder2(String a, String b) {
        StringBuilder builder = new StringBuilder(a);
        builder.append(b);
        return builder.toString();  // builder范围维持在方法内部，未逃逸
    }

}
