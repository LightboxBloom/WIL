package com.katherine.bloomuii.Games.Math;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MathHandler {
    public static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("userTest");

    public static void getSetUserLevel(){

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(MathFragment.levelNumber == -100){ //get user's level from database
                    MathFragment.levelNumber = Integer.parseInt(String.valueOf(snapshot.child("mathLevel").getValue()));
                    MathFragment.sumType();
                }
                else { //updates level in database
                    dbRef.child("mathLevel").setValue(MathFragment.levelNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
