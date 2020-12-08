package com.kangjun.java8.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  Java8 practice
 */
public class StreamTest {

    /**
     *  过滤集合
     */
    @Test
    public void fiterSex(){
        List<PersonModel> personModels = Data.getData();
        //old
        List<PersonModel> manModels = new ArrayList<>();
        for (PersonModel manModel : personModels) {
            if("男".equals(manModel.getSex())){
                manModels.add(manModel);
            }
        }
        //new
        List<PersonModel> collect = personModels.stream().filter(personModel ->
                "男".equals(personModel.getSex())).collect(Collectors.toList());

        //Optional
        Optional<PersonModel> first = personModels.stream().filter(personModel ->
                "男".equals(personModel.getSex())).findFirst();
        if (first.isPresent()) {
            System.out.println(first.get().getName());
        }
    }

    /**
     *  过滤所有的男性 并且小于20岁
     */
    //@Test
    public void fiterSexAndAge(){
        List<PersonModel> personModels = Data.getData();
        //old
        List<PersonModel> manModels = new ArrayList<>();
        for (PersonModel manModel : personModels) {
            if("男".equals(manModel.getSex()) && manModel.getAge() < 20){
                manModels.add(manModel);
            }
        }
        //new 1
        List<PersonModel> collect = personModels.stream().filter(manModel ->
                "男".equals(manModel.getSex()) && manModel.getAge() < 20).collect(Collectors.toList());

        //new 2
        List<PersonModel> collect1 = personModels.stream().filter(manModel -> {
            return "男".equals(manModel.getSex()) && manModel.getAge() < 20;
        }).collect(Collectors.toList());

    }

    /**
     *  获取名称集合  map  flatmap
     */
    //@Test
    public void getUserNameList(){
        List<PersonModel> personModels = Data.getData();
        //old
        List<String> names = new ArrayList<>();
        for (PersonModel manModel : personModels) {
            names.add(manModel.getName());
        }
        //new 1
        List<String> collect = personModels.stream().map(personModel ->
                personModel.getName()).collect(Collectors.toList());
        //new 2
        List<String> collect1 = personModels.stream().map(PersonModel::getName).
                collect(Collectors.toList());
        //new 3
        List<String> collect2 = personModels.stream().map(personModel -> {
            return personModel.getName();
        }).collect(Collectors.toList());

    }

    /**
     *  map flatMap 的区别
     */
    //@Test
    public void flatMap(){
        List<PersonModel> personModels = Data.getData();
        List<Stream<String>> collect = personModels.stream().map(personModel ->
                Arrays.stream(personModel.getName().split(" "))).collect(Collectors.toList());
        List<String> collect1 = personModels.stream().flatMap(personModel ->
                Arrays.stream(personModel.getName().split(" "))).collect(Collectors.toList());

        List<String> collect2 = personModels.stream().map(personModel ->
                personModel.getName().split(" ")).flatMap(str -> Arrays.asList(str).stream()).collect(Collectors.toList());
    }

    /**
     *  allMatch、anyMatch、noneMatch
     *
     */
    @Test
    public void allMatch(){
        List<PersonModel> personModels = Data.getData();
        boolean b = personModels.stream().anyMatch(personModel -> "wu qi".equals(personModel.getName()));
        System.out.println(b);

    }


    /**
     *  Reduce  MapReduce  递归累计
     */
    //@Test
    public void reduceTest(){
        //累加，初始化值是 10
        Integer reduce = Stream.of(1, 2, 3, 4)
                .reduce(10, (count, item) ->{
                    System.out.println("count:"+count);
                    System.out.println("item:"+item);
                    return count + item;
                } );
        System.out.println("reduce: " + reduce);

        Integer reduce1 = Stream.of(1, 2, 3, 4)
                .reduce(0, (x, y) -> x + y);
        System.out.println("reduce1: " + reduce1);

        String reduce2 = Stream.of("1", "2", "3")
                .reduce("0", (x, y) -> (x + "," + y));
        System.out.println("reduce2: " + reduce2);
    }

    /**
     *  collect toList
     */
    //@Test
    public void toListTest(){
        List<PersonModel> personModels = Data.getData();
        List<String> collect = personModels.stream().map(PersonModel::getName).collect(Collectors.toList());
        List<String> collect1 = personModels.stream().map(personModel ->
                personModel.getName()).collect(Collectors.toList());

    }

