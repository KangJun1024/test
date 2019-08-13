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
        int start = 0;
        int end = arrs.length - 1;
        quickSort(arrs,start,end);
    }

    private static void quickSort(Integer[] arrs, int start, int end) {
        //temp: 比较的基准数 首次为第一个元素
        //t: 满足交换条件时的临时变量
        int i,j,temp,t;
        if(start > end){
            return;
        }
        // 两头移动交换
        i = start;
        j = end;
        temp = arrs[start];
        while(i < j){ //不等时，移动左右指针判断符合条件的元素交换
            while(arrs[j] >= temp && i < j){
                j--;
            }
            while(arrs[i] <= temp && i < j){
                i++;
            }
            if(i < j){
                //满足条件时交换
                t = arrs[i];
                arrs[i] = arrs[j];
                arrs[j] = t;
            }
        }
        //交换基准数 i == j
        arrs[start] = arrs[j];
        arrs[j] = temp;
        //基准数两侧递归
        quickSort(arrs,start,j - 1);
        quickSort(arrs,j + 1,end);

    }

}
