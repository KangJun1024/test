package com.kangjun.enumdemo;

/**
 *  Function: 枚举源码剖析
 */
public enum  EnumTest {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5);
    private int value;

    /**
     * 带一个参数的构造函数
     *
     * @param value
     */
    private EnumTest(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public static void main(String[] args) {
        System.out.println("EnumTest.FRI 的 value = " + EnumTest.FIRST.getValue());
        EnumTest []values = EnumTest.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i].getValue());
        }
    }
}
