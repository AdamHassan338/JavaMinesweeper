package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Minesweeper {

    private int number;
    private int size;
    private Raylib.Vector2 mousePos;
    private boolean gameStart =false;
    private boolean firstClick = true;
    private boolean gameEnd;
    private boolean gameWon;
    private Cell[][] grid;

    public Minesweeper() {
    }

    public void init(int windowWidth, int WindowHeight) {
        InitWindow(windowWidth, WindowHeight, "Minesweeper");
        CellRenderer.debugMode = false;

    }


    public void run(int cells) {
        number = cells;
        size = GetScreenWidth() / number;
        gameEnd = false;
        gameWon = false;
        gameStart=false;
        firstClick = false;
        populateGrid(cells);
        firstClick = true;

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

            drawGrid();

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

    private void drawGrid() {

        CellRenderer.spacing = size;
        CellRenderer.borderThinkness = 4;

        for (int x = 0; x < number; x++) {
            for (int y = 0; y < number; y++) {
                Cell c = grid[x][y];
                CellRenderer.drawCell(x, y, c.neigbours, c.isFlagged(), c.isReveald(), c.isMined());

            }
        }
    }


    private void processInput() {
        mousePos = GetMousePosition();
        Raylib.Vector2 cellIndex = pixelToGrid(mousePos);
        Cell c = grid[(int) cellIndex.x()][(int) cellIndex.y()];
        if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            if(!gameStart){
                gameStart = true;
                if(grid[(int)cellIndex.x()][(int) cellIndex.y()].isMined())
                    populateGrid(number);
            }
            revealCell((int) cellIndex.x(), (int) cellIndex.y());
            firstClick = false;
            //return;
        }

        if (IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)) {
            if(!gameStart)
                return;
            c.setFlagged(!c.isFlagged());
        }



    }

    private void populateGrid(int cells) {
        grid = new Cell[cells][cells];

        double random;


        for (int i = 0; i < cells; i++) {
            for (int j = 0; j < cells; j++) {
                random = Math.random() * 100;
                grid[i][j] = new Cell(random < 20);
                if(firstClick){
                    Raylib.Vector2 cellIndex = pixelToGrid(mousePos);

                    if( ((int) cellIndex.x()) ==i && (int) cellIndex.y()==j)
                        grid[i][j] = new Cell( false);
                }

            }
        }

        for (int i = 0; i < cells; i++) {
            for (int j = 0; j < cells; j++) {

                grid[i][j].neigbours = calculateNeighbours(cells, i, j);
            }
        }

    }

    private int calculateNeighbours(int cells, int i, int j) {
        int count = 0;

        if (i + 1 < cells) count += grid[i + 1][j].isMined() ? 1 : 0;
        if (j + 1 < cells) count += grid[i][j + 1].isMined() ? 1 : 0;
        if (i + 1 < cells && j + 1 < cells) count += grid[i + 1][j + 1].isMined() ? 1 : 0;
        if (i - 1 >= 0) count += grid[i - 1][j].isMined() ? 1 : 0;
        if (j - 1 >= 0) count += grid[i][j - 1].isMined() ? 1 : 0;
        if (i - 1 >= 0 && j - 1 >= 0) count += grid[i - 1][j - 1].isMined() ? 1 : 0;
        if (i - 1 >= 0 && j + 1 < cells) count += grid[i - 1][j + 1].isMined() ? 1 : 0;
        if (i + 1 < cells && j - 1 >= 0) count += grid[i + 1][j - 1].isMined() ? 1 : 0;
        return count;
    }


    private Raylib.Vector2 pixelToGrid(Raylib.Vector2 screenCoordinates) {
        Raylib.Vector2 gridCords = new Raylib.Vector2();
        gridCords.x((float) Math.floor((screenCoordinates.x() / size)));
        gridCords.y((float) Math.floor((screenCoordinates.y() / size)));
        return gridCords;

    }


    private void revealCell(int x, int y) {
        Cell c = grid[x][y];
        if (!c.isMined() && !c.isReveald() && !c.isFlagged())
            floodFill(x, y);
        if(c.isMined()){
            c.setReveald(true);
            gameEnd = true;
        }

        if(!isCellsLeft()){
            gameWon = true;
            gameEnd = true;
        }
    }


    private void floodFill(int x, int y) {
        if (x > number - 1 || x < 0 || y > number - 1 || y < 0)
            return;

        if (grid[x][y].isMined() || grid[x][y].isReveald())
            return;

        grid[x][y].setReveald(true);

        if (grid[x][y].neigbours > 0)
            return;


        floodFill(x + 1, y);

        floodFill(x, y + 1);

        floodFill(x + 1, y + 1);

        floodFill(x - 1, y);

        floodFill(x, y - 1);

        floodFill(x - 1, y - 1);

        floodFill(x - 1, y + 1);

        floodFill(x + 1, y - 1);

    }

    private boolean isCellsLeft(){
        for(Cell[] a : grid){
            for(Cell c : a){
                if(!c.isReveald()&&!c.isMined())
                    return true;
            }
        }
        return false;
    }



}

