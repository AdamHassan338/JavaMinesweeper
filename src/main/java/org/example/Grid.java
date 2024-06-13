package org.example;


public class Grid {
    private int rows;
    private int columns;
    private Cell[][] cells;


    public Grid(int rows, int columns){
        this.columns = columns;
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void draw() {

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Cell c = cells[x][y];
                CellRenderer.drawCell(x, y, c.neigbours, c.isFlagged(), c.isReveald(), c.isMined());

            }
        }
    }


    public void populateGrid() {
        cells = new Cell[rows][columns];

        double random;


        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                random = Math.random() * 100;
                cells[i][j] = new Cell(i,j,random < 20);
            }
        }
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {

                cells[i][j].neigbours = calculateNeighbours(i, j);
            }
        }

    }

    private int calculateNeighbours(int i, int j) {
        int count = 0;

        if (i + 1 < columns) count += cells[i + 1][j].isMined() ? 1 : 0;
        if (j + 1 < rows) count += cells[i][j + 1].isMined() ? 1 : 0;
        if (i + 1 < columns && j + 1 < rows) count += cells[i + 1][j + 1].isMined() ? 1 : 0;
        if (i - 1 >= 0) count += cells[i - 1][j].isMined() ? 1 : 0;
        if (j - 1 >= 0) count += cells[i][j - 1].isMined() ? 1 : 0;
        if (i - 1 >= 0 && j - 1 >= 0) count += cells[i - 1][j - 1].isMined() ? 1 : 0;
        if (i - 1 >= 0 && j + 1 < rows) count += cells[i - 1][j + 1].isMined() ? 1 : 0;
        if (i + 1 < columns && j - 1 >= 0) count += cells[i + 1][j - 1].isMined() ? 1 : 0;
        return count;
    }

    //return false on a mine
    public boolean revealCell(int x, int y) {
        Cell c = cells[x][y];
        if (!c.isMined() && !c.isReveald() && !c.isFlagged())
            floodFill(x, y);
        if(c.isMined()){
            c.setReveald(true);
            revealAllMines();
            return false;
        }


        return true;
    }

    public boolean isCellsLeft(){
        for(Cell[] a : cells){
            for(Cell c : a){
                if(!c.isReveald()&&!c.isMined())
                    return true;
            }
        }
        return false;
    }

    private void revealAllMines(){
        for(Cell[] a : cells){
            for(Cell c : a){
                if(c.isMined())
                    c.setReveald(true);
            }
        }
    }


    private void floodFill(int x, int y) {
        if (x > columns - 1 || x < 0 || y > rows - 1 || y < 0)
            return;

        if (cells[x][y].isMined() || cells[x][y].isReveald())
            return;

        cells[x][y].setReveald(true);

        if (cells[x][y].neigbours > 0)
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
    /*Returns a copy*/
    /*create copy constructor later*/
    public final Cell cellAt(int x, int y){
        return new Cell(x,y,cells[x][y].isMined());
    }

}
