package com.kangjun.alibaba;

public class FloatPrimitiveTest {
    public static void main(String[] args) {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        System.out.println(a);
        System.out.println(b);
        if (a == b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}