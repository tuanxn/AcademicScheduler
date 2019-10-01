package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
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
        initViewModel();
        initRecyclerView();

        assessmentData.addAll(aViewModel.mAssessments);
        for (AssessmentEntity assessment: assessmentData) {
            Log.i("assessment", assessment.toString());
        }

    }

    private void initViewModel() {
        aViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
    }

    private void initRecyclerView() {
        assessmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        assessmentRecyclerView.setLayoutManager(layoutManager);

        assessmentAdapter = new AssessmentAdapter(assessmentData, this);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
    }


}
