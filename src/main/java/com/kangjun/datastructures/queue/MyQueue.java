package com.kangjun.datastructures.queue;

import java.util.ArrayList;
import java.util.Queue;

/**
 *  手动实现队列
 *  队列是一种先进先出的一种数据结构
 *  基于ArrayList实现
 */
public class MyQueue<E> {
    ArrayList<E> arrayList;

    /**
     *  有参数的构造函数
     */
    public MyQueue(int capacity){
        arrayList = new ArrayList<>(capacity);
    }
    /**
     *  无参数的构造函数
     */
    public MyQueue(){
        arrayList = new ArrayList<>();
    }
    /**
     *  队列大小
     */
    public int size(){
        return arrayList.size();
    }
    /**
     * 队列添加元素
     */
    public Boolean push(E e){
        return arrayList.add(e);
    }
    /**
     * 删除第一个元素
     */
    public E remove(){
        return  arrayList.remove(0);
    }
    /**
     *  判断队列是否为空
     */
    public boolean isEmpty(){
        return  arrayList.isEmpty();
    }
    /**
     * 查看队列第一个元素
     * @return
     */
    public E getFront() {
        return arrayList.get(0);
    }
    //打印（队列）
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size(); i++) {
            buffer.append(arrayList.get(i));
            if(i < size()-1) {
                buffer.append("->");
            }
        }
        return "Stack [ arrayList = top | " +
                buffer.toString()
                + " ] ";
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.push(i);
        }
        System.out.println(queue);
        queue.remove();
        System.out.println(queue);
        queue.remove();
        System.out.println(queue);
    }

}
