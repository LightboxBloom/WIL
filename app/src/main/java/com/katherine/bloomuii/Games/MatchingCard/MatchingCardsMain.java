package com.katherine.bloomuii.Games.MatchingCard;//package com.k18003144.myapplication;
////package com.katherine.bloomuii.Games.MatchingCard;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.katherine.bloomuii.Games.MatchingCard.UserGameState;
//
//import org.jetbrains.annotations.NotNull;
//
//public class MatchingCardsMain extends AppCompatActivity {
////    GameView gameView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        gameView = new GameView(this);
//
////        setContentView(gameView);
//
//        new GetUserProgress(this).execute();
//
//    }
//
//    class GetUserProgress extends AsyncTask<Void, Void, Void>{
//
//        Context context;
//        UserGameState userProgress;
//
//        public GetUserProgress(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            //Firebase
//            FirebaseDatabase database;
//            DatabaseReference myRef;
//            FirebaseUser currentUser;
//            FirebaseAuth mAuth;
//            final UserGameState[] _userProgress = new UserGameState[1];
//
//            //TODO: Kyle please check out reading and writing level instant to Firebase - getting some null errors for the class UserGameState
//            //TODO: Since new users have no GameUserState branch in firebase db. I need a new set of eyes on this.
//            //TODO: Scroll further down for Promotion of level and update written to Firebase (Promotion isnt tested yet)
//            //**********************************************************************************************************************************
//            //Reading in Game Level from Firebase
//            //Firebase Declarations
//            database = FirebaseDatabase.getInstance();
//            mAuth = FirebaseAuth.getInstance();
//            currentUser = mAuth.getCurrentUser();
//            myRef = database.getReference("Users/" + currentUser.getUid() + "/Games/MatchingCards");
//
//            final Context _context = context;
//            //Read Level
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
//                    _userProgress[0] = dataSnapshot.getValue(UserGameState.class);
//                    userProgress = _userProgress[0];
//                    Toast.makeText(_context, "Progress: "+ _userProgress[0].getGame_Level(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NotNull DatabaseError databaseError) {
//                    System.out.println("The read failed: " + databaseError.getCode());
//                }
//            });
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            GameView gameView = new GameView(this.context, userProgress);
//            setContentView(gameView);
//        }
//    }
//
//}
//
//
