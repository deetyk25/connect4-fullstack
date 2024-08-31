package com.company;

public class Board {
    //2d array
    private static int rows;
    private static int columns;

    Pieces [][]ourBoard;

    public Pieces[][] getOurBoard() {
        return ourBoard;
    }

    public static int getColumns() {
        return columns;
    }
    public static int getRows() {
        return rows;
    }

    public int addPiece(int colToAdd, String color) {
        //in range
        if(colToAdd >= 0 && colToAdd < columns) {
            if(ourBoard[0][colToAdd] == null) {
                boolean addedthePiece = false;
                int addedRow = -1;
                for(int row = rows - 1; row >= 0; row--)
                    if(ourBoard[row][colToAdd] == null) {
                        ourBoard[row][colToAdd] = new Pieces();
                        ourBoard[row][colToAdd].setColor(color); //sets color
                        addedthePiece = true;
                        addedRow = row;
                        break;
                    }
                return addedRow;
            } else {
                //row is full
                System.err.println("This column is full, choose another.");
                return -1;
            }
        } else {
            //not  in range :D
            System.err.println("Stay in the boundaries.");
            return -1;
        }

    }
/*
    public void printBoard() {
        for(int col = 0; col < columns + 8; col ++) System.out.print("-");
        System.out.println();
        //no return value
        for (int row = 0; row < rows; row++) {
            System.out.print("|");
            for(int col = 0; col < columns; col++) {
                if(ourBoard[row][col] == null) System.out.print("_");
                else System.out.print(ourBoard[row][col].getColor());
                System.out.print("|");
            }
            System.out.println();
        }
        for(int col = 0; col < columns + 8; col ++) System.out.print("-");
        System.out.println();
    }
*/
    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        ourBoard = new Pieces[rows][columns];
        for (int row = 0; row < rows; row++)
            for(int col = 0; col < columns; col++)
                ourBoard[row][col] = null;
    }
}
