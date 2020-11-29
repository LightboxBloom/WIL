//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.R;
public class MyClassroomFragment extends Fragment {
    //UI Components
    Dialog leaveClass_popup;
    TextView txtClassroomName;
    TextView header;
    TextView message;
    FloatingActionButton btnLeaveClass;
    Button leaveYes;
    Button leaveNo;
    Bundle bundle;
    CardView itemStudents;
    CardView itemFiles;
    //Global Variables
    private String typeOfClass;
    private String classroomName;
    private int classroomId;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference allClasses;
    private DatabaseReference myClass;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private StorageReference storage;
    private StorageReference fileRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_classroom, container, false);
        btnLeaveClass = view.findViewById(R.id.btnLeaveClass);
        txtClassroomName = view.findViewById(R.id.txtClassName);
        itemStudents = view.findViewById(R.id.itemStudents);
        itemFiles = view.findViewById(R.id.itemFiles);
        leaveClass_popup = new Dialog(getContext());
        leaveClass_popup.setContentView(R.layout.leaveclass_dialog);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //Variables
        bundle = new Bundle();
        bundle = getArguments();
        if (bundle != null) {
            typeOfClass = bundle.getString("TypeOfClass");
            classroomId = bundle.getInt("ClassroomId");
            classroomName = bundle.getString("ClassroomName");

            allClasses = database.getReference("Classrooms/"+classroomId+"/Students/"+currentUser.getUid());
            myClass = database.getReference("Users/"+currentUser.getUid()+"/JoinedClassrooms/"+classroomId);
            txtClassroomName.setText(classroomName);


            if (typeOfClass.equals("MyClassrooms")) {
                btnLeaveClass.setVisibility(View.INVISIBLE);
            }
        }
        viewFiles();
        if(typeOfClass.equals("MyClassrooms")){
            viewStudents();
        }
        else{
            itemStudents.setVisibility(View.INVISIBLE);
        }
        leaveClass();
        return view;
    }
    //Remove Student from Class
    private void leaveClass(){
        btnLeaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaveYes = leaveClass_popup.findViewById(R.id.btnLeaveYes);
                leaveNo = leaveClass_popup.findViewById(R.id.btnLeaveNo);
                header = leaveClass_popup.findViewById(R.id.txtDialogHeader);
                message = leaveClass_popup.findViewById(R.id.txtDialogMessage);
                header.setText("Leave Classroom");
                message.setText("Are you sure that you want to leave this class?");
                leaveClass_popup.show();
                leaveYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Delete Student from Class
                        myClass.removeValue();
                        allClasses.removeValue();
                    }
                });
                leaveNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leaveClass_popup.dismiss();
                    }
                });
            }
        });
    }
    //Navigate to File Repo
    private void viewFiles(){
        itemFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                FileRepoFragment fileRepoFragment = new FileRepoFragment();
                bundle.putInt("ClassroomId", classroomId);
                fileRepoFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, fileRepoFragment);
                fragmentTransaction.commit();
            }
        });
    }
    //Navigate to view all Students
    private void viewStudents(){
        itemStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to view Students Fragment
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                ViewStudentsFragment viewStudentsFragment = new ViewStudentsFragment();
                bundle.putInt("ClassroomId", classroomId);
                viewStudentsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, viewStudentsFragment);
                fragmentTransaction.commit();
            }
        });
    }
}