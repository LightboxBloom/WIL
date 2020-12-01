package com.katherine.bloomuii.Games.PartOfSpeech;
/*
 * Class name: Word
 * Reason: This class is the Object class for each word
 * Parameters:
 * Team member: Matthew Talbot
 * Version 3:
 * 20/10/2020*/
public class Word {
    String text, partOfSpeech ;
    public Word(String text, String partOfSpeech) {
        this.text = text;
        this.partOfSpeech = partOfSpeech;
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
