package com.katherine.bloomuii.Games.Puzzle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.katherine.bloomuii.Fragments.HomeFragment;
import com.katherine.bloomuii.R;

/*
Class name: Main
Reason: This is the starting class
Parameters: Main class from which the program is run
Team Member: Cameron White
Date: 10 June 2020
Version: 2
*/

public class PuzzleMain extends AppCompatActivity {

    ImageView btnBack;
    private FragmentManager fragmentManager;
    //OnCreate method
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzlemenu);

        btnBack = (ImageView) findViewById(R.id.btnPuzzleBack);

        selectedPuzzle();
        btnBackClicked();
    }

    //Initializes puzzle logic class based on which level the user wants
    private void selectedPuzzle() {
        Button btnLevel1 = (Button) findViewById(R.id.btnLevel1);
        Button btnLevel2 = (Button) findViewById(R.id.btnLevel2);

        btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PuzzleMain.this, PuzzleLogic.class);
                intent.putExtra("puzzleColumns", 3);
                intent.putExtra("puzzleID", 1);
                startActivity(intent);
            }
        });

        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PuzzleMain.this, PuzzleLogic.class);
                intent.putExtra("puzzleColumns", 4);
                intent.putExtra("puzzleID", 2);
                startActivity(intent);
            }
        });
    }
    //click to get back to home fragment
    private void btnBackClicked()
    {
        final Fragment homeFragment = new HomeFragment();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, homeFragment)
                        .commit();
            }
        });
    }
}
