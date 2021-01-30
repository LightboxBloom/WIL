package com.katherine.bloomuii.Games.PhotoLabel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.katherine.bloomuii.Fragments.HomeFragment;
import com.katherine.bloomuii.Games.MatchShape.MatchShapesActivity;
import com.katherine.bloomuii.Games.PartOfSpeech.Word;
import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PhotoLabelMenu extends AppCompatActivity implements  View.OnTouchListener{
    TextView temp, selectedlabel,label1, label2, label3, label4, label5, label6;
    ImageView photo1Img, photo1Match, photo2Img, photo2Match, photo3Img, photo3Match, photo4Img,
            photo4Match, photo5Img, photo5Match, photo6Img, photo6Match;
    private AbsoluteLayout mainLayout;
    private Random randomGenerator = new Random();
    private int index, consecutiveCounter, totalMatchCounter, answerCounter, achievementLevel;
    private boolean dragging = false;
    private Rect photo1HitRect = new Rect();
    private Rect photo2HitRect = new Rect();
    private Rect photo3HitRect = new Rect();
    private Rect photo4HitRect = new Rect();
    private Rect photo5HitRect = new Rect();
    private Rect photo6HitRect = new Rect();
    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    //media player
    MediaPlayer mp;

    //text to speech
    private TextToSpeech speech;
    List<String> originalLabelArray = new ArrayList();
    List<String> labelArray_six = new ArrayList();
    List<String> labelArray = new ArrayList();
    List<PhotoLabel> originalPhotoArray = new ArrayList();
    List<PhotoLabel> photoArray_six = new ArrayList();
    List<PhotoLabel> photoArray = new ArrayList();
    final Handler handler = new Handler();
    ImageView btnBack;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_label);
        mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //Media player
        mp = MediaPlayer.create(this, R.raw.popcorn);

        speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result =speech.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(PhotoLabelMenu.this, "Language not supported",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        retrieveFromDatabase();
        declareComponents();
        declareOntouchListeners();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1s = 1000ms
                randomiseLists();
                assignImages();
            }
        }, 1000);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBackClicked();

    }
    //click to get back to home fragment
    private void btnBackClicked() {
        final Fragment homeFragment = new HomeFragment();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoLabelMenu.this.onBackPressed();
            }
        });
    }
    //Logic for click, drag and drop for a label
    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
            if (v == label1){
                temp.setText(label1.getText());
                dragging = true;
                selectedlabel = label1;
                index = 0;
                speech.speak(labelArray.get(index), TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == label2){
                temp.setText(label2.getText());
                dragging = true;
                selectedlabel = label2;
                index = 1;
                speech.speak(labelArray.get(index), TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == label3) {
                temp.setText(label3.getText());
                dragging = true;
                selectedlabel = label3;
                index = 2;
                speech.speak(labelArray.get(index), TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == label4){
                temp.setText(label4.getText());
                dragging = true;
                selectedlabel = label4;
                index = 3;
                speech.speak(labelArray.get(index), TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == label5){
                temp.setText(label5.getText());
                selectedlabel = label5;
                dragging = true;
                index = 4;
                speech.speak(labelArray.get(index), TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == label6){
                temp.setText(label6.getText());
                selectedlabel = label6;
                dragging = true;
                index = 5;
                speech.speak(labelArray.get(index), TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
            else if (v == temp){
                dragging = true;
                speech.speak(labelArray.get(index), TextToSpeech.QUEUE_ADD,null);
                eventConsumed = false;
                temp.setVisibility(View.VISIBLE);
            }
        }
        if (action == MotionEvent.ACTION_UP) {
            if (dragging) {
                photo1Match.getHitRect(photo1HitRect);
                photo2Match.getHitRect(photo2HitRect);
                photo3Match.getHitRect(photo3HitRect);
                photo4Match.getHitRect(photo4HitRect);
                photo5Match.getHitRect(photo5HitRect);
                photo6Match.getHitRect(photo6HitRect);

                if (photo1HitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp,photo1Match );
                    confirmMatch(0,photo1HitRect, temp);
                }
                else if (photo2HitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp,photo2Match );
                    confirmMatch(1,photo2HitRect, temp);
                }
                else if (photo3HitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, photo3Match);
                    confirmMatch(2, photo3HitRect, temp);
                }
                else if (photo4HitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, photo4Match);
                    confirmMatch(3, photo4HitRect, temp);
                }
                else if (photo5HitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, photo5Match);
                    confirmMatch(4, photo5HitRect, temp);
                }
                else if (photo6HitRect.contains(x, y)) {
                    //when you drop shape into outline
                    setSameAbsoluteLocation(temp, photo6Match);
                    confirmMatch(5, photo6HitRect, temp);
                }
                else{
                    Toast.makeText(this,
                            "Drag the label to the side of an image", Toast.LENGTH_SHORT).show();
                    temp.setText(null);
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
    //declares all components to be used
    private void declareComponents(){
        temp= findViewById(R.id.temp);
        label1= findViewById(R.id.label1);
        label2= findViewById(R.id.label2);
        label3 = findViewById(R.id.label3);
        label4 = findViewById(R.id.label4);
        label5 = findViewById(R.id.label5);
        label6 = findViewById(R.id.label6);
        photo1Img = findViewById(R.id.photo1Img);
        photo1Match = findViewById(R.id.photo1Match);
        photo2Img = findViewById(R.id.photo2Img);
        photo2Match = findViewById(R.id.photo2Match);
        photo3Img = findViewById(R.id.photo3Img);
        photo3Match = findViewById(R.id.photo3Match);
        photo4Img = findViewById(R.id.photo4Img);
        photo4Match = findViewById(R.id.photo4Match);
        photo5Img = findViewById(R.id.photo5Img);
        photo5Match = findViewById(R.id.photo5Match);
        photo6Img = findViewById(R.id.photo6Img);
        photo6Match = findViewById(R.id.photo6Match);
    }
    //method to load the default labels and images
    //retrieves data from firebase
    private void retrieveFromDatabase(){
        originalLabelArray.clear();
        originalPhotoArray.clear();


        database = FirebaseDatabase.getInstance();
        /*Code used to retrieve the photo's and labels from the users specific class*/
        DatabaseReference joinedClassroomsReference = database.getReference("Users/" + currentUser.getUid() + "/JoinedClassrooms");
        joinedClassroomsReference.keepSynced(true);
        joinedClassroomsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Long> classroomIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Classroom classroom = snapshot.getValue(Classroom.class);
                    classroomIds.add((long) classroom.getClassroom_Id());
                }

                final DatabaseReference myClassroomsReference = database.getReference("Users/" + currentUser.getUid() + "/MyClassrooms");
                myClassroomsReference.keepSynced(true);
                myClassroomsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Classroom classroom = snapshot.getValue(Classroom.class);
                            classroomIds.add((long) classroom.getClassroom_Id());
                        }

                        final DatabaseReference photoLabel = database.getReference("PhotoLabel");
                        photoLabel.keepSynced(true);
                        photoLabel.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if(classroomIds.contains(data.child("ClassroomId").getValue()) && data.child("Word").exists() && data.child("Photo").exists() || data.child("Default").exists()){
                                        Log.d("TAG", "Found: ");
                                        String word = data.child("Word").getValue().toString();
                                        String photo = data.child("Photo").getValue().toString();
                                        originalLabelArray.add(word);
                                        originalPhotoArray.add(new PhotoLabel(word, photo));
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    //creates local list so that the user only uses data once and the logic to randomize the list
    private void randomiseLists(){
        List<String> tempLabelList = new ArrayList<>();
        List<PhotoLabel> tempPhotoList = new ArrayList();
        for(int i = 0; i < originalLabelArray.size(); i++){
            tempLabelList.add(originalLabelArray.get(i));
            tempPhotoList.add(originalPhotoArray.get(i));
        }

        labelArray_six.clear();
        photoArray_six.clear();
        while(labelArray_six.size() < 6){
            int ran = randomGenerator.nextInt(tempLabelList.size());
            labelArray_six.add(tempLabelList.get(ran));
            photoArray_six.add(tempPhotoList.get(ran));
            tempLabelList.remove(ran);
            tempPhotoList.remove(ran);
        }

        labelArray.clear();
        photoArray.clear();

        for (int i=0; i < 6; i++){
            int ran1 = randomGenerator.nextInt(labelArray_six.size());
            labelArray.add(labelArray_six.get(ran1));
            labelArray_six.remove(ran1);
            int ran2 = randomGenerator.nextInt(photoArray_six.size());
            photoArray.add(photoArray_six.get(ran2));
            photoArray_six.remove(ran2);
        }
    }
    //logic to assign images and labels
    private void assignImages(){
        TextView [] labelImageViews= new TextView[]{label1, label2, label3, label4, label5, label6};
        ImageView [] photoImageViews = new ImageView[]{ photo1Img,photo2Img,photo3Img,photo4Img,photo5Img,photo6Img };
        ImageView [] matchingImageViews = new ImageView[]{ photo1Match, photo2Match, photo3Match, photo4Match, photo5Match, photo6Match };
        for (int i = 0; i < labelArray.size(); i++) {
            // Load the image using Glide
            labelImageViews[i].setText(labelArray.get(i));

            Glide.with(this)
                    .load(photoArray.get(i).photo)
                    .into(photoImageViews[i]);https:
            Glide.with(this)
                    .load(R.drawable.matchboxs)
                    .into(matchingImageViews[i]);
        }
    }
    //logic to declare onTouch listenera
    private void declareOntouchListeners(){
        label1.setOnTouchListener(this);
        label2.setOnTouchListener(this);
        label3.setOnTouchListener(this);
        label4.setOnTouchListener(this);
        label5.setOnTouchListener(this);
        label6.setOnTouchListener(this);
    }
    private void setSameAbsoluteLocation(View v1, View v2) {
        AbsoluteLayout.LayoutParams alp2 = (AbsoluteLayout.LayoutParams) v2.getLayoutParams();
        setAbsoluteLocation(v1, alp2.x, alp2.y);
    }
    private void setAbsoluteLocationCentered(View v, int x, int y) {
        setAbsoluteLocation(v, x - v.getWidth() / 2, y - v.getHeight() / 2);
    }
    private void setAbsoluteLocation(View v, int x, int y) {
        AbsoluteLayout.LayoutParams alp = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
        alp.x = x/1000;
        alp.y = y/1000;
        v.setLayoutParams(alp);
    }
    //Logic to confirm if the dropped image is a match to the corresponding image
    private void confirmMatch(int photoIndex, Rect rect, TextView image){
        if(labelArray.get(index).equals(photoArray.get(photoIndex).label)){
            if(mp.isPlaying()){
                mp.stop();
                mp.reset();
            }
            mp = MediaPlayer.create(this, R.raw.popcorn);
            mp.start();
            updateImageViews(rect, image);
            consecutiveCounter++;
            totalMatchCounter++;
            answerCounter++;
            achievements();
            updateActivity();
            temp.setVisibility(View.INVISIBLE);
        }
        else{
            Toast.makeText(this, "No Match", Toast.LENGTH_SHORT).show();
            consecutiveCounter=0;
            temp.setVisibility(View.INVISIBLE);
        }
    }
    //logic to update achievements for each user
    private void achievements(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/PhotoLabelling");
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
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Bronze medal",
                    Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("1");
            achievementLevel++;
        }
        else if(consecutiveCounter == 20 && achievementLevel < 2){
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Silver medal",
                    Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("2");
            achievementLevel++;
        }
        else if(consecutiveCounter == 30 && achievementLevel < 3){
            Toast.makeText(this, "Congratulations, You have earned the Consecutive Gold medal",
                    Toast.LENGTH_SHORT).show();
            myRef.child("ConsecutiveAchievement").setValue("3");
            achievementLevel++;
        }
        else if(consecutiveCounter % 5==0)
            Toast.makeText(this, "Well done! " + consecutiveCounter + " in a row!",
                    Toast.LENGTH_SHORT).show();
    }
    //Logic to update the activity after all photos are matched correctly
    private void updateActivity(){
        if(answerCounter == 6){
            setContentView(R.layout.activity_photo_label);
            mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
            mainLayout.setOnTouchListener(this);
            declareComponents();
            randomiseLists();
            assignImages();
            declareOntouchListeners();
            answerCounter = 0;
        }
    }
    //Logic to center the label in the drop zone after a correct match is made.
    private void updateImageViews(Rect rect, TextView image){
        float x, y;
        x = rect.centerX()  - image.getWidth()/2;
        y = rect.centerY() - image.getHeight()/2;
        selectedlabel.setX(x);
        selectedlabel.setY(y);
        selectedlabel.setOnTouchListener(null);
        temp.setText(null);
        temp.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        speech.shutdown();
        super.onDestroy();
    }
}