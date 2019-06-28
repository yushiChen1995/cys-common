package com.cys.jdk8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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

        LocalDateTime rightNow= LocalDateTime.now();
        String date= DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(rightNow);
        System.out.println(date);//2019-06-21T16:40:17.355
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println(formatter.format(rightNow));//2019-06-21 16:40:17

    }
}
