package org.example;


public class Cell {
    public boolean isMined() {
        return isMined;
    }

    private  boolean isMined;
    private boolean isFlagged = false;
    private boolean isReveald = false;

    int neigbours;

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isReveald() {
        return isReveald;
    }

    public void setReveald(boolean reveald) {
        isReveald = reveald;
    }


    public Cell(boolean isMined){
        this.isMined = isMined;
    }
}
