package com.example.tuanxn.academicscheduler;

import android.content.Intent;
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
import com.example.tuanxn.academicscheduler.ui.CourseAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyTermActivity extends AppCompatActivity {

    @BindView(R.id.termCourse_recycler_view)
    RecyclerView termCourseRecyclerView;
    private List<CourseEntity> courseData = new ArrayList<>();
    private CourseAdapter courseAdapter;

    @OnClick(R.id.addCourseButton)
    void fabClickHandler() {
        Intent intent = new Intent(this, ModifyCourseActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_term);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        termCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        termCourseRecyclerView.setLayoutManager(layoutManager);

        courseAdapter = new CourseAdapter(courseData, this);
        termCourseRecyclerView.setAdapter(courseAdapter);
    }

}
