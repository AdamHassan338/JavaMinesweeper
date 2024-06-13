package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.IsMouseButtonPressed;


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

        drawGrid();

        EndDrawing();
    }

    private void drawGrid(){
        int borderThinkness = 4;

        int spacing = size;
        CellRenderer.spacing = spacing;
        CellRenderer.borderThinkness = 4;

        for(int x =0 ; x<number; x++){
            for(int y =0; y<number; y++) {
                Cell c = grid[x][y];
                CellRenderer.drawCell(x,y,c.isFlagged(),c.isReveald(),c.isMined());

            }
        }
    }




    private void processInput(){
        mousePos = GetMousePosition();
        Raylib.Vector2 cellIndex = pixelToGrid(mousePos);
        Cell c = grid[(int) cellIndex.x()][(int) cellIndex.y()];
        if( IsMouseButtonPressed(MOUSE_BUTTON_LEFT)){
            c.setReveald(true);
        }

        if(IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)){
            c.setFlagged(!c.isFlagged());
        }

    }

    private void populateGrid(int cells){
        grid = new Cell[cells][cells];

        double random = Math.random() * 100;



        for(int i = 0; i<cells; i++){
            for(int j = 0; j<cells; j++){
                random = Math.random() * 100;
                grid[i][j] = new Cell(random < 20 ? true : false);
            }
        }

    }


    private Raylib.Vector2 pixelToGrid(Raylib.Vector2 screenCordinates){
        Raylib.Vector2 gridCords = new Raylib.Vector2();
        gridCords.x((float) Math.floor((screenCordinates.x()/size)));
        gridCords.y((float) Math.floor((screenCordinates.y()/size)));
        return gridCords;

    }

}

