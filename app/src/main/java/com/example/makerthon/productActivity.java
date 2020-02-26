package com.example.makerthon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.GridView;

import com.example.makerthon.viewAdapter.customGridAdapter;
import com.example.makerthon.viewAdapter.customProductGridAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class productActivity extends AppCompatActivity {

    private GridView mGridView;

    String[] iconText = {
            "Item1",
            "Item2"
    };

    final int[] iconImg = {
            R.drawable.ic_present_black,
            R.drawable.ic_present_black
    };

    String[] stockText = {
            "Stock: ",
            "Stock: "
    };

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // Attaching the layout to the toolbar object
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mGridView = findViewById(R.id.gridview);
        customProductGridAdapter adapter = new customProductGridAdapter(productActivity.this, iconText, stockText, iconImg);
        mGridView.setAdapter(adapter);

        mDatabaseReference.child("stock").child("item1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Long num = (Long)dataSnapshot.getValue();

                    String temp = stockText[0].substring(0, 7);
                    stockText[0] = temp + num.toString();

                    customProductGridAdapter adapter = new customProductGridAdapter(productActivity.this, iconText, stockText, iconImg);
                    mGridView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabaseReference.child("stock").child("item2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    Long num = (Long) dataSnapshot.getValue();

                    String temp = stockText[1].substring(0, 7);
                    stockText[1] = temp + num.toString();


                    customProductGridAdapter adapter = new customProductGridAdapter(productActivity.this, iconText, stockText, iconImg);
                    mGridView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
