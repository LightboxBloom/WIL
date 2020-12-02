package com.katherine.bloomuii.Games.MatchShape;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.katherine.bloomuii.Fragments.HomeFragment;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ShapeMain extends Activity implements OnTouchListener {
    //text to speech
    private TextToSpeech speech;

    //Components
    private ImageView temp, shape1, shape2, shape3, shape4, shape5, shape6;                       // The shapes that the user drags.
    private ImageView shapeToMatch;                                                     // The shape outline that the user is supposed to drag shapes to.
    private AbsoluteLayout mainLayout;

    private boolean dragging = false;
    private Integer index, shapeNumber = 0,  level = 1, freeModeShapeNum, consecutiveCounter = 0,
            achievementLevel = 0, totalMatchCounter;

    ArrayList<Shape> tempShapeList = new ArrayList<>();
    ArrayList<Shape> firstShapeList = new ArrayList<>();
    ArrayList<Shape> finalShapeList = new ArrayList<>();
    ArrayList<Shape> greyShapeList = new ArrayList<>();

    private Rect hitRect = new Rect();
    private Random randomGenerator = new Random();

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    //media player
    MediaPlayer mp;

    //pop up dialogue
    private Dialog popUpDialog, levelDialog;
    private Button startAgainBtn, freeModeBtn ,returnBtn;

    ImageView btnBack;
    private FragmentManager fragmentManager;
    /*    onCreate
    When the activity loads, this is the first method to be called.
    Version 5  */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Method is used to Call methods when the activity first runs
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_main);

        mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

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
                        Toast.makeText(ShapeMain.this, "Language not supported",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/" + currentUser.getUid() +"/Games/MatchingShape");
        declareComponents();
        level = Integer.parseInt(getIntent().getExtras().get("ShapesLevel").toString());
        achievementLevel = Integer.parseInt(getIntent().getExtras().get("ConsecutiveShapesAchievement").toString());
        totalMatchCounter = Integer.parseInt(getIntent().getExtras().get("TotalShapesAchievement").toString());
        retrieveFromDatabase();
        assignResources();

        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBackClicked();
    }
    //click to get back to home fragment
    private void btnBackClicked(){
        final Fragment homeFragment = new HomeFragment();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShapeMain.this.onBackPressed();
            }
        });
    }
    /*    onTouch
        What happens when the User touchs an Image View on the screen
        Version 2  */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //method is used when a image view has been touched, dragged and dropped.
        //the image view is passed as a parameter to the method

        boolean eventConsumed = true;
        int x = (int)event.getX();
        int y = (int)event.getY();
        temp.setX(x - (temp.getWidth())/2);
        temp.setY(y - (temp.getHeight()/2));
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (v == shape1){
                temp.setImageDrawable(shape1.getDrawable());
                dragging = true;
                index = 0;
                speech.speak(finalShapeList.get(index).shapeName, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == shape2){
                temp.setImageDrawable(shape2.getDrawable());
                dragging = true;
                index = 1;
                speech.speak(finalShapeList.get(index).shapeName, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == shape3) {
                temp.setImageDrawable(shape3.getDrawable());
                dragging = true;
                index = 2;
                speech.speak(finalShapeList.get(index).shapeName, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == shape4){
                temp.setImageDrawable(shape4.getDrawable());
                dragging = true;
                index = 3;
                speech.speak(finalShapeList.get(index).shapeName, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == shape5){
                temp.setImageDrawable(shape5.getDrawable());
                dragging = true;
                index = 4;
                speech.speak(finalShapeList.get(index).shapeName, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == shape6){
                temp.setImageDrawable(shape6.getDrawable());
                dragging = true;
                index = 5;
                speech.speak(finalShapeList.get(index).shapeName, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }
            else if (v == temp){
                dragging = true;
                speech.speak(finalShapeList.get(index).shapeName, TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
            }

        }
        if (action == MotionEvent.ACTION_UP) {
            if (dragging) {
                shapeToMatch.getHitRect(hitRect);
                if (hitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, shapeToMatch);
                    confirmMatch();
                }
                else{
                    Toast.makeText(this,
                            "Drag shape onto the grey shape.", Toast.LENGTH_LONG).show();
                    temp.setImageDrawable(null);
                }
            }
            dragging = false;
            eventConsumed = false;
        }
        if (action == MotionEvent.ACTION_MOVE) {
            temp.setVisibility(View.VISIBLE);
            if (v == temp){
                if (dragging){
                    setAbsoluteLocationCentered(temp, x, y);
                }
            }
        }
        return eventConsumed;
    }
    /*    onDestroy
      method to shutdown activity safely
       Version 5 */
    @Override
    protected void onDestroy() {
        speech.shutdown();
        super.onDestroy();
    }
    /*    declareComponents
        method to declare all the existing components on the activity
         Version 2  */
    private void declareComponents() {
        //this method is called first and is used to declare what components exist on the activity
        temp = (ImageView) findViewById(R.id.temp);
        shape1 = (ImageView) findViewById(R.id.shape1_img);
        shape2 = (ImageView) findViewById(R.id.shape2_img);
        shape3 = (ImageView) findViewById(R.id.shape3_img);
        shape4 = (ImageView) findViewById(R.id.shape4_img);
        shape5 = (ImageView) findViewById(R.id.shape5_img);
        shape6 = (ImageView) findViewById(R.id.shape6_img);
        shapeToMatch = (ImageView) findViewById(R.id.shape_to_match_img);
    }
    /*    retrieveFromDatbase
   method to retrieve the shapes from the database
   Version 2  */
    private void retrieveFromDatabase() {
        //this method will contact the database and will pull in all the shapes from the database
        firstShapeList.clear();
        greyShapeList.clear();
        //retrieve list of Shapes from Database
        firstShapeList.add(new Shape("Square", getDrawable(R.drawable.ksquare)));
        firstShapeList.add(new Shape("Circle", getDrawable(R.drawable.kcircle)));
        firstShapeList.add(new Shape("Rectangle", getDrawable(R.drawable.krectangle)));
        firstShapeList.add(new Shape("Triangle", getDrawable(R.drawable.ktriangle)));
        firstShapeList.add(new Shape("Oval", getDrawable(R.drawable.koval)));
        firstShapeList.add(new Shape("Star", getDrawable(R.drawable.star)));

        //retrieve the shape to match against from database
        greyShapeList.add(new Shape("Square", getDrawable(R.drawable.ksquare_outline)));
        greyShapeList.add(new Shape("Circle", getDrawable(R.drawable.kcircle_outline)));
        greyShapeList.add(new Shape("Rectangle", getDrawable(R.drawable.krectangle_outline)));
        greyShapeList.add(new Shape("Triangle", getDrawable(R.drawable.ktriangle_outline)));
        greyShapeList.add(new Shape("Oval", getDrawable(R.drawable.koval_outline)));
        greyShapeList.add(new Shape("Star", getDrawable(R.drawable.star_outline)));
    }
    /*    assignResources
   method to Assign the shapes from the database into local variables
   Version 5  */
    private void assignResources() {
        //once the shapes are pulled into the List, here is where they will be altered and custom
        // made into the shapes we want to appear in the front end

        //code for free mode
        if(level == 6){
            //continuing in free mode
            retrieveFromDatabase();
            freeModeShapeNum = randomGenerator.nextInt(4);
            shapeToMatch.setImageDrawable(greyShapeList.get(freeModeShapeNum).shapeResource);

            tempShapeList.clear();
            tempShapeList.add(firstShapeList.get(freeModeShapeNum));
            firstShapeList.remove(freeModeShapeNum.intValue());
            while(tempShapeList.size() <= (5)){
                int randomNumber = randomGenerator.nextInt(firstShapeList.size());
                tempShapeList.add(firstShapeList.get(randomNumber));
                firstShapeList.remove(randomNumber);
            }
            finalShapeList.clear();
            while(tempShapeList.size() > 0){
                int randomNumber = randomGenerator.nextInt(tempShapeList.size());
                finalShapeList.add(tempShapeList.get(randomNumber));
                tempShapeList.remove(randomNumber);
            }
            //assign image view resources to Object resources
            displayShapes(level);
        }
        //code for levels 1-5
        else if(shapeNumber < greyShapeList.size()){
            shapeToMatch.setImageDrawable(greyShapeList.get(shapeNumber).shapeResource);

            tempShapeList.clear();
            tempShapeList.add(firstShapeList.get(shapeNumber));
            firstShapeList.remove(shapeNumber.intValue());
            while(tempShapeList.size() <= (level)){
                int randomNumber = randomGenerator.nextInt(firstShapeList.size());
                tempShapeList.add(firstShapeList.get(randomNumber));
                firstShapeList.remove(randomNumber);
            }
            finalShapeList.clear();
            while(tempShapeList.size() > 0){
                int randomNumber = randomGenerator.nextInt(tempShapeList.size());
                finalShapeList.add(tempShapeList.get(randomNumber));
                tempShapeList.remove(randomNumber);
            }
            //assign image view resources to Object resources
            displayShapes(level);
        }
        //code to check if game is completed
        else if( level == 5 && shapeNumber == 6){
            level++;
            displayCompleteDialogue();
            myRef.child("hasCompleted").setValue(true);
            myRef.child("Level").setValue(level);
        }
        //code to move to next level
        else{
            if(level != 5){
                displayLevelDialog();
            }
            shapeNumber = 0;
            shapeToMatch.setImageDrawable(greyShapeList.get(shapeNumber).shapeResource);
            level ++;
            myRef.child("Level").setValue(level);
            retrieveFromDatabase();
            assignResources();
        }
    }
    /*    confirmMatch
       method to confirm if the shape that is being dragged, matches the shape in the middle
       Version 5  */
    private void confirmMatch() {
        //this method searches the Lists to find the Shape objects at the relevant index and compares to see if they match.
        if(level == 6)
            shapeNumber = freeModeShapeNum;
        if(greyShapeList.get(shapeNumber).shapeName == finalShapeList.get(index).shapeName) {
            shapeNumber ++;
            totalMatchCounter++;
            consecutiveCounter++;
            achievements();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    retrieveFromDatabase();
                    assignResources();
                    temp.setImageDrawable(null);
                    temp.setVisibility(View.INVISIBLE);

                }
            }, 2000);
        }
        else {
            Toast.makeText(this, "Incorrect Match", Toast.LENGTH_LONG).show();
            consecutiveCounter = 0;
            temp.setImageDrawable(null);
            temp.setVisibility(View.INVISIBLE);
        }
    }
    /*    setSameAbsoluteLocation
 method to drop the dragged image into the same image view as the shape to match
 Version 2  */
    private void setSameAbsoluteLocation(View v1, View v2) {
        AbsoluteLayout.LayoutParams alp2 = (AbsoluteLayout.LayoutParams) v2.getLayoutParams();
        setAbsoluteLocation(v1, alp2.x, alp2.y);
    }

    /*    setAbsoluteLocationCentered
    method to center the dropped image to match the outlined image
     Version 2  */
    private void setAbsoluteLocationCentered(View v, int x, int y) {
        setAbsoluteLocation(v, x - v.getWidth() / 2, y - v.getHeight() / 2);
    }

    /*    setAbsoluteLocation
    method to set the location of the dragged image
    Version 2  */
    private void setAbsoluteLocation(View v, int x, int y) {
        AbsoluteLayout.LayoutParams alp = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
        alp.x = x/1000;
        alp.y = y/1000;
        v.setLayoutParams(alp);
    }
    /*achievements
     * Method to display if the user has earned an achievement or if they have got 5 in a row
     * Version 5*/
    private void achievements(){
        mp.start();
        String msg = "";
        //Total shape match achievement check
        myRef.child("TotalAchievement").setValue(totalMatchCounter);
        if(totalMatchCounter == 10){
            msg = "Congratulations on making 10 matches.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        if(totalMatchCounter == 20){
            msg = "Congratulations on making 20 matches.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        if(totalMatchCounter == 30){
            msg ="Congratulations on making 30 matches.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        if(totalMatchCounter == 50){
            msg = "Congratulations on making 50 matches.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

        //Consecutive achievement check
        if(consecutiveCounter == 10 && achievementLevel < 1){
            msg = "Congratulations, You have earned the Consecutive Bronze medal";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("1");
            achievementLevel++;
        }
        else if(consecutiveCounter == 20 && achievementLevel < 2){
            msg = "Congratulations, You have earned the Consecutive Silver medal";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("2");
            achievementLevel++;
        }
        else if(consecutiveCounter == 30 && achievementLevel < 3){
            msg = "Congratulations, You have earned the Consecutive Gold medal";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("3");
            achievementLevel++;
        }
        else if(consecutiveCounter % 5==0){
            msg = "Well done! " + consecutiveCounter + " in a row!";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
    /*    displayShapes
    method to display the correct amount of shapes related to level number
    Version 5  */
    private void displayShapes(int lvl)   {
        if(lvl >= 1){
            shape1.setImageDrawable(finalShapeList.get(0).shapeResource);
            shape2.setImageDrawable(finalShapeList.get(1).shapeResource);
            //sets shape visibility
            shape3.setVisibility(View.INVISIBLE);
            shape4.setVisibility(View.INVISIBLE);
            shape5.setVisibility(View.INVISIBLE);
            shape6.setVisibility(View.INVISIBLE);
            //set on touch listeners for each Image View (Shape)
            shape1.setOnTouchListener(this);
            shape2.setOnTouchListener(this);
        }
        if(lvl >= 2 ){
            shape1.setImageDrawable(finalShapeList.get(0).shapeResource);
            shape2.setImageDrawable(finalShapeList.get(1).shapeResource);
            shape3.setImageDrawable(finalShapeList.get(2).shapeResource);
            shape3.setVisibility(View.VISIBLE);
            shape4.setVisibility(View.INVISIBLE);
            shape5.setVisibility(View.INVISIBLE);
            shape6.setVisibility(View.INVISIBLE);
            shape3.setOnTouchListener(this);
        }
        if(lvl >= 3 ){
            shape1.setImageDrawable(finalShapeList.get(0).shapeResource);
            shape2.setImageDrawable(finalShapeList.get(1).shapeResource);
            shape3.setImageDrawable(finalShapeList.get(2).shapeResource);
            shape4.setImageDrawable(finalShapeList.get(3).shapeResource);
            shape4.setVisibility(View.VISIBLE);
            shape5.setVisibility(View.INVISIBLE);
            shape6.setVisibility(View.INVISIBLE);
            shape4.setOnTouchListener(this);
        }
        if(lvl >= 4){
            shape1.setImageDrawable(finalShapeList.get(0).shapeResource);
            shape2.setImageDrawable(finalShapeList.get(1).shapeResource);
            shape3.setImageDrawable(finalShapeList.get(2).shapeResource);
            shape4.setImageDrawable(finalShapeList.get(3).shapeResource);
            shape5.setImageDrawable(finalShapeList.get(4).shapeResource);
            shape5.setVisibility(View.VISIBLE);
            shape6.setVisibility(View.INVISIBLE);
            shape5.setOnTouchListener(this);
        }
        if(lvl >= 5){
            shape1.setImageDrawable(finalShapeList.get(0).shapeResource);
            shape2.setImageDrawable(finalShapeList.get(1).shapeResource);
            shape3.setImageDrawable(finalShapeList.get(2).shapeResource);
            shape4.setImageDrawable(finalShapeList.get(3).shapeResource);
            shape5.setImageDrawable(finalShapeList.get(4).shapeResource);
            shape6.setImageDrawable(finalShapeList.get(5).shapeResource);
            shape6.setVisibility(View.VISIBLE);
            shape6.setOnTouchListener(this);
        }
    }
    /*    displayDialogue
  Method to display the dialogue once a game mode is completed to either start again or to quit
  Version 3 */
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
                assignResources();
                popUpDialog.dismiss();
            }
        });
        popUpDialog.show();
        freeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                retrieveFromDatabase();
                assignResources();
                popUpDialog.dismiss();
            }
        });
    }
    /*    displayLevelDialog
    Method to display the dialogue once a game mode is completed to either start again or to quit
    Version 4 */
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
                Toast.makeText(ShapeMain.this,
                        "The user will be taken back to the game menu activity",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    //TODO: Program Back Button to navigate back to home fragment
    //click to get back to home fragment
//    private void btnBackClicked() {
//        final Fragment homeFragment = new HomeFragment();
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, homeFragment)
//                        .commit();
//            }
//        });
//    }
}
