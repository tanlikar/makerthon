package com.example.makerthon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.makerthon.Storage.AppPref;
import com.example.makerthon.Storage.childKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class welcomeActivity extends AppCompatActivity implements childKey {

    private FirebaseAuth auth;
    private AppPref mPref;
    String email;
    String  password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPref = new AppPref(this);

        try {
            email = mPref.getString(EMAIL);
            password = mPref.getString(PASSWORD);
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

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO transistion to register activity
                Intent intent = new Intent(welcomeActivity.this, regActivity.class);
                startActivity(intent);

            }
        });
    }
}
