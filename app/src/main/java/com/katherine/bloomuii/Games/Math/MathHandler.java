package com.katherine.bloomuii.Games.Math;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Games.Order.OrderFragment;

public class MathHandler {
    public static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("userTest");
    //Firebase
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    public static FirebaseUser currentUser;
    public static FirebaseAuth mAuth;

    public static void getSetUserLevel(){
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/MathGame");
        myRef.keepSynced(true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //level check
                if (!snapshot.child("Level").exists()) {
                    //myRef.child("Level").setValue(1);
                    //MathFragment.levelNumber = Integer.valueOf(snapshot.child("Level").getValue().toString());
                    MathFragment.levelNumber = 1;
                    MathFragment.sumType();
                }
                else if (OrderFragment.levelNumber == -100){
                    MathFragment.levelNumber = Integer.valueOf(snapshot.child("Level").getValue().toString());
                    MathFragment.sumType();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(MathFragment.levelNumber == -100){ //get user's level from database
                    MathFragment.levelNumber = Integer.parseInt(String.valueOf(snapshot.child("mathLevel").getValue()));
                    //MathFragment.sumType();
                }
                else { //updates level in database
                    dbRef.child("mathLevel").setValue(MathFragment.levelNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}
