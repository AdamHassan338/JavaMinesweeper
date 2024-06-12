package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Minesweeper {

    private int number;
    private int size;
    private Raylib.Vector2 mousePos;

    private Cell[][] grid;

    public Minesweeper(){
    }
    public void init(int windowWidth,int WindowHeight){
        InitWindow(windowWidth ,WindowHeight,"Minesweeper");


    }


    public void run(int cells){
        number = cells;
        size = GetScreenWidth()/number;

        populateGrid(cells);

        while(!WindowShouldClose()){
            processInput();
            draw();
        }
    }


    private void draw(){
        BeginDrawing();
        int borderThinkness = 4;

        int spacing = size;

        int xPos = 0;
        int yPos = 0;

        for(int x =0 ; x<number; x++){
            for(int y =0; y<number; y++) {
                xPos = x*spacing;
                yPos = y * spacing;
                int topBorder = y==0 ? borderThinkness : 0;
                int leftBorder = x==0 ? borderThinkness : 0;
                //draw outline
                DrawRectangle(xPos , yPos , size, size , BLACK);

                //Draw inside
                DrawRectangle(xPos + leftBorder , yPos + topBorder, size - leftBorder- borderThinkness , size- topBorder- borderThinkness, WHITE);

            }
        }
        EndDrawing();
    }




    private void processInput(){
        mousePos = GetMousePosition();
    }

    private void populateGrid(int cells){
        grid = new Cell[cells][cells];

        for(int i = 0; i<cells; i++){
            for(int j = 0; j<cells; j++){
                grid[i][j] = new Cell(false);
            }
        }

    }
}
