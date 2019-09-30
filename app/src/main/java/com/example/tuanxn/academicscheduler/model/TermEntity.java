package com.example.tuanxn.academicscheduler.model;

import java.util.Date;
import java.util.List;

public class TermEntity {
    private int id;
    private String title;
    private Date startDate;
    private Date endDate;
    private List<CourseEntity> termCourses;

    // Create empty term
    public TermEntity() {
    }

    // Used to update an existing term
    public TermEntity(int id, String title, Date startDate, Date endDate, List<CourseEntity> termCourses) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termCourses = termCourses;
    }

    // Used to create a new term with auto-incrementing
    public TermEntity(String title, Date startDate, Date endDate, List<CourseEntity> termCourses) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termCourses = termCourses;
    }

    // Used for sample data
    public TermEntity(int id, String title, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<CourseEntity> getTermCourses() {
        return termCourses;
    }

    public void setTermCourses(List<CourseEntity> termCourses) {
        this.termCourses = termCourses;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", termCourses=" + termCourses +
                '}';
    }
}
