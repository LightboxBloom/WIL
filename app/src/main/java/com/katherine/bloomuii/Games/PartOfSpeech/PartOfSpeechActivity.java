package com.katherine.bloomuii.Games.PartOfSpeech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/*
 * Class name: PartsOfSpeechMain
 * Reason: Activity creates and handles the parts of speech logic depending on the level selected.
 * Parameters:
 * Team member: Matthew Talbot
 * Version 3:
 * 05/11/2020*/
public class PartOfSpeechActivity extends AppCompatActivity implements View.OnTouchListener{
    private AbsoluteLayout mainLayout;
    private TextView selectedWord, word1Img, word2Img, word3Img, word4Img, word5Img, word6Img
            , word7Img, word8Img, word9Img, word10Img, word11Img, word12Img, temp;
    private ImageView nounImg, verbImg, adjectiveImg;
    private Rect nounHitRect = new Rect();
    private Rect verbHitRect = new Rect();
    private Rect adjectiveHitRect = new Rect();
    List<Word> originalWordList = new ArrayList<>();
    List<Word> wordList = new ArrayList<>();
    private boolean dragging = false;
    int level,answerCounter, consecutiveCounter = 0, achLvl = 0, totMatch, index;
    private Random randomGenerator = new Random();
    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //media player
    MediaPlayer mp;
    //text to speech
    private TextToSpeech speech;
    //pop up dialogue
    private Dialog popUpDialog, levelDialog;
    private Button startAgainBtn, freeModeBtn ,returnBtn;
    final Handler handler = new Handler();
    /*    onCreate
    Method that runs when the activity is opened
    Version 1 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_of_speech);
        mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(this);
        //declaring pop up dialogs
        popUpDialog = new Dialog(this);
        popUpDialog.setContentView(R.layout.custompopup);
        levelDialog = new Dialog(this);
        levelDialog.setContentView(R.layout.levelpopup);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //Media player
        mp = MediaPlayer.create(this, R.raw.popcorn);
        //declaring text to speech properties
        speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result =speech.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(PartOfSpeechActivity.this, "Language not supported",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        declareComponents();
        level = Integer.parseInt(getIntent().getExtras().get("PoSLevel").toString());
        achLvl = Integer.parseInt(getIntent().getExtras().get("PoSConsecutiveAchievement").toString());
        totMatch = Integer.parseInt(getIntent().getExtras().get("PoSTotalAchievement").toString());
        retrieveFromDatabase();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //2s Delay is used as it takes over a second to load all items from firebase
                randomiseList();
                assignImages();
            }
        }, 2000);
    }
    /*    retrieveFromDatabase
Method that retrieves all the words and populates an array
Version 1 */
    private void retrieveFromDatabase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("PhotoLabel");
        myRef.keepSynced(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                for (DataSnapshot data: ds.getChildren()){
                    String word = data.child("Word").getValue().toString();
                    String PoS = data.child("PoS").getValue().toString();
                    originalWordList.add(new Word(word, PoS));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "onDataChange: " + databaseError.toString());
            }
        });
    }
    /*    randomiseList
   Method that makes a local copy of the array so that the user does not have to download the list after each level completed.
   The code then randomizes a copy of the local list
   Version 1 */
    private void randomiseList(){
        List<Word> tempWordList = new ArrayList<>();
        for (int i = 0; i < originalWordList.size();i++){
            tempWordList.add(originalWordList.get(i));
        }
        wordList.clear();
        for (int i = 0; i< 12; i++) {
            int randomNumber = randomGenerator.nextInt(tempWordList.size());
            wordList.add(tempWordList.get(randomNumber));
            tempWordList.remove(randomNumber);
        }
    }
    /*    onDestroy
   Method that passes the level the user is currently on back to the menu when they exit
   Version 1 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PartOfSpeechMenuActivity posMenu = new PartOfSpeechMenuActivity();
        posMenu.level = level;
        Log.d("TAG", "onDestroy: ");
    }
    /*    declareComponents
      Method to declare all the components being used on the activity
      Version 1 */
    private void declareComponents(){
        word1Img = findViewById(R.id.Row1Word1ImageView);
        word2Img = findViewById(R.id.Row1Word2ImageView);
        word3Img = findViewById(R.id.Row1Word3ImageView);
        word4Img = findViewById(R.id.Row1Word4ImageView);
        word5Img = findViewById(R.id.Row2Word1ImageView);
        word6Img = findViewById(R.id.Row2Word2ImageView);
        word7Img = findViewById(R.id.Row2Word3ImageView);
        word8Img = findViewById(R.id.Row2Word4ImageView);
        word9Img = findViewById(R.id.Row3Word1ImageView);
        word10Img = findViewById(R.id.Row3Word2ImageView);
        word11Img = findViewById(R.id.Row3Word3ImageView);
        word12Img = findViewById(R.id.Row3Word4ImageView);
        nounImg = findViewById(R.id.NounsImageView);
        verbImg = findViewById(R.id.VerbsImageView);
        adjectiveImg = findViewById(R.id.AdjectiveImageView);
        temp = findViewById(R.id.tempImage);
        nounImg.setImageDrawable(getDrawable(R.drawable.nouns));
        verbImg.setImageDrawable(getDrawable(R.drawable.verbs));
        adjectiveImg.setImageDrawable(getDrawable(R.drawable.adjectives));
    }
    /*    assignImages
   Assigns random words to the displayed image views
   Version 1 */
    private void  assignImages(){
        if(level >= 1) {
            word1Img.setText(wordList.get(0).text);
            word2Img.setText(wordList.get(1).text);
            word3Img.setText(wordList.get(2).text);
            word4Img.setText(wordList.get(3).text);
            word1Img.setVisibility(View.VISIBLE);
            word2Img.setVisibility(View.VISIBLE);
            word3Img.setVisibility(View.VISIBLE);
            word4Img.setVisibility(View.VISIBLE);
            word5Img.setVisibility(View.INVISIBLE);
            word6Img.setVisibility(View.INVISIBLE);
            word7Img.setVisibility(View.INVISIBLE);
            word8Img.setVisibility(View.INVISIBLE);
            word9Img.setVisibility(View.INVISIBLE);
            word10Img.setVisibility(View.INVISIBLE);
            word11Img.setVisibility(View.INVISIBLE);
            word12Img.setVisibility(View.INVISIBLE);
        }
        if(level >=2){
            word5Img.setText(wordList.get(4).text);
            word6Img.setText(wordList.get(5).text);
            word7Img.setText(wordList.get(6).text);
            word8Img.setText(wordList.get(7).text);
            word5Img.setVisibility(View.VISIBLE);
            word6Img.setVisibility(View.VISIBLE);
            word7Img.setVisibility(View.VISIBLE);
            word8Img.setVisibility(View.VISIBLE);
            word9Img.setVisibility(View.INVISIBLE);
            word10Img.setVisibility(View.INVISIBLE);
            word11Img.setVisibility(View.INVISIBLE);
            word12Img.setVisibility(View.INVISIBLE);
        }
        if(level >= 3){
            word9Img.setText(wordList.get(8).text);
            word10Img.setText(wordList.get(9).text);
            word11Img.setText(wordList.get(10).text);
            word12Img.setText(wordList.get(11).text);
            word9Img.setVisibility(View.VISIBLE);
            word10Img.setVisibility(View.VISIBLE);
            word11Img.setVisibility(View.VISIBLE);
            word12Img.setVisibility(View.VISIBLE);
        }
        declareOnTouchListners();
    }
    /*    onTouch
    What happens when the User touchs an Image View on the screen
    Version 1  */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //method is used when a image view has been touched, dragged and dropped.
        //the image view is passed as a parameter to the method
        if(!dragging){
            temp.setVisibility(View.INVISIBLE);
        }
        boolean eventConsumed = true;
        int x = (int)event.getX();
        int y = (int)event.getY();
        temp.setX(x - (temp.getWidth())/2);
        temp.setY(y - (temp.getHeight())/2);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (v == word1Img){
                temp.setText(word1Img.getText());
                dragging = true;
                selectedWord = word1Img;
                index = 0;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word2Img){
                temp.setText(word2Img.getText());
                dragging = true;
                selectedWord = word2Img;
                index = 1;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word3Img) {
                temp.setText(word3Img.getText());
                dragging = true;
                selectedWord = word3Img;
                index = 2;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word4Img){
                temp.setText(word4Img.getText());
                dragging = true;
                selectedWord = word4Img;
                index = 3;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word5Img){
                temp.setText(word5Img.getText());
                selectedWord = word5Img;
                dragging = true;
                index = 4;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word6Img){
                temp.setText(word6Img.getText());
                selectedWord = word6Img;
                dragging = true;
                index = 5;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word7Img) {
                temp.setText(word7Img.getText());
                selectedWord = word7Img;
                dragging = true;
                index = 6;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word8Img){
                temp.setText(word8Img.getText());
                selectedWord = word8Img;
                dragging = true;
                index = 7;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word9Img){
                temp.setText(word9Img.getText());
                dragging = true;
                selectedWord = word9Img;
                index = 8;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word10Img){
                temp.setText(word10Img.getText());
                dragging = true;
                selectedWord = word10Img;
                index = 9;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word11Img) {
                temp.setText(word11Img.getText());
                dragging = true;
                selectedWord = word11Img;
                index = 10;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == word12Img){
                temp.setText(word12Img.getText());
                dragging = true;
                selectedWord = word12Img;
                index = 11;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == temp){
                dragging = true;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
        }
        if (action == MotionEvent.ACTION_UP) {
            if (dragging) {
                nounImg.getHitRect(nounHitRect);
                verbImg.getHitRect(verbHitRect);
                adjectiveImg.getHitRect(adjectiveHitRect);
                if (nounHitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, nounImg);
                    confirmMatch("Noun");
                }
                else if (verbHitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, verbImg);
                    confirmMatch("Verb");
                }
                else if (adjectiveHitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, adjectiveImg);
                    confirmMatch("Adjective");
                }
                else{
                    Toast.makeText(this,
                            "Drag the word onto a Part of Speech.", Toast.LENGTH_SHORT).show();
                    temp.setVisibility(View.INVISIBLE);
                }
            }
            dragging = false;
            eventConsumed = false;
        }
        if (action == MotionEvent.ACTION_MOVE) {
            if (v == temp ){
                temp.setVisibility(View.VISIBLE);
                if (dragging){
                    setAbsoluteLocationCentered(temp, x, y);
                }
            }
        }
        return eventConsumed;
    }
    /*    setSameAbsoluteLocation
    method to drop the dragged image into the same image view as the shape to match
    Version 1  */
    private void setSameAbsoluteLocation(View v1, View v2) {
        AbsoluteLayout.LayoutParams alp2 = (AbsoluteLayout.LayoutParams) v2.getLayoutParams();
        setAbsoluteLocation(v1, alp2.x, alp2.y);
    }

    /*    setAbsoluteLocationCentered
    method to center the dropped image to match the outlined image
     Version 1  */
    private void setAbsoluteLocationCentered(View v, int x, int y) {
        setAbsoluteLocation(v, x - v.getWidth() / 2, y - v.getHeight() / 2);
    }
    /*    setAbsoluteLocation
    method to set the location of the dragged image
    Version 1  */
    private void setAbsoluteLocation(View v, int x, int y) {
        AbsoluteLayout.LayoutParams alp = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
        alp.x = x/1000;
        alp.y = y/1000;
        v.setLayoutParams(alp);
    }
    /*    confirmMatch
   method to check the selected image view matches the part of Speech that the word belongs to.
   Version 1  */
    private void confirmMatch(String partOfSpeech){
        if(partOfSpeech.equals(wordList.get(index).partOfSpeech)){
            if(mp.isPlaying()){
                mp.stop();
                mp.reset();
            }
            mp = MediaPlayer.create(this, R.raw.popcorn);
            mp.start();
            selectedWord.setVisibility(View.INVISIBLE);
            temp.setVisibility(View.INVISIBLE);
            consecutiveCounter++;
            totMatch++;
            answerCounter++;
            achievements();
            updateLevel();
        }
        else{
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            consecutiveCounter = 0;
        }
        temp.setText(null);
        temp.setVisibility(View.INVISIBLE);
    }
    /*    achievements
   method to check if the user has completed any achievements
   Version 1  */
    private void achievements(){
        //Total shape match achievement check
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/PartsOfSpeech");
        myRef.child("TotalAchievement").setValue(totMatch);
        if(totMatch == 10){
            Toast.makeText(this, "Congratulations on making 10 matches",
                    Toast.LENGTH_SHORT).show();
        }
        if(totMatch == 20){
            Toast.makeText(this, "Congratulations on making 20 matches.",
                    Toast.LENGTH_SHORT).show();
        }
        if(totMatch == 30){
            Toast.makeText(this, "Congratulations on making 30 matches.",
                    Toast.LENGTH_SHORT).show();
        }
        if(totMatch == 50){
            Toast.makeText(this, "Congratulations on making 50 matches.",
                    Toast.LENGTH_SHORT).show();
        }
        //Consecutive achievement check
        if(consecutiveCounter == 10 && achLvl < 1){
            myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/PartsOfSpeech");
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Bronze medal",
                    Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("1");
            achLvl++;
        }
        else if(consecutiveCounter == 20 && achLvl < 2){
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Silver medal",
                    Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("2");
            achLvl++;
        }
        else if(consecutiveCounter == 30 && achLvl < 3){
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Gold medal",
                    Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("3");
            achLvl++;
        }
        else if(consecutiveCounter % 5==0)
            Toast.makeText(this, "Well done! " + consecutiveCounter + " in a row!",
                    Toast.LENGTH_SHORT).show();
    }
    /*    declareOnTouchListners
  method to check if the user has completed any achievements
  Version 1  */
    private void declareOnTouchListners(){
        if(level >= 1) {
            word1Img.setOnTouchListener(this);
            word2Img.setOnTouchListener(this);
            word3Img.setOnTouchListener(this);
            word4Img.setOnTouchListener(this);
        }
        if(level >=2){
            word5Img.setOnTouchListener(this);
            word6Img.setOnTouchListener(this);
            word7Img.setOnTouchListener(this);
            word8Img.setOnTouchListener(this);
        }
        if(level >= 3){
            word9Img.setOnTouchListener(this);
            word10Img.setOnTouchListener(this);
            word11Img.setOnTouchListener(this);
            word12Img.setOnTouchListener(this);
        }
    }
    /*    updateLevel
   method to update the level in the database and client application to display different amounts of Image views
   Version 1  */
    private void updateLevel(){
        if(level ==1 && answerCounter ==4 || level == 2 && answerCounter ==8 ){
            level ++;
            answerCounter =0;
            displayLevelDialog();
            randomiseList();
            assignImages();
        }
        else if(level == 3 && answerCounter ==12){
            level++;
            answerCounter = 0;
            displayCompleteDialogue();
            level = 1;
            myRef.child("Level").setValue(level);
            randomiseList();
            assignImages();
            myRef.child("hasCompleted").setValue(true);
        }
        else if(level == 4 && answerCounter ==12){
            answerCounter = 0;
            randomiseList();
            assignImages();
        }
        myRef.child("Level").setValue(level);
    }
    /*    displayDialogue
  Method to display the dialogue once a game mode is completed to either start again or to quit
  Version 1 */
    private void displayCompleteDialogue(){
        //declaring Custom Pop Up items
        startAgainBtn= popUpDialog.findViewById(R.id.startAgainBtn);
        freeModeBtn = popUpDialog.findViewById(R.id.freeModeBtn);
        startAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                popUpDialog.dismiss();
            }
        });
        popUpDialog.show();
        freeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                level = 3;
                randomiseList();
                assignImages();
                popUpDialog.dismiss();
            }
        });
    }
    /*    displayLevelDialog
        Method to display the dialogue once a game mode is completed to either start again or to quit
        Version 1 */
    private void displayLevelDialog(){
        //declaring Custom Pop Up items
        startAgainBtn= levelDialog.findViewById(R.id.continuenBtn);
        returnBtn = levelDialog.findViewById(R.id.freeModeBtn);
        startAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                levelDialog.dismiss();
            }
        });
        levelDialog.show();
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                levelDialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PartOfSpeechMenuActivity.class);
                startActivity(i);
            }
        });
    }
}