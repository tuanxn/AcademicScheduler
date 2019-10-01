package com.example.tuanxn.academicscheduler.utilities;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateConverter {
    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }
}
