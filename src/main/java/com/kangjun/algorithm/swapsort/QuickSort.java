package com.kangjun.algorithm.swapsort;

import java.util.Date;

/**
 *  Function: 交换排序 - 快速排序(交换位置)
 *
 *  ①交换基数两边满足条件的元素 ②交换基数
 */
public class QuickSort {
    public static void main(String[] args) {
        long stime = System.currentTimeMillis();
        //int数组
        int []arrs = {1,2,3,5,4,6};
        sort(arrs);
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]);
        }
        long etime = System.currentTimeMillis();
        System.out.println("时间：" + (etime - stime));
    }
    private static void sort(int[] arrs) {
        int high = arrs.length - 1;
        int low = 0;
        //快排三要素 待排序数组 起始元素 末尾元素
        //隐藏排序细节
        quickSort(arrs,low,high);
    }

    private static void quickSort(int[] arrs, int low, int high) {
        int i,j,temp,t; //temp基数中间变量 默认起始位置   t满足条件交换的中间变量
        //出口
        if(low > high){
            return;
        }
        //i j 两头交换移动
        i = low;
        j = high;
        //基准数
        temp = arrs[low];
        while (i < j){
            //先看右边，依次往左递减
            while (arrs[j] >= temp && i < j){
                j--;
            }
            //再看左边，依次往右递增
            while (arrs[i] <= temp && i < j){
                i++;
            }
            if(i < j){
                t = arrs[j];
                arrs[j] = arrs[i];
                arrs[i] = t;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arrs[low] = arrs[i];
        arrs[i] = temp;
        //基准数两边递归执行该方法
        //左半数组
        quickSort(arrs,low,j - 1);
        //右半数组
        quickSort(arrs,j + 1,high);

    }
}
