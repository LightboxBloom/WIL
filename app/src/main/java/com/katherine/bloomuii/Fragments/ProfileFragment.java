//Developer: Katherine Chambers
//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.katherine.bloomuii.Activities.LoginActivity;
import com.katherine.bloomuii.R;
import com.katherine.bloomuii.ObjectClasses.User;
import com.squareup.picasso.Picasso;
public class ProfileFragment extends Fragment {
    //UI Components
    TextView fullName;
    ImageView mBack;
    TextView mEdit, mLanguage, mTerms;
    TextView mLogout;
    ImageView btnPicture;
    ImageView mProfilePicture;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private StorageReference fileRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //UI Declarations
        mBack = view.findViewById(R.id.btnPuzzleBack);
        mEdit = view.findViewById(R.id.txtEditProfile);
        mLanguage = view.findViewById(R.id.txtLanguage);
        mTerms = view.findViewById(R.id.txtTerms);
        mLogout = view.findViewById(R.id.btnLogout);
        fullName = view.findViewById(R.id.txtHomeUsername);
        btnPicture = view.findViewById(R.id.btnEditProfilePicture);
        mProfilePicture = view.findViewById(R.id.imgProfilePicture);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid());
        fileRef = FirebaseStorage.getInstance().getReference().child("Users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        //Retrieve Current User Details
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullName.setText(user.getFull_Name());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //Navigations on button click
        setProfilePicture();
        btnBackClicked();
        btnEditClicked();
        btnLanguageClicked();
        btnTermsOfUse();
        btnLogoutClicked();
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
    private void btnEditClicked() {
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
    private void btnBackClicked() {
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
    //click to get back to terms of Use
    private void btnTermsOfUse(){
        mTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentTermsOfUse termsOfUseFragment = new FragmentTermsOfUse();
                fragmentTransaction.replace(R.id.fragmentContainer, termsOfUseFragment);
                fragmentTransaction.commit();
            }
        });
    }
    //click to get back to log out
    private void btnLogoutClicked(){
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
    //Source: StackOverflow
    //https://stackoverflow.com/questions/25347943/how-to-use-picasso-library
    //Set Profile Picture
    private void setProfilePicture() {
        if(fileRef !=null) {
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).centerCrop().fit().into(mProfilePicture);
                }
            });
        }
    }
}
