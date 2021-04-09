package com.bcit.phokimchipower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView test = findViewById(R.id.test_Intent);
        Intent intent = getIntent();
        String name = intent.getStringExtra(AddCourseActivity.COURSE_NAME_EXTRA);
        test.setText(name);
    }
}