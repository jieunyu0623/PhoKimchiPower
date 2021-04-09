package com.bcit.phokimchipower;

import android.content.Intent;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        CardView card = holder.card;
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(card.getContext(), testActivity.class);
                intent.putExtra(AddCourseActivity.COURSE_NAME_EXTRA, model.getCourseName());
                intent.putExtra(AddCourseActivity.COURSE_CURRENT_GRADE_EXTRA, model.getCurrentGrade());
                intent.putExtra(AddCourseActivity.COURSE_WEIGHT_EXTRA, model.getWeight());
                v.getContext().startActivity(intent);
            }
        });
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
        public CardView card;
//        public TextView Course2TextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            Course1TextView = itemView.findViewById(R.id.textView_courseName1);
            card = itemView.findViewById(R.id.card_elem);
//            Course2TextView = itemView.findViewById(R.id.textView_courseName2);

        }
    }
}
