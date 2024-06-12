package org.example;

public class Main {
    public static void main(String[] args) {
        Minesweeper game = new Minesweeper();
        game.init(800,800);
        game.run(10);

    }

}