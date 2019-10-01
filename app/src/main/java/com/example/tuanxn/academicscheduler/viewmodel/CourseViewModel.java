package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    public LiveData<List<CourseEntity>> mCourses;
    private AppRepository mRepository;

    public CourseViewModel(@NonNull Application application) throws Exception {
        super(application);

        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        mCourses = mRepository.mCourses;
    }
}
