package com.katherine.bloomuii.Handlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.ObjectClasses.User;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class ClassroomHandler {
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //Global Variables
    private ArrayList<Classroom> classes;
    private String classId;
    private User user;
    //Constructor
    public ClassroomHandler(){
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid());
        classes = new ArrayList<>();

        findUserName();
        ClassroomIdCreater();
    }
    //Find Current User Details
    private void findUserName(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null) {
                    user = snapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
    //Generate a Class ID
    public void ClassroomIdCreater(){
        Random id = new Random();
        setClassId(id.nextInt(9999)+"#"+user.getUser_ID().substring(0,4));
        ClassroomIdValidator(getClassId());
    }
    //Check if Class Id doesn't Exist
    public void ClassroomIdValidator(String classId){
        boolean exists = false;
        if(!getClasses().isEmpty()) {
            for (Classroom classroom : getClasses()) {
                while (classroom.getClassroom_Id().equals(classId)) {
                    exists = true;
                }
            }
        }
        if(exists){
            ClassroomIdCreater();
        }
    }

    //Getters and Setters
    public ArrayList<Classroom> getClasses() {
        return classes;
    }
    public void setClasses(ArrayList<Classroom> classes) {
        this.classes = classes;
    }
    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
}
