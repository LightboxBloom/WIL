package com.katherine.bloomuii.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.R;

public class LanguageFragment extends Fragment {

    ImageView mBack;
    Button btnEnglish, btnAfrikaans;

    private FirebaseDatabase database;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        mBack = view.findViewById(R.id.btnPuzzleBack);
        btnEnglish = view.findViewById(R.id.btnEnglish);
        btnAfrikaans = view.findViewById(R.id.btnAfrikaans);

        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/");

        //calling methods
        setEnglish();
        setAfrikaans();
        btnBackClicked();

        return view;
    }

    //click to go back to profile fragment
    private void btnBackClicked()
    {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, profileFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void setEnglish(){
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(currentUser.getUid()).child("Language_Preference").setValue("English");
            }
        });
    }
    private void setAfrikaans(){
        btnAfrikaans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(currentUser.getUid()).child("Language_Preference").setValue("Afrikaans");
            }
        });
    }
}
