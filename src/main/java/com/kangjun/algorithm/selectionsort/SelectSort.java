package com.kangjun.algorithm.selectionsort;

/**
 *  Function: 简单选择排序
 */
public class SelectSort {
    public static void main(String[] args) {
        int []arrs = {1,2,3,5,4,6};
        sort(arrs);
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]);
        }
    }

    private static void sort(int[] arrs) {
        //简单选择排序  max/min  ->  first
        if (null == arrs || arrs.length <= 1) {
            return;
        }
        for (int i = 0; i < arrs.length - 1; i++) {
            int temp;
            int index = i;
            for (int j = i + 1; j < arrs.length; j++) {
                if (arrs[index] > arrs[j]) {
                    index = j;
                }
            }
            temp = arrs[index];
            arrs[index] = arrs[i];
            arrs[i] = temp;
        }
    }

}
