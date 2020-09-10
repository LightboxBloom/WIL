package com.k18003144.myapplication;
//package com.katherine.bloomuii.Games.MatchingCard;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class CardSprite {
    private float x;
    private float y;
    private float speed = 0.2f;
    private float xDirection;
    private float destinationX;
    private float destinationY;
    private ArrayList<Bitmap> sprites;
    private Bitmap currentSprite;
    private int currentSpriteIndex;
    private int framesToHide = 40;
    private int framesToReveal = 20;
    private int framesSinceLastHide = 0;
    private int framesSinceLastReveal = 0;
    private CardState cardState;
    private int matchIndex;

    public CardSprite(float startX, float startY, float speed, float xDirection, float destinationX, float destinationY, ArrayList<Bitmap> sprites, int matchIndex) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.xDirection = xDirection;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.sprites = sprites;
        currentSpriteIndex = 0;
        this.currentSprite = sprites.get(0);
        this.matchIndex = matchIndex;
        cardState = CardState.MOVING_TO_POSITION;
    }

    // Moves towards destination
    public void moveToDestination() {
        this.x += CanvasGrid.getInstance().getXPixels(speed) * xDirection;
        float distFromDestination = Math.abs(x - destinationX);
        boolean inPosition = (distFromDestination <= 20);
        if (inPosition && cardState != CardState.HIDING && cardState != CardState.HIDDEN) {
            cardState = CardState.HIDING;
        }
    }

    public void hide() {
        if (framesSinceLastHide >= framesToHide) {
            if (cardState != CardState.HIDDEN) {
                changeSprite(1);
                if (currentSpriteIndex == sprites.size() - 1) {
                    cardState = CardState.HIDDEN;
                }
                framesSinceLastHide = 0;
            }
        } else {
            framesSinceLastHide++;
        }


    }

    private void changeSprite(int direction) {
        if (direction < 0){
            //change backwards
            if(currentSpriteIndex >=1){
                currentSpriteIndex += direction;
            }
        } else if (direction > 0){
            //change forwards
            if(currentSpriteIndex < sprites.size() - 1){
                currentSpriteIndex += direction;
            }
        }
            this.currentSprite = sprites.get(currentSpriteIndex);
    }

    public boolean isTouched(float touchX, float touchY) {
        if (touchX >= this.x && touchX <= this.x + this.currentSprite.getWidth()) {
            //within x bounds of bitmap
            if (touchY >= this.y && touchY <= this.y + this.currentSprite.getHeight()) {
                //within x and y
                return true;
            }
        }

        return false;
    }

    public boolean matches(CardSprite card) {
        return this.matchIndex == card.getMatchIndex();
    }

    public Bitmap getCurrentSprite() {
        return sprites.get(currentSpriteIndex);
    }

    public CardState getCardState() {
        return cardState;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getxDirection() {
        return xDirection;
    }

    public void setxDirection(float xDirection) {
        this.xDirection = xDirection;
    }

    public float getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(float destinationX) {
        this.destinationX = destinationX;
    }

    public float getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(float destinationY) {
        this.destinationY = destinationY;
    }

    public ArrayList<Bitmap> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Bitmap> sprites) {
        this.sprites = sprites;
    }

    public void reveal() {
        if (framesSinceLastReveal >= framesToReveal) {
            if (cardState == CardState.REVEALING) {
                changeSprite(-1);
                if (currentSpriteIndex == 0) {
                    cardState = CardState.REVEALED;
                }
                framesSinceLastReveal = 0;
            }
        } else {
            framesSinceLastReveal++;
        }
    }

    public int getMatchIndex() {
        return matchIndex;
    }
}
