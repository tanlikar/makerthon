package com.example.makerthon;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makerthon.Storage.AppPref;
import com.example.makerthon.Storage.childKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class accountFragment extends Fragment implements childKey {

    private ListView mListView;
    String[] option = new String[] {
      "Change Username",
      "Change Email",
      "Change Password",
      "Log Out"
    };


    private ArrayList<String> list = new ArrayList<>();

    private FirebaseUser user;
    private FirebaseAuth auth;

    private TextView usernameText;

    private AppPref mPref;

    public accountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_account, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();

        mPref = new AppPref(getActivity());

        String username = user.getDisplayName();

        usernameText = root.findViewById(R.id.username);
        usernameText.setText(username);

        mListView = root.findViewById(R.id.listview);
        list.addAll(Arrays.asList(option));

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.activity_listview_text, list);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch(i){
                    case 0:
                        showDialogUsername();
                        break;
                    case 1:
                        showDialogEmail();
                        break;
                    case 2:
                        showDialogPassword();
                        break;
                    case 3:
                        mPref.putString(PASSWORD, "");
                        mPref.putString(EMAIL, "");
                        auth.signOut();
                        Intent intent = new Intent(getActivity(), welcomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                }
            }
        });

        return root;
    }

    public void showDialogEmail(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog_change_email);

        TextInputEditText mEmail = dialog.findViewById(R.id.dialog_editTextEmail);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.btn_dialog_ok_editEmail);
        Button dialogButtonCancel = dialog.findViewById(R.id.btn_dialog_cancel_emailedit);

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(),"Enter email address", Toast.LENGTH_SHORT).show();
                }else{
                    user.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(),
                                                "Email address is updated. Please sign in with new email id!", Toast.LENGTH_SHORT).show();
                                        auth.signOut();

                                        dialog.dismiss();
                                        Intent intent = new Intent(getActivity(), welcomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getActivity(), "Failed to update email!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        dialog.show();

    }

    public void showDialogUsername(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog_change_username);

        TextInputEditText mUsername = dialog.findViewById(R.id.dialog_editTextUsername);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.btn_dialog_ok_editUsername);
        Button dialogButtonCancel = dialog.findViewById(R.id.btn_dialog_cancel_usernameedit);

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String usernameInput = mUsername.getText().toString().trim();

                if(TextUtils.isEmpty(usernameInput)){
                    Toast.makeText(getActivity(),"Enter username", Toast.LENGTH_SHORT).show();
                }else{

                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(usernameInput).build();

                    user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                usernameText.setText(usernameInput);
                                Toast.makeText(getActivity(),"Username updated", Toast.LENGTH_SHORT).show();
                                Log.d("profile", "onComplete: " + "profile updated");
                            }
                        }
                    });
                    dialog.dismiss();

                }
            }
        });

        dialog.show();

    }

    public void showDialogPassword(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog_change_password);

        TextInputEditText mPassword = dialog.findViewById(R.id.editTextPasswordReg);
        TextInputEditText mPasswordConfirm = dialog.findViewById(R.id.editTextPasswordConfirm);

        mPassword.setHint("Old Password");
        mPasswordConfirm.setHint("New Password");

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.btn_dialog_ok_editPassword);
        Button dialogButtonCancel = dialog.findViewById(R.id.btn_dialog_cancel_PasswordEdit);

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String oldPassword = mPassword.getText().toString().trim();
                final String newPassword = mPasswordConfirm.getText().toString().trim();
                final String oriPassword = mPref.getString(PASSWORD);

                if(TextUtils.isEmpty(oldPassword)){
                    Toast.makeText(getActivity(),"Enter old Password", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(newPassword)){
                    Toast.makeText(getActivity(),"Enter new Password", Toast.LENGTH_SHORT).show();
                }

                if(!oldPassword.equals(oriPassword)){
                    Toast.makeText(getActivity(),"Enter wrong old Password", Toast.LENGTH_SHORT).show();
                }



                if(!(TextUtils.isEmpty(oldPassword) ||TextUtils.isEmpty(newPassword) || !oldPassword.equals(oriPassword))){

                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                        auth.signOut();
                                        dialog.dismiss();
                                        Intent intent = new Intent(getActivity(), welcomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();


                                    } else {
                                        Toast.makeText(getActivity(), "Failed to update password!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
            }
        });

        dialog.show();

    }

}
