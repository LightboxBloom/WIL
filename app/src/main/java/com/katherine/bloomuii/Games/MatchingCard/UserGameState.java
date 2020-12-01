package com.katherine.bloomuii.Games.MatchingCard;

public class UserGameState {
    private String Level;
    private int Achievement;
    private int ConsecutiveMatches;

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        this.Level = level;
    }

    public int getAchievement() {
        return Achievement;
    }

    public void setAchievement(int achievement) {
        Achievement = achievement;
    }

    public int getConsecutiveMatches() {
        return ConsecutiveMatches;
    }

    public void setConsecutiveMatches(int consecutiveMatches) {
        ConsecutiveMatches = consecutiveMatches;
    }
}
