package com.kangjun.java8.stream;

import com.kangjun.util.ChineseCharacterUtil;
import io.netty.util.internal.ThreadLocalRandom;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.security.SecureRandom;
import java.text.Collator;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  Java8 practice
 */
public class StreamTest {


    /**
     * 遍历集合
     *
     */
    @Test
    public void foreachSex(){
        List<PersonModel> personModels = Data.getData();
        personModels.stream().forEach(personModel -> {
            System.out.println(personModel.getName() + "1");
        });

        System.out.println("------------------------");

        List<PersonModel> collect = personModels.stream().peek(personModel -> {
            System.out.println(personModel.getName());
        }).collect(Collectors.toList());
    }



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
        List<PersonModel> personModels2 = Data.getData();
        boolean b = personModels.stream().anyMatch(personModel -> "wu qi".equals(personModel.getName()));
        System.out.println(b);

        //集合并集
        personModels.addAll(personModels2);
        List<PersonModel> collect = personModels.stream().distinct().collect(Collectors.toList());
        //集合交集
        List<PersonModel> collect1 = personModels.stream().filter(personModels2::contains).collect(Collectors.toList());

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
    @Test
    public void toMapTest(){
        List<PersonModel> personModels = Data.getData();
        Map<String, Integer> collect = personModels.stream().collect
                (Collectors.toMap(PersonModel::getName, PersonModel::getAge));

        Map<String, Integer> collect2 = personModels.stream().collect(Collectors.toMap(personModel -> personModel.getName(),value -> {
            return value.getAge();
        }));

        Map<String, PersonModel> collect1 = personModels.stream().collect(Collectors.toMap(PersonModel::getName,
                Function.identity(), (k1, k2) -> k1));

        Map<String, PersonModel> collect3 = personModels.stream().collect(Collectors.toMap(PersonModel::getName,
                item -> item, (k1, k2) -> k1));

        LinkedHashMap<String, PersonModel> collect4 =
                personModels.stream().collect(Collectors.toMap(PersonModel::getName, item -> item, (k1, k2) -> k1,
                        LinkedHashMap::new));

        System.out.println(collect);
        System.out.println(collect2);

    }

    /**
     * 指定类型 TreeSet
     */
    @Test
    public void toTreeSetTest(){
        List<String> names = new ArrayList<>();
        List<PersonModel> personModels = Data.getData();
//        TreeSet<PersonModel> collect = personModels.stream().collect(Collectors.toCollection(TreeSet::new));
        personModels.forEach(personModel -> {
            names.add(personModel.getName());
        });

    }

    /**
     * 分组
     */
    @Test
    public void toGroupTest(){
//        List<PersonModel> personModels = Data.getData();
//        Map<Boolean, List<PersonModel>> collect = personModels.stream().
//                collect(Collectors.groupingBy(personModel -> "男".equals(personModel.getSex())));
//
//        Map<String, List<PersonModel>> collect1 =
//                personModels.stream().collect(Collectors.groupingBy(PersonModel::getSex));
//
////        System.out.println(collect);
//        String split = "124~234";
//
//        System.out.println(split.split("~").length);

//        Map<String, Long> values = new HashMap<>();
//        System.out.println(values.get("1"));
//        if(values.get("1") == null){
//            System.out.println("我很牛逼");
//        }
//
//        Long l ;
//        System.out.println(l);
//        String str = "a,b,c,,";
//        String[] ary = str.split(",");
//        System.out.println(ary.length);
//
//        double random = Math.random();
//        int i = new Random().nextInt();
//        long l = new Random().nextLong();
//        long initialSeedUniquifier = ThreadLocalRandom.getInitialSeedUniquifier();

//        List<PersonModel> personModels = Data.getData();
        // 查询用户
//        PersonModel[] idArray = new PersonModel[personModels.size()];
//        PersonModel[] personModels2 = personModels.toArray(idArray);
//
//        System.out.println(idArray.length);
//        System.out.println(personModels2.length);
//
//
//        PersonModel[] idArray2 = new PersonModel[0];
//        PersonModel[] personModels1 = personModels.toArray(idArray2);
//
//        System.out.println(personModels1.length);
//        List<PersonModel> personModels1 = personModels.subList(1, 2);
//        personModels.add(new PersonModel());
//        Map<String, String> values = new HashMap<>();
//        Set<String> strings = values.keySet();
//        strings.remove("1");

//        List<Object> objects = Collections.singletonList("1");
//        Object remove = objects.remove(0);

//        List<String> values = new ArrayList<>();
//        values.add("1");
//        values.add("1");
//        values.add("1");
//        values.add("1");
//        values.add("1");
//        List<String> list = values.subList(1, 2);
////        values.add("1");
//        list.add("1");
        String var = "create_time";
        System.out.println(var.toUpperCase());

    }

