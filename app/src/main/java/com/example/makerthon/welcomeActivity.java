package com.example.makerthon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.makerthon.Storage.AppPref;
import com.example.makerthon.Storage.childKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class welcomeActivity extends AppCompatActivity implements childKey {

    private FirebaseAuth auth;
    private AppPref mPref;
    private DatabaseReference mDatabaseReference;
    String email;
    String  password;
    int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPref = new AppPref(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        try {
            email = mPref.getString(EMAIL);
            password = mPref.getString(PASSWORD);
            //Toast.makeText(this, email + " " + password,Toast.LENGTH_SHORT).show();
        }catch (Exception ignored){

        }

        final Button loginButton = findViewById(R.id.loginButton);
        final Button registerButton = findViewById(R.id.regButton);

        auth = FirebaseAuth.getInstance();

        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        Intent intent = new Intent(welcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        }catch (Exception ignored){

        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO transistion to login activity
                Intent intent = new Intent(welcomeActivity.this, loginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO transistion to register activity
                Intent intent = new Intent(welcomeActivity.this, regActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
