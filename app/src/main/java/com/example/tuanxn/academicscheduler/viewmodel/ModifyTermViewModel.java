package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.database.TermEntity;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModifyTermViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> mLiveTerm = new MutableLiveData<>();
    public LiveData<List<CourseEntity>> mtCourses;
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ModifyTermViewModel(@NonNull Application application) throws Exception {
        super(application);

        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        mtCourses = mRepository.mtCourses;

    }

    public void loadData(final int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermEntity term = mRepository.getTermById(termId);
                mLiveTerm.postValue(term);
            }
        });
    }

    public void saveTerm(String title, String termStart, String termEnd) throws ParseException {
        TermEntity term = mLiveTerm.getValue();
        if (term == null) {
            if(TextUtils.isEmpty(title.trim()) || TextUtils.isEmpty(termStart.trim()) || TextUtils.isEmpty(termEnd.trim())) {
                return;
            }
            term = new TermEntity(title, DateConverter.stringToDate(termStart), DateConverter.stringToDate(termEnd));
        }else {
            term.setTitle(title);
            term.setStartDate(DateConverter.stringToDate(termStart));
            term.setEndDate(DateConverter.stringToDate(termEnd));
        }
        mRepository.insertTerm(term);
    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerm.getValue());
    }
}
