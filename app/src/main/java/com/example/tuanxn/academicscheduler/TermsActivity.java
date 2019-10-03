package com.example.tuanxn.academicscheduler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
        initRecyclerView();
        initViewModel();

    }

    private void initViewModel() {

        final Observer<List<TermEntity>> termsObserver = new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable List<TermEntity> termEntities) {
                termData.clear();
                termData.addAll(termEntities);

                if (termAdapter == null) {
                    termAdapter = new TermAdapter(termData, TermsActivity.this);
                    termRecyclerView.setAdapter(termAdapter);
                }else {
                    termAdapter.notifyDataSetChanged();
                }

            }
        };

        tViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        tViewModel.mTerms.observe(this, termsObserver);
    }

    private void initRecyclerView() {
        termRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        termRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
            termRecyclerView.getContext(), layoutManager.getOrientation());
        termRecyclerView.addItemDecoration(divider);


    }

}
