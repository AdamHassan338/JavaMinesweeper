package org.example;

public class Main {
    public static void main(String[] args) {

        GameController controller = new GameController();
        controller.init(800,800,20);
        //controller.init(10);
        controller.begin();

    }

}