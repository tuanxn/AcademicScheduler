package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.TermEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    public List<TermEntity> mTerms = SampleData.getTerms();

    public TermViewModel(@NonNull Application application) throws Exception {
        super(application);
    }
}
