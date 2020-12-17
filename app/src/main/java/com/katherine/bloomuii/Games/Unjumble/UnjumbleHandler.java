package com.katherine.bloomuii.Games.Unjumble;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Games.Math.MathFragment;
import com.katherine.bloomuii.Games.Order.OrderFragment;
import com.katherine.bloomuii.ObjectClasses.Sentence;

public class UnjumbleHandler {

    public static Sentence counter = new Sentence();//used to create an array of sentences that is the correct size
    //public static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Flashcards").child("Test");
    public static DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference().child("Flashcards").child("Uploads");
    //public static DatabaseReference userTestRef = FirebaseDatabase.getInstance().getReference().child("userTest");
    //public static DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference();

    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    public static FirebaseUser currentUser;
    public static FirebaseAuth mAuth;


    //UNCOMMENT currentUser.getUid WHEN INTEGRATED IN FULL APPLICATION, THEN COMMENT OUT OR REMOVE userKey = ""
    public static String userKey = "";
    //public static String userKey = currentUser.getUid();
    public static void getSetUserLevel(){
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/Unjumble");
        myRef.keepSynced(true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //level check
                if (!snapshot.child("Level").exists()) {
                    //myRef.child("Level").setValue(1);
                    //MathFragment.levelNumber = Integer.valueOf(snapshot.child("Level").getValue().toString());
                    UnjumbleFragment.testNumber = 1;
                    //MathFragment.sumType();
                }
                else if (OrderFragment.levelNumber == -100){
                    UnjumbleFragment.testNumber = Integer.valueOf(snapshot.child("Level").getValue().toString());
                    //MathFragment.sumType();
                }
                UnjumbleFragment.textViews[1].setText("Current Level: " + UnjumbleFragment.testNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*        if(userKey == "") {
            userTestRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (UnjumbleFragment.testNumber == -100) { //get user's level from database
                        UnjumbleFragment.testNumber = Integer.parseInt(String.valueOf(snapshot.child("userLevel").getValue()));
                        //FirebaseData();
                    } else if (UnjumbleFragment.testNumber > UnjumbleHandler.counter.getCount()) { //Indicates that all levels are completed
                        UnjumbleFragment.shuffle(Sentence.sentenceArray[UnjumbleFragment.testNumber - 2]);
                        UnjumbleFragment.hideButtons();
                        UnjumbleFragment.buttons[7].setEnabled(true);
                        userTestRef.child("userLevel").setValue(UnjumbleFragment.testNumber);
                        UnjumbleFragment.textViews[0].setText("Well Done!");
                        UnjumbleFragment.textViews[1].setText("All levels completed!");
                    } else { //change user's level in database
                        userTestRef.child("userLevel").setValue(UnjumbleFragment.testNumber);
                        UnjumbleFragment.textViews[1].setText("Current Level: " + UnjumbleFragment.testNumber); //displays user's current level
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }*/
/*        else{
            baseRef.child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (UnjumbleFragment.testNumber == -100) { //get user's level from database
                        UnjumbleFragment.testNumber = Integer.parseInt(String.valueOf(snapshot.child("userLevel").getValue()));
                        FirebaseData();
                    } else if (UnjumbleFragment.testNumber > UnjumbleHandler.counter.getCount()) { //Indicates that all levels are completed
                        UnjumbleFragment.shuffle(Sentence.sentenceArray[UnjumbleFragment.testNumber - 2]);
                        UnjumbleFragment.hideButtons();
                        UnjumbleFragment.buttons[7].setEnabled(true);
                        baseRef.child(userKey).child("userLevel").setValue(UnjumbleFragment.testNumber);
                        UnjumbleFragment.textViews[0].setText("Well Done!");
                        UnjumbleFragment.textViews[1].setText("All levels completed!");
                    } else { //change user's level in database
                        baseRef.child(userKey).child("userLevel").setValue(UnjumbleFragment.testNumber);
                        UnjumbleFragment.textViews[1].setText("Current Level: " + UnjumbleFragment.testNumber); //displays user's current level
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }*/
    }
    //CHANGE FIREBASE DATA, USE NEW DB REFERENCE FOR THE FLASHCARDS AND RETRIEVE ALL CHILD SENTENCES FROM THE CHILD OF UPLOADS
    public static void FirebaseData() {

        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //UnjumbleFragment.textViews[0].setText("SNAPSHOT EXISTS");

                    counter.setCount((int) snapshot.getChildrenCount()); //counter is set to the number of sentences in the db

                    Sentence.arrayCreate(counter.getCount() + 12); //arrayCreate method is called
                    //Default Unjumble Sentences
                    Sentence.sentenceArray[0].add("The");
                    Sentence.sentenceArray[0].add("dog");
                    Sentence.sentenceArray[0].add("barks");

                    Sentence.sentenceArray[1].add("The");
                    Sentence.sentenceArray[1].add("cat");
                    Sentence.sentenceArray[1].add("meows");

                    Sentence.sentenceArray[2].add("He");
                    Sentence.sentenceArray[2].add("was");
                    Sentence.sentenceArray[2].add("happy");

                    Sentence.sentenceArray[3].add("She");
                    Sentence.sentenceArray[3].add("kicked");
                    Sentence.sentenceArray[3].add("a");
                    Sentence.sentenceArray[3].add("ball");

                    Sentence.sentenceArray[4].add("The");
                    Sentence.sentenceArray[4].add("leaves");
                    Sentence.sentenceArray[4].add("are");
                    Sentence.sentenceArray[4].add("green");

                    Sentence.sentenceArray[5].add("An");
                    Sentence.sentenceArray[5].add("elephant");
                    Sentence.sentenceArray[5].add("was");
                    Sentence.sentenceArray[5].add("walking");

                    Sentence.sentenceArray[6].add("She");
                    Sentence.sentenceArray[6].add("is");
                    Sentence.sentenceArray[6].add("a");
                    Sentence.sentenceArray[6].add("kind");
                    Sentence.sentenceArray[6].add("girl");

                    Sentence.sentenceArray[7].add("He");
                    Sentence.sentenceArray[7].add("saw");
                    Sentence.sentenceArray[7].add("a");
                    Sentence.sentenceArray[7].add("big");
                    Sentence.sentenceArray[7].add("cow");

                    Sentence.sentenceArray[8].add("The");
                    Sentence.sentenceArray[8].add("fat");
                    Sentence.sentenceArray[8].add("cat");
                    Sentence.sentenceArray[8].add("ate");
                    Sentence.sentenceArray[8].add("fish");

                    Sentence.sentenceArray[9].add("The");
                    Sentence.sentenceArray[9].add("small");
                    Sentence.sentenceArray[9].add("cat");
                    Sentence.sentenceArray[9].add("was");
                    Sentence.sentenceArray[9].add("being");
                    Sentence.sentenceArray[9].add("chased");

                    Sentence.sentenceArray[10].add("He");
                    Sentence.sentenceArray[10].add("was");
                    Sentence.sentenceArray[10].add("carrying");
                    Sentence.sentenceArray[10].add("a");
                    Sentence.sentenceArray[10].add("big");
                    Sentence.sentenceArray[10].add("bag");

                    Sentence.sentenceArray[11].add("She");
                    Sentence.sentenceArray[11].add("was");
                    Sentence.sentenceArray[11].add("reading");
                    Sentence.sentenceArray[11].add("an");
                    Sentence.sentenceArray[11].add("interesting");
                    Sentence.sentenceArray[11].add("book");


                    int i = 11; //used for loop to assign word into different sentence lists

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) { //after a sentence's words are assigned, the next sentence is filled
                        i++;
                        String s;
                        s = dataSnapshot.child("Sentence").getValue().toString();
                        String[] words = s.split(" ");
                        for (String word : words) {
                            Sentence.sentenceArray[i].add(word);
                        }

                        //shuffle happens after sentences have words to avoid crashing,
                        //submit and restart buttons are only enabled once the firebase data has been pulled (this stops the user from skipping levels before the data loads)
                        if (UnjumbleFragment.testNumber <= UnjumbleHandler.counter.getCount() + 12) {
                            UnjumbleFragment.shuffle(Sentence.sentenceArray[UnjumbleFragment.testNumber - 1]);
                            UnjumbleFragment.buttons[6].setEnabled(true);
                            UnjumbleFragment.buttons[7].setEnabled(true);
                        } else {
                            //getSetUserLevel();
                            UnjumbleFragment.textViews[0].setText("Well Done!");
                            UnjumbleFragment.textViews[1].setText("All levels completed! Click restart if you want to start at the beginning");
                            UnjumbleFragment.buttons[7].setEnabled(true);
                        }
                    }
                }
                else {
                    //UnjumbleFragment.textViews[0].setText("SNAPSHOT DOES NOT EXIST");
                    counter.setCount(0);

                    Sentence.arrayCreate(12); //arrayCreate method is called
                    //Default Unjumble Sentences
                    Sentence.sentenceArray[0].add("The");
                    Sentence.sentenceArray[0].add("dog");
                    Sentence.sentenceArray[0].add("barks");

                    Sentence.sentenceArray[1].add("The");
                    Sentence.sentenceArray[1].add("cat");
                    Sentence.sentenceArray[1].add("meows");

                    Sentence.sentenceArray[2].add("He");
                    Sentence.sentenceArray[2].add("was");
                    Sentence.sentenceArray[2].add("happy");

                    Sentence.sentenceArray[3].add("She");
                    Sentence.sentenceArray[3].add("kicked");
                    Sentence.sentenceArray[3].add("a");
                    Sentence.sentenceArray[3].add("ball");

                    Sentence.sentenceArray[4].add("The");
                    Sentence.sentenceArray[4].add("leaves");
                    Sentence.sentenceArray[4].add("are");
                    Sentence.sentenceArray[4].add("green");

                    Sentence.sentenceArray[5].add("An");
                    Sentence.sentenceArray[5].add("elephant");
                    Sentence.sentenceArray[5].add("was");
                    Sentence.sentenceArray[5].add("walking");

                    Sentence.sentenceArray[6].add("She");
                    Sentence.sentenceArray[6].add("is");
                    Sentence.sentenceArray[6].add("a");
                    Sentence.sentenceArray[6].add("kind");
                    Sentence.sentenceArray[6].add("girl");

                    Sentence.sentenceArray[7].add("He");
                    Sentence.sentenceArray[7].add("saw");
                    Sentence.sentenceArray[7].add("a");
                    Sentence.sentenceArray[7].add("big");
                    Sentence.sentenceArray[7].add("cow");

                    Sentence.sentenceArray[8].add("The");
                    Sentence.sentenceArray[8].add("fat");
                    Sentence.sentenceArray[8].add("cat");
                    Sentence.sentenceArray[8].add("ate");
                    Sentence.sentenceArray[8].add("fish");

                    Sentence.sentenceArray[9].add("The");
                    Sentence.sentenceArray[9].add("small");
                    Sentence.sentenceArray[9].add("cat");
                    Sentence.sentenceArray[9].add("was");
                    Sentence.sentenceArray[9].add("being");
                    Sentence.sentenceArray[9].add("chased");

                    Sentence.sentenceArray[10].add("He");
                    Sentence.sentenceArray[10].add("was");
                    Sentence.sentenceArray[10].add("carrying");
                    Sentence.sentenceArray[10].add("a");
                    Sentence.sentenceArray[10].add("big");
                    Sentence.sentenceArray[10].add("bag");

                    Sentence.sentenceArray[11].add("She");
                    Sentence.sentenceArray[11].add("was");
                    Sentence.sentenceArray[11].add("reading");
                    Sentence.sentenceArray[11].add("an");
                    Sentence.sentenceArray[11].add("interesting");
                    Sentence.sentenceArray[11].add("book");

                    if (UnjumbleFragment.testNumber <= 12) {
                        UnjumbleFragment.shuffle(Sentence.sentenceArray[UnjumbleFragment.testNumber - 1]);
                        UnjumbleFragment.buttons[6].setEnabled(true);
                        UnjumbleFragment.buttons[7].setEnabled(true);
                    } else {
                        //getSetUserLevel();
                        UnjumbleFragment.textViews[0].setText("Well Done!");
                        UnjumbleFragment.textViews[1].setText("All levels completed! Click restart if you want to start at the beginning");
                        UnjumbleFragment.buttons[7].setEnabled(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counter.setCount((int) snapshot.getChildrenCount()); //counter is set to the number of sentences in the db

                Sentence.arrayCreate(counter.getCount() + 12); //arrayCreate method is called
                //Default Unjumble Sentences
                Sentence.sentenceArray[0].add("The");
                Sentence.sentenceArray[0].add("dog");
                Sentence.sentenceArray[0].add("barks");

                Sentence.sentenceArray[1].add("The");
                Sentence.sentenceArray[1].add("cat");
                Sentence.sentenceArray[1].add("meows");

                Sentence.sentenceArray[2].add("He");
                Sentence.sentenceArray[2].add("was");
                Sentence.sentenceArray[2].add("happy");

                Sentence.sentenceArray[3].add("She");
                Sentence.sentenceArray[3].add("kicked");
                Sentence.sentenceArray[3].add("a");
                Sentence.sentenceArray[3].add("ball");

                Sentence.sentenceArray[4].add("The");
                Sentence.sentenceArray[4].add("leaves");
                Sentence.sentenceArray[4].add("are");
                Sentence.sentenceArray[4].add("green");

                Sentence.sentenceArray[5].add("An");
                Sentence.sentenceArray[5].add("elephant");
                Sentence.sentenceArray[5].add("was");
                Sentence.sentenceArray[5].add("walking");

                Sentence.sentenceArray[6].add("She");
                Sentence.sentenceArray[6].add("is");
                Sentence.sentenceArray[6].add("a");
                Sentence.sentenceArray[6].add("kind");
                Sentence.sentenceArray[6].add("girl");

                Sentence.sentenceArray[7].add("He");
                Sentence.sentenceArray[7].add("saw");
                Sentence.sentenceArray[7].add("a");
                Sentence.sentenceArray[7].add("big");
                Sentence.sentenceArray[7].add("cow");

                Sentence.sentenceArray[8].add("The");
                Sentence.sentenceArray[8].add("fat");
                Sentence.sentenceArray[8].add("cat");
                Sentence.sentenceArray[8].add("ate");
                Sentence.sentenceArray[8].add("fish");

                Sentence.sentenceArray[9].add("The");
                Sentence.sentenceArray[9].add("small");
                Sentence.sentenceArray[9].add("cat");
                Sentence.sentenceArray[9].add("was");
                Sentence.sentenceArray[9].add("being");
                Sentence.sentenceArray[9].add("chased");

                Sentence.sentenceArray[10].add("He");
                Sentence.sentenceArray[10].add("was");
                Sentence.sentenceArray[10].add("carrying");
                Sentence.sentenceArray[10].add("a");
                Sentence.sentenceArray[10].add("big");
                Sentence.sentenceArray[10].add("bag");

                Sentence.sentenceArray[11].add("She");
                Sentence.sentenceArray[11].add("was");
                Sentence.sentenceArray[11].add("reading");
                Sentence.sentenceArray[11].add("an");
                Sentence.sentenceArray[11].add("interesting");
                Sentence.sentenceArray[11].add("book");

                int i = 11; //used for loop to assign word into different sentence lists

                *//*for (DataSnapshot dataSnapshot : snapshot.getChildren()) { //after a sentence's words are assigned, the next sentence is filled
                    i++;
                    for (DataSnapshot child : dataSnapshot.getChildren())  //each word in the database is added to a list
                    {
                        String s;
                        s = Objects.requireNonNull(child.getValue()).toString();
                        Sentence.sentenceArray[i].add(s);

                    }*//*
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) { //after a sentence's words are assigned, the next sentence is filled
                    i++;
                    String s;
                    s = dataSnapshot.child("Sentence").getValue().toString();
                    String[] words = s.split(" ");
                    for (String word: words)
                    {
                        Sentence.sentenceArray[i].add(word);
                    }

                    //shuffle happens after sentences have words to avoid crashing,
                    //submit and restart buttons are only enabled once the firebase data has been pulled (this stops the user from skipping levels before the data loads)
                    if (UnjumbleFragment.testNumber <= UnjumbleHandler.counter.getCount() + 12) {
                        UnjumbleFragment.shuffle(Sentence.sentenceArray[UnjumbleFragment.testNumber - 1]);
                        UnjumbleFragment.buttons[6].setEnabled(true);
                        UnjumbleFragment.buttons[7].setEnabled(true);
                    }
                    else {
                        //getSetUserLevel();
                        UnjumbleFragment.textViews[0].setText("Well Done!");
                        UnjumbleFragment.textViews[1].setText("All levels completed! Click restart if you want to start at the beginning");
                        UnjumbleFragment.buttons[7].setEnabled(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/
    }
}
