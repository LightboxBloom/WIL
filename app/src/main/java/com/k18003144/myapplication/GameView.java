package com.k18003144.myapplication;
//package com.katherine.bloomuii.Games.MatchingCard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
//    private FirebaseUser currentUser;
//    private FirebaseAuth mAuth;

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

    public GameView(Context context, UserGameState userProgress) {
        super(context);

        database = FirebaseDatabase.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
//        myRef = database.getReference("Users/" + currentUser.getUid() + "/Games/MatchingCards");
        myRef = database.getReference("Users/" + "Kyle" + "/Games/MatchingCards");
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
        if(userProgress == null || userProgress.getGameLevel() == null){
            userProgress = new UserGameState();
            userProgress.setGameLevel("EASY");
            myRef.child("GameLevel").setValue("EASY");
            //TODO: Initialize Achievement List and all achievements to false as default for new users
        }
        //Set Level based on User Progress from Firebase
        switch (userProgress.getGameLevel()) {
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
        birdSprite = new InfiniteMovingSprite(0, canvasGrid.getYPixels(5), (ArrayList<Bitmap>) birdBitmaps, 5, 1, 0, 20);

        ArrayList<Bitmap> card1Bitmaps = new ArrayList<Bitmap>();
        card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_1_1));
        card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_1_2));
        card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_1_3));
        card1Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_1_4));

        ArrayList<Bitmap> card2Bitmaps;
        selectedCards = new ArrayList<>();
        card2Bitmaps = new ArrayList<>();
        card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_2_1));
        card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_2_2));
        card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_2_3));
        card2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.rocket_league_card_2_4));

        int numXCells = canvasGrid.getNumXCells();
        int cardWidth = 0;
        int cardHeight = 0;
        cardSprites = new ArrayList<>();
        int pairs = 0;
        switch(gameSettings.getDifficulty()){
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
                cardWidth = (int) canvasGrid.getXPixels(4);
                cardHeight = (int) canvasGrid.getYPixels(6);
                break;
        }

        card1Bitmaps = resizeBitmaps(card1Bitmaps, cardWidth, cardHeight);
        card2Bitmaps = resizeBitmaps(card2Bitmaps, cardWidth, cardHeight);
        float leftPadding = ((canvasGrid.getXPixels(numXCells)/2) - cardWidth)/2;
        float rightX = canvasGrid.getXPixels(numXCells)/2 + leftPadding;
        float currentY = canvasGrid.getYPixels(10);
        float yPadding = canvasGrid.getYPixels(1);
        for (int i = 0; i < pairs; i++){
            cardSprites.add(new CardSprite(canvasGrid.getXPixels(-7), currentY, 0.2f, 1, leftPadding, currentY, card1Bitmaps, 1));
            cardSprites.add(new CardSprite(canvasGrid.getXPixels(numXCells + 3), currentY, 0.2f, -1, rightX, currentY, card2Bitmaps, 2));
            currentY+=cardHeight+yPadding;
        }

    }

    private ArrayList<Bitmap> resizeBitmaps(ArrayList<Bitmap> cardBitmaps, int width, int height) {
        ArrayList<Bitmap> resized = new ArrayList<>();
        for (Bitmap bmp : cardBitmaps){
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
        if(selectedCards.size() >= 2) return true; //ignore touch if two cards are already selected
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
            if(matched){
                _framesToDisplayRevealedCard = framesToRevealIfMatched;
            } else{
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

                    } else{
                        framesSinceDisplayRevealedCard++;
                    }

                    //Dependencies on Game Settings
                    attemptLimit = gameSettings.getAttempts();
                    framesToDisplayRevealedCard = gameSettings.getRevealTime() * MAX_FPS;
                    //***************************************************************************************************************
                } else{
                    numFailures++;
                    selectedCards.get(0).setCardState(CardState.HIDING);
                    selectedCards.get(1).setCardState(CardState.HIDING);
                    selectedCards.clear();
                    framesSinceDisplayRevealedCard = 0;

                }

            }

        }

    }

    private void handleRoundEnd() {
        //*********************************************************************************************************************
        //If 5 Successful Rounds promoted to next level
        //If counter is less than 5, add to round if exceeds 5- level is promoted and round counter restarts
        if(cardSprites.size() == 0){
            if(roundCounter < numRounds){
                roundCounter++;
            }else if(roundCounter == numRounds){
                //Write to Firebase next level and change game settings difficulty
                switch (userProgress.getGameLevel()){
                    case "EASY":
                        myRef.child("GameLevel").setValue("EASY_PLUS");
                        userProgress.setGameLevel("EASY_PLUS");
                        gameSettings = new GameSettings(Difficulty.EASY_PLUS);
                        break;
                    case "EASY_PLUS":
                        myRef.child("GameLevel").setValue("MEDIUM");
                        userProgress.setGameLevel("MEDIUM");
                        gameSettings = new GameSettings(Difficulty.MEDIUM);
                        break;
                    case "MEDIUM":
                        myRef.child("GameLevel").setValue("MEDIUM_PLUS");
                        userProgress.setGameLevel("MEDIUM_PLUS");
                        gameSettings = new GameSettings(Difficulty.MEDIUM_PLUS);
                        break;
                    case "MEDIUM_PLUS":
                        myRef.child("GameLevel").setValue("HARD");
                        userProgress.setGameLevel("HARD");
                        gameSettings = new GameSettings(Difficulty.HARD);
                        break;
                    case "HARD":
                        myRef.child("GameLevel").setValue("HARD_PLUS");
                        userProgress.setGameLevel("HARD_PLUS");
                        gameSettings = new GameSettings(Difficulty.HARD_PLUS);
                        break;
                }
                roundCounter = 1;
            }
            initialise();
            framesSinceStop = 0;
        }else if(numFailures == attemptLimit){
            //reset this round
            initialise();
//            roundCounter = 1;
        }
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
        Log.d("GameView.run", "run: Loop: "+System.currentTimeMillis());
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
        canvas.drawBitmap(backgroundStaticSprite.getImage(), backgroundStaticSprite.getX(), backgroundStaticSprite.getY(), null);
        canvas.drawBitmap(birdSprite.getCurrentSprite(), birdSprite.getX(), birdSprite.getY(), null);
        for (int i = 0; i < cardSprites.size(); i++) {
            canvas.drawBitmap(cardSprites.get(i).getCurrentSprite(), cardSprites.get(i).getX(), cardSprites.get(i).getY(), null);
        }

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);

        textPaint.setTextSize(42);
        float x = canvasGrid.getXPixels(canvasGrid.getNumXCells()-10);
        float y = canvasGrid.getYPixels(2);
        canvas.drawText("Attempts Left: "+(attemptLimit - numFailures), x, y, textPaint);

//        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        paint = new Paint();
        float currentX, currentY;
        // columns
        for (currentX = 0; currentX <= screenWidth; currentX += canvasGrid.getCellWidth()) {
            paint.setARGB(120, 255, 255, 255);
            canvas.drawLine(currentX,  0, currentX, screenHeight, paint);
        }
        // rows
        for (currentY = 0; currentY <= screenHeight; currentY += canvasGrid.getCellHeight()) {
            paint.setARGB(120, 255, 255, 255);
            canvas.drawLine(0,  currentY, screenWidth, currentY, paint);
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
