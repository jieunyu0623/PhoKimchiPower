package com.bcit.phokimchipower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class main_courses extends AppCompatActivity {
    public static final String COURSE_NAME_EXTRA = "ca.bcit.phokimchipower.coursename";
    public static final String COURSE_CURRENT_GRADE_EXTRA = "ca.bcit.phokimchipower.currentgrade";

    RecyclerView recyclerView;
    CourseAdapter adapter;
    DatabaseReference databaseRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button addCourse;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_courses);
        recyclerView = findViewById(R.id.recyclerView);
        addCourse = findViewById(R.id.main_addCourse);
        title = findViewById(R.id.main_courseName);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("courses");
        FirebaseRecyclerOptions<Course> options = new FirebaseRecyclerOptions.Builder<Course>().setQuery(databaseRef, Course.class)
                .build();
        adapter = new CourseAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_courses.this, AddGradesActivity.class);
                startActivity(intent);
            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_courses.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }
}