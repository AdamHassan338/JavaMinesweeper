package org.example.minesweeper;

import org.example.Grid;
import org.example.Minesweeper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

public class MinesweeperTest {

    @Test
    public void interactGridEndsGameOnLossIfNonFlaggedMineIsRevelaed(){
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.init(10, Minesweeper.Diffculty.hard);
        Grid grid = new Grid(10,10);
        minesweeper.setGrid(grid);
        grid.populateGrid(Minesweeper.Diffculty.hard);

        int xTest = 5; int yTest = 5;

        minesweeper.interactGrid(xTest,yTest, Minesweeper.Action.reveal);

        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined() && !grid.cellAt(x,y).isFlagged()){
                    xTest =x;
                    yTest = y;
                }

            }
        }

        minesweeper.interactGrid(xTest,yTest, Minesweeper.Action.reveal);


        int actual = ((minesweeper.isGameEnd()? 1 : 0) << 1) | (minesweeper.isGameWon() ? 1 : 0);
        int expected = 2;

        Assertions.assertEquals(expected,actual,"Minesweeper::interactGrid should end game on loss if non flagged mine is revealed after first reveal");

    }

    @Test
    public void interactGridDoesNotEndsGameOnLossIfNonFlaggedMineIsRevelaedOnFirstReveal(){
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.init(10, Minesweeper.Diffculty.hard);
        Grid grid = new Grid(10,10);
        minesweeper.setGrid(grid);
        grid.populateGrid(Minesweeper.Diffculty.hard);

        int xTest = 5; int yTest = 5;

        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined() && !grid.cellAt(x,y).isFlagged()){
                    xTest =x;
                    yTest = y;
                }

            }
        }

        minesweeper.interactGrid(xTest,yTest, Minesweeper.Action.reveal);

        int actual = ((minesweeper.isGameEnd()? 1 : 0) << 1) | (minesweeper.isGameWon() ? 1 : 0);
        int expected = 0;

        Assertions.assertEquals(expected,actual,"Minesweeper::interactGrid should not end game on loss if non flagged mine is revealed on the first reveal");


    }

    @Test
    public void interactGirdRegeneratedGridIfFirstRevealIsAMine(){
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.init(10, Minesweeper.Diffculty.hard);
        Grid grid = new Grid(10,10);
        minesweeper.setGrid(grid);
        grid.populateGrid(Minesweeper.Diffculty.hard);

        ArrayList<ArrayList<Integer>> mineCoordinates = new ArrayList<>();
        ArrayList<ArrayList<Integer>> newMineCoordinates = new ArrayList<>();

        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined() && !grid.cellAt(x,y).isFlagged()){
                    ArrayList<Integer> coordinate = new ArrayList<>();
                    coordinate.add(x);
                    coordinate.add(y);
                    mineCoordinates.add(coordinate);
                }

            }
        }

        minesweeper.interactGrid(mineCoordinates.get(0).get(0),mineCoordinates.get(0).get(1), Minesweeper.Action.reveal);


        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined() && !grid.cellAt(x,y).isFlagged()){
                    ArrayList<Integer> coordinate = new ArrayList<>();
                    coordinate.add(x);
                    coordinate.add(y);
                    mineCoordinates.add(coordinate);
                }

            }
        }


        boolean expected = false;
        boolean actual = mineCoordinates.equals(newMineCoordinates);

        Assertions.assertEquals(expected,actual,"Minesweeper::interactGrid should regenerate the grid");
    }


    @Test
    public void interactGirdDosentRegeneratedGridIfFirstRevealIsNotAMine(){
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.init(10, Minesweeper.Diffculty.hard);
        Grid grid = new Grid(10,10);
        minesweeper.setGrid(grid);
        grid.populateGrid(Minesweeper.Diffculty.hard);

        ArrayList<ArrayList<Integer>> mineCoordinates = new ArrayList<>();
        ArrayList<ArrayList<Integer>> newMineCoordinates = new ArrayList<>();

        int safeX = 0, safeY = 0;
        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined()){
                    ArrayList<Integer> coordinate = new ArrayList<>();
                    coordinate.add(x);
                    coordinate.add(y);
                    mineCoordinates.add(coordinate);
                } else {
                    safeX = x;
                    safeY = y;
                }

            }
        }

        minesweeper.interactGrid(safeX,safeY, Minesweeper.Action.reveal);


        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined()){
                    ArrayList<Integer> coordinate = new ArrayList<>();
                    coordinate.add(x);
                    coordinate.add(y);
                    newMineCoordinates.add(coordinate);
                }

            }
        }


        boolean expected = true;
        boolean actual = mineCoordinates.equals(newMineCoordinates);

        Assertions.assertEquals(expected,actual,"Minesweeper::interactGrid should not regenerate the grid if first reveal is not a mine");
    }
}
