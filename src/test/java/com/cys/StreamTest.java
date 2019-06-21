package com.cys;

import com.cys.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author cys
 * @date 2019/6/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamTest {
    private static List<String> strList = Arrays.asList("DDD2", "ADD1", "CCC", "BBB3", "BBB2", "AAA2", "zAA1");
    private static List<Integer> numList = Arrays.asList(1, 0, 2, -1, -10, 6, 0, -25, 90);

    /**
     * stream -> 过滤
     */
    @Test
    public void testFilter() {
        strList.stream()
                .filter(s -> s.startsWith("A"))
                .forEach(System.out::println);
    }

    /**
     * stream -> 自定义排序规则
     * 自然排序是根据集合元素的大小，进行元素升序排列。如果需要定制排序，比如降序排列，可通过Comparator接口的帮助。需要重写compare(T o1,T o2)方法。
     * 利用int compare(T o1,T o2)方法，比较o1和o2的大小：如果方法返回正整数，则表示o1大于o2；如果返回0，表示相等；返回负整数，表示o1小于o2。
     * 要实现定制排序，需要将实现Comparator接口的实例作为形参传递给相关排序对象的构造器。
     * 使用定制排序判断两个元素相等的标准是：通过Comparator比较两个元素返回了0。
     * 选择：没有办法去修改类中的内容（没有办法实现compareTo()方法）时，选择compare()方法。
     * 优先级：同时写了两个方法，优先使用定制方法。
     */
    @Test
    public void testCustomizeSorted() {
        List<User> list = new ArrayList<>();
        User user = new User("候张三", "123", 14, (byte) 1);
        User user2 = new User("谌王五", "789", 15, (byte) 0);
        User user3 = new User("王麻子", "456", 19, (byte) 1);
        User user1 = new User("谌李四", "456", 16, (byte) 1);
        User user4 = new User("谌赵七", "456", 50, (byte) 0);
        User user5 = new User("谌王八", "456", 50, (byte) 1);
        list.add(user);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        list.stream().sorted(Comparator.comparing(User::getUserName, (u1, u2) -> {
            //姓谌排姓候前面
            if (u1.startsWith("谌") && !u2.startsWith("谌")) {
                //负整数 -> u1 < u2  u1往前排  u1代表姓谌的
                return -1;
            } else if (!u1.startsWith("谌") && u2.startsWith("谌")) {
                //正整数 -> u1 > u2  u2往前排  u2代表姓谌的
                return 1;
            } else {
                //都是姓谌的往下走
                return 0;
            }
            //相等按年龄倒序排
        }).thenComparing(User::getAge, Comparator.reverseOrder())).forEach(System.out::println);
    }


    /**
     * 返回由此流的元素组成的流，根据自然顺序排序。
     * 如果该流的元件不是Comparable ，一个java.lang.ClassCastException执行终端操作时，
     * 可以抛出。
     * 对于有序流，排序稳定。 对于无序的流，不能保证稳定性。
     * 这是一个stateful intermediate operation 。
     * 结果:新的流
     */
    @Test
    public void testNaturalSorted() {
        numList.stream().sorted().forEach(System.out::println);
    }

    /**
     * 中间操作 map 会将元素根据指定的 Function 接口来依次将元素转成另外的对象。
     * 下面的示例展示了将字符串转换为大写字符串。你也可以通过map来讲对象转换成其他类型，map返回的Stream类型是根据你map传递进去的函数的返回值决定的。
     */
    @Test
    public void testMap() {
        strList.stream()
                .map(String::toLowerCase)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。所有的匹配操作都是 最终操作 ，并返回一个 boolean 类型的值。
     */
    @Test
    public void testMatch() {
        //判断流中是否含有匹配元素,有则返回true
        boolean isAnyMatch = strList.stream().anyMatch(s -> s.startsWith("A"));
        System.out.println("isAnyMatch = " + isAnyMatch); //true
        //判断流中是否都是以字母A开头,都是则返回true
        boolean isAllMatch = strList.stream().allMatch(s -> s.startsWith("A"));
        System.out.println("isAllMatch = " + isAllMatch);//false
        //判断流中是否没有一个是z开头的,有则返回false,只要有一个都是返回false
        boolean isNoneMatch = strList.stream().noneMatch(s -> s.startsWith("z"));
        System.out.println("isNoneMatch = " + isNoneMatch);//false
    }

    /**
     * 计数是一个 最终操作，返回Stream中元素的个数，返回值类型是 long。
     */
    @Test
    public void testCount() {
        long count = strList.stream().filter(s -> s.startsWith("A")).count();
        System.out.println("集合中以字母A开头的共有" + count + "个");
    }

    /**
     *  这是一个 最终操作 ，允许通过指定的函数来讲stream中的多个元素规约为一个元素，规约后的结果是通过Optional 接口表示的：
     *  这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），
     *  和前面 Stream 的第一个、第二个、第 n 个元素组合。从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。
     *  例如 Stream 的 sum 就相当于Integer sum = integers.reduce(0, (a, b) -> a+b);也有没有起始值的情况，
     *  这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。
     */
    @Test
    public void testReduce() {
        Optional<String> reduce = strList.stream().sorted().reduce((s1, s2) -> s1 + "❤" + s2);
        reduce.ifPresent(System.out::println);
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println("concat = " + concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println("minValue = " + minValue);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println("sumValue = " + sumValue);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        System.out.println("sumValue = " + sumValue);
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
        System.out.println("concat = " + concat);
    }
}