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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.database.NoteEntity;
import com.example.tuanxn.academicscheduler.ui.AssessmentAdapter;
import com.example.tuanxn.academicscheduler.ui.NoteAdapter;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;
import com.example.tuanxn.academicscheduler.viewmodel.ModifyCourseViewModel;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tuanxn.academicscheduler.utilities.Constants.COURSE_ID_KEY;

public class ModifyCourseActivity extends AppCompatActivity {

    @BindView(R.id.course_note_recycler_view)
    RecyclerView courseNoteRecyclerView;

    @BindView(R.id.course_assessment_recycler_view)
    RecyclerView courseAssessmentRecyclerView;

    @BindView(R.id.courseTitle)
    TextView title;
    @BindView(R.id.courseStart)
    TextView courseStart;
    @BindView(R.id.courseEnd)
    TextView courseEnd;
    @BindView(R.id.courseStatus)
    Spinner dropdown;
    @BindView(R.id.courseMentorName2)
    TextView courseMentorName;
    @BindView(R.id.courseMentorPhone2)
    TextView courseMentorPhone;
    @BindView(R.id.courseMentorEmail)
    TextView courseMentorEmail;

    private List<NoteEntity> noteData = new ArrayList<>();
    private List<AssessmentEntity> assessmentData = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private AssessmentAdapter assessmentAdapter;
    private ModifyCourseViewModel mcViewModel;
    private static String courseStatus;
    private boolean mNewCourse;
    private int courseId;


