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
import com.katherine.bloomuii.Adapters.ReceivedRequestAdapter;
import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.ObjectClasses.ReceivedRequest;
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
    private int classId;
    private String userId;
    //Constructor
    public ClassroomHandler(){
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Classrooms/");
        classes = new ArrayList<>();

        getAllClasses();
        ClassroomIdCreater();
    }
    private void getAllClasses(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Classroom classroom = child.getValue(Classroom.class);
                    if (classroom != null) {
                        classes.add(classroom);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Generate a Class ID
    public void ClassroomIdCreater(){
        Random id = new Random();
        setClassId(id.nextInt(99999999));
        ClassroomIdValidator(getClassId());
    }
    //Check if Class Id doesn't Exist
    public void ClassroomIdValidator(int classId){
        boolean exists = false;
        if(!getClasses().isEmpty()) {
            for (Classroom classroom : getClasses()) {
                while (classroom.getClassroom_Id() == classId) {
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
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

}
