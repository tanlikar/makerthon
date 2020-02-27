package com.example.makerthon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makerthon.Storage.childKey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class paymentActivity extends AppCompatActivity implements childKey {

    private String currencyText = "";
    private TextView viewCurency;
    private int currency;
    private String temp1;
    private String venID;
    private DatabaseReference mDatabaseReference;
    private String uid;
    private Long point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        viewCurency = findViewById(R.id.currencyText);

        venID = getIntent().getStringExtra(VENID);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();

            mDatabaseReference.child(firebaseUser).child(uid).child("point").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    point = (Long)dataSnapshot.getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        // Attaching the layout to the toolbar object
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void onClick(View v) {
        // handle number button click
        switch (v.getId()) {
            case R.id.t9_key_1:
                currencyText = currencyText + "1";
                updatePoint();
                break;
            case R.id.t9_key_2:
                currencyText = currencyText + "2";
                updatePoint();
                break;
            case R.id.t9_key_3:
                currencyText = currencyText + "3";
                updatePoint();
                break;
            case R.id.t9_key_4:
                currencyText = currencyText + "4";
                updatePoint();
                break;
            case R.id.t9_key_5:
                currencyText = currencyText + "5";
                updatePoint();
                break;
            case R.id.t9_key_6:
                currencyText = currencyText + "6";
                updatePoint();
                break;
            case R.id.t9_key_7:
                currencyText = currencyText + "7";
                updatePoint();
                break;
            case R.id.t9_key_8:
                currencyText = currencyText + "8";
                updatePoint();
                break;
            case R.id.t9_key_9:
                currencyText = currencyText + "9";
                updatePoint();
                break;
            case R.id.t9_key_backspace:
                int charCount = currencyText.length();
                if(charCount>0){
                    currencyText = currencyText.substring(0, charCount-1);
                }
                updatePoint();
                break;

            case R.id.t9_key_0:
                if(!currencyText.equals("")) currencyText = currencyText + "0";
                updatePoint();
                break;

            case R.id.t9_key_enter:
                //TODO add function for sending amount to firebase
                Long pointString = Long.parseLong(currencyText);

                if(pointString > point){
                    Toast.makeText(paymentActivity.this, "Not Sufficient Points", Toast.LENGTH_SHORT).show();
                }else{

                    Long pointBalance = point - pointString;
                    mDatabaseReference.child(firebaseTransaction).child(venID).child(firebasePoint).setValue(pointString);
                    mDatabaseReference.child(firebaseUser).child(uid).child(firebasePoint).setValue(pointBalance);

                    showDialogTransaction(paymentActivity.this, "Transaction completed");

                }




        }
    }

//    public void updateCurrency(){  //not needed, use point instead
//
//        DecimalFormat df = new DecimalFormat("0.00");
//        if(!currencyText.equals("")) {
//            currency = Integer.parseInt(currencyText);
//        }else{
//            currency = 0;
//        }
//        int decimal = currency%100;
//        int wholeNum = currency/100;
//
//        if(decimal < 10){
//            temp1 = Integer.toString(wholeNum) + ".0" + Integer.toString(decimal);
//        }else{
//            temp1 = Integer.toString(wholeNum) + "." + Integer.toString(decimal);
//        }
//
//        Double mCurrency = Double.valueOf(temp1);
//        String dispCurrency = df.format(mCurrency);
//        viewCurency.setText(dispCurrency);
//
//
//    }

    public void showDialogTransaction(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog_transaction_complete);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog_transaction);
        text.setText(msg);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.btn_dialog_ok_transaction);
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();

    }

    public void updatePoint(){
        if(!currencyText.equals("")){
            viewCurency.setText(currencyText);
        }else{
            viewCurency.setText("0");
        }
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
