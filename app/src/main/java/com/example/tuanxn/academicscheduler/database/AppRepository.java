package com.example.tuanxn.academicscheduler.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    public LiveData<List<AssessmentEntity>> mAssessments;
    public LiveData<List<CourseEntity>> mtCourses;
    public LiveData<List<AssessmentEntity>> mcAssessments;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) throws Exception{
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) throws Exception {
        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
        mCourses = getAllCourses();
        mAssessments = getAllAssessments();
        mtCourses = getAllCourses();
        mcAssessments = getAllAssessments();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mDb.termDao().insertAll(SampleData.getTerms());
                    mDb.courseDao().insertAll(SampleData.getCourses());
                    mDb.assessmentDao().insertAll(SampleData.getAssessments());
                }catch (Exception e) {

                }
            }
        });
    }

    private LiveData<List<TermEntity>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    private LiveData<List<CourseEntity>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    private LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mDb.assessmentDao().getAll();
    }

    public void deleteAllDate() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteAll();
                mDb.courseDao().deleteAll();
                mDb.assessmentDao().deleteAll();
            }
        });
    }
}
