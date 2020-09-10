package com.k18003144.myapplication;
//package com.katherine.bloomuii.Games.MatchingCard;

public class CanvasGrid {
    // X and Y should correspond to aspect ratio. Most common aspect ratio being 16:9
    private int numXCells;
    private int numYCells;
    private float cellWidth;
    private float cellHeight;

    private static CanvasGrid instance = null;

    private CanvasGrid(int numXCells, int numYCells, float screenWidth, float screenHeight) {
        this.numXCells = numXCells;
        this.numYCells = numYCells;
        this.cellWidth = screenWidth /numXCells;
        this.cellHeight = screenHeight /numYCells;
    }

    public static CanvasGrid getInstance(){
        return instance;
    }

    public static CanvasGrid getInstance(int numXCells, int numYCells, float screenWidth, float screenHeight){
        instance = new CanvasGrid(numXCells, numYCells, screenWidth, screenHeight);
        return instance;
    }

    //Pass in how many blocks from left and get pixel distance
    public float getXPixels(float xBlocks){
        return xBlocks * cellWidth;
    }

    //Pass in how many blocks from top and get pixel distance
    public float getYPixels(float yBlocks){
        return yBlocks * cellWidth;
    }

    public int getNumXCells() {
        return numXCells;
    }

    public void setNumXCells(int numXCells) {
        this.numXCells = numXCells;
    }

    public int getNumYCells() {
        return numYCells;
    }

    public void setNumYCells(int numYCells) {
        this.numYCells = numYCells;
    }

    public float getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(float cellWidth) {
        this.cellWidth = cellWidth;
    }

    public float getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(float cellHeight) {
        this.cellHeight = cellHeight;
    }
}
