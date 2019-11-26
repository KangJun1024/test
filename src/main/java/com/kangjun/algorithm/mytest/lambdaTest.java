package com.kangjun.algorithm.mytest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *  lambda 用法
 */
public class lambdaTest {
    public static void main(String[] args) {
        //利用lambda表达式创建线程
        new Thread(()-> System.out.println("hello world")).start();
        //常用集合
        JSONArray jsonArray = new JSONArray();
        List<String> list = new ArrayList<>();
        //JSONArray => List
        List<JSONObject> parseArray = JSONArray.parseArray(jsonArray.toJSONString(), JSONObject.class);
        //排序
        list.sort(Comparator.comparing(str -> (String)str));  //升序
        jsonArray.sort(Comparator.comparing(str -> ((JSONObject)str).getString("kang")).reversed()); //降序
        //遍历
        jsonArray.forEach((obj -> {
            System.out.println("遍历输出: " + ((JSONObject)obj).getString("kang"));
        }));
        list.forEach((str ->{
            System.out.println("遍历输出: " + str);
        }));
        //集合过滤
        jsonArray.stream().filter(obj ->
                ((JSONObject)obj).getString("kang").indexOf("kang") > -1).forEach((obj ->{
            System.out.println("输出包含kang的: " + obj);
        }));
        //比较
        parseArray.sort((obj1,obj2) -> obj1.getString("kang").compareTo(obj2.getString("kang")));





    }

}
