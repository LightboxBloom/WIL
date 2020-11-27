/*Created by: Katherine Chambers
* Edited by: Rohini Naidu 28/06/2020*/
package com.katherine.bloomuii.Fragments;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.katherine.bloomuii.R;
import com.katherine.bloomuii.ObjectClasses.User;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
public class EditProfileFragment extends Fragment {
    //UI Components
    ImageView mBack;
    ImageView mProfilePicture;
    ImageView mEditPicture;
    TextView mDisplayDate;
    TextView fullName;
    TextView email;
    Button btnProfileSave;
    ProgressBar progress;
    //Global Variables
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "EditProfileFragment";
    private Uri currImageURI;
    private static final int IMAGE_PICK_CODE = 1000;
    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private StorageReference storage;
    private StorageReference fileRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //Declare UI Components
        mBack = view.findViewById(R.id.btnPuzzleBack);
        mProfilePicture = view.findViewById(R.id.imgProfilePicture);
        mEditPicture = view.findViewById(R.id.btnEditProfilePicture);
        mDisplayDate = view.findViewById(R.id.txtProfileDob);
        fullName = view.findViewById(R.id.txtProfileName);
        email = view.findViewById(R.id.txtProfileEmail);
        btnProfileSave = view.findViewById(R.id.btnLogout);
        progress = view.findViewById(R.id.pbEditProfilePicture);

        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid());
        storage = FirebaseStorage.getInstance().getReference();
        fileRef = storage.child("Users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");

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
        mEditPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfilePicture();
            }
        });
        //Setting new profile picture
        setProfilePicture();
        //Back Button to return to Profile
        btnBackClicked();
        //Date Picker Functionality
        DatePicker();
        return view;
    }

    //Back Button Functionality
    private void btnBackClicked() {
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
    //Source: StackOverflow
    //https://stackoverflow.com/questions/25347943/how-to-use-picasso-library
    private void setProfilePicture() {
        if(fileRef !=null) {
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).centerCrop().fit().into(mProfilePicture);
                    progress.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
    //Edit Profile Picture
    private void editProfilePicture() {
        progress.setVisibility(View.VISIBLE);
        // To open up a gallery browser
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    // To handle when an image is selected from the browser, add the following to your Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_CODE) {
                currImageURI = data.getData();
                fileRef.putFile(currImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                progress.setVisibility(View.VISIBLE);
                                Picasso.get().load(uri).centerCrop().fit().into(mProfilePicture);
                                progress.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
            }
        }
    }
    //Write changes to Firebase
    public void SaveUserToDatabase(User newUser) {
        //adding to RealTime Database
        DatabaseReference myRef = database.getReference("Users");

        myRef.child(currentUser.getUid()).child("Email_Address").setValue(newUser.getEmail_Address());
        myRef.child(currentUser.getUid()).child("Date_Of_Birth").setValue(newUser.getDate_Of_Birth());
        myRef.child(currentUser.getUid()).child("Full_Name").setValue(newUser.getFull_Name());
    }
}//End of Fragment
