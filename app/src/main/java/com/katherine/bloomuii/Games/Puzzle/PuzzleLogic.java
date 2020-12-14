package com.katherine.bloomuii.Games.Puzzle;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
/*
Class name: Puzzle Logic
Reason: This class creates and handles the puzzle logic - dependent on which
        puzzle the user has chosen.
Parameters:
Team Member: Cameron White
Date: 9 June 2020
Version: 1
Date: 10 June 2020
Version: 2
Date: 14 Aug 2020
Version: 3
*/
public class PuzzleLogic extends AppCompatActivity {

    private static int COLUMNS, ROWS, DIMENSIONS, puzzleID;

    private static String [] tileList;
    private static GestureDetectGridView mGridView;

    private static Bitmap image;

    private static UserScore user;

    private static int mColumnWidth, mColumnHeight;
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    //Firebase
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    //onCreate method, puzzle component initializer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle);

        puzzleID = getIntent().getIntExtra("puzzleID",1);
        COLUMNS = getIntent().getIntExtra("puzzleColumns",0);
        ROWS = getIntent().getIntExtra("puzzleRows",0);
        DIMENSIONS = COLUMNS*COLUMNS;

        user = UserScore.getInstance();
        StorageReference storageRef = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://bloom-database.appspot.com/Puzzle/Uploads");

        //create tile list and set up grid view
        init();
        //set the dimensions of the tiles
        setDimensions();

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                int random = new Random().nextInt(listResult.getItems().size());
                listResult.getItems().get(random).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            new Firebase().execute(uri.toString()).get().wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //scramble the tiles
                        PuzzleLogic.this.scramble();

                        display(PuzzleLogic.this.getApplicationContext());
                    }
                });
            }
        });

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
        ArrayList<BitmapDrawable> drawables = splitImage(context);

        //loop through tile list to initialise and set button background
        for (int i=0; i<tileList.length;i++) {
            button = new Button(context);
            for (int k=0; k<tileList.length;k++){
                if (tileList[i].equals(String.valueOf(k)))
                {
                    button.setBackground(drawables.get(k));
                }
            }
            buttons.add(button);
        }

        //add the adapter to the grid view
        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    //Split single image into smaller pieces
    private static ArrayList<BitmapDrawable> splitImage(Context context) {

        int chunkNumbers = COLUMNS*COLUMNS;

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<BitmapDrawable> chunkedImages = new ArrayList<BitmapDrawable>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, image.getWidth(),
                image.getHeight(), true);
        chunkHeight = image.getHeight() / COLUMNS;
        chunkWidth = image.getWidth() / COLUMNS;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;

        //adding bitmaps in correct positions
        for(int x = 0; x < COLUMNS; x++) {
            int xCoord = 0;
            for(int y = 0; y < COLUMNS; y++) {
                chunkedImages.add(new BitmapDrawable(
                        context.getResources(),
                        Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight)));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        return chunkedImages;
    }
    //scramble the tiles
    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        //create a random order of the tiles
        do {
            for (int i = tileList.length-1; i>0; i--)
            {
                index = random.nextInt(i+1);
                temp = tileList[index];
                tileList[index] = tileList[i];
                tileList[i] = temp;
            }
        }while(isSolved());

    }
    //Build up tile list and setup grid view
    public void init(){
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];
        //
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

        //check if the puzzle is solved
        if (isSolved()){
            user.addScore();
            //check if achievement should be rewarded
            if (user.hasAchievement()){
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Congratulations!")
                        .setMessage("You have completed " + user.getScore() + " puzzles")
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).show();
            }
            else{
                Toast.makeText(context,"Puzzle completed!",Toast.LENGTH_SHORT).show();
            }
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
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1
                || position == COLUMNS * 4 - 1) {
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

    public class Firebase extends AsyncTask<String,Void,Void> {

        public Void doInBackground(String...uri) {
            try {
                URL url = new URL(uri[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                image = BitmapFactory.decodeStream(input);
                connection.disconnect();
            } catch (IOException e) {
                // Log exception
                return null;
            }
            return null;
        }

    }

}
