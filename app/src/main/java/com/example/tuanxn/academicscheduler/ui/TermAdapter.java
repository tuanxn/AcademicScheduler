package com.example.tuanxn.academicscheduler.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tuanxn.academicscheduler.R;
import com.example.tuanxn.academicscheduler.database.TermEntity;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private final List<TermEntity> mTerms;
    private final Context mContext;

    public TermAdapter(List<TermEntity> mTerms, Context mContext) {
        this.mTerms = mTerms;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TermEntity term = mTerms.get(position);
        holder.mTextView.setText(term.getTitle() + "\nStart: " + DateConverter.dateToString(term.getStartDate()) + " - End: " + DateConverter.dateToString(term.getEndDate()));

    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_text)
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
