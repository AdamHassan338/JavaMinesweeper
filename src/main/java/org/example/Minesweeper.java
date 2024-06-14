package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Minesweeper {
    public enum Action{
        reveal,
        toggleFlag
    }

    public enum Diffculty{
        easy,
        medium,
        hard
    }

    private  Raylib.Vector2 mousePos;
    private int size;
    private boolean gameStart =false;

    private boolean gameEnd;
    private boolean gameWon;

    private Diffculty diffculty;

    private Grid grid;

    private boolean consoleMode;

    public static final String name = "Minesweeper";


    public Minesweeper() {
    }

    public Raylib.Vector2 getGridDimentions() {
        Raylib.Vector2 v = new Raylib.Vector2();
        v.x(grid.getColumns());
        v.y(grid.getRows());
        return v;
    }

    public void setMousePos(Raylib.Vector2 mousePos) {
        this.mousePos = mousePos;
        grid.hoverIndex = pixelToGrid(mousePos);

    }


    public void setConsoleMode(boolean consoleMode) {
        this.consoleMode = consoleMode;
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

    public boolean isGameWon() {
        return gameWon;
    }


    public void endGame(boolean win){
        if(win)
            gameWon = true;
        gameEnd = true;
    }


    public void init(int cells,Diffculty diffculty) {
        grid = new Grid(cells,cells);
        size = GetScreenWidth() / cells;
        CellRenderer.spacing = size;
        gameEnd = false;
        gameWon = false;
        gameStart=false;
        this.diffculty = diffculty;
        grid.populateGrid(diffculty);
        mousePos = new Raylib.Vector2();


    }

    public void restart(){
        gameEnd = false;
        gameWon = false;
        gameStart=false;
        grid.populateGrid(diffculty);
        mousePos = new Raylib.Vector2();
        if(consoleMode)
            grid.draw(false);
    }


    public void draw() {

        if(consoleMode){
            grid.draw(false);
            return;
        }

        int textSize = 200;
        int textHeight = textSize*2;
        Raylib.Color textColour = RED;
        rlClearScreenBuffers();
        BeginDrawing();

            grid.draw(true);

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
                        grid.populateGrid(diffculty);
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
                if(c.isReveald())
                    break;
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

