package com.example.tuanxn.academicscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.tuanxn.academicscheduler.R;
import com.example.tuanxn.academicscheduler.model.TermEntity;
import com.example.tuanxn.academicscheduler.ui.TermAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsActivity extends AppCompatActivity {

    @BindView(R.id.term_recycler_view)
    RecyclerView termRecyclerView;

    @OnClick(R.id.termAddButton)
    void fabClickHandler() {
        Intent intent = new Intent(this, ModifyTermActivity.class);
        startActivity(intent);
    }

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

        try {
            termData.addAll(SampleData.getTerms());
            for (TermEntity term: termData) {
                Log.i("term", term.toString());
            }

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
