package com.kangjun.algorithm.mytest;

/**
 *  冒泡排序 左右比较交换方式
 */
public class DubbleSortTest {
    public static void main(String[] args) {
        //定义数组
        Integer arrs[] = {4,8,6,1,2,9};
        dubbleSort(arrs);
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]);
        }
    }

    private static void dubbleSort(Integer[] arrs) {
        for (int i = 0; i < arrs.length - 1; i++) {  //比较趟数 每趟确定一个数
            for (int j = 0; j < arrs.length - 1 - i; j++) { //每趟比较的次数 && 交换满足条件元素的位置
                if(arrs[j] <= arrs[j + 1]){
                    //交换位置
                    int temp = arrs[j];
                    arrs[j] = arrs[j + 1];
                    arrs[j + 1] = temp;
                }
            }
        }
    }
}
