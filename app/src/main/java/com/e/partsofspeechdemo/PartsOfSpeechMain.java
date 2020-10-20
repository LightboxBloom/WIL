package com.e.partsofspeechdemo;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/*    \PartsOfSpeechMain
Activity for the Parts of Speech game
Version 1 */
public class PartsOfSpeechMain extends Activity implements OnTouchListener
{
    private AbsoluteLayout mainLayout;
    private ImageView temp, selectedWord, word1Img, word2Img, word3Img, word4Img, word5Img, word6Img, word7Img, word8Img, word9Img, word10Img, word11Img, word12Img, nounImg, verbImg, adjectiveImg;
    private Rect nounHitRect = new Rect();
    private Rect verbHitRect = new Rect();
    private Rect adjectiveHitRect = new Rect();
    List<Word> originalWordList = new ArrayList<>();
    List<Word> wordList = new ArrayList<>();
    private boolean dragging = false;
    int level, answerCounter, consecutiveCounter = 0, achievementLevel = 0, totalMatchCounter, index;
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

    /*    onCreate
    Method that runs when the activity is opened
    Version 1 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parts_of_speech_main);
        mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(this);

        popUpDialog = new Dialog(this);
        popUpDialog.setContentView(R.layout.custompopup);

        levelDialog = new Dialog(this);
        levelDialog.setContentView(R.layout.levelpopup);

        //Media player
        mp = MediaPlayer.create(this, R.raw.popcorn);

        speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result =speech.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(PartsOfSpeechMain.this, "Language not supported",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/fHRTVSzz1EXpC89KzxfPWczk9hv2/Games/PartsOfSpeech");
        declareComponents();
        level = Integer.parseInt(getIntent().getExtras().get("PoSLevel").toString());
        achievementLevel = Integer.parseInt(getIntent().getExtras().get("PoSConsecutiveAchievement").toString());
        totalMatchCounter = Integer.parseInt(getIntent().getExtras().get("PoSTotalAchievement").toString());
        retrieveFromDatabase();
        assignImages();
    }

    /*    retrieveFromDatabase
    Method that retrieves all the words and populates an array
    Version 1 */
    private void retrieveFromDatabase() {
        originalWordList.clear();
        originalWordList.add(new Word("stir", "Verb", getDrawable(R.drawable.stir)));
        originalWordList.add(new Word("run", "Verb",getDrawable(R.drawable.run)));
        originalWordList.add(new Word("pull", "Verb", getDrawable(R.drawable.pull)));
        originalWordList.add(new Word("think", "Verb", getDrawable(R.drawable.think)));
        originalWordList.add(new Word("dance", "Verb", getDrawable(R.drawable.dance)));
        originalWordList.add(new Word("eat", "Verb",getDrawable(R.drawable.eat)));
        originalWordList.add(new Word("smell", "Verb", getDrawable(R.drawable.smell)));
        originalWordList.add(new Word("throw", "Verb", getDrawable(R.drawable.throwv)));
        originalWordList.add(new Word("kick", "Verb", getDrawable(R.drawable.kick)));
        originalWordList.add(new Word("hear", "Verb", getDrawable(R.drawable.hear)));
        originalWordList.add(new Word("boat", "Noun", getDrawable(R.drawable.boat)));
        originalWordList.add(new Word("hat", "Noun", getDrawable(R.drawable.hat)));
        originalWordList.add(new Word("cat", "Noun", getDrawable(R.drawable.cat)));
        originalWordList.add(new Word("dog", "Noun", getDrawable(R.drawable.dog)));
        originalWordList.add(new Word("mouse", "Noun", getDrawable(R.drawable.mouse)));
        originalWordList.add(new Word("barn", "Noun", getDrawable(R.drawable.barn)));
        originalWordList.add(new Word("phone", "Noun", getDrawable(R.drawable.phone)));
        originalWordList.add(new Word("tree", "Noun", getDrawable(R.drawable.tree)));
        originalWordList.add(new Word("apple", "Noun", getDrawable(R.drawable.apple)));
        originalWordList.add(new Word("chair", "Noun", getDrawable(R.drawable.chair)));
        originalWordList.add(new Word("table", "Noun", getDrawable(R.drawable.table)));
        originalWordList.add(new Word("pencil", "Noun", getDrawable(R.drawable.pencil)));
        originalWordList.add(new Word("school", "Noun", getDrawable(R.drawable.school)));
        originalWordList.add(new Word("Short", "Adjective", getDrawable(R.drawable.shorta)));
        originalWordList.add(new Word("tall", "Adjective", getDrawable(R.drawable.tall)));
        originalWordList.add(new Word("big", "Adjective", getDrawable(R.drawable.big)));
        originalWordList.add(new Word("small", "Adjective",getDrawable(R.drawable.small)));
        originalWordList.add(new Word("happy", "Adjective", getDrawable(R.drawable.happy)));
        originalWordList.add(new Word("sad", "Adjective", getDrawable(R.drawable.sad)));
        originalWordList.add(new Word("mad", "Adjective",getDrawable(R.drawable.mad)));
        originalWordList.add(new Word("brave", "Adjective",getDrawable(R.drawable.brave)));
        originalWordList.add(new Word("scared", "Adjective", getDrawable(R.drawable.scared)));
        originalWordList.add(new Word("calm", "Adjective", getDrawable(R.drawable.calm)));
        originalWordList.add(new Word("fancy", "Adjective", getDrawable(R.drawable.fancy)));
        originalWordList.add(new Word("beautiful", "Adjective", getDrawable(R.drawable.beautiful)));
        originalWordList.add(new Word("thin", "Adjective", getDrawable(R.drawable.thin)));
        originalWordList.add(new Word("fat", "Adjective", getDrawable(R.drawable.fat)));

        wordList.clear();
         for (int i = 0; i< 12; i++) {
             int randomNumber = randomGenerator.nextInt(originalWordList.size());
             wordList.add(originalWordList.get(randomNumber));
             originalWordList.remove(randomNumber);
         }
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
            word1Img.setImageDrawable(wordList.get(0).wordResource);
            word2Img.setImageDrawable(wordList.get(1).wordResource);
            word3Img.setImageDrawable(wordList.get(2).wordResource);
            word4Img.setImageDrawable(wordList.get(3).wordResource);
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
            word5Img.setImageDrawable(wordList.get(4).wordResource);
            word6Img.setImageDrawable(wordList.get(5).wordResource);
            word7Img.setImageDrawable(wordList.get(6).wordResource);
            word8Img.setImageDrawable(wordList.get(7).wordResource);
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
            word9Img.setImageDrawable(wordList.get(8).wordResource);
            word10Img.setImageDrawable(wordList.get(9).wordResource);
            word11Img.setImageDrawable(wordList.get(10).wordResource);
            word12Img.setImageDrawable(wordList.get(11).wordResource);
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

        boolean eventConsumed = true;
        int x = (int)event.getX();
        int y = (int)event.getY();
        temp.setX(x - (temp.getWidth())/2);
        temp.setY(y - (temp.getHeight())/2);

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (v == word1Img){
                temp.setImageDrawable(word1Img.getDrawable());
                dragging = true;
                selectedWord = word1Img;
                index = 0;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word2Img){
                temp.setImageDrawable(word2Img.getDrawable());
                dragging = true;
                selectedWord = word2Img;
                index = 1;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word3Img) {
                temp.setImageDrawable(word3Img.getDrawable());
                dragging = true;
                selectedWord = word3Img;
                index = 2;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word4Img){
                temp.setImageDrawable(word4Img.getDrawable());
                dragging = true;
                selectedWord = word4Img;
                index = 3;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word5Img){
                temp.setImageDrawable(word5Img.getDrawable());
                selectedWord = word5Img;
                dragging = true;
                index = 4;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word6Img){
                temp.setImageDrawable(word6Img.getDrawable());
                selectedWord = word6Img;
                dragging = true;
                index = 5;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word7Img) {
                temp.setImageDrawable(word7Img.getDrawable());
                selectedWord = word7Img;
                dragging = true;
                index = 6;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word8Img){
                temp.setImageDrawable(word8Img.getDrawable());
                selectedWord = word8Img;
                dragging = true;
                index = 7;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word9Img){
                temp.setImageDrawable(word9Img.getDrawable());
                dragging = true;
                selectedWord = word9Img;
                index = 8;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word10Img){
                temp.setImageDrawable(word10Img.getDrawable());
                dragging = true;
                selectedWord = word10Img;
                index = 9;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word11Img) {
                temp.setImageDrawable(word11Img.getDrawable());
                dragging = true;
                selectedWord = word11Img;
                index = 10;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == word12Img){
                temp.setImageDrawable(word12Img.getDrawable());
                dragging = true;
                selectedWord = word12Img;
                index = 11;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == temp){
                Log.d("TAG", "declareOnTouchListners: Level 2(temp)");
                dragging = true;
                speech.speak(wordList.get(index).text, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
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
                    temp.setImageDrawable(null);
                }
            }

            dragging = false;
            eventConsumed = false;
        }
        if (action == MotionEvent.ACTION_MOVE) {
            temp.setVisibility(View.VISIBLE);
            if (v == temp ){
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
            selectedWord.setImageDrawable(null);
            selectedWord.setVisibility(View.INVISIBLE);
            temp.setImageDrawable(null);
            temp.setVisibility(View.INVISIBLE);
            consecutiveCounter++;
            totalMatchCounter++;
            answerCounter++;
            achievements();
            updateLevel();
        }
        else{
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            consecutiveCounter = 0;
            temp.setImageDrawable(null);
            temp.setVisibility(View.INVISIBLE);
        }
    }
    /*    achievements
    method to check if the user has completed any achievements
    Version 1  */
    private void achievements(){
        mp.start();
        //Total shape match achievement check
        myRef.child("TotalAchievement").setValue(totalMatchCounter);
        if(totalMatchCounter == 10){
            Toast.makeText(this, "Congratulations on making 10 matches", Toast.LENGTH_SHORT).show();
        }
        if(totalMatchCounter == 20){
            Toast.makeText(this, "Congratulations on making 20 matches.", Toast.LENGTH_SHORT).show();
        }
        if(totalMatchCounter == 30){
            Toast.makeText(this, "Congratulations on making 30 matches.", Toast.LENGTH_SHORT).show();
        }
        if(totalMatchCounter == 50){
            Toast.makeText(this, "Congratulations on making 50 matches.", Toast.LENGTH_SHORT).show();
        }

        //Consecutive achievement check
        if(consecutiveCounter == 10 && achievementLevel < 1){
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Bronze medal", Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("1");
            achievementLevel++;
        }
        else if(consecutiveCounter == 20 && achievementLevel < 2){
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Silver medal", Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("2");
            achievementLevel++;
        }
        else if(consecutiveCounter == 30 && achievementLevel < 3){
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Gold medal", Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("3");
            achievementLevel++;
        }
        else if(consecutiveCounter % 5==0)
            Toast.makeText(this, "Well done! " + consecutiveCounter + " in a row!", Toast.LENGTH_SHORT).show();
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
        }
        else if(level == 3 && answerCounter ==12){
            level++;
            answerCounter = 0;
            displayCompleteDialogue();
            myRef.child("hasCompleted").setValue(true);
        }
        else if(level == 4 && answerCounter ==12){
            answerCounter = 0;
            retrieveFromDatabase();
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
                level = 1;
                myRef.child("Level").setValue(level);
                retrieveFromDatabase();
                assignImages();
                popUpDialog.dismiss();
            }
        });
        popUpDialog.show();

        freeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                retrieveFromDatabase();
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
                retrieveFromDatabase();
                assignImages();
                levelDialog.dismiss();
            }
        });
        levelDialog.show();

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                levelDialog.dismiss();
                Toast.makeText(PartsOfSpeechMain.this,
                        "The user will be taken back to the game menu activity",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
