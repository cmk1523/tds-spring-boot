package com.techdevsolutions.springBoot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static String TIMEZONE_GMT = "GMT";
    public static String ISO_UTC_STRING = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static String SQL_STRING = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_STRING = "yyyy-MM-dd";

    public static Date getYesterdaysDate(TimeZone timeZone) {
        return DateUtils.getYesterdaysDate(new Date(), timeZone);
    }

    public static Date getYesterdaysDate(Date date, TimeZone timeZone) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(timeZone);
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date getLastMonthsDate(Date date, TimeZone timeZone) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(timeZone);
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public static String DateToISO(Date date) {
        return DateUtils.DateToISO(date.getTime());
    }

    public static String DateToISO(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.ISO_UTC_STRING);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public static String DateToSQL(Date date) {
        return DateUtils.DateToSQL(date.getTime());
    }

    public static String DateToSQL(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.SQL_STRING);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public static Date StringToDate(String dateInISOFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.ISO_UTC_STRING);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(dateInISOFormat);
    }
}


