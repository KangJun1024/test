package com.kangjun.datastructures.nodedemo;

/**
 *  链表反转实战 单向链表类
 */
public class MyNode<K,V> {
    //属性
    private final K key;
    private V value;
    private MyNode<K,V> next;

    public MyNode(K key, V value, MyNode<K, V> node) {
        this.key = key;
        this.value = value;
        this.next = node;
    }

    /**
     *  获得单向链表（头插法）
     * @return
     */
    public static MyNode<Integer,Integer> getOneWayLinkedList(int length){
        MyNode<Integer,Integer> temp = null;
        for (int i = 0; i < length; i++) {
            temp = new MyNode<>(i,i,temp);
        }
        return temp;
    }

    /**
     *  输出单向链表
     * @return
     */
    public static void forLinkedList(MyNode<Integer,Integer> linkedList){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        while (linkedList != null){
            sb.append("[k: ").append(linkedList.getKey()).append(" v: ").append(linkedList.getValue())
                    .append("]");
            linkedList = linkedList.getNext();
        }
        sb.append("}");
        System.out.println(sb.toString());
    }

    /**
     *  链表反转
     * @param head
     */
    public static MyNode<Integer,Integer> reverse(MyNode<Integer,Integer> head){
        //反转之后的链表
        MyNode<Integer,Integer> reverse = null;
        //原链表
        MyNode<Integer,Integer> current = head;
        while (current != null){
            //定义临时链表
            MyNode<Integer,Integer> temp = current;   // 7 6 5 4 3 2 1
            current = current.getNext(); //7 6 5 4 3 2 1
            temp.setNext(reverse);
            reverse = temp;
        }
        return reverse;            // null  7
    }

    /**
     *  链表反转（递归）
     * @param head
     */
    public static MyNode<Integer,Integer> reverse2(MyNode<Integer,Integer> head){
        //递归出口
        if (head == null || head.getNext() == null){
            return head;
        }
        MyNode<Integer,Integer> reversedHead = reverse2(head.getNext());
        //获取先前的下一个节点，让节点指向自身
        head.getNext().setNext(head);
        //破坏以前自己指向的下一个节点
        head.setNext(null);
        return reversedHead;
    }

    public static void main(String[] args) {
        //获得长度为8的单向链表
        MyNode<Integer, Integer> oneWayLinkedList = getOneWayLinkedList(8);
        //遍历链表
        forLinkedList(oneWayLinkedList);
        //链表反转
        MyNode<Integer, Integer> reverse = reverse(oneWayLinkedList);
        //遍历链表
        forLinkedList(reverse);
        //链表反转（递归）
        MyNode<Integer, Integer> reverse2 = reverse2(reverse);
        //遍历链表
        forLinkedList(reverse2);

    }





    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public MyNode<K, V> getNext() {
        return next;
    }

    public void setNext(MyNode<K, V> next) {
        this.next = next;
    }
}
