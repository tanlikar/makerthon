package com.example.makerthon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.makerthon.Storage.AppPref;
import com.example.makerthon.Storage.childKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class regActivity extends AppCompatActivity implements childKey {

    private TextInputEditText mUsername, mPassword, mPasswordConfirm, mEmail;
    private Button mRegButton;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private AppPref mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        // Attaching the layout to the toolbar object
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mUsername = findViewById(R.id.editTextUsername);
        mPassword = findViewById(R.id.editTextPasswordReg);
        mPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        mRegButton = findViewById(R.id.RegButton);
        mEmail = findViewById(R.id.editTextEmail);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPref = new AppPref(this);

        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                String passwordConfirm = mPasswordConfirm.getText().toString().trim();
                final String username = mUsername.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(regActivity.this,"Enter email address", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(regActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(passwordConfirm)){
                    Toast.makeText(regActivity.this, "Enter password again", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(regActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                }

                if(!password.equals(passwordConfirm)){
                    Toast.makeText(regActivity.this, "Password not same", Toast.LENGTH_SHORT).show();
                }

                if(!(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(passwordConfirm)||TextUtils.isEmpty(username)||(!password.equals(passwordConfirm)))){
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(regActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(regActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(regActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {

                                        String userId = task.getResult().getUser().getUid();
                                        mDatabase.child("users").child(userId).child("Username").setValue(username);
                                        mDatabase.child("users").child(userId).child("point").setValue(0);

                                        mPref.putString(USERNAME, username);
                                        mPref.putString(EMAIL, email);
                                        mPref.putString(PASSWORD, password);

                                        startActivity(new Intent(regActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });


                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
