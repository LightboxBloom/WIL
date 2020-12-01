package com.katherine.bloomuii.Games.MatchingCard;

import java.util.ArrayList;

public class FlashcardUpload {
    private String _id;
    private String BackgroundColor;
    private String Caption;
    private String OwnerUid;
    private String Sentence;
    private ArrayList<String> StorageUris;
    private String TextColor;

    public FlashcardUpload(String _id, String backgroundColor, String caption, String ownerUid, String sentence, ArrayList<String> storageUris, String textColor) {
        this._id = _id;
        BackgroundColor = backgroundColor;
        Caption = caption;
        OwnerUid = ownerUid;
        Sentence = sentence;
        StorageUris = storageUris;
        TextColor = textColor;
    }

    public FlashcardUpload() {
        _id = "";
        BackgroundColor = "";
        Caption = "";
        OwnerUid = "";
        Sentence = "";
        StorageUris = new ArrayList<>();
        TextColor = "";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
}
