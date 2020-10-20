package com.e.partsofspeechdemo;

import android.graphics.drawable.Drawable;

public class Word {
    String text, partOfSpeech ;

    Drawable wordResource;

    public Word(String text, String partOfSpeech, Drawable wordResource) {
        this.text = text;
        this.partOfSpeech = partOfSpeech;
        this.wordResource = wordResource;
    }

    public Drawable getWordResource() {
        return wordResource;
    }

    public void setWordResource(Drawable wordResource) {
        this.wordResource = wordResource;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
