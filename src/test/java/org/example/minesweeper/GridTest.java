package org.example.minesweeper;

import org.example.Grid;
import org.example.Minesweeper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GridTest {
    @Test
    public void isValidCoordinateReturnsTrueIfCoordinateIsInGridDimentions(){
        Grid grid = new Grid(10,10);
        final boolean expected = true;
        final boolean actual = grid.isValidCoordinate(5,5);
        Assertions.assertEquals(expected,actual,"Grid::IsValidCoordinate should return true if input coordinate is within grid dimensions ");
    }

    @Test
    public void isValidCoordinateReturnsFalseIfCoordinateIsOutsideGridDimentions(){
        Grid grid = new Grid(20,10);
        final boolean expected = false;
        final boolean actual = grid.isValidCoordinate(-1,34);
        Assertions.assertEquals(expected,actual,"Grid::IsValidCoordinate should return false if input coordinate is outside grid dimensions ");
    }


    @Test
    public void populateGridShouldCreateExactlyTenPercenetOfCellsToBeMinesOnEasyDifficulty(){
        Grid grid = new Grid(10,10);
        grid.populateGrid(Minesweeper.Diffculty.easy);
        int expected = 10;
        int actual = 0;

        //count number of mines
        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined())
                    actual +=1;
            }
        }

        Assertions.assertEquals(expected,actual,"Grid::populateGrid should create exactly 10% of cells as mines on easy difficulty");
    }

    @Test
    public void populateGridShouldCreateExactly15PercenetOfCellsToBeMinesOnMediumDifficulty(){
        Grid grid = new Grid(10,10);
        grid.populateGrid(Minesweeper.Diffculty.medium);
        int expected = 15;
        int actual = 0;

        //count number of mines
        for(int x = 0 ; x<10 ; x++ ){
            for (int y = 0 ; y<10 ; y++){
                if(grid.cellAt(x,y).isMined())
                    actual +=1;
            }
        }

        Assertions.assertEquals(expected,actual,"Grid::populateGrid should create exactly 15% of cells as mines on medium difficulty");
    }

    @Test
    public void populateGridShouldCreateExactly20PercenetOfCellsToBeMinesOnMediumDifficulty(){
        int rows = 10, colums = 10;
        Grid grid = new Grid(rows,colums);
        grid.populateGrid(Minesweeper.Diffculty.hard);
        int expected = 20;
        int actual = 0;

        //count number of mines
        for(int x = 0 ; x<colums ; x++ ){
            for (int y = 0 ; y<rows ; y++){
                if(grid.cellAt(x,y).isMined())
                    actual +=1;
            }
        }

        Assertions.assertEquals(expected,actual,"Grid::populateGrid should create exactly 20% of cells as mines on hard difficulty");
    }

    @Test
    public void revealCellShouldReturnFalseOnIndexWithAMineThatIsNotFlagged(){
        int rows = 10, colums = 10;
        Grid grid = new Grid(rows,colums);
        grid.populateGrid(Minesweeper.Diffculty.hard);
        int xTest = 0,yTest =0;
        for(int x = 0 ; x<colums ; x++ ){
            for (int y = 0 ; y<rows ; y++){
                if(grid.cellAt(x,y).isMined() && !grid.cellAt(x,y).isFlagged()){
                    xTest =x;
                    yTest = y;
                }

            }
        }

        boolean expected = false;
        boolean actual = grid.revealCell(xTest,yTest);
        Assertions.assertEquals(expected,actual,"Grid::revealCell should return false on index with a mine that Is Not Flagged");

    }


    @Test
    public void revealCellShouldReturnTrueOnIndexWithAMineThatIsFlagged(){
        int rows = 10, colums = 10;
        Grid grid = new Grid(rows,colums);
        grid.populateGrid(Minesweeper.Diffculty.hard);
        int xTest = 0,yTest =0;
        for(int x = 0 ; x<colums ; x++ ){
            for (int y = 0 ; y<rows ; y++){
                if(grid.cellAt(x,y).isMined()){
                    grid.cellAt(x,y).setFlagged(true);
                    xTest =x;
                    yTest = y;
                }

            }
        }

        boolean expected = true;
        boolean actual = grid.revealCell(xTest,yTest);
        Assertions.assertEquals(expected,actual,"Grid::revealCell should return true on index with a mine that Is Flagged");

    }



    @Test
    public void revealCellShouldReturnTrueOnIndexWithoutAMine(){
        int rows = 10, colums = 10;
        Grid grid = new Grid(rows,colums);
        grid.populateGrid(Minesweeper.Diffculty.hard);

        int xTest = 0,yTest =0;
        for(int x = 0 ; x<colums ; x++ ){
            for (int y = 0 ; y<rows ; y++){
                if(!grid.cellAt(x,y).isMined()){
                    xTest =x;
                    yTest = y;
                }

            }
        }

        boolean expected = true;
        boolean actual = grid.revealCell(xTest,yTest);
        Assertions.assertEquals(expected,actual,"Grid::revealCell should return true on index without a mine");

    }

    @Test
    public void isCellsLeftShouldReturnTrueIfThereAreCellsNotReveled(){
        int rows = 10, colums = 10;
        Grid grid = new Grid(rows,colums);
        grid.populateGrid(Minesweeper.Diffculty.hard);

        int xTest = 0,yTest =0;
        grid.cellAt(xTest,yTest).setReveald(false);

        boolean expected = true;
        boolean actual = grid.isCellsLeft();
        Assertions.assertEquals(expected,actual,"Grid::isCellsLeft should return true if there are cells that are not reveled");

    }


    @Test
    public void isCellsLeftShouldReturnFalseIfThereAreCellsNotReveled(){
        int rows = 10, colums = 10;
        Grid grid = new Grid(rows,colums);
        grid.populateGrid(Minesweeper.Diffculty.hard);

        int xTest = 0,yTest =0;
        for(int x = 0 ; x<colums ; x++ ){
            for (int y = 0 ; y<rows ; y++){
                grid.cellAt(x,y).setReveald(true);
            }
        }

        boolean expected = false;
        boolean actual = grid.isCellsLeft();
        Assertions.assertEquals(expected,actual,"Grid::isCellsLeft should return false if all cells are reveled");

    }


}
