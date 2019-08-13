package com.kangjun.java8.optional;

import java.util.Optional;

/**
 *  Optional类这是Java 8新增的一个类，用以解决程序中常见的NullPointerException异常问题
 */
public class OptionalTest {

    public static void main(String[] args) {
        //1.创建optional对象
        //①empty() 方法用于创建一个没有值的Optional对象：
        Optional<String> emptyOpt = Optional.empty();
        //②of() 方法使用一个非空的值创建Optional对象
        String str = "Hello World";
        Optional<String> notNullOpt = Optional.of(str);
        //③ofNullable() 方法接收一个可以为null的值
        Optional<String> nullableOpt = Optional.ofNullable(null);

        //2.判断Null 如果创建的对象没有值，调用isPresent()方法会返回false，
        // 调用get()方法抛出NoSuchElementException异常。 No value present
        boolean present = notNullOpt.isPresent();
        System.out.println(present);

        //3.获取对象
        Object obj = notNullOpt.get();
        System.out.println(obj);

        //4.使用map提取对象的值
        //①获取User对象中的roleId属性值，常见的方式是直接获取
        String parameter = "teacher";
        User u = new User(parameter);
        String roleId = null;
        if (u != null) {
            roleId = u.getRoleId();
        }
        //②使用Optional中提供的map()方法可以更简单的方式实现
        Optional<User> userOpt = Optional.ofNullable(u);
        Optional<String> roleIdOpt = userOpt.map(User::getRoleId);
        System.out.println(roleIdOpt);

        //5.使用orElse方法设置默认值
        //①orElse()：如果有值就返回，否则返回一个给定的值作为默认值；
        Optional<String> strOpt = Optional.of(str);
        String s = emptyOpt.orElse("Shang hai");
        System.out.println(s);
        //②orElseGet()：与orElse()方法作用类似，区别在于生成默认值的方式不同。
        // 该方法接受一个Supplier函数式接口参数，用于生成默认值；
        String s1 = emptyOpt.orElseGet(() -> "Hello ShenYang");
        System.out.println(s1);
        //③orElseThrow()：与前面介绍的get()方法类似，当值为null时调用这两个方法都会抛出
        // NoSuchElementException异常，区别在于该方法可以指定抛出的异常类型。
        String s2 = notNullOpt.orElseThrow(() -> new IllegalArgumentException("Argument 'str' cannot be null or blank."));
        System.out.println(s2);

        //6.使用filter()方法过滤
        //filter()方法可用于判断Optional对象是否满足给定条件，一般用于条件过滤
        Optional<String> optional = Optional.of("kangjda@163.com");
        Optional<String> s3 = optional.filter(str2 -> str2.contains("163e"));
        System.out.println(s3);

        //7.使用建议
        //尽量避免在程序中直接调用Optional对象的get()和isPresent()方法；
        //避免使用Optional类型声明实体类的属性；

        //8.正确示例
        //①orElse()方法的使用
        //(strOpt.orElse("Hello World") )  == (str != null ?  str : "Hello World" )

        //②简化if-else
        if (u != null) {
            String username = u.getUserName();
            if (username != null) {
                System.out.println(username.toLowerCase());
            }else {
                System.out.println("1");
            }
        }else {
            System.out.println("1");
        }

        System.out.println(userOpt.map(User::getRoleId).map(String::toLowerCase).orElse(null));


    }

}
