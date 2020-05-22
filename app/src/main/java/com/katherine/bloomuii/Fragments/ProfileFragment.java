package com.katherine.bloomuii.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.katherine.bloomuii.R;

public class ProfileFragment extends Fragment
{

    TextView txt_settings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txt_settings = view.findViewById(R.id.txt_settings);

        goToSettings();

        return view;
    }

    private void goToSettings()
    {
        txt_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.fragment_container, settingsFragment);
                fragmentTransaction.commit();
            }
        });
    }


}
