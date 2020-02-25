package com.example.makerthon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class paymentActivity extends AppCompatActivity {

    private String currencyText = "";
    private TextView viewCurency;
    private int currency;
    private String temp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        viewCurency = findViewById(R.id.currencyText);

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
                updateCurrency();
                break;
            case R.id.t9_key_2:
                currencyText = currencyText + "2";
                updateCurrency();
                break;
            case R.id.t9_key_3:
                currencyText = currencyText + "3";
                updateCurrency();
                break;
            case R.id.t9_key_4:
                currencyText = currencyText + "4";
                updateCurrency();
                break;
            case R.id.t9_key_5:
                currencyText = currencyText + "5";
                updateCurrency();
                break;
            case R.id.t9_key_6:
                currencyText = currencyText + "6";
                updateCurrency();
                break;
            case R.id.t9_key_7:
                currencyText = currencyText + "7";
                updateCurrency();
                break;
            case R.id.t9_key_8:
                currencyText = currencyText + "8";
                updateCurrency();
                break;
            case R.id.t9_key_9:
                currencyText = currencyText + "9";
                updateCurrency();
                break;
            case R.id.t9_key_backspace:
                int charCount = currencyText.length();
                if(charCount>0){
                    currencyText = currencyText.substring(0, charCount-1);
                }
                updateCurrency();
                break;

            case R.id.t9_key_0:
                if(!currencyText.equals("")) currencyText = currencyText + "0";
                updateCurrency();
                break;

            case R.id.t9_key_enter:
                //TODO add function for sending amount to firebase

        }
    }

    public void updateCurrency(){  //not needed, use point instead

        DecimalFormat df = new DecimalFormat("0.00");
        if(!currencyText.equals("")) {
            currency = Integer.parseInt(currencyText);
        }else{
            currency = 0;
        }
        int decimal = currency%100;
        int wholeNum = currency/100;

        if(decimal < 10){
            temp1 = Integer.toString(wholeNum) + ".0" + Integer.toString(decimal);
        }else{
            temp1 = Integer.toString(wholeNum) + "." + Integer.toString(decimal);
        }

        Double mCurrency = Double.valueOf(temp1);
        String dispCurrency = df.format(mCurrency);
        viewCurency.setText(dispCurrency);


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
