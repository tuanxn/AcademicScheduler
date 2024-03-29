package com.example.tuanxn.academicscheduler.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "courses")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int termId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String status;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    @Ignore
    private List<NoteEntity> courseNotes;
    @Ignore
    private List<AssessmentEntity> courseAssessments;

    // Empty Course
    @Ignore
    public CourseEntity() {
    }

    // Used to update an existing course
    @Ignore
    public CourseEntity(int id, int termId, String title, Date startDate, Date endDate, String status, String mentorName, String mentorPhone, String mentorEmail, List<NoteEntity> courseNotes, List<AssessmentEntity> courseAssessments) {
        this.id = id;
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseNotes = courseNotes;
        this.courseAssessments = courseAssessments;
    }

    // Create a new course with auto-incrementing
    @Ignore
    public CourseEntity(int termId, String title, Date startDate, Date endDate, String status, String mentorName, String mentorPhone, String mentorEmail, List<NoteEntity> courseNotes, List<AssessmentEntity> courseAssessments) {

        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseNotes = courseNotes;
        this.courseAssessments = courseAssessments;
    }

    // Create a new course with auto-incrementing
    @Ignore
    public CourseEntity(int termId, String title, Date startDate, Date endDate, String status, String mentorName, String mentorPhone, String mentorEmail) {

        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }

    // For sample data
    public CourseEntity(int id, int termId, String title, Date startDate, Date endDate, String status, String mentorName, String mentorPhone, String mentorEmail) {

        this.id = id;
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public List<AssessmentEntity> getCourseAssessments() {
        return courseAssessments;
    }

    public void setCourseAssessments(List<AssessmentEntity> courseAssessments) {
        this.courseAssessments = courseAssessments;
    }

    public List<NoteEntity> getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(List<NoteEntity> courseNotes) {
        this.courseNotes = courseNotes;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", termId=" + termId +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", mentorName='" + mentorName + '\'' +
                ", mentorPhone='" + mentorPhone + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", courseNotes=" + courseNotes +
                ", courseAssessments=" + courseAssessments +
                '}';
    }
}
