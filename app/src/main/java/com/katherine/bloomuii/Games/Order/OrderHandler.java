package com.katherine.bloomuii.Games.Order;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Activities.MainActivity;
import com.katherine.bloomuii.Games.Math.MathFragment;
import com.katherine.bloomuii.Games.Order.OrderFragment;

public class OrderHandler {
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
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/OrderThat");
        myRef.keepSynced(true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                //level check
                if (!snapshot.child("Level").exists()) {
                    //myRef.child("Level").setValue(1);
                    //OrderFragment.levelNumber = Integer.valueOf(snapshot.child("Level").getValue().toString());
                    OrderFragment.levelNumber = 1;
                    //OrderFragment.levelCreate();
                }
                else if (OrderFragment.levelNumber == -100){
                    OrderFragment.levelNumber = Integer.valueOf(snapshot.child("Level").getValue().toString());
                }
                //OrderFragment.levelNumber = Integer.valueOf(snapshot.child("Level").getValue().toString());
                else {
                    myRef.child("Level").setValue(OrderFragment.levelNumber);
                }
                OrderFragment.ascOrDesc();
                OrderFragment.levelCreate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



/*        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(OrderFragment.levelNumber == -100){ //get user's level from database
                    OrderFragment.levelNumber = Integer.parseInt(String.valueOf(snapshot.child("orderNumberLevel").getValue()));
                    //OrderFragment.levelCreate();
                }
                else { //updates level in database
                    dbRef.child("orderNumberLevel").setValue(OrderFragment.levelNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}
