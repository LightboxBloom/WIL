/*Created by: Katherine Chambers
* Edited by: Rohini Naidu 28/06/2020*/
package com.katherine.bloomuii.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.R;
import com.katherine.bloomuii.ObjectClasses.User;

import java.util.Calendar;

public class EditProfileFragment extends Fragment {

    ImageView mBack;
    TextView mDisplayDate;
    TextView fullName;
    TextView email;
    Button btnProfileSave;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final String TAG = "EditProfileFragment";

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth  mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //Declare UI Components
        mBack = view.findViewById(R.id.btnPuzzleBack);
        mDisplayDate = view.findViewById(R.id.txtProfileDob);
        fullName = view.findViewById(R.id.txtProfileName);
        email = view.findViewById(R.id.txtProfileEmail);
        btnProfileSave = view.findViewById(R.id.btnLogout);

        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid());

        //Read from Firebase and populate text fields with current users details
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullName.setText(user.getFull_Name());
                email.setText(user.getEmail_Address());
                mDisplayDate.setText(user.getDate_Of_Birth());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //If Save button clicked - save changes to Firebase
        btnProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(email.getText().toString(), fullName.getText().toString(), mDisplayDate.getText().toString());
                SaveUserToDatabase(user);
                Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
            });
        //Back Button to return to Profile
        btnBackClicked();
        //Date Picker Functionality
        DatePicker();
        return view;
    }

    //Back Button Functionality
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

    //Date Picker Functionality
    private void DatePicker() {
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "-" + (month + 1) + "-" + year;
                mDisplayDate.setText(date);
            }
        };
    }

    //Write changes to Firebase
    public void SaveUserToDatabase(User newUser)
    {
        //adding to RealTime Database
        DatabaseReference myRef = database.getReference("Users");

        myRef.child(currentUser.getUid()).child("Email_Address").setValue(newUser.getEmail_Address());
        myRef.child(currentUser.getUid()).child("Date_Of_Birth").setValue(newUser.getDate_Of_Birth());
        myRef.child(currentUser.getUid()).child("Full_Name").setValue(newUser.getFull_Name());
    }
}//End of Fragment
