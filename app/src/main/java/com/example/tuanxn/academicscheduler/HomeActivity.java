package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tuanxn.academicscheduler.viewmodel.HomeViewModel;

import javax.security.auth.login.LoginException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel hViewModel;

    @OnClick(R.id.viewTerms)
    void viewTermsClickHandler() {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.viewCourses)
    void viewCoursesClickHandler() {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.viewAssessments)
    void viewAssessmentsClickHandler() {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initViewModel();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        }else if (id == R.id.action_delete_all){
            deleteAllData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        hViewModel.deleteAllData();
    }

    private void addSampleData() {
        hViewModel.addSampleData();
    }

    private void initViewModel() {
        hViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }
}
