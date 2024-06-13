package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Minesweeper {

    private int number;
    private int size;
    private Raylib.Vector2 mousePos;

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

        populateGrid(cells);

        while (!WindowShouldClose()) {
            processInput();
            draw();
        }
    }


    private void draw() {
        BeginDrawing();

        drawGrid();

        EndDrawing();
    }

    private void drawGrid() {
        int borderThinkness = 4;

        int spacing = size;
        CellRenderer.spacing = spacing;
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
            reavelCell((int) cellIndex.x(), (int) cellIndex.y());
        }

        if (IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)) {
            c.setFlagged(!c.isFlagged());
        }

    }

    private void populateGrid(int cells) {
        grid = new Cell[cells][cells];

        double random = Math.random() * 100;


        for (int i = 0; i < cells; i++) {
            for (int j = 0; j < cells; j++) {
                random = Math.random() * 100;
                grid[i][j] = new Cell(random < 20 ? true : false);
            }
        }

        for (int i = 0; i < cells; i++) {
            for (int j = 0; j < cells; j++) {
                int count = 0;

                if (i + 1 < cells) count += grid[i + 1][j].isMined() ? 1 : 0;
                if (j + 1 < cells) count += grid[i][j + 1].isMined() ? 1 : 0;
                if (i + 1 < cells && j + 1 < cells) count += grid[i + 1][j + 1].isMined() ? 1 : 0;
                if (i - 1 >= 0) count += grid[i - 1][j].isMined() ? 1 : 0;
                if (j - 1 >= 0) count += grid[i][j - 1].isMined() ? 1 : 0;
                if (i - 1 >= 0 && j - 1 >= 0) count += grid[i - 1][j - 1].isMined() ? 1 : 0;
                if (i - 1 >= 0 && j + 1 < cells) count += grid[i - 1][j + 1].isMined() ? 1 : 0;
                if (i + 1 < cells && j - 1 >= 0) count += grid[i + 1][j - 1].isMined() ? 1 : 0;

                grid[i][j].neigbours = count;
            }
        }

    }


    private Raylib.Vector2 pixelToGrid(Raylib.Vector2 screenCordinates) {
        Raylib.Vector2 gridCords = new Raylib.Vector2();
        gridCords.x((float) Math.floor((screenCordinates.x() / size)));
        gridCords.y((float) Math.floor((screenCordinates.y() / size)));
        return gridCords;

    }


    private void reavelCell(int x, int y) {
        Cell c = grid[x][y];
        if (!c.isMined() && !c.isReveald() && !c.isFlagged())
            floodFill(x, y);
        if(c.isMined()){
            c.setReveald(true);
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



}

