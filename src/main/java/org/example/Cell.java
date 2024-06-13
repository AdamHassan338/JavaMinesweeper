package org.example;


public class Cell {
    private boolean isMined;
    private boolean isFlagged = false;
    private boolean isReveald = false;



    int x;
    int y;

    int neigbours;

    public Cell(int x, int y,boolean isMined){
        this.isMined = isMined;
        this.x = x;
        this.y = y;
    }

    public boolean isMined() {
        return isMined;
    }

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


}
