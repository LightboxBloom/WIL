package com.k18003144.myapplication;
//package com.katherine.bloomuii.Games.MatchingCard;

public class GameSettings {
    private Difficulty difficulty; //0 = easy, 1 = medium, 2 = hard
//    private int[] showTimes = {10, 8, 6, 5, 4, 3}; // Durations to show cards for
    private int[] showTimes = {3, 3, 3, 3, 3, 3}; // Durations to show cards for
    private int[] attempts = {20, 20, 15, 15, 10, 10};

    public GameSettings(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getRevealTime() {
        int index;
        switch (difficulty) {
            // EASY IS 0, SAME AS DEFAULT
            case EASY_PLUS:
                index = 1;
                break;
            case MEDIUM:
                index = 2;
                break;
            case MEDIUM_PLUS:
                index = 3;
                break;
            case HARD:
                index = 4;
                break;
            case HARD_PLUS:
                index = 5;
                break;
            default:
                index = 0;
        }
        return showTimes[index] * 3;
    }

    public int getAttempts() {
        int index;
        switch (difficulty) {
            // EASY IS 0, SAME AS DEFAULT
            case EASY_PLUS:
                index = 1;
                break;
            case MEDIUM:
                index = 2;
                break;
            case MEDIUM_PLUS:
                index = 3;
                break;
            case HARD:
                index = 4;
                break;
            case HARD_PLUS:
                index = 5;
                break;
            default:
                index = 0;
        }
        return attempts[index];
    }
}

