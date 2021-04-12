package com.bcit.phokimchipower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class CourseDetailActivity extends AppCompatActivity {

    ImageView assignments;
    ImageView labs;
    ImageView midterms;
    ImageView finals;
    ImageView projects;
    ImageView quizzes;
    String message;
    final HashMap<String, Double> a = new HashMap<>();
    final HashMap<String, Double> l = new HashMap<>();
    final HashMap<String, Double> m = new HashMap<>();
    final HashMap<String, Double> f = new HashMap<>();
    final HashMap<String, Double> p = new HashMap<>();
    final HashMap<String, Double> q = new HashMap<>();

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String courseName;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
        double courseGrade = intent.getDoubleExtra(main_courses.COURSE_CURRENT_GRADE_EXTRA, 0.0);
        courseName = intent.getStringExtra(main_courses.COURSE_NAME_EXTRA);
        HashMap<String, Double> hashMap = (HashMap<String, Double>) intent.getSerializableExtra("hashMap");

        TextView courseName_detail = findViewById(R.id.courseName_detail);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        uid = user.getUid();
        reference = database.getReference("Users");

        assignments = findViewById(R.id.assessment1);
        labs = findViewById(R.id.assessment2);
        midterms = findViewById(R.id.assessment3);
        finals = findViewById(R.id.assessment4);
        projects = findViewById(R.id.assessment5);
        quizzes = findViewById(R.id.assessment6);

        reference = reference.child(uid).child("courses");

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    Course c = ss.getValue(Course.class);
                    if (c.getCourseName().equals(courseName)) {
                        for(DataSnapshot snapshot1: ss.child("grades").getChildren()) {
                            Grade grade = snapshot1.getValue(Grade.class);
                            if (grade.getEvaluationType() != null) {
                                if (grade.getEvaluationType().equals("Assignment")) {
                                    a.put(grade.getGradeName(), grade.getGrade());
                                }
                                if (grade.getEvaluationType().equals("Labs")) {
                                    l.put(grade.getGradeName(), grade.getGrade());
                                }
                                if (grade.getEvaluationType().equals("Midterm")) {
                                    m.put(grade.getGradeName(), grade.getGrade());
                                }
                                if (grade.getEvaluationType().equals("Projects")) {
                                    p.put(grade.getGradeName(), grade.getGrade());
                                }
                                if (grade.getEvaluationType().equals("Final")) {
                                    f.put(grade.getGradeName(), grade.getGrade());
                                }
                                if (grade.getEvaluationType().equals("Quizzes")) {
                                    q.put(grade.getGradeName(), grade.getGrade());
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        showDialog(assignments, a, "Assignments");
        showDialog(labs, l, "Labs");
        showDialog(quizzes, q, "Quizzes");
        showDialog(projects, p, "Projects");
        showDialog(finals, f, "Final");
        showDialog(midterms, m, "Midterm");

        TextView title1 = findViewById(R.id.textView15);
        TextView title2 = findViewById(R.id.textView17);
        TextView title3 = findViewById(R.id.textView19);
        TextView title4 = findViewById(R.id.textView21);
        TextView title5 = findViewById(R.id.textView23);
        TextView title6 = findViewById(R.id.textView25);

        TextView current_grade = findViewById(R.id.current_grade_detail);

        Button button = findViewById(R.id.back_to_main);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailActivity.this, main_courses.class);
                intent.putExtra("hashMap", hashMap);
                startActivity(intent);
            }
        });

        Button addGrade = findViewById(R.id.button_add_grade);

        addGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailActivity.this, AddGradesActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        TextView grade1 = findViewById(R.id.textView16);
        TextView grade2 = findViewById(R.id.textView18);
        TextView grade3 = findViewById(R.id.textView20);
        TextView grade4 = findViewById(R.id.textView22);
        TextView grade5 = findViewById(R.id.textView24);
        TextView grade6 = findViewById(R.id.textView26);

        current_grade.setText(Double.toString(courseGrade));

        if(hashMap != null) {
            if (hashMap.containsKey("Assignment")) {
                grade1.setText(Double.toString(hashMap.get(title1.getText().toString())));
            }

            if (hashMap.containsKey("Labs")) {
                grade2.setText(Double.toString(hashMap.get(title2.getText().toString())));
            }

            if (hashMap.containsKey("Midterm")) {
                grade3.setText(Double.toString(hashMap.get(title3.getText().toString())));
            }

            if (hashMap.containsKey("Final")) {
                grade4.setText(Double.toString(hashMap.get(title4.getText().toString())));
            }

            if (hashMap.containsKey("Projects")) {
                grade5.setText(Double.toString(hashMap.get(title5.getText().toString())));
            }

            if (hashMap.containsKey("Quizzes")) {
                grade6.setText(Double.toString(hashMap.get(title6.getText().toString())));
            }
        }



        courseName_detail.setText(courseName);


    }

    public void showDialog(ImageView courseTypeImageView, HashMap<String, Double> courseArray, String title) {

        courseTypeImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               for (Map.Entry grade: courseArray.entrySet()) {
                   System.out.println(grade);
                   message += grade.getKey().toString() + ":  " + grade.getValue().toString() + "\n\n";
               }
               new AlertDialog.Builder(CourseDetailActivity.this)
                       .setTitle(title)
                       .setMessage(message)
                       .setNeutralButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                           }
                       })
                       .show(); //shows the dialog message.
               message = "";
           }
       });
    }
}