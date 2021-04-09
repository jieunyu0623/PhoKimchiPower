package com.bcit.phokimchipower;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddGradesActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String uid;
    User current_user;
    String course_number;
    ArrayList<String> courses;
    EditText evaluation_name;
    EditText grade;
    Button add_grade_button;
    Course current_course;
    String courseName;
    private static final String TAG = "AddGradesActivity";

    Spinner evaluation_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);
        evaluation_type = findViewById(R.id.evaluation_type_spinner);
        evaluation_name = findViewById(R.id.evaluation_name_EditText_grades);
        grade = findViewById(R.id.grade_editText_grades);
        add_grade_button = findViewById(R.id.add_button_grade);
        courses = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        uid = user.getUid();
        current_user = new User(user.getUid(), user.getEmail(), user.getDisplayName());

        ArrayList<String> list = new ArrayList<>();
        list.add("Assignment");
        list.add("Quizzes");
        list.add("Labs");
        list.add("Midterm");
        list.add("Final");
        list.add("Projects");
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evaluation_type.setAdapter(arrAdapter);

        //attach the listener to the spinner
//        evaluation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String choice = parent.getItemAtPosition(position).toString();
//                System.out.println(choice);
//                Toast.makeText(AddGradesActivity.this, choice, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        Intent intent = getIntent();
        courseName = intent.getStringExtra(AddCourseActivity.COURSE_NAME_EXTRA);


//        System.out.println(reference.child("courses"));

        //course_number = String.valueOf(current_user.getCourseNumber());
//
        createSpinnerInfo();

//        evaluation_type.setOnItemSelectedListener(new MyOnItemSelectedListener());


        //adds all the courses inherited from the current user to the courses arraylist and dynamically creates a string-array in XML.
        //createSpinnerDropDown();

        add_grade_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!evaluation_type.isSelected()) {
//                    Toast.makeText(AddGradesActivity.this, "You need to select the evaluation type.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (evaluation_name.getText().toString().isEmpty() || evaluation_name.getText().toString().length() == 0) {
                    Toast.makeText(AddGradesActivity.this, "You need to enter the name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (grade.getText().toString().isEmpty() || grade.getText().toString().length() == 0) {
                    Toast.makeText(AddGradesActivity.this, "You need to enter the grade.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(grade.getText().toString()) < 0) {
                    Toast.makeText(AddGradesActivity.this, "Grades can't be negative", Toast.LENGTH_SHORT).show();
                    return;
                }
                reference.child(uid).child("courses").child(current_user.getCourseNumber() + "").child("currentGrade");


//                Intent intent = new Intent(AddGradesActivity.this, testActivity.class); //navigates back to the main page.
//                startActivity(intent);
//                finish();
                addGrade();
                Toast.makeText(AddGradesActivity.this, "you successfully added a grade!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addGrade() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // String selected_evaluation = evaluation_type.getSelectedItem().toString();
                String selected_evaluation = "Assignments";

                final ArrayList<Grade> newGrades = new ArrayList<>();
                final HashMap<String, Object> postGrade = new HashMap<>();

                String gradeName = evaluation_name.getText().toString();
                double userGrade = Double.parseDouble(grade.getText().toString());

                Grade g = new Grade(selected_evaluation, gradeName, userGrade);
                for (DataSnapshot ss: snapshot.getChildren()) {
                    Course c = ss.getValue(Course.class);
                    if (c.getCourseName().equals(courseName)) {
                        if (ss.hasChild("grades")) {
                            postGrade.put(ss.getKey(), ss.getValue(Grade.class));
                            postGrade.forEach((k, v) -> {
                                System.out.println(k);
                                System.out.println(v);
                            });
                            int size = postGrade.size();
                            postGrade.put(Integer.toString(size), g);
                            reference.child(uid).child("courses").child(ss.getKey()).child("grades").updateChildren(postGrade);
                        } else {
                            newGrades.add(g);
                            reference.child(uid).child("courses").child(ss.getKey()).child("grades").setValue(newGrades);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

    private void createSpinnerDropDown() {

        for (Course course: current_user.getCourses()) {
            courses.add(course.toString());
        }

        ArrayList<String> weight = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    Course c = ss.getValue(Course.class);
                    c.getWeight().forEach((k, v) -> {
                        weight.add(k);
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        //create an ArrayAdapter from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, weight);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        evaluation_type.setAdapter(dataAdapter);
        //attach the listener to the spinner
        evaluation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = parent.getItemAtPosition(position).toString();
                System.out.println(choice);
                Toast.makeText(AddGradesActivity.this, choice, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
            }
        };
        reference.child(uid).child("courses").addListenerForSingleValueEvent(listener);
    }

    private ArrayList<String> getAssessmentNames() {
        ArrayList<String> weights = new ArrayList<>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    Course c = ss.getValue(Course.class);
                    if (courseName.equals(c.getCourseName())) {
                        c.getWeight().forEach((k, v) -> {
                            weights.add(k);
                        });
                    current_course = c;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.child(uid).child("courses").addListenerForSingleValueEvent(listener);
        return weights;
    }


    public void createSpinnerInfo() {
        ArrayList<String> user_evaluations = getAssessmentNames();
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, user_evaluations);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evaluation_type.setAdapter(arrAdapter);
        evaluation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = parent.getItemAtPosition(position).toString();
                System.out.println(choice);
                Toast.makeText(AddGradesActivity.this, choice, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

