package com.kangjun.algorithm.mytest;

/**
 *  简单选择排序  GG
 */
public class SelectSortTest {
    public static void main(String[] args) {
        //定义数组
        Integer arrs[] = {4,8,6,1,2,9};
        selectSort(arrs);
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]);
        }

    }

    private static void selectSort(Integer[] arrs) {
        for (int i = 0; i < arrs.length - 1; i++) {
            //用于交换中间变量
            int temp;
            //交换的索引
            int index = i;
            for (int j = i + 1; j < arrs.length; j++) {  //每一个元素以此和后边的每一个元素做比较交换
                if(arrs[index] < arrs[j]){
                    //符合条件的索引
                    index = j;
                }
            }
            temp = arrs[i];
            arrs[i] =  arrs[index];
            arrs[index] = temp;
        }
    }
}
