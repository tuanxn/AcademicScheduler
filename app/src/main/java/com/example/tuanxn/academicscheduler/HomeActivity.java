package com.example.tuanxn.academicscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @OnClick(R.id.viewTerms)
    void viewTermsClickHandler() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.viewCourses)
    void viewCoursesClickHandler() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.viewAssessments)
    void viewAssessmentsClickHandler() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }
}
