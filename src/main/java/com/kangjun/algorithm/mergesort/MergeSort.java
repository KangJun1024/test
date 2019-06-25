package com.kangjun.algorithm.mergesort;

/**
 *  Function: 归并排序
 */
public class MergeSort {
    public static void main(String[] args) {
        //int数组
        int []arrs = {1,2,3,5,4,6};
        sort(arrs);
    }

    private static void sort(int[] arrs) {
        //隐藏实现细节
        int high = arrs.length - 1;
        int low = 0;
        mergeSort(arrs,low,high);

    }

    private static void mergeSort(int[] arrs, int start, int end) {
        if(start >= end) return;
        //递归
        int middle= (start + end)/2;
        mergeSort(arrs,start,middle);
        mergeSort(arrs,middle + 1,end);
        //归并排序需要分配的临时数组
        //这是归并排序的核心
        int []temp  = new int[end-start+1];
        int i = start;
        int j = middle + 1;
        int index = 0;
        while(i <= middle && j <= end){
            if(arrs[i] <= arrs[j]){
                temp[index++] = arrs[i++];
            }else{
                temp[index++] = arrs[j++];
            }
        }
        while(i <= middle){
            temp[index++] = arrs[i++];
        }
        while(j <= end){
            temp[index++] = arrs[j++];
        }
        i=start;
        index = 0;
        for(;i <= end;i++){
            arrs[i] = temp[index++];
        }

    }

}
