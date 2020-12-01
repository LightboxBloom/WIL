package com.katherine.bloomuii.Games.MatchingCard;

import java.util.ArrayList;

public class FlashcardUpload {
    private String BackgroundColor;
    private String Caption;
    private String OwnerUid;
    private long ClassroomId;
    private String Sentence;
    private ArrayList<String> StorageUris;
    private String TextColor;

    public FlashcardUpload(String backgroundColor, String caption, String ownerUid, long classroomId, String sentence, ArrayList<String> storageUris, String textColor) {
        BackgroundColor = backgroundColor;
        Caption = caption;
        OwnerUid = ownerUid;
        Sentence = sentence;
        StorageUris = storageUris;
        TextColor = textColor;
        ClassroomId = classroomId;
    }

    public FlashcardUpload() {

    }

    public String getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getOwnerUid() {
        return OwnerUid;
    }

    public void setOwnerUid(String ownerUid) {
        OwnerUid = ownerUid;
    }

    public String getSentence() {
        return Sentence;
    }

    public void setSentence(String sentence) {
        Sentence = sentence;
    }

    public ArrayList<String> getStorageUris() {
        return StorageUris;
    }

    public void setStorageUris(ArrayList<String> storageUris) {
        StorageUris = storageUris;
    }

    public String getTextColor() {
        return TextColor;
    }

    public void setTextColor(String textColor) {
        TextColor = textColor;
    }

    public long getClassroomId() {
        return ClassroomId;
    }

    public void setClassroomId(long classroomId) {
        ClassroomId = classroomId;
    }


}
