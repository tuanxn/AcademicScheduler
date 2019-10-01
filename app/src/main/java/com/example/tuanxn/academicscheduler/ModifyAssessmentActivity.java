package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tuanxn.academicscheduler.viewmodel.ModifyAssessmentViewModel;

import butterknife.ButterKnife;

public class ModifyAssessmentActivity extends AppCompatActivity {

    private ModifyAssessmentViewModel maViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.AssessmentType);
        //create a list of items for the spinner.
        String[] items = new String[]{"Objective Assessment", "Performance Assessment"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        maViewModel = ViewModelProviders.of(this).get(ModifyAssessmentViewModel.class);
    }

}
