package com.example.tuanxn.academicscheduler.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity courseEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> courses);

    @Delete
    void delete(CourseEntity courseEntity);

    @Query("SELECT * FROM courses WHERE id=:id")
    CourseEntity getById(int id);

    @Query("SELECT * FROM courses ORDER BY endDate DESC")
    LiveData<List<CourseEntity>> getAll();

    @Query("DELETE FROM courses")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM courses")
    int getCount();

}
