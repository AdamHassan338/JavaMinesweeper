package org.example;

import com.raylib.Jaylib;
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

                Raylib.Color colour = WHITE;

                if(grid[x][y].isReveald())
                    colour = RED;

                //draw outline
                DrawRectangle(xPos , yPos , size, size , BLACK);

                //Draw inside
                DrawRectangle(xPos + leftBorder , yPos + topBorder, size - leftBorder- borderThinkness , size- topBorder- borderThinkness, colour);

            }
        }
        EndDrawing();
    }




    private void processInput(){
        mousePos = GetMousePosition();

        if( IsMouseButtonPressed(MOUSE_BUTTON_LEFT)){
            Raylib.Vector2 cellIndex = pixelToGrid(mousePos);
            grid[(int) cellIndex.x()][(int) cellIndex.y()].setReveald(true);
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

