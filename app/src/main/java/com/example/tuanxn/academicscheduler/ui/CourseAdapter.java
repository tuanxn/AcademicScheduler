package com.example.tuanxn.academicscheduler.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tuanxn.academicscheduler.ModifyCourseActivity;
import com.example.tuanxn.academicscheduler.R;
import com.example.tuanxn.academicscheduler.database.CourseEntity;
import com.example.tuanxn.academicscheduler.utilities.DateConverter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tuanxn.academicscheduler.utilities.Constants.COURSE_ID_KEY;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final List<CourseEntity> mCourses;
    private final Context mContext;

    public CourseAdapter(List<CourseEntity> mCourses, Context mContext) {
        this.mCourses = mCourses;
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
        final CourseEntity course = mCourses.get(position);
        holder.mTextView.setText(course.getTitle() + " - " + course.getStatus() + "\nStart: " + DateConverter.dateToString(course.getStartDate()) + " - End: " + DateConverter.dateToString(course.getEndDate()));

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ModifyCourseActivity.class);
                intent.putExtra(COURSE_ID_KEY, course.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_text)
        TextView mTextView;
        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
