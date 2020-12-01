package com.katherine.bloomuii.Games.MatchingCard;

import java.util.ArrayList;

public class FlashcardUploadSnapshot {
    private ArrayList<FlashcardUpload> flashcardUploads;

    public FlashcardUploadSnapshot(ArrayList<FlashcardUpload> flashcardUploads) {
        this.flashcardUploads = flashcardUploads;
    }

    public FlashcardUploadSnapshot() {
        flashcardUploads = new ArrayList<>();
    }

    public ArrayList<FlashcardUpload> getFlashcardUploads() {
        return flashcardUploads;
    }

    public void setFlashcardUploads(ArrayList<FlashcardUpload> flashcardUploads) {
        this.flashcardUploads = flashcardUploads;
    }
}
