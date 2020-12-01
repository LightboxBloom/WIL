package com.katherine.bloomuii.Games.MatchingCard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private static final String TAG = "GameView";
    private boolean isRunning = true;
    private Thread gameThread;
    private SurfaceHolder holder;
    private CanvasGrid canvasGrid;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private final static int MAX_FPS = 60; //desired fps
    private final static float FRAME_PERIOD = 1000.0f / MAX_FPS;

    float minX, screenWidth, minY, screenHeight;
    Paint paint;
    Bitmap backgroundBitmap;
    StaticSprite backgroundStaticSprite;
    List<Bitmap> birdBitmaps;
    InfiniteMovingSprite birdSprite;
    ArrayList<CardSprite> cardSprites;
    ArrayList<CardSprite> selectedCards;
    boolean canInitialise = false;
    boolean hasInitialised = false;
    UserGameState userProgress;
    GameSettings gameSettings;
    int roundCounter = 1;
    int numRounds = 2;

    float framesToDisplayRevealedCard;
    int attemptLimit;
    int numFailures = 0;

    //Should be in ratio of display aspect ratio which is 16:9 most of the time
    int numYCells = 48;
    int numXCells = 27;
    ArrayList<Flashcard> downloadedFlashcards;

    int numConsecutiveMatches = 0;

    public GameView(Context context, UserGameState userProgress, ArrayList<Flashcard> downloadedFlashcards) {
        super(context);

        this.downloadedFlashcards = downloadedFlashcards;
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid() + "/Games/MatchingCards");
        this.userProgress = userProgress;

        setGameLevel();
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                setWillNotDraw(false);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                minX = 0;
                minY = 0;
                screenHeight = height;
                screenWidth = width;
                canvasGrid = CanvasGrid.getInstance(numXCells, numYCells, screenWidth, screenHeight);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        gameThread = new Thread(this);
        gameThread.start();
    }

    private void setGameLevel() {
        //For new users start at first level
        if (userProgress == null || userProgress.getLevel() == null) {
            userProgress = new UserGameState();
            userProgress.setLevel("EASY");
            myRef.child("Level").setValue(userProgress);
            //TODO: Initialize Achievement List and all achievements to false as default for new users
        }
        //Set Level based on User Progress from Firebase
        switch (userProgress.getLevel()) {
            case "EASY":
                gameSettings = new GameSettings(Difficulty.EASY);
                break;
            case "EASY_PLUS":
                gameSettings = new GameSettings(Difficulty.EASY_PLUS);
                break;
            case "MEDIUM":
                gameSettings = new GameSettings(Difficulty.MEDIUM);
                break;
            case "MEDIUM_PLUS":
                gameSettings = new GameSettings(Difficulty.MEDIUM_PLUS);
                break;
            case "HARD":
                gameSettings = new GameSettings(Difficulty.HARD);
                break;
            case "HARD_PLUS":
                gameSettings = new GameSettings(Difficulty.HARD_PLUS);
                break;
        }

        //Dependencies on Game Settings
        framesToDisplayRevealedCard = gameSettings.getRevealTime() * MAX_FPS;
        attemptLimit = gameSettings.getAttempts();
        canInitialise = true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canInitialise && !hasInitialised) {
            initialise();
            hasInitialised = true;
        }
    }

    private void initialise() {
        numFailures = 0;
        minX = 0;
        minY = 0;
        screenHeight = getHeight();
        screenWidth = getWidth();
        canvasGrid = CanvasGrid.getInstance(numXCells, numYCells, screenWidth, screenHeight);

        paint = new Paint();

        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_image_cropped);
        backgroundStaticSprite = new StaticSprite(0, 0, backgroundBitmap);

        birdBitmaps = new ArrayList<Bitmap>();
        birdBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird_1));
        birdBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird_2));
        birdBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird_3));
        birdBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird_4));
        birdBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird_5));

        for (int i=0; i< birdBitmaps.size(); i++){
            Bitmap bitmap = birdBitmaps.get(i);
            double initialHeight = bitmap.getHeight();
            int birdHeight = (int) canvasGrid.getYPixels(3);
            int birdWidth = (int) (birdHeight/initialHeight * birdBitmaps.get(i).getWidth());
            bitmap = getResizedBitmap(bitmap, birdWidth, birdHeight);
            birdBitmaps.set(i, bitmap);
        }


        birdSprite = new InfiniteMovingSprite(0, canvasGrid.getYPixels(5), (ArrayList<Bitmap>) birdBitmaps, 5, 1, 0, 20);

        ArrayList<Bitmap> card1Bitmaps = new ArrayList<Bitmap>();
        ArrayList<Bitmap> card2Bitmaps = new ArrayList<>();
        ArrayList<Bitmap> card3Bitmaps = new ArrayList<>();
        ArrayList<Bitmap> card4Bitmaps = new ArrayList<>();

        switch (downloadedFlashcards.size()) {
            case 0:
                card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cat_1));
                card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cat_2));
                card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cat_3));
                card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));

                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.chicken_1));
                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.chicken_2));
                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.chicken_3));
                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));

                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_1));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_2));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_3));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));

                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_1));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_2));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_3));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));
                break;
            case 1:
                card1Bitmaps = downloadedFlashcards.get(0).getBitmaps();

                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.chicken_1));
                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.chicken_2));
                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.chicken_3));
                card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));

                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_1));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_2));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_3));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));

                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_1));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_2));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_3));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));
                break;
            case 2:
                card1Bitmaps = downloadedFlashcards.get(0).getBitmaps();
                card2Bitmaps = downloadedFlashcards.get(1).getBitmaps();

                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_1));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_2));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.cub_3));
                card3Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));

                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_1));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_2));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_3));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));
                break;
            case 3:
                card1Bitmaps = downloadedFlashcards.get(0).getBitmaps();
                card2Bitmaps = downloadedFlashcards.get(1).getBitmaps();
                card3Bitmaps = downloadedFlashcards.get(2).getBitmaps();

                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_1));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_2));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.match_bird_3));
                card4Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.blank));
                break;
            case 4:
                card1Bitmaps = downloadedFlashcards.get(0).getBitmaps();
                card2Bitmaps = downloadedFlashcards.get(1).getBitmaps();
                card3Bitmaps = downloadedFlashcards.get(2).getBitmaps();
                card4Bitmaps = downloadedFlashcards.get(3).getBitmaps();
                break;
            default:
                break;
        }

        selectedCards = new ArrayList<>();

        int numXCells = canvasGrid.getNumXCells();
        int cardWidth = 0;
        int cardHeight = 0;
        cardSprites = new ArrayList<>();
        int pairs = 0;
        switch (gameSettings.getDifficulty()) {
            case EASY:
            case EASY_PLUS:
                pairs = 2;
                cardWidth = (int) canvasGrid.getXPixels(8);
                cardHeight = (int) canvasGrid.getYPixels(12);
                break;
            case MEDIUM:
            case MEDIUM_PLUS:
                pairs = 3;
                cardWidth = (int) canvasGrid.getXPixels(6);
                cardHeight = (int) canvasGrid.getYPixels(9);
                break;
            case HARD:
            case HARD_PLUS:
                pairs = 4;
                cardWidth = (int) canvasGrid.getXPixels(6);
                cardHeight = (int) canvasGrid.getYPixels(9);
                break;
        }

        card1Bitmaps = resizeBitmaps(card1Bitmaps, cardWidth, cardHeight);
        card2Bitmaps = resizeBitmaps(card2Bitmaps, cardWidth, cardHeight);
        card3Bitmaps = resizeBitmaps(card3Bitmaps, cardWidth, cardHeight);
        card4Bitmaps = resizeBitmaps(card4Bitmaps, cardWidth, cardHeight);

        ArrayList<ArrayList<Bitmap>> bitmaps2D = new ArrayList<>();
        bitmaps2D.add(card1Bitmaps);
        bitmaps2D.add(card2Bitmaps);
        bitmaps2D.add(card3Bitmaps);
        bitmaps2D.add(card4Bitmaps);

        float leftPadding = ((canvasGrid.getXPixels(numXCells) / 2) - cardWidth) / 2;
        float rightX = canvasGrid.getXPixels(numXCells) / 2 + leftPadding;
        float currentY = canvasGrid.getYPixels(10);
        float yPadding = canvasGrid.getYPixels(1);


        // Gross ugly code that unfortunately works
        ArrayList<Vector2D> positions = new ArrayList<>();
        for (int i = 0; i < pairs; i++) {
            positions.add(new Vector2D(leftPadding, currentY));
            positions.add(new Vector2D(rightX, currentY));
            currentY += cardHeight + yPadding;
        }
        Collections.shuffle(positions);

        int positionsIndex = 0;
