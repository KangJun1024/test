package com.kangjun.algorithm.mergesort;
/**
 *  Function: 归并排序
 */
public class MergeSortDemo {
    public static void main(String[] args) {
        //demo 合并两个有序的数组
        int [] A = new int[]{4,5,6,10,12,17};
        int [] B = new int[]{1,3,8,9,15,20,32,50};
        mergeArrs(A,B);

    }

    private static void mergeArrs(int[] A, int[] B) {
        int indexA = 0;
        int indexB = 0;
        int[] C = new int[A.length + B.length];
        int indexC = 0;
        while(indexA < A.length && indexB < B.length) {
            if (A[indexA] < B[indexB]) {
                C[indexC++] = A[indexA++];
            } else {
                C[indexC++] = B[indexB++];
            }
        }
        while (indexA < A.length) {
            C[indexC++] = A[indexA++];
        }
        while (indexB < B.length) {
            C[indexC++] = B[indexB++];
        }
        for (int i = 0; i < C.length; i++) {
            System.out.println(C[i]);
        }
    }
}
