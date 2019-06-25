package com.kangjun.algorithm.swapsort;

/**
 *  Function: 交换排序 - 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        long stime = System.currentTimeMillis();
        //int数组
        int []arrs = {1,2,3,5,4,6};
        sort(arrs);
        long etime = System.currentTimeMillis();
        System.out.println("时间：" + (etime - stime));
    }

    private static void sort(int[] arrs) {
        for (int i = 0; i < arrs.length - 1; i++) { //n-1趟就可以出结果 每趟排除一个值(max/min)
            for (int j = 0; j < arrs.length - 1 - i; j++) { //每趟的比较次数也逐渐减1
                if(arrs[j] > arrs[j + 1]){
                    //交换位置 引入中间变量
                    int temp = arrs[j];
                    arrs[j] = arrs[j + 1];
                    arrs[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]);
        }

    }
}