//        for (int i = 0; i < pairs; i++) {
        for (int i = 0; i < pairs; i++) {
            //Card 1 in pair
            // decide which side to come in from on x
            if(positions.get(positionsIndex).getX() < canvasGrid.getXPixels(numXCells) / 2){
                // coming from left
                cardSprites.add(new CardSprite(canvasGrid.getXPixels(-7), positions.get(positionsIndex).getY(), 0.2f, 1, positions.get(positionsIndex).getX(), positions.get(positionsIndex).getY(), bitmaps2D.get(i), i));
            } else{
                // coming from right
                cardSprites.add(new CardSprite(canvasGrid.getXPixels(numXCells + 3), positions.get(positionsIndex).getY(), 0.2f, -1, positions.get(positionsIndex).getX(), positions.get(positionsIndex).getY(), bitmaps2D.get(i), i));
            }
            positionsIndex++;
//            i++;

            //Card 2 in pair
            // decide which side to come in from on x
            if(positions.get(positionsIndex).getX() < canvasGrid.getXPixels(numXCells) / 2){
                // coming from left
                cardSprites.add(new CardSprite(canvasGrid.getXPixels(-7), positions.get(positionsIndex).getY(), 0.2f, 1, positions.get(positionsIndex).getX(), positions.get(positionsIndex).getY(), bitmaps2D.get(i), i));
            } else{
                // coming from right
                cardSprites.add(new CardSprite(canvasGrid.getXPixels(numXCells + 3), positions.get(positionsIndex).getY(), 0.2f, -1, positions.get(positionsIndex).getX(), positions.get(positionsIndex).getY(), bitmaps2D.get(i), i));
            }
            positionsIndex++;
        }

    }

    private ArrayList<Bitmap> resizeBitmaps(ArrayList<Bitmap> cardBitmaps, int width, int height) {
        ArrayList<Bitmap> resized = new ArrayList<>();
        for (Bitmap bmp : cardBitmaps) {
            resized.add(getResizedBitmap(bmp, width, height));
        }
        return resized;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
//        bm.recycle();
        return resizedBitmap;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (selectedCards.size() >= 2) return true; //ignore touch if two cards are already selected
        for (CardSprite cardSprite : cardSprites) {
            if (cardSprite.isTouched(event.getX(), event.getY())) {
                if (cardSprite.getCardState() == CardState.HIDDEN) {
                    handleCardTouch(cardSprite);
                }
                break;
            }
        }

        return true;
    }

    int framesSinceDisplayRevealedCard = 0;

    private void handleCardTouch(CardSprite cardSprite) {
        cardSprite.setCardState(CardState.REVEALING);
        cardSprite.reveal();
        selectedCards.add(cardSprite);
        handleMatch();
    }

    private void handleMatch() {
        float _framesToDisplayRevealedCard;
        float framesToRevealIfMatched = MAX_FPS; //1 second
        float framesToRevealIfNotMatched = gameSettings.getRevealTime() * MAX_FPS;
        if (selectedCards.size() == 2) {
            boolean matched = selectedCards.get(0).matches(selectedCards.get(1)) && selectedCards.get(0) != selectedCards.get(1);
            if (matched) {
                _framesToDisplayRevealedCard = framesToRevealIfMatched;
            } else {
                _framesToDisplayRevealedCard = framesToRevealIfNotMatched;
            }


            //Only do this once both cards are revealed
            if (selectedCards.get(0).getCardState() == CardState.REVEALED && selectedCards.get(1).getCardState() == CardState.REVEALED) {
                if (matched) {
                    if (framesSinceDisplayRevealedCard >= _framesToDisplayRevealedCard) {
                        cardSprites.remove(selectedCards.get(0));
                        cardSprites.remove(selectedCards.get(1));
                        selectedCards.clear();
                        framesSinceDisplayRevealedCard = 0;
                        handleRoundEnd();
                        numConsecutiveMatches++;
                        new UpdateAchievement().execute();

                    } else {
                        framesSinceDisplayRevealedCard++;
                    }

                    //Dependencies on Game Settings
                    attemptLimit = gameSettings.getAttempts();
                    framesToDisplayRevealedCard = gameSettings.getRevealTime() * MAX_FPS;
                    //***************************************************************************************************************
                } else {
                    numConsecutiveMatches = 0;
                    numFailures++;
                    selectedCards.get(0).setCardState(CardState.HIDING);
                    selectedCards.get(1).setCardState(CardState.HIDING);
                    selectedCards.clear();
                    framesSinceDisplayRevealedCard = 0;
                    if (numFailures == attemptLimit) {
                        //reset this round
                        initialise();
                        roundCounter = 1;
                    }
                }

            }

        }

    }

    private void updateAchievement() {
        Log.d(TAG, "updateAchievement: Update");
        int matches = userProgress.getAchievement() + 1;
        userProgress.setAchievement(matches);
    }

    private void handleRoundEnd() {
        //*********************************************************************************************************************
        //If 5 Successful Rounds promoted to next level
        //If counter is less than 5, add to round if exceeds 5- level is promoted and round counter restarts
        if (cardSprites.size() == 0) {
            if (roundCounter < numRounds) {
                roundCounter++;
            } else if (roundCounter == numRounds) {
                //Write to Firebase next level and change game settings difficulty
                switch (userProgress.getLevel()) {
                    case "EASY":
                        userProgress.setLevel("EASY_PLUS");
                        myRef.child("Level").setValue(userProgress.getLevel());
                        gameSettings = new GameSettings(Difficulty.EASY_PLUS);
                        break;
                    case "EASY_PLUS":
                        userProgress.setLevel("MEDIUM");
                        myRef.child("Level").setValue(userProgress.getLevel());
                        gameSettings = new GameSettings(Difficulty.MEDIUM);
                        break;
                    case "MEDIUM":
                        userProgress.setLevel("MEDIUM_PLUS");
                        myRef.child("Level").setValue(userProgress.getLevel());
                        gameSettings = new GameSettings(Difficulty.MEDIUM_PLUS);
                        break;
                    case "MEDIUM_PLUS":
                        userProgress.setLevel("HARD");
                        myRef.child("Level").setValue(userProgress.getLevel());
                        gameSettings = new GameSettings(Difficulty.HARD);
                        break;
                    case "HARD":
                        userProgress.setLevel("HARD_PLUS");
                        myRef.child("Level").setValue(userProgress.getLevel());
                        gameSettings = new GameSettings(Difficulty.HARD_PLUS);
                        break;
                }
                roundCounter = 1;
            }
            initialise();
            framesSinceStop = 0;
        }
//        else if (numFailures == attemptLimit) {
//            //reset this round
//            initialise();
//            roundCounter = 1;
//        }
    }

    public void pause() {
        isRunning = false;
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
    }

    public void resume() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        Log.d("GameView.run", "run: Loop: " + System.currentTimeMillis());
        while (isRunning) {
            if (hasInitialised) {
                // We need to make sure that the surface is ready
                if (!holder.getSurface().isValid()) {
                    continue;
                }
                long startTime = System.currentTimeMillis();

                // update
                step();

                // draw
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    render(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                float deltaTime = System.currentTimeMillis() - startTime;
                int sleepTime = (int) (FRAME_PERIOD - deltaTime);
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                while (sleepTime < 0) {
                    step();
                    sleepTime += FRAME_PERIOD;
                }
            }

        }


    }

    float framesSinceStop = 0;

    private void step() {
        birdSprite.move();
        if (birdSprite.getX() >= screenWidth) birdSprite.setX(0);
        birdSprite.changeSprite();
        handleMatch();

        for (CardSprite cardSprite : cardSprites) {
            if (cardSprite.getCardState() == CardState.MOVING_TO_POSITION) {
                cardSprite.moveToDestination();
            } else if (cardSprite.getCardState() == CardState.HIDING) {
                if (framesSinceStop >= framesToDisplayRevealedCard) {
                    cardSprite.hide();
                } else {
                    framesSinceStop++;
                }

            } else if (cardSprite.getCardState() == CardState.REVEALING) {
                cardSprite.reveal();
            }
        }


    }

    void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));
