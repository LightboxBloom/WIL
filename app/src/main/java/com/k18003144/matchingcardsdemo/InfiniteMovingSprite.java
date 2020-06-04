package com.k18003144.matchingcardsdemo;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InfiniteMovingSprite {
    private float speed;
    private float xDirection;
    private float yDirection;
    private float x;
    private float y;
    private ArrayList<Bitmap> sprites;
    private Bitmap currentSprite;
    private int frameChangeInterval;
    private int currentSpriteIndex;
    private int framesSinceLastChange;

    public InfiniteMovingSprite(float x, float y, ArrayList<Bitmap> sprites, float speed, float xDirection, float yDirection, int frameChangeInterval) {
        this.x = x;
        this.y = y;
        this.sprites = sprites;
        this.speed = speed;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.frameChangeInterval = frameChangeInterval;
        currentSprite = this.sprites.get(0);
        framesSinceLastChange = 0;
    }

    public void move() {
        this.x += speed * xDirection;
        this.y += speed * yDirection;
    }

    public void changeSprite(){
        if(framesSinceLastChange >= frameChangeInterval){
            nextSprite();
            framesSinceLastChange = 0;
        }
        framesSinceLastChange++;

    }

    private void nextSprite() {
        currentSpriteIndex++;
        if(currentSpriteIndex==sprites.size()) currentSpriteIndex=0;
        currentSprite = sprites.get(currentSpriteIndex);
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

    public float getyDirection() {
        return yDirection;
    }

    public void setyDirection(float yDirection) {
        this.yDirection = yDirection;
    }

    public List<Bitmap> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Bitmap> sprites) {
        this.sprites = sprites;
    }

    public Bitmap getCurrentSprite() {
        return currentSprite;
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
}
