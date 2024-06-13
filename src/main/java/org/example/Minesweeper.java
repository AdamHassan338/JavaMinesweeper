package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Minesweeper {
    public enum Action{
        reveal,
        toggleFlag
    }
    private  Raylib.Vector2 mousePos;
    private int size;
    private boolean gameStart =false;

    private boolean gameEnd;
    private boolean gameWon;

    private Grid grid;

    public static final String name = "Minesweeper";


    public Minesweeper() {
    }


    public void setMousePos(Raylib.Vector2 mousePos) {
        this.mousePos = mousePos;
        grid.hoverIndex = pixelToGrid(mousePos);

    }



    public boolean isGameStart() {
        return gameStart;
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }



    public boolean isGameEnd() {
        return gameEnd;
    }


    public void endGame(boolean win){
        if(win)
            gameWon = true;
        gameEnd = true;
    }


    public void init(int cells) {
        grid = new Grid(cells,cells);
        size = GetScreenWidth() / cells;
        CellRenderer.spacing = size;
        gameEnd = false;
        gameWon = false;
        gameStart=false;
        grid.populateGrid();
        mousePos = new Raylib.Vector2();


    }


    public void draw() {
        int textSize = 200;
        int textHeight = textSize*2;
        Raylib.Color textColour = RED;
        rlClearScreenBuffers();
        BeginDrawing();

            grid.draw();

        if(gameEnd) {
            textColour = RED;
            String topText = "GAME";
            String bottomText = "OVER";
            if(gameWon){
                textColour = GREEN;
                textSize = 100;
                textHeight = textSize;
                topText = "YOU WON";
                bottomText = "";
            }


            int textWidth = MeasureText(topText,textSize);
            int textWidth2 = MeasureText(bottomText,textSize);

            DrawText(topText,(GetScreenWidth() - textWidth)/2,(GetScreenHeight()-textHeight)/2,textSize,textColour);

            DrawText(bottomText,(GetScreenWidth() - textWidth2)/2,(GetScreenHeight()-textHeight)/2 + textSize,textSize,textColour);

        }


        EndDrawing();
    }

    public void intertactGrid(int x, int y, Action action){
        switch (action){
            case reveal:
                if(!isGameStart()){
                    setGameStart(true);
                    if(grid.cellAt(x,y).isMined())
                        grid.populateGrid();
                }

                if(!grid.revealCell(x,y)){
                    endGame(false);
                }
                if(!grid.isCellsLeft()){
                    endGame(true);
                }
                break;
            case toggleFlag:
                if(!isGameStart())
                    return;
                Cell c = grid.cellAt(x,y);
                c.setFlagged(!c.isFlagged());
                break;
        }
    }

    public Raylib.Vector2 pixelToGrid(Raylib.Vector2 screenCoordinates) {
        Raylib.Vector2 gridCords = new Raylib.Vector2();
        gridCords.x((float) Math.floor((screenCoordinates.x() / size)));
        gridCords.y((float) Math.floor((screenCoordinates.y() / size)));
        return gridCords;

    }



}

