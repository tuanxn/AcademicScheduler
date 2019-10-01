package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;

public class ModifyCourseViewModel extends AndroidViewModel {

    public LiveData<List<AssessmentEntity>> mcAssessments;
    private AppRepository mRepository;

    public ModifyCourseViewModel(@NonNull Application application) throws Exception {
        super(application);

        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        mcAssessments = mRepository.mcAssessments;
    }
}
