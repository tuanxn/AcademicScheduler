package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModifyCourseViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();
    public LiveData<List<AssessmentEntity>> mcAssessments;
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ModifyCourseViewModel(@NonNull Application application) throws Exception {
        super(application);

        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        mcAssessments = mRepository.mcAssessments;
    }

    public void loadData(final int courseId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                CourseEntity course = mRepository.getCourseById(courseId);
                mLiveCourse.postValue(course);
            }
        });
    }
}