    @Test
    public void getLocalDays(){

        // 创建代表一年中第一天的LocalDate对象。
        int year = LocalDate.now().getYear();
        LocalDate now = LocalDate.of(year, Month.JANUARY, 1);
        // 查找一年中的第一个星期日
        LocalDate saturday = now.with(DayOfWeek.SATURDAY);
        LocalDate sunday = now.with(DayOfWeek.SUNDAY);
        do {
            System.out.println(sunday.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            sunday = sunday.plus(Period.ofDays(7));
        } while (sunday.getYear() <= year + 1);
        do {
            System.out.println(saturday.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            saturday = saturday.plus(Period.ofDays(7));
        } while (saturday.getYear() <= year + 1);

        // 元旦

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


    /**
     *  自定义函数排序
     */
    @Test
    public void testSort(){
        List<PersonModel> personModels = Data.getData();

        sortByNamePinyin(personModels);

//        personModels = personModels.stream().sorted(new Comparator<PersonModel>() {
//            @Override
//            public int compare(PersonModel o1, PersonModel o2) {
//                int i1 = getSortInt(o1);
//                int i2 = getSortInt(o2);
//
//                if(i1 < i2)
//                    return -1;
//
//                if(i1 > i2)
//                    return 1;
//
//                return 0;
//            }
//
//            private int getSortInt(PersonModel personModel){
//                if(StringUtils.equals("18",personModel.getAge() + "") &&
//                        StringUtils.equals("男",personModel.getSex())){
//                    return 0;
//                }else if(StringUtils.equals("男",personModel.getSex() + "")){
//                    return 1;
//                }else {
//                    return 2;
//                }
//            }
//
//
//        }).collect(Collectors.toList());

        personModels = personModels
                .stream()
                .sorted(Comparator.comparing(PersonModel::getAge)
                        .thenComparing(PersonModel::getSex,Comparator.reverseOrder()))
                .collect(Collectors.toList());

        personModels.stream().forEach(personModel -> {

            System.out.println(personModel.toString());
        });
    }


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
//        System.out.println("-----------List-----------");
//        testList();
//        System.out.println("-----------Set-----------");
//        testSet();

        String split = "124~234";

//        for (String s : split.split("@")) {
//            System.out.println(s);
//        }

//        String[] split1 = split.split("~");
//        List<String> strings = Arrays.asList(split1);
//        String abc = "YCHZ,LHMZ,YCJX";
//        boolean lhmz = abc.contains("LHMZ");
//        System.out.println(lhmz);
        SecureRandom random = new SecureRandom();
        Integer num = random.nextInt(3);
        System.out.println(num);


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

    private void sortByNamePinyin(List<PersonModel> personModels) {
        personModels.sort((o1, o2) -> {
            Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
            return cmp.compare(ChineseCharacterUtil.getUpperCase(o1.getName(), true),
                    ChineseCharacterUtil.getUpperCase(o2.getName(), true));
        });
    }


}
