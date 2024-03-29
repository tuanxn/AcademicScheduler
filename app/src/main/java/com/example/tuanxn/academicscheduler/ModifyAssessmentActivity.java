package com.example.tuanxn.academicscheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_assessment_alarms, menu);
        if (!mNewAssessment) {

            inflater.inflate(R.menu.menu_delete_assessment, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            boolean validated = true;
            String errorMessage = "";
            if (TextUtils.isEmpty(aTitle.getText().toString().trim())) {
                errorMessage += "Missing assessment title\n";
                validated = false;
            }
            if (TextUtils.isEmpty(aStart.getText().toString().trim())) {
                errorMessage += "Missing assessment start\n";
                validated = false;
            } else if(!aStart.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
                errorMessage += "Incorrect date format for assessment date\nPlease enter MM/dd/yyyy\n";
                validated = false;
            }
            if (TextUtils.isEmpty(aEnd.getText().toString().trim())) {
                errorMessage += "Missing assessment end\n";
                validated = false;
            } else if(!aEnd.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
                errorMessage += "Incorrect date format for assessment date\nPlease enter MM/dd/yyyy\n";
                validated = false;
            }
            if (validated) {
                try {
                    saveAndReturn();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            }else {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                return true;
            }
        }else if(item.getItemId() == R.id.action_delete_assessment) {
            maViewModel.deleteAssessment();
            finish();
        }else if(item.getItemId() == R.id.action_assessment_start_alarm) {
            Intent intent=new Intent(this,MyReceiver.class);
            intent.putExtra("TITLE", aTitle.getText().toString());
            intent.putExtra("DATE", aStart.getText().toString());
            intent.putExtra("NOTIFYMESSAGE", " starting ");
            PendingIntent sender= PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            try {
                alarmManager.set(AlarmManager.RTC_WAKEUP, DateConverter.stringToMilli(aStart.getText().toString()), sender);
                Log.i("test", DateConverter.stringToMilli(aStart.getText().toString()).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "alarm set for " + aTitle.getText().toString(), Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.action_assessment_end_alarm) {
            Intent intent=new Intent(this,MyReceiver.class);
            intent.putExtra("TITLE", aTitle.getText().toString());
            intent.putExtra("DATE", aEnd.getText().toString());
            intent.putExtra("NOTIFYMESSAGE", " ending ");
            PendingIntent sender= PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            try {
                alarmManager.set(AlarmManager.RTC_WAKEUP, DateConverter.stringToMilli(aEnd.getText().toString()), sender);
                Log.i("test", DateConverter.stringToMilli(aEnd.getText().toString()).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "alarm set for " + aTitle.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndReturn() throws ParseException {
        maViewModel.saveAssessment(aTitle.getText().toString(), aStart.getText().toString(),
                aEnd.getText().toString(), dropdown.getSelectedItem().toString());
        finish();
    }
}
