package com.example.makerthon;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class accountFragment extends Fragment {

    private ListView mListView;
    String[] option = new String[] {
      "Change Username",
      "Change Email",
      "Change Password",
      "Log Out"
    };

    private ArrayList<String> list = new ArrayList<>();

    public accountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_account, container, false);

        mListView = root.findViewById(R.id.listview);
        list.addAll(Arrays.asList(option));

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.activity_listview_text, list);

        mListView.setAdapter(adapter);

        return root;
    }

}
