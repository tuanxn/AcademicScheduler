package com.example.tuanxn.academicscheduler.utilities;

import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.database.TermEntity;

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

    public static List<TermEntity> getTerms() throws Exception {
        List<TermEntity> terms = new ArrayList<>();
        terms.add(new TermEntity( 1001, SAMPLE_TERM_TITLE_1, getDate("10/05/2019"), getDate("10/20/2019")));
        terms.add(new TermEntity( 1002, SAMPLE_TERM_TITLE_2, getDate("10/21/2019"), getDate("10/31/2019")));
        terms.add(new TermEntity( 1003, SAMPLE_TERM_TITLE_3, getDate("11/01/2019"), getDate("11/20/2019")));
        return terms;
    }

    public static List<CourseEntity> getCourses() throws Exception {
        List<CourseEntity> courses = new ArrayList<>();
        courses.add(new CourseEntity(2001, 1001, "MATH", getDate("10/05/2019"), getDate("10/20/2019"), "Completed", "John", "550-5555", "john@gmail.com", "Math Notes"));
        courses.add(new CourseEntity(2002, 1002, "SCIENCE", getDate("10/21/2019"), getDate("10/25/2019"), "In Progress", "Steve", "551-5555", "steve@gmail.com", "Science Notes"));
        courses.add(new CourseEntity(2003, 1002, "PHYSICS", getDate("10/26/2019"), getDate("10/31/2019"), "In Progress", "Joseph", "552-5555", "joseph@gmail.com", "Physics Notes"));
        courses.add(new CourseEntity(2004, 1003, "ENGLISH", getDate("11/05/2019"), getDate("11/10/2019"), "Plan to Take", "Jerry", "553-5555", "jerry@gmail.com", "English Notes"));
        return courses;
    }

    public static List<AssessmentEntity> getAssessments() throws Exception {
        List<AssessmentEntity> assessments = new ArrayList<>();
        assessments.add(new AssessmentEntity(3001, 2001, "MATH TEST", "Objective Assessment", getDate("10/18/2019"), getDate("10/18/2019")));
        assessments.add(new AssessmentEntity(3002, 2002, "SCIENCE TEST", "Objective Assessment", getDate("10/23/2019"), getDate("10/23/2019")));
        assessments.add(new AssessmentEntity(3003, 2002, "SCIENCE LAB", "Performance Assessment", getDate("10/24/2019"), getDate("10/24/2019")));
        assessments.add(new AssessmentEntity(3004, 2003, "PHYSICS LAB", "Performance Assessment", getDate("10/27/2019"), getDate("10/30/2019")));
        assessments.add(new AssessmentEntity(3005, 2004, "ESSAY", "Performance Assessment", getDate("11/07/2019"), getDate("11/10/2019")));
        return assessments;
    }
}
