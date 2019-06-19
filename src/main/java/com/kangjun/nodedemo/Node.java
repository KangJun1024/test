package com.kangjun.nodedemo;

/**
 * 链表
 * @param <K>
 * @param <V>
 */
public class Node<K,V> {
    private final K key;
    private V value;
    private Node<K, V> next;

    public Node(K key, V value, Node<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    /**
     * 获得单向链表（头插法生成的单向链表--后来的在链表头部）
     */
    public static Node<Integer, Integer> getOneWayLinkedList(int length) {
        Node<Integer, Integer> temp = null;
        for (int i = 1; i <= length; i++) {
            //头插法：先来的在链尾
            temp = new Node<>(i, i, temp);   //8 链头 -> 1 -> null
        }
        return temp;
    }

    /**
     * 输出单向链表
     *
     * @param linkedList 单向链表，链表头的位置开始。
     */
    public static void forLinkedList(Node<Integer, Integer> linkedList) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        while (linkedList != null) {
            sb.append("[k：").append(linkedList.getKey()).append(" v：").append(linkedList.getValue()).append("]");
            linkedList = linkedList.getNext();
        }
        sb.append("}");
        System.out.println(sb.toString());
    }

    /**
     * 逆序单链表
     *
     * @param head 单链表
     */
    public static Node<Integer, Integer> reverse1(Node<Integer, Integer> head) {
        //反转之后链表
        Node<Integer, Integer> reverse = null;
        //原链表
        Node<Integer, Integer> current = head;
        while (current != null) {                    // 7 -> 8 -> null
            Node<Integer, Integer> temp = current;
            current = current.getNext();
            temp.setNext(reverse);
            reverse = temp;
        }
        return reverse;
    }

    /**
     * 逆序单链表（递归）
     *
     * @param head 单链表
     */
    public static Node<Integer, Integer> reverse2(Node<Integer, Integer> head) {
        //当为空或者本节点为末尾节点的时候
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node<Integer, Integer> reversedHead = reverse2(head.getNext());
        //获取先前的下一个节点，让该节点指向自身
        head.getNext().setNext(head);
        //破坏以前自己指向下一个节点
        head.setNext(null);
        //层层传递给最上面的
        return reversedHead;
    }

    public static void main(String[] args) {
        //获得链表数据结构
        Node node = getOneWayLinkedList(8);
        //输出链表节点数据
        forLinkedList(node);
        //链表反转
        Node rNode = reverse1(node); // 887 776 665 554
        //输出链表节点数据
        forLinkedList(rNode);
        //链表反转
        Node r2Node = reverse2(rNode);
        //输出链表节点数据
        forLinkedList(r2Node);

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

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }
}
