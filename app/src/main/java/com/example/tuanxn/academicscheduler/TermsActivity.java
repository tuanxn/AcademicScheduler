package com.example.tuanxn.academicscheduler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.tuanxn.academicscheduler.R;
import com.example.tuanxn.academicscheduler.model.TermEntity;
import com.example.tuanxn.academicscheduler.ui.TermAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsActivity extends AppCompatActivity {

    @BindView(R.id.term_recycler_view)
    RecyclerView termRecyclerView;
    private List<TermEntity> termData = new ArrayList<>();
    private TermAdapter termAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try {
            termData.addAll(SampleData.getTerms());

        }catch (Exception e) {

        }

    }

    private void initRecyclerView() {
        termRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        termRecyclerView.setLayoutManager(layoutManager);

        termAdapter = new TermAdapter(termData, this);
        termRecyclerView.setAdapter(termAdapter);
    }

}
