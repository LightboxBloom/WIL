package com.example.unjumblegamedemo;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String correctAnswer; //Used to store the answer that the user must give to proceed to the next level
    public static String userAnswer = ""; //Used to track what the answer the user is giving
    public static int testNumber = -100; //Used to track the level that the user is on, initialized at -100 to trigger database retrieval
    public static Button[] buttons = new Button[8]; //Button array used to initialize all buttons
    public static TextView[] textViews = new TextView[2]; //Button array used to initialize all buttons


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseApp.initializeApp(this);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true); //Firebase functionality works when offline, online is required for first launch to retrieve data

        FirebaseHandler.getSetUserLevel();

        for(int i=0; i<buttons.length; i++)        //initializing buttons
        {
            String buttonID = "button" + (i+1);

            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
            buttons[i].setVisibility(View.INVISIBLE);
        }

        for(int i=0; i<textViews.length; i++)        //initializing textViews
        {
            String textViewID = "textView" + (i+1);

            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            textViews[i] = findViewById(resID);
            textViews[i].setText("");
        }

        //Submit and Restart buttons are set to visible
        buttons[6].setVisibility(View.VISIBLE);
        buttons[7].setVisibility(View.VISIBLE);

        //These two buttons are deactivated while the data is being pulled from firebase
        buttons[6].setEnabled(false);
        buttons[7].setEnabled(false);
    }

    //Handles the shuffling of sentences so that the word order is random
    public static void shuffle(List<String> sentences){

        List<String> shuffleList = new ArrayList<>(sentences);
        Collections.shuffle(shuffleList);

        //These loops handle which buttons must be visible and active depending on how long a sentence is
        //These loops also handle which words are assigned to which buttons
        if(sentences.size() == 1){
            for(int i=1; i<buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            buttons[0].setVisibility(View.VISIBLE);
            buttons[0].setEnabled(true);
            buttons[0].setText(shuffleList.get(0));
        }
        else if(sentences.size() == 2){
            for(int i=2; i<buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for(int i=0; i<2; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i));
                }
            }
        }
        else if(sentences.size() == 3){
            for(int i=3; i<buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for(int i=0; i<3; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i));
                }
            }
        }
        else if(sentences.size() == 4){
            for(int i=4; i<buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for(int i=0; i<4; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i));
                }
            }
        }
        else if(sentences.size() == 5){
            for(int i=0; i<5; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i));
                }
            }
            buttons[5].setVisibility(View.INVISIBLE);
        }
        else if(sentences.size() == 6) {
            for (int i = 0; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i));
                }
            }
        }

        //Used to set the value for the correct answer by creating a string using all the words from a sentence
        StringBuilder sb = new StringBuilder();
        for (String s : sentences)
        {
            sb.append(s);
            sb.append(" ");
        }
        correctAnswer = sb.toString().trim();
        FirebaseHandler.getSetUserLevel();

    }

    //method for enabling the buttons that hold words (used when restarting a level or loading a new one)
    public static void enableButtons(){
        for(int i=0; i<buttons.length - 2; i++) {
            buttons[i].setEnabled(true);
        }
    }

    public static void hideButtons(){
        for(int i=0; i<buttons.length - 2; i++) {
            buttons[i].setVisibility(View.INVISIBLE);
        }
    }

    //All word buttons are set to 'Not Clicked' by default
    boolean clicked1=false;
    boolean clicked2=false;
    boolean clicked3=false;
    boolean clicked4=false;
    boolean clicked5=false;
    boolean clicked6=false;

    //This means that all the clickable word buttons are set to 'Not Clicked'
    //This is used for giving user correct error feedback
    public void unclickButtons(){
        clicked1=false;
        clicked2=false;
        clicked3=false;
        clicked4=false;
        clicked5=false;
        clicked6=false;
    }

    @Override
    public void onClick(View v) {   //handles the click events for all the buttons

        switch (v.getId()) {
            case R.id.button1:
                buttons[0].setEnabled(false); //button is made unclickable so the user cannot use the same word twice

                userAnswer = userAnswer + " " + buttons[0].getText().toString(); //the word the user selects is added to their answer
                textViews[0].setText(userAnswer); //user's current answer is displayed in the textview

                clicked1=true; //button is set to 'Clicked'
                break;

            case R.id.button2:
                buttons[1].setEnabled(false);

                userAnswer = userAnswer + " " + buttons[1].getText().toString();
                textViews[0].setText(userAnswer);

                clicked2=true;
                break;

            case R.id.button3:
                buttons[2].setEnabled(false);

                userAnswer = userAnswer + " " + buttons[2].getText().toString();
                textViews[0].setText(userAnswer);

                clicked3=true;
                break;

            case R.id.button4:
                buttons[3].setEnabled(false);

                userAnswer = userAnswer + " " + buttons[3].getText().toString();
                textViews[0].setText(userAnswer);

                clicked4=true;
                break;

            case R.id.button5:

                buttons[4].setEnabled(false);

                userAnswer = userAnswer + " " + buttons[4].getText().toString();
                textViews[0].setText(userAnswer);

                clicked5=true;
                break;

            case R.id.button6:

                buttons[5].setEnabled(false);

                userAnswer = userAnswer + " " + buttons[5].getText().toString();
                textViews[0].setText(userAnswer);

                clicked6=true;
                break;


            case R.id.button7: //submit button

                if (userAnswer.contains(correctAnswer)) { //handles event when the user gives correct answer
                    testNumber = testNumber + 1;

                    if (testNumber > FirebaseHandler.counter.getCount()) {
                        Toast.makeText(this, "All Levels Complete! Congratulations!", Toast.LENGTH_SHORT).show();
                        buttons[7].setEnabled(false);
                        buttons[6].setEnabled(false);
                        shuffle(Sentence.sentenceArray[testNumber - 2]);
                        hideButtons();
                    } else {
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        shuffle(Sentence.sentenceArray[testNumber - 1]);

                        unclickButtons();

                        userAnswer = "";
                        textViews[0].setText(userAnswer);
                    }


                } else {            //handles event when the user gives incorrect answer
                    //Different error messages are given based on whether the user didn't use all available words or if they just gave the wrong answer but did use all available words
                    if(Sentence.sentenceArray[testNumber - 1].size()<=1){
                        if(!clicked1 || !clicked2 || !clicked3){
                            Toast.makeText(this, "Incorrect answer! Use all available words before using the submit button!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Incorrect answer! Click RESTART to try this level again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(Sentence.sentenceArray[testNumber - 1].size()<=2){
                        if(!clicked1 || !clicked2 || !clicked3){
                            Toast.makeText(this, "Incorrect answer! Use all available words before using the submit button!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Incorrect answer! Click RESTART to try this level again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(Sentence.sentenceArray[testNumber - 1].size()<=3){
                        if(!clicked1 || !clicked2 || !clicked3){
                            Toast.makeText(this, "Incorrect answer! Use all available words before using the submit button!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Incorrect answer! Click RESTART to try this level again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(Sentence.sentenceArray[testNumber - 1].size()<=4){
                        if(!clicked1 || !clicked2 || !clicked3 || !clicked4){
                            Toast.makeText(this, "Incorrect answer! Use all available words before using the submit button!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Incorrect answer! Click RESTART to try this level again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(Sentence.sentenceArray[testNumber - 1].size()<=5){
                        if(!clicked1 || !clicked2 || !clicked3 || !clicked4 || !clicked5){
                            Toast.makeText(this, "Incorrect answer! Use all available words before using the submit button!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Incorrect answer! Click RESTART to try this level again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(Sentence.sentenceArray[testNumber - 1].size()<=6) {
                        {
                            if (!clicked1 || !clicked2 || !clicked3 || !clicked4 || !clicked5 || !clicked6) {
                                Toast.makeText(this, "Incorrect answer! Use all available words before using the submit button!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Incorrect answer! Click RESTART to try this level again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                break;

            case R.id.button8: //Restart Button
                //allows for restarting from level 1 (Used for testing purposes)
                //allows for restarting from level 1 (Used for testing purposes)
                //allows for restarting from level 1 (Used for testing purposes)
                //allows for restarting from level 1 (Used for testing purposes)
                //allows for restarting from level 1 (Used for testing purposes)
                if (testNumber > FirebaseHandler.counter.getCount()) {
                    testNumber = 1;
                    shuffle(Sentence.sentenceArray[testNumber - 1]);
                    buttons[6].setEnabled(true);
                    userAnswer = "";
                    textViews[0].setText(userAnswer);
                }
                //resets the current user level
                else {
                    Toast.makeText(this, "Level Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer = ""; //user answer reset
                    textViews[0].setText(userAnswer);//reset user answer is displayed to user

                    unclickButtons(); //buttons are set to 'Not Clicked'
                    shuffle(Sentence.sentenceArray[testNumber - 1]); //words are shuffled
                    enableButtons(); //buttons are enabled
                    break;
                }
        }
    }
}