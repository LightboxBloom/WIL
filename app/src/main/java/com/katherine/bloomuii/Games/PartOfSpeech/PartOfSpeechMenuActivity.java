package com.katherine.bloomuii.Games.PartOfSpeech;

import androidx.appcompat.app.AppCompatActivity;

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
import com.katherine.bloomuii.R;
/*
 * Class name: PartsOfSpeechMenu
 * Reason: This activity is a menu for the user to choose which level they want to play
 * Parameters:
 * Team member: Matthew Talbot
 * Version 1:
 * 20/10/2020*/
public class PartOfSpeechMenuActivity extends AppCompatActivity {
    Button lvl1Btn,lvl2Btn, lvl3Btn, freeModeBtn;
    public int level;
    int achievementLevel = 0, totalMatchesCount;
    ImageView consecBronzeImg, consecSilverImg, consecGoldImg, totalBronzeImage,
            totalSilverImage, totalGoldImage, totalMasterImage;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_of_speech_menu);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        readingGameLevel();
        assignComponents();
        lvl1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 1;
                openPartsOfSpeechMain();
            }
        });
        lvl2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level =  2;
                openPartsOfSpeechMain();
            }
        });
        lvl3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 3;
                openPartsOfSpeechMain();
            }
        });
        freeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 4;
                openPartsOfSpeechMain();
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
        freeModeBtn = findViewById(R.id.freeModeBtn);
        consecSilverImg = findViewById(R.id.consecSilverImage);
        consecBronzeImg = findViewById(R.id.consecBronzeImage);
        consecGoldImg = findViewById(R.id.consecGoldImage);
        totalBronzeImage = findViewById(R.id.totalBronzeImage);
        totalSilverImage = findViewById(R.id.totalSilverImage);
        totalGoldImage= findViewById(R.id.totalGoldImage);
        totalMasterImage = findViewById(R.id.masterTotalImage);
    }
    /*    readingGameLevel
  reads the database for information about user
  Version 5  */
    private void readingGameLevel() {
        //Reading in Game Level from Firebase
        //Firebase Declarations

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/PartsOfSpeech");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot ds) {
                if(ds.child("Level").exists()){
                    level = Integer.valueOf(ds.child("Level").getValue().toString());
                }
                else
                    level = 1;
                if(ds.child("hasCompleted").exists()){
                    lvl2Btn.setVisibility(View.VISIBLE);
                    lvl3Btn.setVisibility(View.VISIBLE);
                    freeModeBtn.setVisibility(View.VISIBLE);
                }
                //Consecutive achievement check
                if(ds.child("ConsecutiveAchievement").exists()){
                    String ach = ds.child("ConsecutiveAchievement").getValue().toString();
                    achievementLevel = Integer.valueOf(ach);
                }
                //Total match check
                if(ds.child("TotalAchievement").exists()){
                    String tot = ds.child("TotalAchievement").getValue().toString();
                    totalMatchesCount = Integer.valueOf(tot);
                }
                displayAchievements();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
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
        if(level >= 4 ){
            freeModeBtn.setVisibility(View.VISIBLE);
        }
        //displays what consecutive achievements are completed
        if(achievementLevel >=1)
            consecBronzeImg.setVisibility(View.VISIBLE);
        if(achievementLevel >=2)
            consecSilverImg.setVisibility(View.VISIBLE);
        if(achievementLevel ==3)
            consecGoldImg.setVisibility(View.VISIBLE);

        //displays what total matches achievements are completed
        if(totalMatchesCount >= 1)
            totalBronzeImage.setVisibility(View.VISIBLE);
        if(totalMatchesCount >=20)
            totalSilverImage.setVisibility(View.VISIBLE);
        if(totalMatchesCount >= 50)
            totalGoldImage.setVisibility(View.VISIBLE);
        if(totalMatchesCount >= 150)
            totalMasterImage.setVisibility(View.VISIBLE);

    }
    /*    openPartsOfSpeechMain
    method to open the main Parts of speech activity and pass it values
    Version 5  */
    private void openPartsOfSpeechMain() {
        Intent i = new Intent(this, PartOfSpeechActivity.class);
        i.putExtra("PoSLevel", level);
        i.putExtra("PoSConsecutiveAchievement", achievementLevel);
        i.putExtra("PoSTotalAchievement", totalMatchesCount);
        startActivity(i);
    }
}