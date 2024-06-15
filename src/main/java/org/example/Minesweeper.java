package org.example;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Minesweeper extends Game {
    public enum Action{
        reveal,
        toggleFlag
    }

    public enum Diffculty{
        easy,
        medium,
        hard
    }
    static Texture mineTexture;
    private  Raylib.Vector2 mousePos;
    private int size;
    private boolean gameStart =false;


    private boolean gameWon;

    private Diffculty diffculty;

    private Grid grid;

    private boolean consoleMode;

    public static final String name = "Minesweeper";

    private long startTime =0;
    private long endTime =0;
    private long duration = 0;


    public Minesweeper() {
    }

    public Raylib.Vector2 getGridDimentions() {
        Raylib.Vector2 v = new Raylib.Vector2();
        v.x(grid.getColumns());
        v.y(grid.getRows());
        return v;
    }

    public void setDiffculty(Diffculty diffculty) {
        this.diffculty = diffculty;
    }

    public void setMousePos(Raylib.Vector2 mousePos) {
        this.mousePos = mousePos;
        grid.hoverIndex = pixelToGrid(mousePos);

    }

    public long getDuration() {
        return duration;
    }


    public void setConsoleMode(boolean consoleMode) {
        this.consoleMode = consoleMode;
        if(!consoleMode && mineTexture == null){
            mineTexture = LoadTexture("Assets/mine.png");
        }
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
        endTime = System.currentTimeMillis();
        duration = (endTime-startTime);
    }


    public void init(int cells,Diffculty diffculty) {
        grid = new Grid(cells,cells);
        size = (int)(GetScreenHeight()-75) / cells;
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
        startTime =0;
        endTime=0;
        duration =0;
        grid.populateGrid(diffculty);
        mousePos = new Raylib.Vector2();
        if(consoleMode)
            grid.draw(false);
    }

    @Override
    public void draw() {

        if(consoleMode){
            grid.draw(false);
            return;
        }

        if(gameStart && !gameEnd){
            endTime = System.currentTimeMillis();
            duration = (endTime-startTime);
        }

        int textSize = 200;
        int textHeight = textSize*2;
        int timeWidth = MeasureText(String.valueOf(duration/1000),50);
        int minesWidth = MeasureText(String.valueOf(grid.getNumberMines()),50);
        Raylib.Color textColour = RED;
        rlClearScreenBuffers();
        BeginDrawing();
            ClearBackground(LIGHTGRAY);
            //bottom
            DrawRectangle(0,725,GetScreenWidth(),75,LIGHTGRAY);

            grid.draw(true);

            DrawText(String.valueOf(duration/1000),(GetScreenWidth()-timeWidth)/2,725 +(75-50)/2,50,BLACK);
            DrawText(String.valueOf(grid.getNumberMines()),(GetScreenWidth()-minesWidth)/8 -(mineTexture.width()),725 +(75-50)/2,50,BLACK);
            DrawTexture(mineTexture,(GetScreenWidth()/8) +(mineTexture.width())/2,725 +(75-mineTexture.height())/2,WHITE);

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
            //semi transparent overlay
            DrawRectangle(0,0,GetScreenWidth(),GetScreenHeight(), new Jaylib.Color(0,0,0,150));

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
                    startTime = System.currentTimeMillis();
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

