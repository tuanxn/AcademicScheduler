package com.example.tuanxn.academicscheduler.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity termEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);

    @Delete
    void delete(TermEntity termEntity);

    @Query("SELECT * FROM terms WHERE id=:id")
    TermEntity getById(int id);

    @Query("SELECT * FROM terms ORDER BY endDate DESC")
    LiveData<List<TermEntity>> getAll();

    @Query("DELETE FROM terms")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();



}
