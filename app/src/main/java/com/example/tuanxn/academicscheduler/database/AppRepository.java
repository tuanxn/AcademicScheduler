package com.example.tuanxn.academicscheduler.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;
    public static int createdTermId;
    public static int createdCourseId;

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    public LiveData<List<AssessmentEntity>> mAssessments;
    public LiveData<List<CourseEntity>> mtCourses;
    public LiveData<List<AssessmentEntity>> mcAssessments;
    public LiveData<List<NoteEntity>> mcNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) throws Exception{
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) throws Exception {
        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
        mCourses = getAllCourses();
        mAssessments = getAllAssessments();
        mtCourses = getAllCourses();
        mcAssessments = getAllAssessments();
        mcNotes = getAllNotes();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mDb.termDao().insertAll(SampleData.getTerms());
                    mDb.courseDao().insertAll(SampleData.getCourses());
                    mDb.assessmentDao().insertAll(SampleData.getAssessments());
                    mDb.noteDao().insertAll(SampleData.getNotes());
                }catch (Exception e) {

                }
            }
        });
    }

    private LiveData<List<TermEntity>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    private LiveData<List<CourseEntity>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    private LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mDb.assessmentDao().getAll();
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }

    public void deleteAllData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteAll();
                mDb.courseDao().deleteAll();
                mDb.assessmentDao().deleteAll();
                mDb.noteDao().deleteAll();
            }
        });
    }

    public AssessmentEntity getAssessmentById(int assessmentId) {
        return mDb.assessmentDao().getById(assessmentId);
    }

    public void insertAssessment(final AssessmentEntity assessment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.assessmentDao().insert(assessment);
            }
        });
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDao().getById(termId);
    }

    public void insertTerm(final TermEntity term) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                createdTermId = (int) mDb.termDao().insert(term);
            }
        });
    }

    public CourseEntity getCourseById(int courseId) {
        return mDb.courseDao().getById(courseId);
    }

    public void insertCourse(final CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                createdCourseId = (int) mDb.courseDao().insert(course);
            }
        });
    }

    public void insertNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insert(note);
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return mDb.noteDao().getById(noteId);
    }

    public void deleteNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().delete(note);
            }
        });
    }

    public void deleteAssessment(final AssessmentEntity assessment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.assessmentDao().delete(assessment);
            }
        });
    }

    public void deleteCourse(final CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().delete(course);
            }
        });
    }

    public void deleteTerm(final TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().delete(term);
            }
        });
    }
}
