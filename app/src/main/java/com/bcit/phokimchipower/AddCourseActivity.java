package com.bcit.phokimchipower;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddCourseActivity extends AppCompatActivity {

    public static final String COURSE_NAME_EXTRA = "ca.bcit.phokimchipower.coursename";
    public static final String COURSE_CURRENT_GRADE_EXTRA = "ca.bcit.phokimchipower.currentgrade";
    public static final String COURSE_WEIGHT_EXTRA = "ca.bcit.phokimchipower.weight";

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    Spinner spinner6;
    EditText weight1;
    EditText weight2;
    EditText weight3;
    EditText weight4;
    EditText weight5;
    EditText weight6;
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
        weight1 = findViewById(R.id.editText_weight1);
        weight2 = findViewById(R.id.editText_weight2);
        weight3 = findViewById(R.id.editText_weight3);
        weight4 = findViewById(R.id.editText_weight4);
        weight5 = findViewById(R.id.editText_weight5);
        weight6 = findViewById(R.id.editText_weight6);
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
            final int weight1Value = Integer.parseInt(weight1.getText().toString());
            final int weight2Value = Integer.parseInt(weight2.getText().toString());
            final int weight3Value = Integer.parseInt(weight3.getText().toString());
            final int weight4Value = Integer.parseInt(weight4.getText().toString());
            final int weight5Value = Integer.parseInt(weight5.getText().toString());
            final int weight6Value = Integer.parseInt(weight6.getText().toString());
            final ArrayList<Course> newCourse = new ArrayList<>();
            final HashMap<String, Integer> weight = new HashMap<>();
            final HashMap<String, Object> postCourse = new HashMap<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //ArrayList<Course> newCourse = new ArrayList<>();
                //HashMap<String, Integer> weight = new HashMap<>();
                weight.put(spinner1.getSelectedItem().toString(), weight1Value);
                weight.put(spinner2.getSelectedItem().toString(), weight2Value);
                weight.put(spinner3.getSelectedItem().toString(), weight3Value);
                weight.put(spinner4.getSelectedItem().toString(), weight4Value);
                weight.put(spinner5.getSelectedItem().toString(), weight5Value);
                weight.put(spinner6.getSelectedItem().toString(), weight6Value);
                Course c = new Course(name.getText().toString(), weight);
//                HashMap<String, Object> postCourse = new HashMap<>();
                if(snapshot.hasChild("courses")) {
                    for(DataSnapshot ss: snapshot.child("courses").getChildren()){
                        postCourse.put(ss.getKey(), ss.getValue(Course.class));
                        int size = postCourse.size();
                        postCourse.put(Integer.toString(size), c);
                    }
                    databaseRef.child("courses").updateChildren(postCourse);
                } else {
                    newCourse.add(c);
                    databaseRef.child("courses").setValue(newCourse);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseRef.addListenerForSingleValueEvent(listener);
        Intent intent = new Intent(AddCourseActivity.this, main_courses.class);
        startActivity(intent);
    }
}