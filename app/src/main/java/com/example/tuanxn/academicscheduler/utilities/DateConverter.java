package com.example.tuanxn.academicscheduler.utilities;

import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateConverter {

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }

    public static Date stringToDate(String value) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy").parse(value);
    }

    public static Long stringToMilli(String value) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = stringToDate(value);
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
