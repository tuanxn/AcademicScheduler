package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        initRecyclerView();
        initViewModel();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {

        final Observer<List<CourseEntity>> coursesObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                courseData.clear();
                courseData.addAll(courseEntities);

                if (courseAdapter == null) {
                    courseAdapter = new CourseAdapter(courseData, ModifyTermActivity.this);
                    termCourseRecyclerView.setAdapter(courseAdapter);
                }else {
                    courseAdapter.notifyDataSetChanged();
                }

            }
        };

        mtViewModel = ViewModelProviders.of(this).get(ModifyTermViewModel.class);
        mtViewModel.mtCourses.observe(this, coursesObserver);
    }

    private void initRecyclerView() {
        termCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        termCourseRecyclerView.setLayoutManager(layoutManager);
    }

}