    /**
     *  collect toSet
     */
   // @Test
    public void toSetTest(){
        List<PersonModel> personModels = Data.getData();
        // ①
        Set<String> collect = personModels.stream().map(PersonModel::getName).collect(Collectors.toSet());
        Set<String> collect1 = personModels.stream().map(personModel ->
                personModel.getName()).collect(Collectors.toSet());

    }

    /**
     *  collect toMap
     */
    //@Test
    public void toMapTest(){
        List<PersonModel> personModels = Data.getData();
        Map<String, Integer> collect = personModels.stream().collect
                (Collectors.toMap(PersonModel::getName, PersonModel::getAge));

        personModels.stream().collect(Collectors.toMap(personModel -> personModel.getName(),value -> {
            return value + "1";
        }));

    }

    /**
     * 指定类型 TreeSet
     */
    //@Test
    public void toTreeSetTest(){
        List<PersonModel> personModels = Data.getData();
        TreeSet<PersonModel> collect = personModels.stream().collect(Collectors.toCollection(TreeSet::new));

    }

    /**
     * 分组
     */
    //@Test
    public void toGroupTest(){
        List<PersonModel> personModels = Data.getData();
        Map<Boolean, List<PersonModel>> collect = personModels.stream().
                collect(Collectors.groupingBy(personModel -> "男".equals(personModel.getSex())));
        System.out.println(collect);
    }

    /**
     * 分隔
     */
    //@Test
    public void toJoiningTest(){
        List<PersonModel> personModels = Data.getData();
        String collect = personModels.stream().map(PersonModel::getName).
                collect(Collectors.joining(",", "{", "}"));
        System.out.println(collect);
    }

    // ============================================================================================
    private static int size=10000000;

    public static void main(String[] args) {

//        PersonModel personModel=new PersonModel();

//        //对象为空则打出 -
//        Optional<Object> o = Optional.of(personModel);
//        System.out.println(o.isPresent()?o.get():"-");
//
//        //名称为空则打出 -
//        Optional<String> name = Optional.ofNullable(personModel.getName());
//        System.out.println(name.isPresent()?name.get():"-");
//
//        //如果不为空，则打出xxx
//        Optional.ofNullable("test").ifPresent(na->{
//            System.out.println(na+"ifPresent");
//        });
//
//        //如果空，则返回指定字符串
//        System.out.println(Optional.ofNullable(null).orElse("-"));
//        System.out.println(Optional.ofNullable("1").orElse("-"));
//
//        //如果空，则返回 指定方法，或者代码
//        System.out.println(Optional.ofNullable(null).orElseGet(()->{
//            return "hahah";
//        }));
//        System.out.println(Optional.ofNullable("1").orElseGet(()->{
//            return "hahah";
//        }));

        //如果空，则可以抛出异常
//        System.out.println(Optional.ofNullable(null).orElseThrow(()->{
//            throw new RuntimeException("ss");
//        }));
        System.out.println("-----------List-----------");
        testList();
//        System.out.println("-----------Set-----------");
//        testSet();


    }

    /**
     * 测试list
     */
    public static void testList(){
        List<Integer> list = new ArrayList<>(size);
        for (Integer i = 0; i < size; i++) {
            list.add(new Integer(i));
        }

        List<Integer> temp1 = new ArrayList<>(size);
//        //老的
//        long start=System.currentTimeMillis();
//        for (Integer i: list) {
//            temp1.add(i);
//        }
//        System.out.println(+System.currentTimeMillis()-start);

//        //同步
//        long start1=System.currentTimeMillis();
//        list.stream().collect(Collectors.toList());
//        System.out.println(System.currentTimeMillis()-start1);
//
        //并发
        long start2=System.currentTimeMillis();
        list.parallelStream().collect(Collectors.toList());
        System.out.println(System.currentTimeMillis()-start2);
    }

    /**
     * 测试set
     */
    public static void testSet(){
        List<Integer> list = new ArrayList<>(size);
        for (Integer i = 0; i < size; i++) {
            list.add(new Integer(i));
        }

        Set<Integer> temp1 = new HashSet<>(size);
        //老的
        long start=System.currentTimeMillis();
        for (Integer i: list) {
            temp1.add(i);
        }
        System.out.println(+System.currentTimeMillis()-start);

        //同步
        long start1=System.currentTimeMillis();
        list.stream().collect(Collectors.toSet());
        System.out.println(System.currentTimeMillis()-start1);

        //并发
        long start2=System.currentTimeMillis();
        list.parallelStream().collect(Collectors.toSet());
        System.out.println(System.currentTimeMillis()-start2);
    }

}
