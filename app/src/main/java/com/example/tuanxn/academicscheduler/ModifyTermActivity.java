package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.ui.CourseAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;
import com.example.tuanxn.academicscheduler.viewmodel.ModifyTermViewModel;

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
    private ModifyTermViewModel mtViewModel;

    @OnClick(R.id.addCourseButton)
    void fabClickHandler() {
        Intent intent = new Intent(this, ModifyCourseActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initViewModel();
        initRecyclerView();

        courseData.addAll(mtViewModel.mCourses);
        for (CourseEntity course: courseData) {
            Log.i("course", course.toString());
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {
        mtViewModel = ViewModelProviders.of(this).get(ModifyTermViewModel.class);
    }

    private void initRecyclerView() {
        termCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        termCourseRecyclerView.setLayoutManager(layoutManager);

        courseAdapter = new CourseAdapter(courseData, this);
        termCourseRecyclerView.setAdapter(courseAdapter);
    }

}
