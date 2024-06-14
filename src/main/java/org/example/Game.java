package org.example;

import com.raylib.Raylib;

abstract class Game {
    public enum Diffculty{
        easy,
        medium,
        hard
    }
    Game(){
        this.mousePos = new Raylib.Vector2();
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    protected boolean gameEnd;

    public Raylib.Vector2 getMousePos() {
        return mousePos;
    }

    public void setMousePos(Raylib.Vector2 mousePos) {
        this.mousePos = mousePos;
    }

    protected  Raylib.Vector2 mousePos;
    abstract void draw();


}
