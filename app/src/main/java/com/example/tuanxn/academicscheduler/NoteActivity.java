package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

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

        // User will need to add gmail account to AVD
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Here are my notes");
                intent.putExtra(Intent.EXTRA_TEXT, nTextView.getText().toString());
                Intent mailer = Intent.createChooser(intent, null);
                startActivity(mailer);
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
            if(TextUtils.isEmpty(nTextView.getText().toString().trim())) {
                Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                saveAndReturn();
                return true;
            }
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
