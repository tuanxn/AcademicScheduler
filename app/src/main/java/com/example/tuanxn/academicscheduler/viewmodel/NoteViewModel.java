package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.CourseEntity;

public class NoteViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();
    private AppRepository mRepository;

    public NoteViewModel(@NonNull Application application) throws Exception {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void saveNote(String noteText) {
        CourseEntity note = mLiveCourse.getValue();
        if (note == null) {

        }else {
            note.setNotes(noteText);
        }
        mRepository.insertNote(note);
    }
}
