package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.ParseException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModifyAssessmentViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessment = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ModifyAssessmentViewModel(@NonNull Application application) throws Exception {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int assessmentId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AssessmentEntity assessment = mRepository.getAssessmentById(assessmentId);
                mLiveAssessment.postValue(assessment);
            }
        });
    }

    public void saveAssessment(String title, String startDate, String endDate, String type) throws ParseException {
        AssessmentEntity assessment = mLiveAssessment.getValue();
        if (assessment == null) {
            if(TextUtils.isEmpty(title.trim()) || TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
                return;
            }
            // TODO
//            assessment = new AssessmentEntity(title, DateConverter.stringToDate(startDate), DateConverter.stringToDate(endDate), type);
        }else {
            assessment.setTitle(title);
            assessment.setStartDate(DateConverter.stringToDate(startDate));
            assessment.setEndDate(DateConverter.stringToDate(endDate));
            assessment.setType(type);
        }
        mRepository.insertAssessment(assessment);
    }
}
