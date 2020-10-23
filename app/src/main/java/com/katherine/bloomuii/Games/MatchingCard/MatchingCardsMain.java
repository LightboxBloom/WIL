package com.katherine.bloomuii.Games.MatchingCard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MatchingCardsMain extends AppCompatActivity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);

        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }


}
