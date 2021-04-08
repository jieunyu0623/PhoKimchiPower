package com.bcit.phokimchipower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    Button mLoginBtn;
    EditText mEmailText, mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginButton_login);
        mEmailText = findViewById(R.id.emailPlainText_login);
        mPasswordText = findViewById(R.id.passwordPlainText_login);

        //when the login button is pressed...
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(LoginActivity.this, main_courses.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(LoginActivity.this,"cannot login!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    private void signOut() {
        mAuth.signOut();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        ; // when the user hits the goback button
        return super.onSupportNavigateUp(); // goback button activates.
    }
}