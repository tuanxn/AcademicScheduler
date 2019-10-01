package com.example.tuanxn.academicscheduler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.tuanxn.academicscheduler.model.CourseEntity;
import com.example.tuanxn.academicscheduler.model.TermEntity;
import com.example.tuanxn.academicscheduler.ui.CourseAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseActivity extends AppCompatActivity {

    @BindView(R.id.course_recycler_view)
    RecyclerView courseRecyclerView;
    private List<CourseEntity> courseData = new ArrayList<>();
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();


        try {
            courseData.addAll(SampleData.getCourses());
            for (CourseEntity course: courseData) {
                Log.i("course", course.toString());
            }

        }catch (Exception e) {

        }
    }

    private void initRecyclerView() {
        courseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(layoutManager);

        courseAdapter = new CourseAdapter(courseData, this);
        courseRecyclerView.setAdapter(courseAdapter);
    }

}
