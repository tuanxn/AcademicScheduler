package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanxn.academicscheduler.database.AssessmentEntity;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.ui.AssessmentAdapter;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;
import com.example.tuanxn.academicscheduler.viewmodel.ModifyCourseViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tuanxn.academicscheduler.utilities.Constants.COURSE_ID_KEY;

public class ModifyCourseActivity extends AppCompatActivity {

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

    private List<AssessmentEntity> assessmentData = new ArrayList<>();
    private AssessmentAdapter assessmentAdapter;
    private ModifyCourseViewModel mcViewModel;
    private static String courseStatus;
    private boolean mNewCourse;


    @OnClick(R.id.addAssessmentButton)
    void fabClickHandler() {
        Intent intent = new Intent(this, ModifyAssessmentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.editNote)
    void editClickHandler() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {

        final Observer<List<AssessmentEntity>> assessmentsObserver = new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessmentEntities) {
                assessmentData.clear();
                assessmentData.addAll(assessmentEntities);

                if (assessmentAdapter == null) {
                    assessmentAdapter = new AssessmentAdapter(assessmentData, ModifyCourseActivity.this);
                    courseAssessmentRecyclerView.setAdapter(assessmentAdapter);
                }else {
                    assessmentAdapter.notifyDataSetChanged();
                }
            }
        };

        mcViewModel = ViewModelProviders.of(this).get(ModifyCourseViewModel.class);
        mcViewModel.mcAssessments.observe(this, assessmentsObserver);
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
            int courseId = extras.getInt(COURSE_ID_KEY);
            mcViewModel.loadData(courseId);
        }
    }

    private void initRecyclerView() {
        courseAssessmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        courseAssessmentRecyclerView.setLayoutManager(layoutManager);
    }

}
