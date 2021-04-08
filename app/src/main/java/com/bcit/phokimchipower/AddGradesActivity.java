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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class AddGradesActivity extends AppCompatActivity {

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
        //course_number = String.valueOf(current_user.getCourseNumber());

        evaluation_type.setOnItemSelectedListener(new MyOnItemSelectedListener());

        //adds all the courses inherited from the current user to the courses arraylist and dynamically creates a string-array in XML.
        createSpinnerDropDown();

        add_grade_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!evaluation_type.isSelected()) {
                    Toast.makeText(AddGradesActivity.this,"You need to select the evaluation type.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (evaluation_name.getText().toString().isEmpty() || evaluation_name.getText().toString().length() == 0) {
                    Toast.makeText(AddGradesActivity.this,"You need to enter the name.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (grade.getText().toString().isEmpty() || grade.getText().toString().length() == 0) {
                    Toast.makeText(AddGradesActivity.this,"You need to enter the grade.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(grade.getText().toString()) < 0) {
                    Toast.makeText(AddGradesActivity.this,"Grades can't be negative",Toast.LENGTH_SHORT).show();
                    return;
                }
                reference.child(uid).child("courses").child(current_user.getCourseNumber() + "").child("currentGrade")


                Intent intent = new Intent(AddGradesActivity.this, testActivity.class); //navigates back to the main page.
                startActivity(intent);
                finish();
                Toast.makeText(AddGradesActivity.this, "you successfully added a grade!", Toast.LENGTH_SHORT).show();

            }
        });


        reference.child(uid).child("courses").child(current_user.getCourseNumber() + "").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    reference.child(uid).child(current_user.getCourseNumber() + "").child(current_user.getCourses().get(0).toString());
                    current_user = singleSnapshot.getValue(User.class);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        });





    }

    private void createSpinnerDropDown() {

        //Array list of animals to display in the spinner
        for (Course course: current_user.getCourses()) {
            courses.add(course.toString());
        }
        //create an ArrayAdapter from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, courses);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        evaluation_type.setAdapter(dataAdapter);
        //attach the listener to the spinner
        evaluation_type.setOnItemSelectedListener(new MyOnItemSelectedListener());

    }
}

class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String uid;
    User current_user;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        uid = user.getUid();
        current_user = new User(user.getUid(), user.getEmail(), user.getDisplayName());

        String position = String.valueOf(current_user.getCourses().get(pos - 1));

//        reference.child(uid).child("courses").child(position).child("currentGrade").setValue()
//                addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                reference.child(uid).child("courses").child(String.valueOf(pos)).child("currentGrade").child(snapshot);
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        String selectedItem = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}

