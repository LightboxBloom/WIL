package com.katherine.bloomuii.Games.Puzzle;

public class UserScore {
    private final int[] ACHIEVEMENTS = {1, 20, 50, 100};
    private static UserScore mInstance = null;
    public int score = 0;
    protected UserScore(){}

    public static synchronized UserScore getInstance() {
        if(null == mInstance){
            mInstance = new UserScore();
        }
        return mInstance;
    }
    public void addScore(){
        score++;
    }
    public int getScore(){
        return score;
    }
    public boolean hasAchievement(){
        for (int i:ACHIEVEMENTS) {
            if (i==score)
                return true;
        }
        return false;
    }
}
