package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AppRepository;

public class HomeViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    public HomeViewModel(@NonNull Application application) throws Exception {
        super(application);
        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllDate();
    }
}
