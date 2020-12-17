package com.katherine.bloomuii.Games.MatchShape;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.katherine.bloomuii.Fragments.HomeFragment;
import com.katherine.bloomuii.R;

public class MatchShapesActivity extends AppCompatActivity {
    Button lvl1Btn,lvl2Btn, lvl3Btn, lvl4Btn, lvl5Btn, freeModeBtn;
    int level, achievementLevel = 0, totalMatchesCount;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    ImageView btnBack;
    private FragmentManager fragmentManager;

    /*    onCreate
    loads activity with the levels and achievements the user has reached and achieved
    Version 5  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_shapes);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        assignComponents();
        readingGameLevel();


        lvl1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 1;
                readingGameLevel();
                openShapeMain();
            }
        });

        lvl2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level =  2;
                readingGameLevel();
                openShapeMain();
            }
        });

        lvl3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 3;
                readingGameLevel();
                openShapeMain();
            }
        });

        lvl4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 4;
                readingGameLevel();
                openShapeMain();
            }
        });

        lvl5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 5;
                readingGameLevel();
                openShapeMain();
            }
        });

        freeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 6;
                readingGameLevel();
                openShapeMain();
            }
        });
        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBackClicked();
    }
    //click to get back to home fragment
    private void btnBackClicked(){
        final Fragment homeFragment = new HomeFragment();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchShapesActivity.this.onBackPressed();
            }
        });
    }

    /*    assignComponents
  method to assign the declared components to ID's in layout
  Version 5  */
    private void assignComponents(){
        lvl1Btn = findViewById(R.id.level1Btn);
        lvl2Btn = findViewById(R.id.level2Btn);
        lvl3Btn = findViewById(R.id.level3Btn);
        lvl4Btn = findViewById(R.id.level4Btn);
        lvl5Btn = findViewById(R.id.level5Btn);
        freeModeBtn = findViewById(R.id.freeModeBtn);
    }

    /*    readingGameLevel
    reads the database for information about user
    Version 5  */
    private void readingGameLevel() {
        //Reading in Game Level from Firebase
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/MatchingShape");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("hasCompleted").exists()){
                    lvl2Btn.setVisibility(View.VISIBLE);
                    lvl3Btn.setVisibility(View.VISIBLE);
                    lvl4Btn.setVisibility(View.VISIBLE);
                    lvl5Btn.setVisibility(View.VISIBLE);
                    freeModeBtn.setVisibility(View.VISIBLE);
                }
                //level check
                if(dataSnapshot.child("Level").exists()){
                    level = Integer.valueOf(dataSnapshot.child("Level").getValue().toString());
                }
                else{
                    level = 1;
                }
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveAchievement").exists()){
                    String cons = dataSnapshot.child("ConsecutiveAchievement").getValue().toString();
                    achievementLevel = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    totalMatchesCount = Integer.valueOf(tot);
                }
                displayAchievements();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*    displayAchievements
    displays the users achievements based off of database data
    Version 5  */
    private void displayAchievements(){
        //displays what levels are available
        if(level >= 2){
            lvl2Btn.setVisibility(View.VISIBLE);
        }
        if(level >= 3){
            lvl3Btn.setVisibility(View.VISIBLE);
        }
        if(level >= 4){
            lvl4Btn.setVisibility(View.VISIBLE);
        }
        if(level >= 5){
            lvl5Btn.setVisibility(View.VISIBLE);
        }
        if(level >= 6){
            freeModeBtn.setVisibility(View.VISIBLE);
        }
    }
    /*    openShapeMain
    method to open the main shape activity and pass it values
    Version 5  */
    private void openShapeMain() {
        Intent i = new Intent(this, ShapeMain.class);
        i.putExtra("ShapesLevel", level);
        i.putExtra("ConsecutiveShapesAchievement", achievementLevel);
        i.putExtra("TotalShapesAchievement", totalMatchesCount);
        startActivity(i);
    }

}