package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.viewmodel.NoteViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends AppCompatActivity {

    @BindView(R.id.note_text)
    TextView nTextView;

    private NoteViewModel nViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initViewModel();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {
        nViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        nViewModel.mLiveCourse.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@Nullable CourseEntity courseEntity) {
                nTextView.setText(courseEntity.getNotes());
            }
        });
    }
}
