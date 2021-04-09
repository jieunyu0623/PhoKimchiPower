package com.bcit.phokimchipower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
        double courseGrade = intent.getDoubleExtra(AddCourseActivity.COURSE_CURRENT_GRADE_EXTRA, 0.0);
        String courseName = intent.getStringExtra(AddCourseActivity.COURSE_NAME_EXTRA);
        HashMap<String, Double> hashMap = (HashMap<String, Double>) intent.getSerializableExtra("hashMap");
        TextView courseName_detail = findViewById(R.id.courseName_detail);

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



        courseName_detail.setText(courseName);


    }
}