package com.katherine.bloomuii.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.katherine.bloomuii.R;

public class ProfileFragment extends Fragment
{

    ImageView mBack;
    TextView mEdit, mLanguage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //
        mBack = view.findViewById(R.id.btnBack);
        mEdit = view.findViewById(R.id.txtEditProfile);
        mLanguage = view.findViewById(R.id.txtLanguage);

        //calling methods
        btnBackClicked();
        btnEditClicked();
        btnLanguageClicked();

        return view;
    }

    //click to get to language fragment
    private void btnLanguageClicked() {
        mLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LanguageFragment languageFragment = new LanguageFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, languageFragment);
                fragmentTransaction.commit();
            }
        });
    }

    //click to get to edit profile
    private void btnEditClicked()
    {
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, editProfileFragment);
                fragmentTransaction.commit();
            }
        });
    }

    //click to get back to home fragment
    private void btnBackClicked()
    {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
                fragmentTransaction.commit();
            }
        });
    }

}
