package com.kangjun.algorithm.classloader;

public class ClassLoaderTest extends Father{

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }
    ClassLoaderTest(){
        System.out.println("3");
    }

    public static void main(String[] args) {
        new ClassLoaderTest();
    }

}
class Father{
    static ClassLoaderTest classLoaderTest = new ClassLoaderTest();

    static {
        System.out.println("4");
    }

    {
        System.out.println("5");
    }
    Father(){
        System.out.println("6");
    }

}
