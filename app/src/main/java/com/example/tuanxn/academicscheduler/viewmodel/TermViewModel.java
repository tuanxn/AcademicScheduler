package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.TermEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    public LiveData<List<TermEntity>> mTerms;
    private AppRepository mRepository;

    public TermViewModel(@NonNull Application application) throws Exception {
        super(application);

        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        mTerms = mRepository.mTerms;
    }
}
