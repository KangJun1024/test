package com.kangjun.alibaba;

import java.util.Arrays;
import java.util.List;

/**
 *  为什么阿里开发手册明确说明 Arrays.asList() 不能使用其修改方法？
 */
public class ArrayToList {
    public static void main(String[] args) {
        List list = Arrays.asList("a", "b", "c");
        list.add("g");

    }
}
