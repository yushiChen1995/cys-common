package com.cys.jdk8;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TimeHelper {

    private static DateTimeFormatter dtf_millisecond = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static DateTimeFormatter dtf_second = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dtf_date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter dtf_time = DateTimeFormatter.ofPattern("HH:mm:ss");


    public static Long getEndOfTheDayBySpecifyTime(Long currentTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        return calendar.getTimeInMillis();
    }


}