    @OnClick(R.id.addAssessmentButton)
    void fabClickHandler() {
        boolean validated = true;
        String errorMessage = "";
        if (assessmentData.size() == 5) {
            errorMessage += "Cannot add more than 5 assessments\nPlease delete an assessment first";
            validated = false;
        }
        if (TextUtils.isEmpty(title.getText().toString().trim())) {
            errorMessage += "Missing course title\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseStart.getText().toString().trim())) {
            errorMessage += "Missing course start\n";
            validated = false;
        } else if(!courseStart.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
            errorMessage += "Incorrect date format for course date\nPlease enter MM/dd/yyyy\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseEnd.getText().toString().trim())) {
            errorMessage += "Missing course end\n";
            validated = false;
        } else if(!courseEnd.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
            errorMessage += "Incorrect date format for course date\nPlease enter MM/dd/yyyy\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseMentorName.getText().toString().trim())) {
            errorMessage += "Missing course mentor name\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseMentorPhone.getText().toString().trim())) {
            errorMessage += "Missing course mentor phone\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseMentorEmail.getText().toString().trim())) {
            errorMessage += "Missing course mentor email\n";
            validated = false;
        }
        if (validated) {
            try {
                saveAndReturn();
                Log.i("test", "CourseId: " + Integer.toString(AppRepository.createdCourseId));
                Intent intent = new Intent(this, ModifyAssessmentActivity.class);
                startActivity(intent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();


        //get the spinner from the xml
        Spinner dropdown = (Spinner)findViewById(R.id.courseStatus);
        Toast.makeText(this, dropdown.toString(), Toast.LENGTH_SHORT).show();
        //create a list of items for the spinner.
        String[] items = new String[]{"Plan to Take", "In Progress", "Completed", "Dropped"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

    }

    private void initViewModel() {

        final Observer<List<AssessmentEntity>> assessmentsObserver = new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessmentEntities) {
                assessmentData.clear();
                for (AssessmentEntity assessmentEntity: assessmentEntities) {
                    if (courseId == assessmentEntity.getCourseId()) {
                        assessmentData.add(assessmentEntity);
                    }
                }
//                assessmentData.addAll(assessmentEntities);

                if (assessmentAdapter == null) {
                    assessmentAdapter = new AssessmentAdapter(assessmentData, ModifyCourseActivity.this);
                    courseAssessmentRecyclerView.setAdapter(assessmentAdapter);
                }else {
                    assessmentAdapter.notifyDataSetChanged();
                }
            }
        };

        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                noteData.clear();
                for (NoteEntity noteEntity: noteEntities) {
                    if (courseId == noteEntity.getCourseId()) {
                        noteData.add(noteEntity);
                    }
                }
//                noteData.addAll(noteEntities);

                if (noteAdapter == null) {
                    noteAdapter = new NoteAdapter(noteData, ModifyCourseActivity.this);
                    courseNoteRecyclerView.setAdapter(noteAdapter);
                }else {
                    noteAdapter.notifyDataSetChanged();
                }
            }
        };

        mcViewModel = ViewModelProviders.of(this).get(ModifyCourseViewModel.class);

        mcViewModel.mcAssessments.observe(this, assessmentsObserver);

        mcViewModel.mcNotes.observe(this, notesObserver);

        mcViewModel.mLiveCourse.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@Nullable CourseEntity courseEntity) {
                title.setText(courseEntity.getTitle());
                courseStart.setText(DateConverter.dateToString(courseEntity.getStartDate()));
                courseEnd.setText(DateConverter.dateToString(courseEntity.getEndDate()));
                courseStatus = courseEntity.getStatus();
                switch (courseStatus) {
                    case "Plan to Take":
                        dropdown.setSelection(0);
                        break;
                    case "In Progress":
                        dropdown.setSelection(1);
                        break;
                    case "Completed":
                        dropdown.setSelection(2);
                        break;
                    case "Dropped":
                        dropdown.setSelection(3);
                        break;
                    default:
                }
                courseMentorName.setText(courseEntity.getMentorName());
                courseMentorPhone.setText(courseEntity.getMentorPhone());
                courseMentorEmail.setText(courseEntity.getMentorEmail());
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New course");
            mNewCourse = true;
        }else {
            setTitle("Edit course");
            courseId = extras.getInt(COURSE_ID_KEY);
            mcViewModel.loadData(courseId);
        }
    }

    private void initRecyclerView() {
        courseAssessmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        courseAssessmentRecyclerView.setLayoutManager(layoutManager);

        courseNoteRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        courseNoteRecyclerView.setLayoutManager(layoutManager1);

        // TODO
        assessmentAdapter = new AssessmentAdapter(assessmentData, this);
        courseAssessmentRecyclerView.setAdapter(assessmentAdapter);

        noteAdapter = new NoteAdapter(noteData, this);
        courseNoteRecyclerView.setAdapter(noteAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(
                courseNoteRecyclerView.getContext(), layoutManager.getOrientation());
        courseNoteRecyclerView.addItemDecoration(divider);

        DividerItemDecoration divider1 = new DividerItemDecoration(
                courseAssessmentRecyclerView.getContext(), layoutManager.getOrientation());
        courseAssessmentRecyclerView.addItemDecoration(divider1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addnote, menu);
        if (!mNewCourse) {
            inflater.inflate(R.menu.menu_delete_course, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            boolean validated = true;
            String errorMessage = "";
            if (TextUtils.isEmpty(title.getText().toString().trim())) {
                errorMessage += "Missing course title\n";
                validated = false;
            }
            if (TextUtils.isEmpty(courseStart.getText().toString().trim())) {
                errorMessage += "Missing course start\n";
                validated = false;
            } else if(!courseStart.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
                errorMessage += "Incorrect date format for course date\nPlease enter MM/dd/yyyy\n";
                validated = false;
            }
            if (TextUtils.isEmpty(courseEnd.getText().toString().trim())) {
                errorMessage += "Missing course end\n";
                validated = false;
            } else if(!courseEnd.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
                errorMessage += "Incorrect date format for course date\nPlease enter MM/dd/yyyy\n";
                validated = false;
            }
            if (TextUtils.isEmpty(courseMentorName.getText().toString().trim())) {
                errorMessage += "Missing course mentor name\n";
                validated = false;
            }
            if (TextUtils.isEmpty(courseMentorPhone.getText().toString().trim())) {
                errorMessage += "Missing course mentor phone\n";
                validated = false;
            }
            if (TextUtils.isEmpty(courseMentorEmail.getText().toString().trim())) {
                errorMessage += "Missing course mentor email\n";
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
        }else if(id == R.id.action_add_note) {
            addNoteClickHandler();
            return true;
        }else if(id == R.id.action_delete_course) {
            mcViewModel.deleteCourse();
            finish();
        }else if(id == R.id.action_course_start_alarm) {
            Intent intent=new Intent(this,MyReceiver.class);
            intent.putExtra("TITLE", "Course " + title.getText().toString());
            intent.putExtra("DATE", courseStart.getText().toString());
            intent.putExtra("NOTIFYMESSAGE", " starting ");
            PendingIntent sender= PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            try {
                alarmManager.set(AlarmManager.RTC_WAKEUP, DateConverter.stringToMilli(courseStart.getText().toString()), sender);
                Log.i("test", DateConverter.stringToMilli(courseStart.getText().toString()).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "alarm set for " + title.getText().toString(), Toast.LENGTH_SHORT).show();
        }else if(id == R.id.action_course_end_alarm) {
            Intent intent=new Intent(this,MyReceiver.class);
            intent.putExtra("TITLE", "Course " + title.getText().toString());
            intent.putExtra("DATE", courseEnd.getText().toString());
            intent.putExtra("NOTIFYMESSAGE", " ending ");
            PendingIntent sender= PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            try {
                alarmManager.set(AlarmManager.RTC_WAKEUP, DateConverter.stringToMilli(courseEnd.getText().toString()), sender);
                Log.i("test", DateConverter.stringToMilli(courseEnd.getText().toString()).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "alarm set for " + title.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNoteClickHandler() {
        boolean validated = true;
        String errorMessage = "";
        if (TextUtils.isEmpty(title.getText().toString().trim())) {
            errorMessage += "Missing course title\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseStart.getText().toString().trim())) {
            errorMessage += "Missing course start\n";
            validated = false;
        } else if(!courseStart.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
            errorMessage += "Incorrect date format for course date\nPlease enter MM/dd/yyyy\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseEnd.getText().toString().trim())) {
            errorMessage += "Missing course end\n";
            validated = false;
        } else if(!courseEnd.getText().toString().matches("\\d{2}/\\d{2}/\\d{4}")) {
            errorMessage += "Incorrect date format for course date\nPlease enter MM/dd/yyyy\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseMentorName.getText().toString().trim())) {
            errorMessage += "Missing course mentor name\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseMentorPhone.getText().toString().trim())) {
            errorMessage += "Missing course mentor phone\n";
            validated = false;
        }
        if (TextUtils.isEmpty(courseMentorEmail.getText().toString().trim())) {
            errorMessage += "Missing course mentor email\n";
            validated = false;
        }
        if (validated) {
            try {
                saveAndReturn();
                Log.i("test", "CourseId: " + Integer.toString(AppRepository.createdCourseId));
                Intent intent = new Intent(this, NoteActivity.class);
                startActivity(intent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAndReturn() throws ParseException {
        mcViewModel.saveCourse(title.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(),
                dropdown.getSelectedItem().toString(), courseMentorName.getText().toString(), courseMentorPhone.getText().toString(),
                courseMentorEmail.getText().toString());
        finish();
    }
}
