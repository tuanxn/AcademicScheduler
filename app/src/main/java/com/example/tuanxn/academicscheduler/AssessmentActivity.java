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

import com.example.tuanxn.academicscheduler.model.AssessmentEntity;
import com.example.tuanxn.academicscheduler.ui.AssessmentAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentActivity extends AppCompatActivity {

    @BindView(R.id.assessment_recycler_view)
    RecyclerView assessmentRecyclerView;
    private List<AssessmentEntity> assessmentData = new ArrayList<>();
    private AssessmentAdapter assessmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();


        try {
            assessmentData.addAll(SampleData.getAssessments());
            for (AssessmentEntity assessment: assessmentData) {
                Log.i("assessment", assessment.toString());
            }

        }catch (Exception e) {

        }
    }

    private void initRecyclerView() {
        assessmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        assessmentRecyclerView.setLayoutManager(layoutManager);

        assessmentAdapter = new AssessmentAdapter(assessmentData, this);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
    }


}
