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
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tuanxn.academicscheduler.database.AppRepository;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.database.TermEntity;
import com.example.tuanxn.academicscheduler.ui.CourseAdapter;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;
import com.example.tuanxn.academicscheduler.viewmodel.ModifyTermViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tuanxn.academicscheduler.utilities.Constants.TERM_ID_KEY;

public class ModifyTermActivity extends AppCompatActivity {

    @BindView(R.id.termTitle)
    TextView title;
    @BindView(R.id.termStart)
    TextView termStart;
    @BindView(R.id.termEnd)
    TextView termEnd;

    @BindView(R.id.termCourse_recycler_view)
    RecyclerView termCourseRecyclerView;

    private List<CourseEntity> courseData = new ArrayList<>();
    private CourseAdapter courseAdapter;
    private ModifyTermViewModel mtViewModel;
    private boolean mNewTerm;

    @OnClick(R.id.addCourseButton)
    void fabClickHandler() {

        try {
            saveAndReturn();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("test", "TermId: " + Long.toString(AppRepository.createdTermId));
            Intent intent = new Intent(this, ModifyCourseActivity.class);
            intent.putExtra(TERM_ID_KEY, AppRepository.createdTermId);
            startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

    }

    private void initViewModel() {

        final Observer<List<CourseEntity>> coursesObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                courseData.clear();
                courseData.addAll(courseEntities);

                if (courseAdapter == null) {
                    courseAdapter = new CourseAdapter(courseData, ModifyTermActivity.this);
                    termCourseRecyclerView.setAdapter(courseAdapter);
                }else {
                    courseAdapter.notifyDataSetChanged();
                }

            }
        };

        mtViewModel = ViewModelProviders.of(this).get(ModifyTermViewModel.class);
        mtViewModel.mtCourses.observe(this, coursesObserver);
        mtViewModel.mLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {
                title.setText(termEntity.getTitle());
                termStart.setText(DateConverter.dateToString(termEntity.getStartDate()));
                termEnd.setText(DateConverter.dateToString(termEntity.getEndDate()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New term");
            mNewTerm = true;
        }else {
            setTitle("Edit term");
            int termId = extras.getInt(TERM_ID_KEY);
            mtViewModel.loadData(termId);
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
        mtViewModel.saveTerm(title.getText().toString(), termStart.getText().toString(), termEnd.getText().toString());
        finish();
    }

    private void initRecyclerView() {
        termCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        termCourseRecyclerView.setLayoutManager(layoutManager);
    }

}
