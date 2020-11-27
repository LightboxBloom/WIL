package com.katherine.bloomuii.Games.Puzzle;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.katherine.bloomuii.R;

import java.util.ArrayList;
import java.util.Random;

/*
Class name: Puzzle Logic
Reason: This class creates and handles the puzzle logic - dependent on which
        puzzle the user has chosen.
Parameters:
Team Member: Cameron White
Date: 10 June 2020
Version: 2
*/

public class PuzzleLogic extends AppCompatActivity {

    private static int COLUMNS;
    private static int DIMENSIONS;
    private static int puzzleID;

    private static String [] tileList;

    private static GestureDetectGridView mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    //onCreate method, puzzle component initializer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle);

        puzzleID = getIntent().getIntExtra("puzzleID",1);
        COLUMNS = getIntent().getIntExtra("puzzleColumns",0);
        DIMENSIONS = COLUMNS*COLUMNS;

        init();

        scramble();

        setDimensions();
    }

    //Set the dimensions of the based on number of puzzle pieces
    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusBarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight-statusBarHeight;

                mColumnWidth = displayWidth/COLUMNS;
                mColumnHeight = requiredHeight/COLUMNS;

                display(getApplicationContext());
            }
        });
    }

    //Get status bar height - to ensure full screen image
    private int getStatusBarHeight (Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen","android");

        if (resourceId>0){
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //Create and display the puzzle to the user (add button tiles to grid)
    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        if (puzzleID==1){
            for (int i=0; i<tileList.length;i++) {
                button = new Button(context);

                if (tileList[i].equals("0")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece1);
                } else if (tileList[i].equals("1")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece2);
                } else if (tileList[i].equals("2")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece3);
                } else if (tileList[i].equals("3")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece4);
                } else if (tileList[i].equals("4")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece5);
                } else if (tileList[i].equals("5")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece6);
                } else if (tileList[i].equals("6")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece7);
                } else if (tileList[i].equals("7")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece8);
                } else if (tileList[i].equals("8")) {
                    button.setBackgroundResource(R.drawable.pigeon_piece9);
                }
                buttons.add(button);
            }

        }else if(puzzleID==2){

            for (int i=0; i<tileList.length;i++) {
                button = new Button(context);

                if (tileList[i].equals("0")) {
                    button.setBackgroundResource(R.drawable.dog_piece1);
                } else if (tileList[i].equals("1")) {
                    button.setBackgroundResource(R.drawable.dog_piece2);
                } else if (tileList[i].equals("2")) {
                    button.setBackgroundResource(R.drawable.dog_piece3);
                } else if (tileList[i].equals("3")) {
                    button.setBackgroundResource(R.drawable.dog_piece4);
                } else if (tileList[i].equals("4")) {
                    button.setBackgroundResource(R.drawable.dog_piece5);
                } else if (tileList[i].equals("5")) {
                    button.setBackgroundResource(R.drawable.dog_piece6);
                } else if (tileList[i].equals("6")) {
                    button.setBackgroundResource(R.drawable.dog_piece7);
                } else if (tileList[i].equals("7")) {
                    button.setBackgroundResource(R.drawable.dog_piece8);
                } else if (tileList[i].equals("8")) {
                    button.setBackgroundResource(R.drawable.dog_piece9);
                } else if (tileList[i].equals("9")) {
                    button.setBackgroundResource(R.drawable.dog_piece10);
                } else if (tileList[i].equals("10")) {
                    button.setBackgroundResource(R.drawable.dog_piece11);
                } else if (tileList[i].equals("11")) {
                    button.setBackgroundResource(R.drawable.dog_piece12);
                } else if (tileList[i].equals("12")) {
                    button.setBackgroundResource(R.drawable.dog_piece13);
                } else if (tileList[i].equals("13")) {
                    button.setBackgroundResource(R.drawable.dog_piece14);
                } else if (tileList[i].equals("14")) {
                    button.setBackgroundResource(R.drawable.dog_piece15);
                } else if (tileList[i].equals("15")) {
                    button.setBackgroundResource(R.drawable.dog_piece16);
                }
                buttons.add(button);
            }
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    //scramble the tiles
    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length-1; i>0; i--)
        {
            index = random.nextInt(i+1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    //Build up tile list and setup grid view
    public void init(){
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];
        for(int i = 0; i<DIMENSIONS; i++){
            tileList[i] = String.valueOf(i);
        }
    }

    //Create swap tile functionality
    public static void swap(Context context, int position, int swap){
        String newPosition = tileList[position+swap];
        tileList[position + swap] = tileList[position];
        tileList[position] = newPosition;
        display(context);

        if (isSolved()){
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }

    }

    //Check if the puzzle is solved
    private static boolean isSolved() {
        boolean solved = false;

        for (int i=0; i<tileList.length; i++){
            if (tileList[i].equals(String.valueOf(i))){
                solved  = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }

    //Handle tile movement
    public static void moveTiles(Context context, String direction, int position) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) {

                // Only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }
}
