//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Adapters.ClassroomAdapter;
import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewClassroomFragment extends Fragment {
    //UI Components
    FloatingActionButton addClassroom, sendRequest, viewRequest;
    ListView lvClassroom;
    ClassroomAdapter classroomAdapter;
    TextView joinedClass;
    TextView myClass;
    ImageView mBack;
    ProgressBar progressBar;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference myclassRef;
    private DatabaseReference joinedClassRef;
    DatabaseReference userRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //Global Variables
    private ArrayList<Classroom> myclasses;
    private ArrayList<Classroom> joinedclasses;
    private String typeOfClass;
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_classroom, container, false);
        //UI Declarations
        addClassroom = view.findViewById(R.id.btnAddClassroom);
        sendRequest = view.findViewById(R.id.btnSendRequest);
        viewRequest = view.findViewById(R.id.btnViewRequests);
        lvClassroom = view.findViewById(R.id.lvClassroom);
        joinedClass = view.findViewById(R.id.txtJoinedClasses);
        progressBar = view.findViewById(R.id.pClassroom);
        myClass = view.findViewById(R.id.txtMyClasses);
        mBack = view.findViewById(R.id.btnPuzzleBack);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myclassRef = database.getReference().child("Users/"+currentUser.getUid()+"/MyClassrooms");
        joinedClassRef = database.getReference().child("Users/"+currentUser.getUid()+"/JoinedClassrooms");
        userRef = database.getReference().child("Users/" + currentUser.getUid());
        //Global Variable Declarations
        typeOfClass = "MyClassrooms";
        myclasses = new ArrayList<>();
        joinedclasses = new ArrayList<>();
        bundle = new Bundle();
        //retrieve all Joined Classes
        retrieveMyClassrooms();
        //On button Pressed to Display Joined Classes of User
        joinedClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeOfClass = "JoinedClassrooms";
                progressBar.setVisibility(View.VISIBLE);
                retrieveJoinedClassrooms();
            }
        });
        //On button Pressed to Display My Classes of User
        myClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeOfClass = "MyClassrooms";
                progressBar.setVisibility(View.VISIBLE);
                retrieveMyClassrooms();
            }
        });
        //Navigate to File Repo List
        getClassroomSelected();
        //On button pressed Navigate to create Classroom Fragment
        createClassroom();
        //On button pressed Navigate to send Requests Fragment
        sendRequest();
        //On button pressed Navigate to view Requests Fragment
        viewRequests();
        //On button pressed Navigate to previous Fragment
        btnBackClicked();
        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }
    //Retrieve all Users Joined Classes ie User is Student
    private void retrieveJoinedClassrooms(){
        joinedClassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                joinedclasses.clear();
                for(DataSnapshot child: children){
                    Classroom classroom = child.getValue(Classroom.class);
                    if(classroom != null){
                        joinedclasses.add(classroom);
                    }
                }
                if(getContext() != null){
                    classroomAdapter = new ClassroomAdapter(getContext(),android.R.layout.simple_list_item_1,joinedclasses);
                    lvClassroom.setAdapter(classroomAdapter);
                }
                else{
                    //Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Retrieve all Users My Classes ie User is Admin
    private void retrieveMyClassrooms(){

        myclassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                myclasses.clear();
                for(DataSnapshot child: children){
                    Classroom classroom = child.getValue(Classroom.class);
                    if(classroom != null){
                        myclasses.add(classroom);
                    }
                }
                if(getContext() != null){
                    classroomAdapter = new ClassroomAdapter(getContext(),android.R.layout.simple_list_item_1,myclasses);
                    lvClassroom.setAdapter(classroomAdapter);
                }
                else{
                    //Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Navigate to send Requests Fragment
    private void createClassroom(){
        addClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
                        Date date;
                        // If the user is younger than 18 years old, they will be prompted that they're too young to make a class
                        try {
                            date = format.parse(user.getDate_Of_Birth());
                            if(getAge(date) < 18){
                                Toast.makeText(getContext(), "Sorry! You need to be 18 years or older to do this", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        CreateClassroomFragment createClassroom = new CreateClassroomFragment();
                        fragmentTransaction.replace(R.id.fragmentContainer, createClassroom);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private int getAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
            age--;
        }

        return age;
    }

    //Navigate to view Requests Fragment
    private void sendRequest(){
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SendRequestFragment sendRequestFragment = new SendRequestFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, sendRequestFragment);
                fragmentTransaction.commit();
            }
        });
    }
    //Navigate to view Requests Fragment
    private void viewRequests(){
        viewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MyRequestFragment myRequestFragment = new MyRequestFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, myRequestFragment);
                fragmentTransaction.commit();
            }
        });
    }
    //Navigate to previous Fragment
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
    //On item clicked, Navigate to File Repo
    private void getClassroomSelected(){
        lvClassroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                MyClassroomFragment myClassroomFragment = new MyClassroomFragment();
                if(typeOfClass.equals("JoinedClassrooms")) {
                    bundle.putInt("ClassroomId", joinedclasses.get(i).getClassroom_Id());
                    bundle.putString("ClassroomName", joinedclasses.get(i).getClassroom_Name());
                    bundle.putString("TypeOfClass", typeOfClass);
                }
                else if(typeOfClass.equals("MyClassrooms")){
                    bundle.putInt("ClassroomId", myclasses.get(i).getClassroom_Id());
                    bundle.putString("ClassroomName", myclasses.get(i).getClassroom_Name());
                    bundle.putString("TypeOfClass", typeOfClass);
                }
                myClassroomFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, myClassroomFragment);
                fragmentTransaction.commit();
            }
        });
    }
}