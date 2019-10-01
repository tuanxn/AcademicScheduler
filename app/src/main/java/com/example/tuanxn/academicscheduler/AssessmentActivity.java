package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.ui.AssessmentAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;
import com.example.tuanxn.academicscheduler.viewmodel.AssessmentViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentActivity extends AppCompatActivity {

    @BindView(R.id.assessment_recycler_view)
    RecyclerView assessmentRecyclerView;
    private List<AssessmentEntity> assessmentData = new ArrayList<>();
    private AssessmentAdapter assessmentAdapter;
    private AssessmentViewModel aViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

    }

    private void initViewModel() {

        final Observer<List<AssessmentEntity>> assessmentsObserver = new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessmentEntities) {
                assessmentData.clear();
                assessmentData.addAll(assessmentEntities);
                if (assessmentAdapter == null) {
                    assessmentAdapter = new AssessmentAdapter(assessmentData, AssessmentActivity.this);
                    assessmentRecyclerView.setAdapter(assessmentAdapter);
                }else {
                    assessmentAdapter.notifyDataSetChanged();
                }

            }
        };

        aViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        aViewModel.mAssessments.observe(this, assessmentsObserver);
    }

    private void initRecyclerView() {

        assessmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        assessmentRecyclerView.setLayoutManager(layoutManager);

    }


}
