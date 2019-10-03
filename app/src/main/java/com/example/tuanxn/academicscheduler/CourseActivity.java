package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
        initRecyclerView();
        initViewModel();

    }

    private void initViewModel() {

        final Observer<List<CourseEntity>> coursesObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                courseData.clear();
                courseData.addAll(courseEntities);
                if (courseAdapter == null) {
                    courseAdapter = new CourseAdapter(courseData, CourseActivity.this);
                    courseRecyclerView.setAdapter(courseAdapter);
                }else {
                    courseAdapter.notifyDataSetChanged();
                }

            }
        };

        cViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        cViewModel.mCourses.observe(this, coursesObserver);
    }

    private void initRecyclerView() {
        courseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                courseRecyclerView.getContext(), layoutManager.getOrientation());
        courseRecyclerView.addItemDecoration(divider);


    }

}
