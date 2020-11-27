package com.katherine.bloomuii.Games.MatchShape;

import androidx.annotation.NonNull;
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

public class MatchShapesActivity extends AppCompatActivity {
    Button lvl1Btn,lvl2Btn, lvl3Btn, lvl4Btn, lvl5Btn, freeModeBtn;
    int level, achievementLevel = 0, totalMatchesCount;
    ImageView consecBronzeImg, consecSilverImg, consecGoldImg, totalBronzeImage, totalSilverImage,
            totalGoldImage, totalMasterImage;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    /*    onCreate
    loads activity with the levels and achievements the user has reached and achieved
    Version 5  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_shapes);
        //database.getInstance().setPersistenceEnabled(true);
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
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/fHRTVSzz1EXpC89KzxfPWczk9hv2/Games/MatchingShape"); /*"Users/"+ currentUser.getUid() +"/Games/MatchingShapes"*/
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
        //displays what consecutive achievements are completed
        if(achievementLevel >=1)
            consecBronzeImg.setVisibility(View.VISIBLE);
        if(achievementLevel >=2)
            consecSilverImg.setVisibility(View.VISIBLE);
        if(achievementLevel ==3)
            consecGoldImg.setVisibility(View.VISIBLE);

        //displays what total matches achievements are completed
        if(totalMatchesCount >= 10)
            totalBronzeImage.setVisibility(View.VISIBLE);
        if(totalMatchesCount >=20)
            totalSilverImage.setVisibility(View.VISIBLE);
        if(totalMatchesCount >= 30)
            totalGoldImage.setVisibility(View.VISIBLE);
        if(totalMatchesCount >= 50)
            totalMasterImage.setVisibility(View.VISIBLE);

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