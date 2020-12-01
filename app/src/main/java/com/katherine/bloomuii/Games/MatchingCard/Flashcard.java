package com.katherine.bloomuii.Games.MatchingCard;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Flashcard {
    private ArrayList<Bitmap> bitmaps;
    public Flashcard() {
        bitmaps = new ArrayList<>();
    }
    public Flashcard(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
    public void addBitmap(Bitmap bitmap){
        this.bitmaps.add(bitmap);
    }
    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }
    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
}
