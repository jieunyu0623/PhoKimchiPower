package com.bcit.phokimchipower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AddCourseActivity extends AppCompatActivity {
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    Spinner spinner6;
    EditText name;
    Button add;

    DatabaseReference databaseRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        spinner1 = findViewById(R.id.spinner_evalType1);
        spinner2 = findViewById(R.id.spinner_evalType2);
        spinner3 = findViewById(R.id.spinner_evalType3);
        spinner4 = findViewById(R.id.spinner_evalType4);
        spinner5 = findViewById(R.id.spinner_evalType5);
        spinner6 = findViewById(R.id.spinner_evalType6);
        name = findViewById(R.id.editText_addCourse_name);
        add = findViewById(R.id.button_addCourse);
        ArrayList<String> list = new ArrayList<>();
        list.add("Assignment");
        list.add("Quizzes");
        list.add("Labs");
        list.add("Midterm");
        list.add("Final");
        list.add("Projects");
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrAdapter);
        spinner2.setAdapter(arrAdapter);
        spinner3.setAdapter(arrAdapter);
        spinner4.setAdapter(arrAdapter);
        spinner5.setAdapter(arrAdapter);
        spinner6.setAdapter(arrAdapter);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });
    }

    private void addCourse() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course c = new Course(name.getText().toString());
                User newUser = snapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseRef.child("courses").addListenerForSingleValueEvent(listener);
        Intent intent = new Intent(AddCourseActivity.this, main_courses.class);
        startActivity(intent);
    }
}