package com.example.tuanxn.academicscheduler.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessmentEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessments);

    @Delete
    void delete(AssessmentEntity assessmentEntity);

    @Query("SELECT * FROM assessments WHERE id=:id")
    AssessmentEntity getById(int id);

    @Query("SELECT * FROM assessments ORDER BY endDate ASC")
    LiveData<List<AssessmentEntity>> getAll();

    @Query("DELETE FROM assessments")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM assessments")
    int getCount();


}


