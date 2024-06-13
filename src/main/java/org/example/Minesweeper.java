package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Minesweeper {

    private int number;
    private int size;
    private Raylib.Vector2 mousePos;
    private boolean gameStart =false;
    private boolean gameEnd;
    private boolean gameWon;

    private Grid grid;

    public Minesweeper() {
    }

    public void init(int windowWidth, int WindowHeight) {
        InitWindow(windowWidth, WindowHeight, "Minesweeper");
        CellRenderer.debugMode = true;

    }




    public void run(int cells) {
        grid = new Grid(cells,cells);
        size = GetScreenWidth() / cells;
        CellRenderer.spacing = size;
        gameEnd = false;
        gameWon = false;
        gameStart=false;
        grid.populateGrid();

        while (!WindowShouldClose()) {
            processInput();
            draw();
        }
    }


    private void draw() {
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




    private void processInput() {
        mousePos = GetMousePosition();
        if(gameEnd)
            return;
        Raylib.Vector2 cellIndex = pixelToGrid(mousePos);
        Cell c = grid.cellAt((int) cellIndex.x(),(int) cellIndex.y());
        if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            if(!gameStart){
                gameStart = true;
                if(grid.cellAt((int)cellIndex.x(),(int) cellIndex.y()).isMined())
                    grid.populateGrid();
            }

            if(!grid.revealCell((int) cellIndex.x(), (int) cellIndex.y())){
                gameEnd = true;
            }
            if(!grid.isCellsLeft()){
                gameWon = true;
                gameEnd = true;
            }
            //return;
        }

        if (IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)) {
            if(!gameStart)
                return;
            c.setFlagged(!c.isFlagged());
        }



    }





    private Raylib.Vector2 pixelToGrid(Raylib.Vector2 screenCoordinates) {
        Raylib.Vector2 gridCords = new Raylib.Vector2();
        gridCords.x((float) Math.floor((screenCoordinates.x() / size)));
        gridCords.y((float) Math.floor((screenCoordinates.y() / size)));
        return gridCords;

    }












}

