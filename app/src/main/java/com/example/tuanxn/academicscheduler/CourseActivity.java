package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.ui.CourseAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;
import com.example.tuanxn.academicscheduler.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseActivity extends AppCompatActivity {

    @BindView(R.id.course_recycler_view)
    RecyclerView courseRecyclerView;
    private List<CourseEntity> courseData = new ArrayList<>();
    private CourseAdapter courseAdapter;
    private CourseViewModel cViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initViewModel();
        initRecyclerView();

        courseData.addAll(cViewModel.mCourses);
        for (CourseEntity course: courseData) {
            Log.i("course", course.toString());
        }
    }

    private void initViewModel() {
        cViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
    }

    private void initRecyclerView() {
        courseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(layoutManager);

        courseAdapter = new CourseAdapter(courseData, this);
        courseRecyclerView.setAdapter(courseAdapter);
    }

}
