package com.katherine.bloomuii.ObjectClasses;

import android.graphics.drawable.Drawable;

import com.katherine.bloomuii.Adapters.DiaryAdapter;

public class DiaryEntry {
    private String Diary_Date;
    private String Diary_Emotion;
    private String Diary_Entry;

    public DiaryEntry(){}
    public DiaryEntry(String diaryDate, String diaryEmotion, String diaryEntry){
        this.setDiary_Date(diaryDate);
        this.setDiary_Emotion(diaryEmotion);
        this.setDiary_Entry(diaryEntry);
    }

    public String getDiary_Date() {
        return Diary_Date;
    }

    public void setDiary_Date(String diaryDate) {
        this.Diary_Date = diaryDate;
    }

    public String getDiary_Emotion() {
        return Diary_Emotion;
    }

    public void setDiary_Emotion(String diaryEmotion) {
        this.Diary_Emotion = diaryEmotion;
    }

    public String getDiary_Entry() {
        return Diary_Entry;
    }

    public void setDiary_Entry(String diaryEntry) {
        this.Diary_Entry = diaryEntry;
    }
}
