package com.example.tuanxn.academicscheduler.utilities;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateConverter {

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
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
