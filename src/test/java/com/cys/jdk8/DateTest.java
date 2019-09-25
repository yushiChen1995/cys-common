package com.cys.jdk8;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.cys.cys.IntentionLevel.roleMap;

/**
 * @author cys
 * @date 2019/6/21
 */

public class DateTest {

    /**
     * Clock 类提供了访问当前日期和时间的方法，Clock 是时区敏感的，可以用来取代
     * System.currentTimeMillis() 来获取当前的微秒数。某一个特定的时间点也可以使用 Instant 类来表示，
     * Instant 类也可以用来创建旧版本的java.util.Date 对象。
     */
    @Test
    public void testClock() {
        //当前毫秒值
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        System.out.println("millis = " + millis);//当前时间的毫秒值 1561105417325
        Instant instant = clock.instant();
        System.out.println("instant = " + instant);//2019-06-21T08:21:17.543Z
        Date date = Date.from(instant);
        System.out.println("date = " + date);//Fri Jun 21 16:22:33 CST 2019
        long time = date.getTime();
        System.out.println("time = " + time); //1561105417325
    }

    @Test
    public void testLocalDate() {
        LocalDate toDay = LocalDate.now();//获取现在的日期
        System.out.println("今天的日期: " + toDay);//2019-06-21
        LocalDate tomorrow = toDay.plus(1, ChronoUnit.DAYS);//明天
        System.out.println("明天的日期: " + tomorrow);//2019-06-23
        LocalDate localDate = toDay.minusDays(1);
        System.out.println("昨天的日期: " + localDate);
        DayOfWeek dayOfWeek = toDay.getDayOfWeek();
        System.out.println("今天是周" + dayOfWeek.getValue());
    }

    @Test
    public void testDateTimeFormatter() {
        String strDate = "2019-06-21 16:21:10";
        // 根据需要解析的日期、时间字符串定义解析所用的格式器
        DateTimeFormatter fomatter1 = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime = LocalDateTime.parse(strDate, fomatter1);
        System.out.println("dateTime = " + dateTime);//2019-06-21T16:21:10

        LocalDateTime rightNow = LocalDateTime.now();
        String date = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(rightNow);
        System.out.println(date);//2019-06-21T16:40:17.355
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println(formatter.format(rightNow));//2019-06-21 16:40:17

    }

    /**
     * 每天执行
     */
    @Test
    public void executedDaily() {
        //得到当天的结束时间
        Long endOfTheDay = TimeHelper.getEndOfTheDayBySpecifyTime(1551369600000L);
        //得到当天的开始时间
        Date startDate = new Date(1551369600000L);
        Date endDate = new Date(endOfTheDay);
        Long thatDayTime = System.currentTimeMillis();
        int count = 0;
        while (thatDayTime >= endDate.getTime()) {
            System.out.println(endDate.getTime());
            System.out.println(startDate.getTime());
            count++;
            startDate = DateUtils.addDays(startDate, 1);

            endDate = DateUtils.addDays(endDate, 1);

        }
        System.out.println("count = " + count);
    }

    /**
     * 获取某个日期距离当前日多少天
     */
    @Test
    public void timeConversion() throws ParseException {
        //将字符串转为日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dstr = "2019-01-19 00:00:00 ";
        java.util.Date date = sdf.parse(dstr);
        long s1 = date.getTime();//将时间转为毫秒
        long s2 = System.currentTimeMillis();//得到当前的毫秒
        int day = (int) ((s2 - s1) / 1000 / 60 / 60 / 24);
        System.out.println("距现在已有" + day + "天，你得抓紧时间学习了");

    }

    /**
     * test占位符
     */
    @Test
    public void testPlaceholder() {
        String s = "https://m.lechebang.cn/molun/{0}/payment/scan/index";
        String format = MessageFormat.format(s, "58");
        System.out.println("format = " + format);

        String s1 = "https://m.lechebang.cn/molun/%s/payment/scan/index";
        String format1 = String.format(s1, 58);
        System.out.println("format1 = " + format1);
    }

    @Test
    public void testGetMaxRole() {
        System.out.println("ROLE:" + roleMap);
        List<String> roles = new ArrayList<>();
        roles.add("班主任");
        roles.add("教务处");
        roles.add("系主任");
        roles.add("我最大");
        roles.add("院领导");
        roles.add("老大");


        Map<String, Byte> sortMap = sort(roleMap);
        System.out.println("sort = " + sortMap);
        Set<String> keySet = sortMap.keySet();
        System.out.println("keySet = " + keySet);

        String maxRole = getMaxRole(roles, roleMap);
        System.out.println("maxRole = " + maxRole);

    }

    /**
     * 将所有的角色从大到小排序
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    private <K, V extends Comparable<? super V>> Map<K, V> sort(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            int compare = (o1.getValue()).compareTo(o2.getValue());
            return -compare;
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 获取最大的角色
     *
     * @param roles
     * @param map
     * @return
     */
    private String getMaxRole(List<String> roles, Map<String, Byte> map) {
        Map<String, Byte> sortMap = sort(map);
        System.out.println("sort = " + sortMap);
        Set<String> keySet = sortMap.keySet();
        System.out.println("keySet = " + keySet);
        for (String roleKey : keySet) {
            for (String role : roles) {
                if (roleKey.equals(role)) {
                    return role;
                }

            }

        }
        return null;
    }

    public static void main(String[] args) {

    }

}




