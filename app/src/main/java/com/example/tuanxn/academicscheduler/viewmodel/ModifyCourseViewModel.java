package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.database.NoteEntity;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModifyCourseViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();
    public LiveData<List<AssessmentEntity>> mcAssessments;
    public LiveData<List<NoteEntity>> mcNotes;
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ModifyCourseViewModel(@NonNull Application application) throws Exception {
        super(application);

        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        mcAssessments = mRepository.mcAssessments;
        mcNotes = mRepository.mcNotes;
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

    public void saveCourse(String title, String courseStart, String courseEnd, String status, String mentorName, String mentorPhone, String mentorEmail) throws ParseException {
        CourseEntity course = mLiveCourse.getValue();
        if (course == null) {
            if(TextUtils.isEmpty(title.trim()) || TextUtils.isEmpty(courseStart.trim()) || TextUtils.isEmpty(courseEnd.trim()) || TextUtils.isEmpty(mentorName.trim()) || TextUtils.isEmpty(mentorPhone.trim()) || TextUtils.isEmpty(mentorEmail.trim())) {
                return;
            }
            course = new CourseEntity(AppRepository.createdTermId, title, DateConverter.stringToDate(courseStart), DateConverter.stringToDate(courseEnd), status, mentorName, mentorPhone, mentorEmail);
        }else {
            course.setTitle(title);
            course.setStartDate(DateConverter.stringToDate(courseStart));
            course.setEndDate(DateConverter.stringToDate(courseEnd));
            course.setStatus(status);
            course.setMentorName(mentorName);
            course.setMentorPhone(mentorPhone);
            course.setMentorEmail(mentorEmail);
        }
        mRepository.insertCourse(course);
    }
}
