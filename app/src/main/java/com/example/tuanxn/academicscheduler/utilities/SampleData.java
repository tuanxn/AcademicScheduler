package com.example.tuanxn.academicscheduler.utilities;

import com.example.tuanxn.academicscheduler.model.TermEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleData {

    public static final String SAMPLE_TERM_TITLE_1 = "FALL 2019";
    public static final String SAMPLE_TERM_TITLE_2 = "SPRING 2020";
    public static final String SAMPLE_TERM_TITLE_3 = "SUMMER 2020";

    public static Date getDate(String sDate) throws Exception{
        return new SimpleDateFormat("MM/dd/yyyy").parse(sDate);
    }

    public static List<TermEntity> getTerms() throws Exception{
        List<TermEntity> terms = new ArrayList<>();
        terms.add(new TermEntity(1, SAMPLE_TERM_TITLE_1, getDate("10/05/2019"), getDate("10/20/2019")));
        terms.add(new TermEntity(2, SAMPLE_TERM_TITLE_2, getDate("10/21/2019"), getDate("10/31/2019")));
        terms.add(new TermEntity(3, SAMPLE_TERM_TITLE_3, getDate("11/01/2019"), getDate("11/20/2019")));
        return terms;
    }
}
