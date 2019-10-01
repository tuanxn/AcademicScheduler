package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;

public class ModifyTermViewModel extends AndroidViewModel {

    public List<CourseEntity> mCourses = SampleData.getCourses();

    public ModifyTermViewModel(@NonNull Application application) throws Exception {
        super(application);
    }
}
