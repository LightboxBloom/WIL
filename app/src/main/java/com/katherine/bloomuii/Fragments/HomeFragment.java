/*Created by: Katherine Chambers
* Edited by: Rohini Naidu 28/06/2020*/
package com.katherine.bloomuii.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Activities.LoginActivity;
import com.katherine.bloomuii.Activities.MainActivity;
import com.katherine.bloomuii.Activities.SignupActivity;
import com.katherine.bloomuii.Games.MatchShape.ShapeMain;
import com.katherine.bloomuii.Games.MatchingCard.MatchingCardsMain;
import com.katherine.bloomuii.Games.Puzzle.PuzzleMain;
import com.katherine.bloomuii.Games.Unjumble.UnjumbleMain;
import com.katherine.bloomuii.R;
import com.katherine.bloomuii.ObjectClasses.User;

public class HomeFragment extends Fragment {

    TextView fullName;
    CardView itemPuzzle, itemShape, itemUnjumble, itemMatching;
    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fullName = view.findViewById(R.id.txtHomeUsername);
        itemPuzzle = view.findViewById(R.id.itemPuzzle);
        itemShape = view.findViewById(R.id.itemShapes);
        itemUnjumble = view.findViewById(R.id.itemUnjumble);
        itemMatching = view.findViewById(R.id.itemMatching);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullName.setText(user.getFull_Name());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        Puzzle();
        MatchShape();
        Unjumble();
        MatchingCard();
        return view;
    }

    private void MatchShape() {
        itemShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShapeMain.class));
            }
        });
    }

    private void Puzzle(){
        itemPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PuzzleMain.class));
            }
        });
    }
    private void Unjumble(){
        itemUnjumble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UnjumbleMain.class));
            }
        });
    }
    private void MatchingCard(){
        itemMatching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MatchingCardsMain.class));
            }
        });
    }

}
