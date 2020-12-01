//Developer:Rohini Naidu
package com.katherine.bloomuii.Handlers;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class StoryBoardHandler {
    private Calendar date;
    private ArrayList<String> randomStory;
    private ArrayList<String> images;
    private String image;
    private String message;
    //Constructor
    public StoryBoardHandler(){
        setDate(Calendar.getInstance());
        initializeArray();
        randomizeStory();
    }
    //Initialize Arrays
    private void initializeArray(){
        randomStory = new ArrayList<>();
        images = new ArrayList<>();

        randomStory.add("Raise you hand when you want to speak");
        randomStory.add("Be kind to others");
        randomStory.add("Baths are good!");
        randomStory.add("Look tidy");
        randomStory.add("Brush your teeth");
        randomStory.add("Clothes");

        images.add("boyatdesk");
        images.add("friends");
        images.add("bath");
        images.add("brushhair");
        images.add("brushteeth");
        images.add("changing");
    }
    //Randomize Story
    private void randomizeStory(){
       Random algo = new Random();
       int index = algo.nextInt(randomStory.size()-1);
        setImage(images.get(index));
        setMessage(randomStory.get(index));
    }
    //Getters and Setters
    public Calendar getDate() {
        return date;
    }
    public void setDate(Calendar date) {
        this.date = date;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
