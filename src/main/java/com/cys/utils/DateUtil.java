package com.cys.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chenyushi
 * @date 2018/10/9
 */
public class DateUtil {
    private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_MONTH_DAY = "MMdd";
    public static final String FORMAT_MONTH = "yyyy-MM";
    public static final String FORMAT_MINUTES = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_TIME = "yyyyMMddHHmmss";
    private static final DateFormat sdfHm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public static boolean isDate(String date){
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static String getCurrentDateStr(){
        return getCurrentDateStr(FORMAT_DATETIME);
    }

    public static String getDatetimeStr(long datetime){
        return format(new Date(datetime),FORMAT_DATETIME);
    }

    public static String getMonthDayStr(Date date){
        return format(date, FORMAT_MONTH_DAY);
    }

    public static String getDateStr(long datetime){
        return format(new Date(datetime),FORMAT_DATE);
    }

    public static String getDateStr(Date datetime){
        return format(datetime,FORMAT_DATE);
    }

    public static Date getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        return currDate;
    }

    /**
     * param   2010-09-01
     */
    public static Date parseStringToDate(String dateStr){
        return getDate(dateStr, FORMAT_DATE);
    }

    private static Date getDate(String dateStr, String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Date date = null;
        try {
            if(dateStr != null && dateStr.length() > 0){
                date = sdf.parse(dateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseStringToDate(String date_str,String format){
        return getDate(date_str, format);
    }

    public static Integer parseStringToInt(String date_str){
        if(date_str == null){
            return null;
        }
        Date date = (date_str.length() > "2010-02-11 ".length()) ? parseStringToDateTime(date_str)
                : parseStringToDate(date_str);
        return date == null ? null : new Long(date.getTime()/1000).intValue();
    }

    /**
     * param   2010-09-01 19:29:10
     */
    public static Date parseStringToDateTime(String date_str){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        Date date = null;
        try {
            if(date_str != null && date_str.length() > 0){
                date = sdf.parse(date_str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private static String getCurrentDateStr(String strFormat){
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        return format(currDate, strFormat);
    }

    public static String format(Date aTs_Datetime, String as_Pattern){
        if (aTs_Datetime == null || as_Pattern == null){
            return null;
        }
        SimpleDateFormat dateFromat = new SimpleDateFormat(as_Pattern);
        return dateFromat.format(aTs_Datetime);
    }
    public static void main(String[] args){
        System.out.println(getCurrentDateStr());
        System.out.println(parseStringToDateTime(getCurrentDateStr()).getTime());
        System.out.println(getDatetimeStr(System.currentTimeMillis()));
        System.out.println(getCurrentDateStr("yyyy.MM.dd HH:mm:ss"));
        System.out.println(format(new Date(System.currentTimeMillis()),"yyyy.MM.dd HH:mm:ss"));
    }

    public static String timeStampToStr(Timestamp tm){
        String result = "";
        if(tm != null){
            result = DateUtil.getDatetimeStr(tm.getTime());
        }
        return result;
    }

    public static Integer currentTime(){
        return new Long(System.currentTimeMillis()/1000).intValue();
    }

    public static Integer currentTime(Date d){
        return new Long(d.getTime()/1000).intValue();
    }

    public static String fromUnixTimeStamp(long timestamp){
        String time = null;
        try{
            time = DateUtil.getDatetimeStr(timestamp*1000);
        }catch(NumberFormatException e){
        }
        return time;
    }

    public static Date unixTime2Date(long timestamp){
        return timestamp > 0 ? new Date(timestamp*1000) : null;
    }

    public static Date unixTime2Date(Integer timestamp){
        return timestamp !=null ? new Date(timestamp*1000) : null;
    }

    public static Date addForNow(int field, int amount){
        Calendar cal = Calendar.getInstance();
        cal.add(field, amount);
        return cal.getTime();
    }

    public static int daysBetween(Date originalDate, Date newDdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        originalDate=sdf.parse(sdf.format(originalDate));
        newDdate=sdf.parse(sdf.format(newDdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        long originalTime = cal.getTimeInMillis();
        cal.setTime(newDdate);
        long newTime = cal.getTimeInMillis();
        long betweenDays=(newTime-originalTime)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    public static Long strToMillisecond(String date) throws ParseException {
        if (StringUtils.isBlank(date)){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat(FORMAT_DATE).parse(date));
        return c.getTimeInMillis();
    }
     public static Date strToDate(String date) throws ParseException {
         if (StringUtils.isBlank(date)){
             return null;
         }
         return new SimpleDateFormat(FORMAT_DATE).parse(date);
    }

    public static String parseTime(String str) throws ParseException {
        if (StringUtils.isBlank(str)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        Date date = sdf.parse(str);
        SimpleDateFormat formatter  =  new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return formatter.format(date);
    }

    /**
     * 计算 hour 小时后的时间
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 获取当天23:59:59秒毫秒值
     * @return 当天23:59:59秒毫秒值
     */
    public static Long getEarlyMorningMillisecond(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * 获取指定日期 格式：2018-04-03
     * @return
     * @throws ParseException
     */
    public static Calendar getYMDHMSDay(String day) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = sdfHm.parse(day);
        cal.setTime(date);
        return cal;
    }

    public static String millisecondToString(Long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATETIME);
        return  formatter.format(time);
    }


    /**
     * 得到当天开始的毫秒值
     * @return 当天开始毫秒值
     */
    public static Long getThatDayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date time = todayStart.getTime();
        return time.getTime();
    }

    /**
     * 得到当天结束的毫秒值
     * @return 当天结束毫秒值
     */
    public static Long getThatDayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        Date time = todayEnd.getTime();
        return time.getTime();
    }

    /**
     * 获取上个月开始时间的毫秒值
     * @return 上个月开始时间的毫秒值
     */
    public static Long getTheStartTimeOfTheMonth(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND,0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }

    /**
     * 获取上个月结束时间的毫秒值
     * @return 上个月结束时间的毫秒值
     */
    public static Long getTheMillisecondValueOfTheEndOfTheMonth(){
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, -1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        ca.set(Calendar.MINUTE, 59);
        //将秒至59
        ca.set(Calendar.SECOND,59);
        //将毫秒至999
        ca.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        return ca.getTimeInMillis();
    }

    /**
     * 获取指定时间的开始毫秒值
     * @param time 时间戳  (2019-01)
     * @return 毫秒值
     */
    public static Long getSpecifiedOfStartTime(String time) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat(FORMAT_MONTH).parse(time));
        return c.getTimeInMillis();
    }

    /**
     * 获取指定的时间结束时间毫秒值
     * @param time
     * @return
     * @throws ParseException
     */
    public static Long getSpecifiedOfEndTime(String time) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat("yyyy-MM").parse(time));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        c.set(Calendar.MINUTE, 59);
        //将秒至59
        c.set(Calendar.SECOND,59);
        //将毫秒至999
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

}
