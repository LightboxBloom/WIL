package com.katherine.bloomuii.ObjectClasses;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    public static List<String>[] sentenceArray; //An array of lists used to store the sentences
    private int countSentences; //this is used to track how many sentences are in the db

    public Sentence(){}

    public int getCount(){ //getter
        return  countSentences;
    }
    public void setCount(int count){ //setter
        this.countSentences = count;
    }
    public static void arrayCreate(int n) { //Creates the sentenceArray that has n values (n will be set to the number of sentences in the db)
        sentenceArray = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            sentenceArray[i] = new ArrayList<String>();
        }
    }
}
