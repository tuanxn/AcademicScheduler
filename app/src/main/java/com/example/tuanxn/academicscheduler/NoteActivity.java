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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.database.NoteEntity;
import com.example.tuanxn.academicscheduler.viewmodel.NoteViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tuanxn.academicscheduler.utilities.Constants.NOTE_ID_KEY;

public class NoteActivity extends AppCompatActivity {

    @BindView(R.id.note_text)
    TextView nTextView;

    private NoteViewModel nViewModel;
    private boolean mNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    }

    private void initViewModel() {
        nViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        nViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                nTextView.setText(noteEntity != null ? noteEntity.getText() : null);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New note");
            mNewNote = true;
        }else {
            setTitle("Edit note");
            int noteId = extras.getInt(NOTE_ID_KEY);
            nViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_delete_note, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete_note) {
            nViewModel.deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndReturn() {
        nViewModel.saveNote(nTextView.getText().toString());
        finish();
    }
}
