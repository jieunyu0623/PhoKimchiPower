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
    HashMap<Object,String> hashMap = new HashMap<>();
    String uid;
    FirebaseUser user;
    EditText email;
    EditText name;
    EditText password;
    String user_email;
    String user_name;
    String user_password;
    CheckBox agreement;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //gets the user email, name and password from user inputs.
        //user id is automatically created.
        uid = mAuth.getUid();
        email = findViewById(R.id.email_signup);
        name = findViewById(R.id.name_signup);
        password = findViewById(R.id.password_signup);

        agreement = findViewById(R.id.agreement_signup);

        signUpButton = findViewById(R.id.signupButton_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //converts the user information into string.
                user_email = email.getText().toString();
                user_name = name.getText().toString();
                user_password = password.getText().toString();

                createAccount(user_email, user_password);
                //and then goes to the main page.
                //delete sign up buttons and sign in buttons.
                //replace those buttons with user info. ex) "Hello, <username>"
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
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
                            user_email = user.getEmail();
                            uid = user.getUid();
                            user_name = name.getText().toString().trim();

                            //puts user data to the hashmap.
                            hashMap.put("uid",uid);
                            hashMap.put("email",user_email);
                            hashMap.put("name",user_name);
                            hashMap.put("password", user_password);

                            mAuth = FirebaseAuth.getInstance(); //gets the authentication access
                            database = FirebaseDatabase.getInstance(); //gets the access to the database
                            reference = database.getReference("Users"); //gets the User instance from the firebase

                            //escapes the signup page once the signup is successful.
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class); //navigates back to the main page.
                            startActivity(intent);
                            finish();
                            Toast.makeText(SignupActivity.this, "you successfully created an account!", Toast.LENGTH_SHORT).show();

                        } else {
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
        onBackPressed();; // when the user hits the goback button
        return super.onSupportNavigateUp(); // goback button activates.
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Successfully Signed in", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
    }

    private void reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this,
                            "Reload successful!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "reload", task.getException());
                    Toast.makeText(SignupActivity.this,
                            "Failed to reload user.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        if(!isChecked) {
            Toast.makeText(this, "You must agree to the term to proceed", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }

}

