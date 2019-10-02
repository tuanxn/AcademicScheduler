package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.viewmodel.ModifyAssessmentViewModel;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tuanxn.academicscheduler.utilities.Constants.ASSESSMENT_ID_KEY;
import static com.example.tuanxn.academicscheduler.utilities.DateConverter.dateToString;

public class ModifyAssessmentActivity extends AppCompatActivity {

    @BindView(R.id.assessmentTitle)
    TextView aTitle;
    @BindView(R.id.assessmentStart)
    TextView aStart;
    @BindView(R.id.assessmentEnd)
    TextView aEnd;
    @BindView(R.id.assessmentType)
    Spinner dropdown;

    private ModifyAssessmentViewModel maViewModel;
    private boolean mNewAssessment;
    public static String assessmentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.assessmentType);
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
        maViewModel.mLiveAssessment.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(@Nullable AssessmentEntity assessmentEntity) {
                aTitle.setText(assessmentEntity.getTitle());
                aStart.setText(dateToString(assessmentEntity.getStartDate()));
                aEnd.setText(dateToString(assessmentEntity.getEndDate()));
                assessmentType = assessmentEntity.getType();
                if(assessmentType.equals("Objective Assessment")) {
                    dropdown.setSelection(0);
                }else {
                    dropdown.setSelection(1);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New assessment");
            mNewAssessment = true;
        }else {
            setTitle("Edit assessment");
            int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            maViewModel.loadData(assessmentId);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            try {
                saveAndReturn();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndReturn() throws ParseException {
        maViewModel.saveAssessment(aTitle.getText().toString(), aStart.getText().toString(), aEnd.getText().toString(), dropdown.getSelectedItem().toString());
        finish();
    }
}
