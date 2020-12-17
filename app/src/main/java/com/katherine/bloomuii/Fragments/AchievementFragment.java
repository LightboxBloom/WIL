package com.katherine.bloomuii.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.katherine.bloomuii.Games.PhotoLabel.PhotoLabel;
import com.katherine.bloomuii.ObjectClasses.DiaryEntry;
import com.katherine.bloomuii.R;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AchievementFragment extends Fragment {
    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //components
    ImageView mBack, shapesFirst, shapesConsTen, shapesConsTwenty, shapesTotTwenty, shapeTotFifty ,
            shapesTotHundred, shapesTotMaster, diaryFirst, amDiary, begDiary, intDiary, masterDiary,
            labelFirst, labelConsTen, labelConsTwenty, labelTotTwenty, labelTotFifty, labelTotHundred, labelMaster,
            posFirst, posConsTen, posConsTwenty, posTotTwenty, posTotFifty, posTotHundred, posMaster,
            cardFirst, cardConsTen, cardConsTwenty, cardTotTwenty, cardTotFifty, cardTotHundred, cardMaster,
            puzzleFirst,puzzleTotTwenty, puzzleTotFifty, puzzleTotHundred, puzzleMaster,
            orderConsTen, orderConsTwenty, orderFirst,orderTotTwenty, orderTotFifty, orderTotHundred, orderMaster,
            unjumbleConsTen, unjumbleConsTwenty, unjumbleFirst,unjumbleTotTwenty, unjumbleTotFifty, unjumbleTotHundred, unjumbleMaster,
            mathConsTen, mathConsTwenty, mathFirst,mathTotTwenty, mathTotFifty, mathTotHundred, mathMaster;

    //variables
    int shapeCons, shapeTot, labelCons, labelTot, posCons, posTot, cardCons, cardTot, puzzleTot,
            orderCons, orderTot, unjumbleCons, unjumbleTot, mathCons, mathTot;
    private List<String> dates = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        declarations(view);

        diaryEntry();
        labelThat();
        matchingCards();
        matchingShapes();
        mathGame();
        numberOrderGame();
        partsOfSpeech();
        puzzle();
        unjumble();

        btnBackClicked();

        return view;
    }

    private void declarations(View view){
        shapesFirst = view.findViewById(R.id.imgFirstShape);
        shapesConsTen = view.findViewById(R.id.img10ConsecutiveShapes);
        shapesConsTwenty = view.findViewById(R.id.img20ConsecutiveShapes);
        shapesTotTwenty = view.findViewById(R.id.img20Shapes);
        shapeTotFifty = view.findViewById(R.id.img50Shapes);
        shapesTotHundred = view.findViewById(R.id.img100Shapes);
        shapesTotMaster = view.findViewById(R.id.imgShapesMaster);

        diaryFirst = view.findViewById(R.id.imgFirstDiaryEntry);
        amDiary = view.findViewById(R.id.imgAmateurDiary);
        begDiary = view.findViewById(R.id.imgBeginnerDiary);
        intDiary = view.findViewById(R.id.imgIntermediateDiary);
        masterDiary = view.findViewById(R.id.imgMasterDiary);

        labelFirst = view.findViewById(R.id.imgFirstLabel);
        labelConsTen = view.findViewById(R.id.img10ConsecutiveLabels);
        labelConsTwenty= view.findViewById(R.id.img20ConsecutiveLabels);
        labelTotTwenty = view.findViewById(R.id.img20Labels);
        labelTotFifty = view.findViewById(R.id.img50Labels);
        labelTotHundred = view.findViewById(R.id.img100Labels);
        labelMaster = view.findViewById(R.id.imgLabelMaster);

        posFirst  = view.findViewById(R.id.imgFirstSpeech);
        posConsTen = view.findViewById(R.id.img10ConsecutiveSpeech);
        posConsTwenty = view.findViewById(R.id.img20ConsecutiveSpeech);
        posTotTwenty = view.findViewById(R.id.img20Speech);
        posTotFifty = view.findViewById(R.id.img50Speech);
        posTotHundred = view.findViewById(R.id.img100Speech);
        posMaster = view.findViewById(R.id.imgSpeechMaster);

        cardFirst = view.findViewById(R.id.imgFirstCard);
        cardConsTen = view.findViewById(R.id.img10ConsecutiveCards);
        cardConsTwenty = view.findViewById(R.id.img20ConsecutiveCards);
        cardTotTwenty = view.findViewById(R.id.img20Cards);
        cardTotFifty = view.findViewById(R.id.img50Cards);
        cardTotHundred = view.findViewById(R.id.img100Cards);
        cardMaster = view.findViewById(R.id.imgCardMaster);

        puzzleFirst = view.findViewById(R.id.imgFirstPuzzle);
        puzzleTotTwenty = view.findViewById(R.id.img20Puzzles);
        puzzleTotFifty = view.findViewById(R.id.img50Puzzles);
        puzzleTotHundred = view.findViewById(R.id.img100Puzzles);
        puzzleMaster = view.findViewById(R.id.imgPuzzleMaster);

        orderFirst = view.findViewById(R.id.imgFirstOrder);
        orderConsTen = view.findViewById(R.id.img10ConsecutiveOrders);
        orderConsTwenty = view.findViewById(R.id.img20ConsecutiveOrders);
        orderTotTwenty = view.findViewById(R.id.img20Orders);
        orderTotFifty = view.findViewById(R.id.img50Orders);
        orderTotHundred = view.findViewById(R.id.img100Orders);
        orderMaster = view.findViewById(R.id.imgOrderMaster);

        unjumbleConsTen = view.findViewById(R.id.img10ConsecutiveUnjumble);
        unjumbleConsTwenty = view.findViewById(R.id.img20ConsecutiveUnjumble);
        unjumbleFirst = view.findViewById(R.id.imgFirstUnjumble);
        unjumbleTotTwenty = view.findViewById(R.id.img20Unjumble);
        unjumbleTotFifty = view.findViewById(R.id.img50Unjumble);
        unjumbleTotHundred = view.findViewById(R.id.img100Unjumble);
        unjumbleMaster = view.findViewById(R.id.imgUnjumbleMaster);

        mathConsTen = view.findViewById(R.id.img10ConsecutiveSums);
        mathConsTwenty = view.findViewById(R.id.img20ConsecutiveSums);
        mathFirst = view.findViewById(R.id.imgFirstMath);
        mathTotTwenty = view.findViewById(R.id.img20Sums);
        mathTotFifty = view.findViewById(R.id.img50Sums);
        mathTotHundred = view.findViewById(R.id.img100Sums);
        mathMaster = view.findViewById(R.id.imgMathMaster);

        mBack = view.findViewById(R.id.btnPuzzleBack);
    }

    private void btnBackClicked() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void diaryEntry() {
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/DiaryEntries");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0){
                    //since it has more then one child, we know they have done their first entry
                    diaryFirst.setImageResource(R.drawable.first_diary_entry);
                    //code to pull the entire object into a list and then into a list of dates only
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for(DataSnapshot child: children){
                        DiaryEntry pulledEntry = child.getValue(DiaryEntry.class);
                        if(pulledEntry != null){
                            dates.add(pulledEntry.getDiary_Date());
                        }
                    }
                }
                //code used to split up the day in order to check if there have been 7 in a row.
                List<LocalDate> localDateList = new ArrayList<>();
                for (int i = 0; i<dates.size(); i++) {
                    String[] data = dates.get(i).split("-");
                    Month m = Month.of(Integer.parseInt(data[1]));
                    LocalDate localDate =
                            LocalDate.of(Integer.parseInt(data[2]),m,Integer.parseInt(data[0]));
                    localDateList.add(localDate);
                    Date date = java.sql.Date.valueOf(String.valueOf(localDate));
                }
                System.out.println();
                Collections.sort(localDateList);
                int count = 1;
                for (int i = 0; i < localDateList.size() - 1; i++) {
                    LocalDate date1 = localDateList.get(i);
                    LocalDate date2 = localDateList.get(i + 1);
                    if (date1.plusDays(1).equals(date2) ||
                            (date1.equals(30) && date2.equals(1)) ||
                            (date1.equals(31) && date2.equals(1)) ) {
                        count++;
                        if(count >=7)
                            amDiary.setImageResource(R.drawable.amateur_diary_logger);
                        if(count >=30)
                            begDiary.setImageResource(R.drawable.beginner_diary_logger);
                        if(count >=180)
                            intDiary.setImageResource(R.drawable.intermediate_diary_logger);
                        if(count >=365)
                            masterDiary.setImageResource(R.drawable.master_diary_logger);
                    }
                    else
                        count = 0;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void labelThat(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/PhotoLabelling");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveAchievement").exists()){
                    String cons = dataSnapshot.child("ConsecutiveAchievement").getValue().toString();
                    labelCons = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    labelTot = Integer.valueOf(tot);
                }
                //displays what consecutive achievements are completed
                if(labelCons >=1)
                    labelConsTen.setImageResource(R.drawable.consecutive_10_matches_label);
                if(labelCons >=2)
                    labelConsTwenty.setImageResource(R.drawable.consecutive_20_matches_label);

                //displays what total matches achievements are completed
                if(labelTot >= 1)
                    labelFirst.setImageResource(R.drawable.first_matched_label);
                if(labelTot >=20)
                    labelTotTwenty.setImageResource(R.drawable.matched_20_label);
                if(labelTot >= 50)
                    labelTotFifty.setImageResource(R.drawable.matched_50_label);
                if(labelTot >= 100)
                    labelTotHundred.setImageResource(R.drawable.matched_100_label);
                if(labelTot >= 150)
                    labelMaster.setImageResource(R.drawable.label_master);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void matchingCards(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/MatchingCards");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveMatches").exists()){
                    String cons = dataSnapshot.child("ConsecutiveMatches").getValue().toString();
                    cardCons = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("Achievement").exists()){
                    String tot = dataSnapshot.child("Achievement").getValue().toString();
                    cardTot = Integer.valueOf(tot);
                }
                //displays what consecutive achievements are completed
                if(cardCons >=1)
                    cardConsTen.setImageResource(R.drawable.consecutive_10_matches_cards);
                if(cardCons >=2)
                    cardConsTwenty.setImageResource(R.drawable.consecutive_20_matches_cards);

                //displays what total matches achievements are completed
                if(cardTot >= 1)
                    cardFirst.setImageResource(R.drawable.first_match_cards);
                if(cardTot >=20)
                    cardTotTwenty.setImageResource(R.drawable.matched_20_cards);
                if(cardTot >= 50)
                    cardTotFifty.setImageResource(R.drawable.matched_50_cards);
                if(cardTot >= 100)
                    cardTotHundred.setImageResource(R.drawable.matched_100_cards);
                if(cardTot >= 150)
                    cardMaster.setImageResource(R.drawable.match_master_cards);

                Log.d("TAG", "Cards Cons: " + cardCons + " cards Total: " + cardTot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void matchingShapes(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/MatchingShape");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveAchievement").exists()){
                    String cons = dataSnapshot.child("ConsecutiveAchievement").getValue().toString();
                    shapeCons = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    shapeTot = Integer.valueOf(tot);
                }
                //displays what consecutive achievements are completed
                if(shapeCons >=1)
                    shapesConsTen.setImageResource(R.drawable.consecutive_10_matches_shapes);
                if(shapeCons >=2)
                    shapesConsTwenty.setImageResource(R.drawable.consecutive_20_matches_shapes);

                //displays what total matches achievements are completed
                if(shapeTot >= 1)
                    shapesFirst.setImageResource(R.drawable.first_match_shapes);
                if(shapeTot >=20)
                    shapesTotTwenty.setImageResource(R.drawable.matched_20_shapes);
                if(shapeTot >= 50)
                    shapeTotFifty.setImageResource(R.drawable.matched_50_shapes);
                if(shapeTot >= 100)
                    shapesTotHundred.setImageResource(R.drawable.matched_100_shapes);
                if(shapeTot >= 150)
                    shapesTotMaster.setImageResource(R.drawable.shapes_master);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void mathGame(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/MathGame");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveAchievement").exists()){
                    String cons = dataSnapshot.child("ConsecutiveAchievement").getValue().toString();
                    mathCons = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    mathTot = Integer.valueOf(tot);
                }
                //displays what consecutive achievements are completed
                if(mathCons >=1)
                    mathConsTen.setImageResource(R.drawable.consecutive_10_maths);
                if(mathCons >=2)
                    mathConsTwenty.setImageResource(R.drawable.consecutive_20_maths);

                //displays what total matches achievements are completed
                if(mathTot >= 1)
                    mathFirst.setImageResource(R.drawable.first_correct_sum);
                if(mathTot >=20)
                    mathTotTwenty.setImageResource(R.drawable.correct_20_maths);
                if(mathTot >= 50)
                    mathTotFifty.setImageResource(R.drawable.correct_50_maths);
                if(mathTot >= 100)
                    mathTotHundred.setImageResource(R.drawable.correct_100_maths);
                if(mathTot >= 150)
                    mathMaster.setImageResource(R.drawable.math_master);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void numberOrderGame(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/OrderThat");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveAchievement").exists()){
                    String cons = dataSnapshot.child("ConsecutiveAchievement").getValue().toString();
                    orderCons = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    orderTot = Integer.valueOf(tot);
                }
                //displays what consecutive achievements are completed
                if(orderCons >=1)
                    orderConsTen.setImageResource(R.drawable.consecutive_10_orders);
                if(orderCons >=2)
                    orderConsTwenty.setImageResource(R.drawable.consecutive_20_orders);
                //displays what total matches achievements are completed
                if(orderTot >= 1)
                    orderFirst.setImageResource(R.drawable.first_correct_order);
                if(orderTot >=20)
                    orderTotTwenty.setImageResource(R.drawable.ordered_20_numbers);
                if(orderTot >= 50)
                    orderTotFifty.setImageResource(R.drawable.ordered_50_numbers);
                if(orderTot >= 100)
                    orderTotHundred.setImageResource(R.drawable.ordered_100_numbers);
                if(orderTot >= 150)
                    orderMaster.setImageResource(R.drawable.order_master);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void partsOfSpeech(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/MatchingShape");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveAchievement").exists()){
                    String cons = dataSnapshot.child("ConsecutiveAchievement").getValue().toString();
                    posCons = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    posTot = Integer.valueOf(tot);
                }
                //displays what consecutive achievements are completed
                if(posCons >=1)
                    posConsTen.setImageResource(R.drawable.consecutive_10_matches_speech);
                if(posCons >=2)
                    posConsTwenty.setImageResource(R.drawable.consecutive_20_matches_speech);
                //displays what total matches achievements are completed
                if(posTot >= 1)
                    posFirst.setImageResource(R.drawable.first_match_speech);
                if(posTot >=20)
                    posTotTwenty.setImageResource(R.drawable.matched_20_speech);
                if(posTot >= 50)
                    posTotFifty.setImageResource(R.drawable.matched_50_speech);
                if(posTot >= 100)
                    shapesTotHundred.setImageResource(R.drawable.matched_100_speech);
                if(posTot >= 150)
                    posMaster.setImageResource(R.drawable.parts_of_speech_master);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void puzzle(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/Puzzle");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    puzzleTot = Integer.valueOf(tot);
                }
                //displays what total matches achievements are completed
                if(puzzleTot >= 1)
                    puzzleFirst.setImageResource(R.drawable.first_puzzle_complete);
                if(puzzleTot >=20)
                    puzzleTotTwenty.setImageResource(R.drawable.completed_20_puzzles);
                if(puzzleTot >= 50)
                    puzzleTotFifty.setImageResource(R.drawable.completed_50_puzzles);
                if(puzzleTot >= 100)
                    puzzleTotHundred.setImageResource(R.drawable.completed_100_puzzles);
                if(puzzleTot >= 150)
                    puzzleMaster.setImageResource(R.drawable.puzzle_master);

                Log.d("TAG", "Puzzle Total: " + puzzleTot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void unjumble(){
        myRef = database.getReference("Users/"+ currentUser.getUid() +"/Games/Unjumble");
        myRef.keepSynced(true);
        //Read Level
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //Consecutive achievement check
                if(dataSnapshot.child("ConsecutiveAchievement").exists()){
                    String cons = dataSnapshot.child("ConsecutiveAchievement").getValue().toString();
                    unjumbleCons = Integer.valueOf(cons);
                }
                //Total match check
                if(dataSnapshot.child("TotalAchievement").exists()){
                    String tot = dataSnapshot.child("TotalAchievement").getValue().toString();
                    unjumbleTot = Integer.valueOf(tot);
                }
                //displays what consecutive achievements are completed
                if(unjumbleCons >=1)
                    unjumbleConsTen.setImageResource(R.drawable.consecutive_10_unjmbles);
                if(unjumbleCons >=2)
                    unjumbleConsTwenty.setImageResource(R.drawable.consecutive_20_unjmbles);
                //displays what total matches achievements are completed
                if(unjumbleTot >= 1)
                    unjumbleFirst.setImageResource(R.drawable.first_unjumble);
                if(unjumbleTot >=20)
                    unjumbleTotTwenty.setImageResource(R.drawable.unjumbled_20);
                if(unjumbleTot >= 50)
                    unjumbleTotFifty.setImageResource(R.drawable.unjumbled_50);
                if(unjumbleTot >= 100)
                    unjumbleTotHundred.setImageResource(R.drawable.unjumbled_100);
                if(unjumbleTot >= 150)
                    unjumbleMaster.setImageResource(R.drawable.master_unjumbler);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
