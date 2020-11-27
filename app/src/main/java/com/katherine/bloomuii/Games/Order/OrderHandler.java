package com.katherine.bloomuii.Games.Order;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Activities.MainActivity;
import com.katherine.bloomuii.Games.Order.OrderFragment;

public class OrderHandler {
    public static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("userTest");

    public static void getSetUserLevel(){

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(OrderFragment.levelNumber == -100){ //get user's level from database
                    OrderFragment.levelNumber = Integer.parseInt(String.valueOf(snapshot.child("orderNumberLevel").getValue()));
                    OrderFragment.levelCreate();
                }
                else { //updates level in database
                    dbRef.child("orderNumberLevel").setValue(OrderFragment.levelNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
