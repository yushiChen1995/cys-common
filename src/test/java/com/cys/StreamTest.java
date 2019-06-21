package com.cys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cys
 * @date 2019/6/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamTest {

    /**
     * stream -> 过滤
     */
    @Test
    public void testFilter() {
        List<String> list = new ArrayList<>();
        list.add("ddd2");
        list.add("aaa2");
        list.add("bbb1");
        list.add("aaa1");
        list.add("bbb3");
        list.add("ccc");
        list.add("bbb2");
        list.add("ddd1");
        list.stream()
                .filter(s -> s.startsWith("a"))
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
        List<Integer> list = Arrays.asList(1, 0, 2, -1, -10, 6, 0, -25, 90);
        list.stream().sorted().forEach(System.out::println);
    }
}