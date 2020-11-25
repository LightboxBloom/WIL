//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.R;
import com.katherine.bloomuii.Handlers.ClassroomHandler;

import static android.content.ContentValues.TAG;
public class CreateClassroomFragment extends Fragment {
    //UI Components
    Button btnCreate;
    TextView classroomName;
    ImageView mBack;
    //Firebase Intializations
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference currentUserRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //Global general variables
    private User user;
    private ClassroomHandler createClass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_classroom, container, false);
        //UI Declarations
        btnCreate = view.findViewById(R.id.btnCreate);
        classroomName = view.findViewById(R.id.txtClassname);
        mBack = view.findViewById(R.id.btnPuzzleBack);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid() + "/MyClassrooms");
        currentUserRef = database.getReference("Users/" + currentUser.getUid());
        //Retrieve all Classroom Ids
        createClass = new ClassroomHandler();
        //Find Current User Details
        findUserName();
        //Create Class
        CreateClassroom();
        //Navigate to Previous Fragment
        btnBackClicked();
        return view;
    }
    //Create Classroom and Save to Firebase
    private void CreateClassroom(){
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate Fields
                if(!classroomName.getText().toString().equals("")){
                    //Save my Classrooms to Firebase
                    myRef.child(String.valueOf(createClass.getClassId())).child("Classroom_Name").setValue(classroomName.getText().toString());
                    myRef.child(String.valueOf(createClass.getClassId())).child("Classroom_Id").setValue(createClass.getClassId());
                    myRef.child(String.valueOf(createClass.getClassId())).child("Teacher_Name").setValue(user.getFull_Name());
                    navigateToClassroomList();
                }
            }
        });
    }
    //Find CurrentUsers Details
    private void findUserName(){
        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null) {
                    //Retrieve current users details
                    user = snapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
    //Navigate to view all classrooms when classroom created
    private void navigateToClassroomList(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ViewClassroomFragment viewClassroom = new ViewClassroomFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, viewClassroom);
        fragmentTransaction.commit();
    }
    //Back Button Functionality
    private void btnBackClicked() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ViewClassroomFragment viewClassFragment = new ViewClassroomFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, viewClassFragment);
                fragmentTransaction.commit();
            }
        });
    }
}