//        canvas.drawBitmap(backgroundStaticSprite.getImage(), backgroundStaticSprite.getX(), backgroundStaticSprite.getY(), null);
        canvas.drawBitmap(birdSprite.getCurrentSprite(), birdSprite.getX(), birdSprite.getY(), null);
        for (int i = 0; i < cardSprites.size(); i++) {
            canvas.drawBitmap(cardSprites.get(i).getCurrentSprite(), cardSprites.get(i).getX(), cardSprites.get(i).getY(), null);
        }

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);

        textPaint.setTextSize(42);
        float x = canvasGrid.getXPixels(canvasGrid.getNumXCells() - 10);
        float y = canvasGrid.getYPixels(2);
        canvas.drawText("Attempts Left: " + (attemptLimit - numFailures), x, y, textPaint);

//        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        paint = new Paint();
        float currentX, currentY;
        // columns
        for (currentX = 0; currentX <= screenWidth; currentX += canvasGrid.getCellWidth()) {
            paint.setARGB(120, 255, 255, 255);
            canvas.drawLine(currentX, 0, currentX, screenHeight, paint);
        }
        // rows
        for (currentY = 0; currentY <= screenHeight; currentY += canvasGrid.getCellHeight()) {
            paint.setARGB(120, 255, 255, 255);
            canvas.drawLine(0, currentY, screenWidth, currentY, paint);
        }
    }

    class UpdateAchievement extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "updateAchievement: Update");
            int matches = userProgress.getAchievement() + 1;
            userProgress.setAchievement(matches);
            myRef.child("Achievement").setValue(userProgress.getAchievement());

            if(numConsecutiveMatches > userProgress.getConsecutiveMatches()){
                userProgress.setConsecutiveMatches(numConsecutiveMatches);
            }

            myRef.child("ConsecutiveMatches").setValue(userProgress.getConsecutiveMatches());
            return null;
        }
    }

}


enum CardState {
    MOVING_TO_POSITION,
    HIDING,
    HIDDEN,
    REVEALING,
    REVEALED,
    EXITING
}
