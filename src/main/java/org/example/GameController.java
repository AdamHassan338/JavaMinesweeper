package org.example;

import com.raylib.Raylib;

import java.util.Scanner;

import static com.raylib.Raylib.*;

public class GameController {
    private Minesweeper game;
    private Vector2 mousePos;
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
                game.draw();
                processInput();

            }
        }

    }



    private void processInput() {
        Vector2 cellIndex;
        Minesweeper.Action action = null;

        if(!enableUI){

            if(game.isGameEnd()){
                System.out.println("");
                if(game.isGameWon())
                    System.out.println("YOU WON");
                else
                    System.out.println("YOU LOST\n");
                System.out.println("Do you want to play again? [Yes,No] :");
                if(getYesNoInput())
                    game.restart();
                else {
                    System.out.printf("GOOD BYE");
                    System.exit(0);
                }
            }

            System.out.println("");
            action =  getAction();
            cellIndex = getCoordinate();

        }else{
            mousePos = GetMousePosition();
            game.setMousePos(mousePos);
            if(game.isGameEnd()){
                if(IsKeyPressed(KEY_R))
                    game.restart();
                return;
            }

            cellIndex = game.pixelToGrid(mousePos);
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                action = Minesweeper.Action.reveal;

            }

            if (IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)) {
                action = Minesweeper.Action.toggleFlag;

            }
        }

        try{
            game.intertactGrid((int) cellIndex.x(), (int) cellIndex.y(), action);
        }catch (Exception e){
            //action is uninitalised for the first game loop when in Window mode
            //do nothing is fine
        }


    }

    private boolean getYesNoInput(){
        while (true) {
            String input = sc.nextLine();
            switch (input.trim().toUpperCase()) {
                case "YES", "Y":
                    return true;
                case "NO", "N":
                    return false;
                default:
                    System.out.println("INVALID INPUT, ENTER YES OR NO");
            }
        }

    }

    private Minesweeper.Action getAction(){
        while(true){
            System.out.println("");
            System.out.print("Enter action to take, R (reveal a cell) or F (flag/unflag a cell): ");
            String responce = sc.nextLine();
            System.out.println("");
            switch (responce.toUpperCase()){
                case "R","REVEAL":
                    return Minesweeper.Action.reveal;
                case "F","FLAG","TOGGLE FLAG":
                    if(!game.isGameStart()) {
                        System.out.println("FIRST MOVE MUST BE A REVEAL");
                        break;
                    }
                    return Minesweeper.Action.toggleFlag;
                default:
                    System.out.println("RESPONSE INVALID");
                    break;
            }
        }
    }

    private Vector2 getCoordinate() {
        while (true) {
            System.out.println("");
            System.out.print("Enter the Coordinate of the cell you wish to select, Format 'x,y': ");
            String responce = sc.nextLine();
            String[] coords = responce.split(",");

            System.out.println("");

            if (coords.length != 2) {
                System.out.println("RESPONSE INVALID, TOO LONG");
            } else {
                try {
                    int x = Integer.parseInt(coords[0].trim());
                    int y = Integer.parseInt(coords[1].trim());
                    if (x < 0 || y < 0) {
                        System.out.println("RESPONSE INVALID, NEGATIVE NUMBER");
                    }else if(x>=game.getGridDimentions().x() || y >= game.getGridDimentions().y()){
                        System.out.println("RESPONSE INVALID, COORDINATES NOT ON GRID");
                    }else {
                        Vector2 v = new Vector2();
                        v.x(x);
                        v.y(y);
                        return v;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("RESPONSE INVALID");
                }

            }
        }
    }

}
