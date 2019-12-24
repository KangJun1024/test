package com.kangjun.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TestHashMap {
    public static void main(String[] args) {
//        HashMapThread thread0 = new HashMapThread();
//        HashMapThread thread1 = new HashMapThread();
//        HashMapThread thread2 = new HashMapThread();
//        HashMapThread thread3 = new HashMapThread();
//        HashMapThread thread4 = new HashMapThread();
//        thread0.start();
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
        
//        new TestHashMap().computeNum("100",0);

        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        for (String s : stringList) {
            stringList.remove(s);
        }
        
    }

    public void computeNum(String prefix,int num){
        int num_1 = 1;
        int num_2 = 2;
        num = num > 0? num_1++ : num_2--;
        System.out.println(prefix + num + num_1 + num_2);
    }

}

class HashMapThread extends Thread {
    private static AtomicInteger ai = new AtomicInteger();
    private static Map map = new HashMap<>();

    @Override
    public void run() {
        while (ai.get() < 1000000) {
            map.put(ai.get(), ai.get());
            ai.incrementAndGet();
            System.out.println(ai.get());
        }
    }

}