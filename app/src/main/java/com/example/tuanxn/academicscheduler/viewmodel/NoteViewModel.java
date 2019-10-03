package com.example.tuanxn.academicscheduler.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.database.NoteEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public NoteViewModel(@NonNull Application application) throws Exception {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
            }
        });
    }

    public void saveNote(String noteText) {
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            if(TextUtils.isEmpty(noteText.trim())) {
                return;
            }
            note = new NoteEntity(AppRepository.createdCourseId, noteText.trim());
        }else {
            note.setText(noteText);
        }
        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
