package org.example;

import com.raylib.Raylib;

import java.util.Scanner;

import static com.raylib.Raylib.*;

public class GameController {
    private Minesweeper game;
    private Raylib.Vector2 mousePos;
    private boolean enableUI;
    private Scanner sc;

    public GameController(){

    }

    public void init(int windowWidth, int WindowHeight,int cells) {
        InitWindow(windowWidth, WindowHeight, "Minesweeper");
        CellRenderer.init();
        CellRenderer.debugMode = false;
        game = new Minesweeper();
        game.init(cells);
        game.setConsoleMode(false);
        enableUI = true;

    }

    public void init(int cells) {
        game = new Minesweeper();
        game.init(cells);
        game.setConsoleMode(true);
        enableUI = false;

        sc=new Scanner(System.in);


    }

    public void begin(){
        if (enableUI) {
            while (!WindowShouldClose()) {
                processInput();
                game.draw();
            }
        }else{
            while(true) {
                processInput();
                game.draw();
            }
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
