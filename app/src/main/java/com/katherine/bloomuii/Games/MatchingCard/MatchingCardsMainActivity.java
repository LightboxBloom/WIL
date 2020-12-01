package com.katherine.bloomuii.Games.MatchingCard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MatchingCardsMainActivity extends AppCompatActivity {
    //    GameView gameView;
    ArrayList<Flashcard> gameFlashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.matching_cards_loading);

        //Firebase
        final FirebaseDatabase database;
        final DatabaseReference levelReference;
        final FirebaseUser currentUser;
        FirebaseAuth mAuth;
        //Reading in Game Level from Firebase
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        levelReference = database.getReference("Users/" + currentUser.getUid() + "/Games/MatchingCards");
//        levelReference = database.getReference("Users/" + "Kyle" + "/Games/MatchingCards");

        //Read Level
        levelReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                UserGameState userProgress = dataSnapshot.getValue(UserGameState.class);
                if (userProgress == null) {
                    userProgress = new UserGameState();
                    userProgress.setLevel("EASY");
                    userProgress.setAchievement(0);
                    levelReference.child("Level").setValue("EASY");
                    levelReference.child("Achievement").setValue(0);
                    levelReference.child("ConsecutiveMatches").setValue(0);
                }

                final UserGameState finalUserProgress = userProgress;
                final DatabaseReference joinedClassroomsReference = database.getReference("Users/" + currentUser.getUid() + "/JoinedClassrooms");
                joinedClassroomsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList<Long> classroomIds = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Classroom classroom = snapshot.getValue(Classroom.class);
                            classroomIds.add((long) classroom.getClassroom_Id());
                        }

                        final DatabaseReference myClassroomsReference = database.getReference("Users/" + currentUser.getUid() + "/MyClassrooms");
                        myClassroomsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Classroom classroom = snapshot.getValue(Classroom.class);
                                    classroomIds.add((long) classroom.getClassroom_Id());
                                }

                                final DatabaseReference flashcardsReference = database.getReference("Flashcards/Uploads");
                                flashcardsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        ArrayList<FlashcardUpload> userUploads = new ArrayList<>();
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            FlashcardUpload flashcardUpload = snapshot.getValue(FlashcardUpload.class);
                                            if(classroomIds.contains(flashcardUpload.getClassroomId())){
                                                userUploads.add(flashcardUpload);
                                            }
                                        }
                                        try {
                                            new DownloadBitmaps().execute(userUploads).get();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        GameView gameView = new GameView(getBaseContext(), finalUserProgress, gameFlashcards);
                                        setContentView(gameView);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

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

            class DownloadBitmaps extends AsyncTask<ArrayList<FlashcardUpload>, Void, Void> {

                @Override
                protected Void doInBackground(ArrayList<FlashcardUpload>... lists) {
                    ArrayList<FlashcardUpload> flashcards = lists[0];
                    ArrayList<FlashcardUpload> flashcardsToDownload;
                    if (flashcards.size() <= 4) {
                        flashcardsToDownload = flashcards;
                    } else {
                        flashcardsToDownload = getFlashcardsToDownload(flashcards);
                    }
                    ArrayList<Flashcard> downloadedFlashcards = new ArrayList<>();

                    for (FlashcardUpload flashcardUpload : flashcardsToDownload) {
                        Flashcard flashcard = new Flashcard();
                        for (String uri : flashcardUpload.getStorageUris()) {
                            try {
                                URL url = new URL(uri);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(input);
                                flashcard.addBitmap(bitmap);
                                connection.disconnect();
                            } catch (IOException e) {
                                // Log exception
                                return null;
                            }
                        }
                        downloadedFlashcards.add(flashcard);
                    }

                    for (Flashcard flashcard : downloadedFlashcards) {
                        flashcard.addBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.blank));
                    }

                    gameFlashcards = downloadedFlashcards;
                    return null;
                }

                private ArrayList<FlashcardUpload> getFlashcardsToDownload(ArrayList<FlashcardUpload> flashcards) {
                    Collections.shuffle(flashcards);
                    ArrayList<FlashcardUpload> outputFlashcards = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        outputFlashcards.add(flashcards.get(i));
                    }
                    return outputFlashcards;
                }
            }

        }
