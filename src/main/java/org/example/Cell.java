package org.example;


public class Cell {
    private  boolean isMined;
    private boolean isFlagged = false;
    private boolean isReveald = false;

    public Cell(boolean isMined){
        this.isMined = isMined;
    }
}
