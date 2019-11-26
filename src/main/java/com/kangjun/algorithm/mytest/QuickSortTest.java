package com.kangjun.algorithm.mytest;

/**
 *  快速排序 交换排序 二分法 GGGG
 */
public class QuickSortTest {
    public static void main(String[] args) {
        //定义数组
        Integer arrs[] = {4,8,6,1,2,9};
        sort(arrs);
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]);
        }
    }

    private static void sort(Integer[] arrs) {
        //快速排序 第一个数值比较交换 分合思想
        int start = 0;
        int end = arrs.length - 1;
        quickSort(arrs,start,end);
    }

    private static void quickSort(Integer[] arrs, int start, int end) {
        //i: 左指针移动起始位置 j: 右指针移动起始位置 t: 左右交换临时变量 temp: 基准数交换临时变量
        if(start > end){
            return;
        }
        int i,j,temp,t;
        i = start;
        j = end;
        temp = arrs[start];
        // i >= j 交换基准数
        while (i < j){
            while (arrs[j] <= temp && i < j){
                j--;
            }
            while (arrs[i] >= temp && i < j){
                i++;
            }
            if(i < j){
                t = arrs[i];
                arrs[i] = arrs[j];
                arrs[j] = t;
            }
        }
        arrs[start] = arrs[j];
        arrs[j] = temp;
        //i == j 交换基准数的元数
        quickSort(arrs,start,j - 1);
        quickSort(arrs,j + 1,end);

    }

}
