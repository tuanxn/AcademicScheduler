package com.example.tuanxn.academicscheduler.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int courseId;
    private String text;

    @Ignore
    public NoteEntity() {
    }

    @Ignore
    public NoteEntity(int courseId, String text) {
        this.courseId = courseId;
        this.text = text;
    }

    public NoteEntity(int id, int courseId, String text) {
        this.id = id;
        this.courseId = courseId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", text='" + text + '\'' +
                '}';
    }
}
