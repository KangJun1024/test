package com.kangjun.algorithm.mytest;

/**
 *  简单选择排序 每趟第一个
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
        if (null == arrs || arrs.length <= 1) {
            return;
        }
        for (int i = 0; i < arrs.length; i++) {    //循环趟数
            int index = i; //比较索引 0 1 2 3 4
            int temp ; //比较的值
            for (int j = i + 1; j < arrs.length; j++) {
                if(arrs[index] < arrs[j]){
                    index = j;
                }
            }
            //交换
            temp = arrs[index];
            arrs[index] = arrs[i];
            arrs[i] = temp;
        }
    }
}
