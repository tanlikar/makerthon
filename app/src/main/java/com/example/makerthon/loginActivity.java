package com.example.makerthon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makerthon.Storage.AppPref;
import com.example.makerthon.Storage.childKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity implements childKey {

    private EditText mEmail;
    private TextInputEditText mPassword;
    private Button mLogin;
    private FirebaseAuth auth;
    private AppPref mPref;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        mPref = new AppPref(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Attaching the layout to the toolbar object
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEmail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        mLogin = findViewById(R.id.loginButton);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(loginActivity.this,"Enter email address", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(loginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }

                if(!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(loginActivity.this, "Wrong Username / Password", Toast.LENGTH_SHORT).show();
                            } else {
                                //TODO go to home page of app

                                mPref.putString(EMAIL, email);
                                mPref.putString(PASSWORD, password);
//                                mPref.putString(UID, task.getResult().getUser().getUid());

                                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                startActivity(intent);
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
