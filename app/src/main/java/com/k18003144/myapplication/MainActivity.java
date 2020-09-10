package com.k18003144.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    //    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        gameView = new GameView(this);
//        setContentView(gameView);

        //Firebase
        FirebaseDatabase database;
        final DatabaseReference myRef;
//            FirebaseUser currentUser;
//            FirebaseAuth mAuth;
        //Reading in Game Level from Firebase
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
//            mAuth = FirebaseAuth.getInstance();
//            currentUser = mAuth.getCurrentUser();
//            myRef = database.getReference("Users/" + currentUser.getUid() + "/Games/MatchingCards");
        myRef = database.getReference("Users/" + "Kyle" + "/Games/MatchingCards");

        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                UserGameState userProgress = dataSnapshot.getValue(UserGameState.class);
                if(userProgress == null){
                    userProgress = new UserGameState();
                    userProgress.setGameLevel("EASY");
                    myRef.child("GameLevel").setValue("EASY");
                }
                Toast.makeText(getBaseContext(), "Progress: " + userProgress.getGameLevel(), Toast.LENGTH_SHORT).show();
                GameView gameView = new GameView(getBaseContext(), userProgress);
                setContentView(gameView);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



    }

    class GetUserProgress extends AsyncTask<Void, Void, Void> {
        Context context;
        UserGameState userProgress;

        public GetUserProgress(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Firebase
            FirebaseDatabase database;
            final DatabaseReference myRef;
//            FirebaseUser currentUser;
//            FirebaseAuth mAuth;
            final UserGameState[] _userProgress = new UserGameState[1];
            //Reading in Game Level from Firebase
            //Firebase Declarations
            database = FirebaseDatabase.getInstance();
//            mAuth = FirebaseAuth.getInstance();
//            currentUser = mAuth.getCurrentUser();
//            myRef = database.getReference("Users/" + currentUser.getUid() + "/Games/MatchingCards");
            myRef = database.getReference("Users/" + "Kyle" + "/Games/MatchingCards");

            final Context _context = context;
            //Read Level
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    _userProgress[0] = dataSnapshot.getValue(UserGameState.class);
                    userProgress = _userProgress[0];
                    if(userProgress == null){
                        userProgress = new UserGameState();
                        userProgress.setGameLevel("EASY");
                        myRef.child("GameLevel").setValue("EASY");
                    }
                    Toast.makeText(_context, "Progress: " + userProgress.getGameLevel(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


        }
    }
}