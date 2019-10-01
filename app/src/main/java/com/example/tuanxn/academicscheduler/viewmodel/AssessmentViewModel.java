package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    public List<AssessmentEntity> mAssessments = SampleData.getAssessments();

    public AssessmentViewModel(@NonNull Application application) throws Exception {
        super(application);
    }
}
