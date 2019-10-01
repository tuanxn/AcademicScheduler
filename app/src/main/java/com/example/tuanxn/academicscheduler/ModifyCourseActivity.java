package com.example.tuanxn.academicscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.OnClick;

public class ModifyCourseActivity extends AppCompatActivity {

    @OnClick(R.id.addAssessmentButton)
    void fabClickHandler() {
        Intent intent = new Intent(this, ModifyAssessmentActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the spinner from the xml
        Spinner dropdown = (Spinner)findViewById(R.id.courseStatus);
        Toast.makeText(this, dropdown.toString(), Toast.LENGTH_SHORT).show();
        //create a list of items for the spinner.
        String[] items = new String[]{"In Progress", "Completed", "Dropped", "Plan to Take"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
