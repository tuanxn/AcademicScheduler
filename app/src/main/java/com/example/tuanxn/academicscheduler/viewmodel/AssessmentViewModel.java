package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    public LiveData<List<AssessmentEntity>> mAssessments;
    private AppRepository mRepository;

    public AssessmentViewModel(@NonNull Application application) throws Exception {
        super(application);

        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        mAssessments = mRepository.mAssessments;
    }
}
