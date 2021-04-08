package com.bcit.phokimchipower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class AddGrades extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String uid;
    User current_user;
    private static final String TAG = "AddGradesActivity";

    Spinner evaluation_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);

        evaluation_type = findViewById(R.id.evaluation_type_spinner);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        uid = user.getUid();
        current_user = new User(user.getUid(), user.getEmail(), user.getDisplayName());

        //loading all the courses to the drop down list.


        //from the drop down list, let's say the user chose the first course.
        //reference.child(uid).child(current_user.getCourseNumber() + "").child(current_user.getCourses().get(0).toString());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    current_user = singleSnapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        });



    }
}