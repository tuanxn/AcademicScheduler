package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.tuanxn.academicscheduler.database.TermEntity;
import com.example.tuanxn.academicscheduler.ui.TermAdapter;
import com.example.tuanxn.academicscheduler.utilities.SampleData;
import com.example.tuanxn.academicscheduler.viewmodel.TermViewModel;

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
    private TermViewModel tViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initViewModel();
        initRecyclerView();

        termData.addAll(tViewModel.mTerms);
        for (TermEntity term: termData) {
            Log.i("term", term.toString());
        }

    }

    private void initViewModel() {
        tViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
    }

    private void initRecyclerView() {
        termRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        termRecyclerView.setLayoutManager(layoutManager);

        termAdapter = new TermAdapter(termData, this);
        termRecyclerView.setAdapter(termAdapter);
    }

}
