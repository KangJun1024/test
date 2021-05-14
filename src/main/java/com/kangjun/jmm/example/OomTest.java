package com.kangjun.jmm.example;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  运行时数据区
 *   内存溢出分为两大类：OutOfMemoryError和StackOverflowError
 */
public class OomTest {

    static class Key{
        Integer id;

        public Key(Integer id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Key key = (Key) o;
//            return Objects.equals(id, key.id);
//        }
    }

    /**
     *  堆内存溢出 -Xms20m -Xmx20m  堆内存参数调优
     *   java.lang.OutOfMemoryError:Java heap space
     */
    @Test
    public void heapOomError(){
        List<byte[]> list = new ArrayList<>();
        int i = 0;
        while (true){
            list.add(new byte[5 * 1024 * 1024]);
            System.out.println("count is: " + (++i));
        }

    }

    /**
     *  堆内存泄露 -Xms20m -Xmx20m  不能被垃圾回收器识别 GCRoot
     *   java.lang.OutOfMemoryError:Java heap space
     */
    @Test
    public void MemoryLeakOomError(){
       Map m = new HashMap();
       while (true){
           for (int i = 0; i < 10; i++) {
               if (!m.containsKey(new Key(i))) {
                   m.put(new Key(i),i);
               }
           }
       }

    }

    /**
     *  栈内存泄露 -Xms20m -Xmx20m  不能被垃圾回收器识别 GCRoot
     *   java.lang.OutOfMemoryError:Java heap space
     */
    @Test
    public void StackOomError(){
        new OomTest().testStack();

    }

    /**
     *  创建本地线程内存溢出
     */
    @Test
    public void UnableCreateNativeError(){
        while (true){
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(() -> System.out.println("aaaa"));
        }
    }







    public void testStack(){
        int num = 1;
        num++;
        this.testStack();
        System.out.println(num);
    }






}


