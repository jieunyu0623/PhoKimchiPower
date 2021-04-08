package com.bcit.phokimchipower;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class CourseAdapter extends FirebaseRecyclerAdapter<Course, CourseAdapter.CourseViewHolder> {
    public CourseAdapter(@NonNull FirebaseRecyclerOptions<Course> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CourseViewHolder holder, int position, @NonNull Course model) {
        holder.Course1TextView.setText(model.getCourseName());
//        holder.Course2TextView.setText(model.getCourseName());
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_cardview, parent, false);
        return new CourseAdapter.CourseViewHolder(view);
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        public TextView Course1TextView;
//        public TextView Course2TextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            Course1TextView = itemView.findViewById(R.id.textView_courseName1);
//            Course2TextView = itemView.findViewById(R.id.textView_courseName2);

        }
    }
}
