package com.ruixun.car.systemmgr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * DateHelper类是一个关于日期时间的帮助类，
 * 主要是为了简化日期时间java.util.Date类的简化和计算日期天数差，提供了几个静态的方法。
 *
 * @author yt
 * @date 2020/3/6 - 17:51
 */
public class DateHelper {
    /**
     * 指定模板的日期格式化类对象--java8新的日期类
     */
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * 指定模板的日期格式化类对象
     */
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取当前日期的格式化字符串形式
     * @return
     */
    public static String getDate(){
        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        return now.format(dateTimeFormatter);
    }

    /**
     * 获取指定日期的格式化字符串形式
     * @param date
     * @return
     */
    public static String getDate(Date date){
        return sdf.format(date);
    }

    /**
     * 将日期字符串转化为日期
     * @param date
     * @return
     */
    public static LocalDate getDate(String date)  {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }

    /**
     * 获取两个字符串格式日期的天数差
     * @param date1
     * @param date2
     * @return
     */
    public static int getDayDiff(String date1, String date2) throws ParseException {
        //先将字符串日期转化为日期类型
        Date eDate = sdf.parse(date1);
        Date sDate = sdf.parse(date2);
        //两个日期之间的间隔天数
        int betweenTwoDates = (int) ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));
        //返回间隔天数
        return Math.abs(betweenTwoDates);

    }

}