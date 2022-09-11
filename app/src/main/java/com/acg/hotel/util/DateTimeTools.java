package com.acg.hotel.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeTools {

    /**
     * 设置时间为0点 0 分 0 秒
     * <p>
     * Author: en
     * Date: 2020年3月5日 下午5:13:05
     *
     * @param date 时间
     * @return Date
     */
    /*
     时 分 秒 毫秒 全设置为0
     */
    public static Date getOnlyDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Date加/减天数
     * <p>
     * Author: en
     * Date: 2020年3月5日 下午4:51:17
     *
     * @param date      被加减的日期
     * @param addDayNum 正数+，负数-
     * @return Date
     *
     * 添加天数
     */
    public static Date dateAddDay(Date date, int addDayNum) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addDayNum);
        return calendar.getTime();
    }

    /**
     * Date 加/减小时
     * <p>
     * Author： em
     * Date 2020年3月9日08:02:32
     *
     * @param date    被加减的日期
     * @param addHour 正数+，负数-
     * @return Date
     */
    public static Date dateAddHour(Date date, int addHour) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, addHour);
        return calendar.getTime();
    }


}
