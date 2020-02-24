package com.example.makerthon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makerthon.Storage.childKey;
import com.example.makerthon.viewAdapter.customGridAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class homeFragment extends Fragment implements childKey {

    String[] iconText = {
            "Scan",
            "Products"
    };

    final int[] iconImg = {
            R.drawable.ic_scan_black,
            R.drawable.ic_product_black
    };

    private GridView mGridView;
    private String uid;
    private Long point;
    private DatabaseReference mDatabaseReference;
    private TextView mTextView;
    private boolean containUid = false;

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mTextView = root.findViewById(R.id.pointText);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mGridView = root.findViewById(R.id.gridview);
        customGridAdapter adapter = new customGridAdapter(getActivity(), iconText, iconImg);
        mGridView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();

            mDatabaseReference.child(firebaseUser).child(uid).child("point").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    point = (Long)dataSnapshot.getValue();
                    mTextView.setText(point.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){

                    IntentIntegrator  integrator = new IntentIntegrator(getActivity());
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Scan QR Code");
                    integrator.setOrientationLocked(true);
                    integrator.forSupportFragment(homeFragment.this).initiateScan();
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        String result = scanResult.getContents();
        String[] arrOfStrresult = result.split("_");

        //UID_100_apple_1

        if(arrOfStrresult[0].equals("UID")){

            mDatabaseReference.child(product).child(productUid).child(arrOfStrresult[2]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if(snapshot.getValue().toString().equals(arrOfStrresult[3])){
                                Log.d("scan", "onDataChange: " + snapshot.getValue().toString());
                                containUid = true;
                                break;

                            }
                        }

                        if(containUid == true) {
                            viewDialog alert = new viewDialog();
                            alert.showDialogError(getActivity(), "QR code already scanned");
                            containUid = false;
                        }else {
                            mDatabaseReference.child(product).child(productUid).child(arrOfStrresult[2]).push().setValue(arrOfStrresult[3]);
                            mDatabaseReference.child(firebaseUser).child(uid).child(userPoint).setValue(point + Long.parseLong(arrOfStrresult[1])).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        viewDialog alert = new viewDialog();
                                        alert.showDialogCorrect(getActivity(), arrOfStrresult[1] + " points added");
                                    }
                                }
                            });
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


        if (scanResult != null) {
            Toast.makeText(getActivity(), scanResult.getContents(), Toast.LENGTH_SHORT).show();
        }
    }

}
