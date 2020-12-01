package com.katherine.bloomuii.Games.Puzzle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class UserScore {

    private final int[] ACHIEVEMENTS = {1, 20, 50, 100};

    private static UserScore mInstance = null;
    private static int totalMatchesCount;
    private static FirebaseUser currentUser;


    protected UserScore(){}

    public static synchronized UserScore getInstance() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(mInstance == null){
            mInstance = new UserScore();
            retrieveFromDatabase();
        }
        return mInstance;
    }

    public void addScore(){
        totalMatchesCount++;
        updateDatabase();
    }

    public int getScore(){
        retrieveFromDatabase();
        return totalMatchesCount;
    }

    public boolean hasAchievement(){
        retrieveFromDatabase();
        for (int i:ACHIEVEMENTS) {
            if (i==totalMatchesCount)
            return true;
        }
        return false;
    }

    public static void retrieveFromDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/Puzzle");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot ds) {
                //Total match check
                if(ds.child("TotalAchievement").exists()){
                    String tot = ds.child("TotalAchievement").getValue().toString();
                    totalMatchesCount = Integer.valueOf(tot);
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public static void updateDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/Puzzle");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot ds) {
                myRef.child("TotalAchievement").setValue(totalMatchesCount);
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

}
