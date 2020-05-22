package com.katherine.bloomuii.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.katherine.bloomuii.R;

public class DiaryFragment extends Fragment {

    FloatingActionButton mAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mAdd = view.findViewById(R.id.btn_float);

        //calling method
        fab();

        return view;
    }

    //method for clicking fab
    private void fab() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddEntryFragment addEntryFragment = new AddEntryFragment();
                fragmentTransaction.replace(R.id.fragment_container, addEntryFragment);
                fragmentTransaction.commit();
            }
        });
    }//end fab method
}
