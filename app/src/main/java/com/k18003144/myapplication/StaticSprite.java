package com.k18003144.myapplication;
//package com.katherine.bloomuii.Games.MatchingCard;

import android.graphics.Bitmap;

public class StaticSprite {
    protected float x;
    protected float y;
    private Bitmap image;

    public StaticSprite(float x, float y, Bitmap image){
        this.x = x;
        this.y = y;
        this.image = image;
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
