package org.example;

import com.raylib.Raylib;

import static com.raylib.Raylib.*;

public class GameController {
    private Minesweeper game;
    private Raylib.Vector2 mousePos;

    public GameController(){

    }

    public void init(int windowWidth, int WindowHeight,int cells) {
        InitWindow(windowWidth, WindowHeight, "Minesweeper");
        CellRenderer.debugMode = false;
        game = new Minesweeper();
        game.init(cells);

    }

    public void begin(){

        while (!WindowShouldClose()) {
            processInput();
            game.draw();
        }
    }



    private void processInput() {
        mousePos = GetMousePosition();
        game.setMousePos(mousePos);
        if(game.isGameEnd())
            return;
        Raylib.Vector2 cellIndex = game.pixelToGrid(mousePos);
        if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            game.intertactGrid((int) cellIndex.x(), (int) cellIndex.y(), Minesweeper.Action.reveal);

        }

        if (IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)) {
            game.intertactGrid((int) cellIndex.x(), (int) cellIndex.y(), Minesweeper.Action.toggleFlag);

        }



    }

}
