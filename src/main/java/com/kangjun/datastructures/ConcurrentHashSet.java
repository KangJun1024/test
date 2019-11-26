package com.kangjun.datastructures;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  JUC  并发容器Set CRUD
 */
public class ConcurrentHashSet<T> extends AbstractSet<T> {
    /**
     *  应用到Map key唯一的特性 实现Set数据结构
     * @return
     */
    //成员变量
    private ConcurrentHashMap<T,Object> Map = new ConcurrentHashMap<>();
    private Object PRESENT = new Object();
    //计数器 ConcurrentHashMap size方法 不稳定 TODO
    private AtomicInteger count = new AtomicInteger();

    /**
     *  迭代器
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return Map.keySet().iterator();
    }

    @Override
    public int size() {
        return count.get();
    }

    @Override
    public boolean add(T t) {
        count.incrementAndGet();
        return Map.put(t,PRESENT) == null;
    }

    @Override
    public boolean remove(Object o) {
        count.decrementAndGet();
        return Map.remove(o) == PRESENT;
    }

    public static void main(String[] args) {
        ConcurrentHashSet<String> hashSet = new ConcurrentHashSet<>();
        hashSet.add("kang");
        hashSet.add("jun");
        Iterator<String> it = hashSet.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println(hashSet.size());
        hashSet.remove("kang");
        System.out.println(hashSet.size());
    }
}
