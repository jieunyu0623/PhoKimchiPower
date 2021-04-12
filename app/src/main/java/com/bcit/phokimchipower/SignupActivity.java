package com.bcit.phokimchipower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    HashMap<Object, String> hashMap = new HashMap<>();
    String uid;
    FirebaseUser user;
    EditText email;
    EditText name;
    EditText password;
    EditText password_check;
    String user_email;
    String user_name;
    String user_password;
    String user_password_check;

    CheckBox agreement;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        System.out.println("0");

        mAuth = FirebaseAuth.getInstance(); //gets the authentication access
        database = FirebaseDatabase.getInstance(); //gets the access to the database
        reference = database.getReference("Users"); //gets the User instance from the firebase

        email = findViewById(R.id.email_signup);
        name = findViewById(R.id.name_signup);
        password = findViewById(R.id.password_signup);
        password_check = findViewById(R.id.password_check_signup);

        agreement = findViewById(R.id.agreement_signup);

        signUpButton = findViewById(R.id.signupButton_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //converts the user information into string.
                user_email = email.getText().toString();
                user_name = name.getText().toString();
                user_password = password.getText().toString();
                user_password_check = password_check.getText().toString();

                if (user_password.equals(user_password_check)) {
                    if (!agreement.isChecked()) {
                        Toast.makeText(SignupActivity.this, "Please agree to the term.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    createAccount(user_email,user_password);
                } else {
                    Toast.makeText(SignupActivity.this, "Password Check failed. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            System.out.println("success");
                            user = mAuth.getCurrentUser();
                            uid = user.getUid();
                            reference = reference.child(uid);

                            hashMap.put("name", user_name);
                            hashMap.put("email", user_email);

                            reference.setValue(hashMap);
                            System.out.println(user.getEmail());

                            //escapes the signup page once the signup is successful.
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class); //navigates back to the main page.
                            startActivity(intent);
                            finish();
                            Toast.makeText(SignupActivity.this, "you successfully created an account!", Toast.LENGTH_SHORT).show();

                        } else {
                            //if the length of the password is less than 6...
                            if (user_password.length() < 6) {
                                Toast.makeText(SignupActivity.this, "Password should be at least 6 characters.",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            //if signup fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed. This account already exists!",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp(); // goback button activates.
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = user_email;
        String name = user_name;
        String password = user_password;
        boolean isChecked = agreement.isChecked();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "You must enter email, password and name.", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (!isChecked) {
            Toast.makeText(this, "You must agree to the term to proceed", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }

}

