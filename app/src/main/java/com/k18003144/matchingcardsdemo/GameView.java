package com.k18003144.matchingcardsdemo;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private static final String TAG = "GameView";
    private boolean isRunning = false;
    private Thread gameThread;
    private SurfaceHolder holder;
    private CanvasGrid canvasGrid;

    private final static int MAX_FPS = 60; //desired fps
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    float minX, screenWidth, minY, screenHeight;
    Paint paint;
    Bitmap backgroundBitmap;
    StaticSprite backgroundStaticSprite;
    List<Bitmap> birdBitmaps;
    InfiniteMovingSprite birdSprite;
    ArrayList<CardSprite> cardSprites;
    ArrayList<CardSprite> selectedCards;
    float framesBeforeCover = 1200;
    float framesSinceStop = 0;
    boolean hasInitialised = false;
    String difficulty = "easy";
//    String difficulty = "medium";
//    String difficulty = "hard";

    //Should be in ratio of display aspect ratio which is 16:9 most of the time
    int numXCells = 27;
    int numYCells = 48;

    public GameView(Context context) {
        super(context);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!hasInitialised) {
            initialise();
            hasInitialised = true;
        }
    }

    private void initialise() {
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
        birdSprite = new InfiniteMovingSprite(0, 100, (ArrayList<Bitmap>) birdBitmaps, 5, 1, 0, 20);

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
        ArrayList<HashMap<String, Float>> coordinates = new ArrayList<>();
        int gameAreaYCells = (int) (0.75*numYCells);
        int gameAreaStartingYCell = (int) (0.25*numYCells) + 1;
        int gameAreaEndingYCell = numYCells;
        switch(difficulty){
            case "easy":
                pairs = 2;
                cardWidth = (int) canvasGrid.getXPixels(8);
                cardHeight = (int) canvasGrid.getYPixels(12);
                break;
            case "medium":
                pairs = 3;
                cardWidth = (int) canvasGrid.getXPixels(6);
                cardHeight = (int) canvasGrid.getYPixels(9);
                break;
            case "hard":
                pairs = 4;
                cardWidth = (int) canvasGrid.getXPixels(4);
                cardHeight = (int) canvasGrid.getYPixels(6);
                break;
        }

        card1Bitmaps = resizeBitmaps(card1Bitmaps, cardWidth, cardHeight);
        card2Bitmaps = resizeBitmaps(card2Bitmaps, cardWidth, cardHeight);
        float leftPadding = ((canvasGrid.getXPixels(numXCells)/2) - cardWidth)/2;
        float leftX = leftPadding;
        float rightX = canvasGrid.getXPixels(numXCells)/2 + leftPadding;
        float startingY = canvasGrid.getYPixels(10);
        float currentY = startingY;
        float yPadding = canvasGrid.getYPixels(1);
        for (int i = 0; i < pairs; i++){
            cardSprites.add(new CardSprite(canvasGrid.getXPixels(-7), currentY, 0.2f, 1, leftX, currentY, card1Bitmaps, 1));
            cardSprites.add(new CardSprite(canvasGrid.getXPixels(numXCells + 3), currentY, 0.2f, -1, rightX, currentY, card2Bitmaps, 2));
            currentY+=cardHeight+yPadding;

//            cardSprites.add(new CardSprite(canvasGrid.getXPixels(-7), bottomY, 0.2f, 1, leftX, bottomY, card1Bitmaps, 1));
//            cardSprites.add(new CardSprite(canvasGrid.getXPixels(numXCells + 3), bottomY, 0.2f, -1, rightX, bottomY, card2Bitmaps, 2));
        }

//        for(CardSprite cardSprite : cardSprites){
//            ArrayList<Bitmap> cardBitmaps  = cardSprite.getSprites();
//            ArrayList<Bitmap> resizedBitmaps = new ArrayList<>();
//            for(Bitmap bitmap : cardBitmaps){
//                double newHeight;
//                double newWidth;
//
//                newHeight = (0.75 * screenHeight) / (cardSprites.size());
//                newWidth = (screenWidth * newHeight)/ screenHeight;
//                resizedBitmaps.add(getResizedBitmap(bitmap, (int) Math.round(newWidth), (int) Math.round(newHeight)));
//            }
//            cardSprite.setSprites(resizedBitmaps);
//        }

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

    int framesToDisplayRevealedCard = 60;
    int framesSinceDisplayRevealedCard = 0;
    private void handleCardTouch(CardSprite cardSprite) {
        cardSprite.setCardState(CardState.REVEALING);
        cardSprite.reveal();
        selectedCards.add(cardSprite);
        handleMatch();
    }

    private void handleMatch() {
        if (selectedCards.size() == 2) {
            //Only do this once both cards are revealed
            if (selectedCards.get(0).getCardState() == CardState.REVEALED && selectedCards.get(1).getCardState() == CardState.REVEALED) {
                if (selectedCards.get(0).matches(selectedCards.get(1))) {
                    if (framesSinceDisplayRevealedCard >= framesToDisplayRevealedCard) {
                        cardSprites.remove(selectedCards.get(0));
                        cardSprites.remove(selectedCards.get(1));
                        selectedCards.clear();
                        framesSinceDisplayRevealedCard = 0;
                    } else{
                        framesSinceDisplayRevealedCard++;
                    }
                } else{
                    selectedCards.get(0).setCardState(CardState.HIDING);
                    selectedCards.get(1).setCardState(CardState.HIDING);
                    selectedCards.clear();
                }

            }

        }

        if(cardSprites.size() == 0){
            initialise();
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

    private void step() {
        birdSprite.move();
        if (birdSprite.getX() >= screenWidth) birdSprite.setX(0);
        birdSprite.changeSprite();
        handleMatch();

        for (CardSprite cardSprite : cardSprites) {
            if (cardSprite.getCardState() == CardState.MOVING_TO_POSITION) {
                cardSprite.moveToDestination();
            } else if (cardSprite.getCardState() == CardState.HIDING) {
                if (framesSinceStop >= framesBeforeCover) {
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

        drawGrid(canvas);
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
