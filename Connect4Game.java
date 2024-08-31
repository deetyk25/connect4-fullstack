package com.company;
import java.util.Random;

public class Connect4Game {
    private Board board;
    private final String color1;
    private final String color2;

    //if player 1 turn - 1st
    //false if p2 goes first
    private boolean isOnePlaying;
    public void resetGame(int rows, int columns) {
        this.board = new Board(rows, columns);
        isOnePlaying = (new Random()).nextBoolean();
    }
    public boolean isOneReallyPlaying() {
        return isOnePlaying; //sets color and turn
    }
    public Connect4Game(String color1, String color2, int rows, int columns) {
        this.board = new Board(rows, columns);
        this.color1 = color1;
        this.color2 = color2;
        isOnePlaying = (new Random()).nextBoolean();
    }
    public int round(int col) {
        String color = isOnePlaying ? color1 : color2;
        int row = board.addPiece(col, color);
        if(row != -1) isOnePlaying = !isOnePlaying;
        return row;
    }
    public boolean checkWinnerGUI(int column) {
        String victorColor;
        if(!isOnePlaying) {
            victorColor = color1;
        } else {
            victorColor = color2;
        }
        return checkWinner(column, victorColor); //connects to GUI
    }
    public boolean checkWinner(int col, String victorColor) {
        int rows = board.getRows();
        int columns = board.getColumns();
        Pieces[][] ourBoard = board.getOurBoard();

        for(int row = 0; row < rows; row++) {
            if (ourBoard[row][col] != null) {
                int winningStreak = 3;
                //down
                for (int selectedRow = row + 1; selectedRow < rows; selectedRow++) {
                    if (ourBoard[selectedRow][col].getColor().equals(victorColor)) {
                        winningStreak--;
                        if (winningStreak == 0) return true;
                    } else winningStreak = 3;
                }
                winningStreak = 4;
                //horizontal
                for (int selectedCol = col - 3; selectedCol < col + 3; selectedCol++) {
                    if (selectedCol < 0) continue;
                    if (selectedCol >= columns) break; //sets parameters

                    if(ourBoard[row][selectedCol] != null && ourBoard[row][selectedCol].getColor().equals(victorColor)) {
                        winningStreak--;
                        if(winningStreak == 0) {
                            return true;
                        }
                    } else { winningStreak = 4; }
                }
                //left
                if(checkDiagonal(row, col, victorColor, false)) return true;
                //right
                if(checkDiagonal(row, col, victorColor, true)) return true;
                return false;
            }
        }
        return false;
    }
    private boolean checkDiagonal(int row, int col, String victorColor, boolean rightDiag) {
        int rows = board.getRows();
        int columns = board.getColumns();

        int winningStreak = 4; //tiles left to fill
        int reverse = rightDiag ? 1 : -1;

        Pieces[][] ourBoard = board.getOurBoard();
        // diagonal l --> r
        for (int selectedRow = row - 3, selectedCol = col - (3 * reverse); selectedRow <= row + 3; selectedRow++, selectedCol += reverse) {
            if (!rightDiag) {
                if (selectedRow < 0 || selectedCol < 0) continue;
                if (selectedRow >= rows || selectedCol >= columns) break;
            } else {
                if (selectedRow < 0 || selectedCol >= columns) continue;
                if (selectedRow >= rows || selectedCol < 0) break;
            }
            if (ourBoard[selectedRow][selectedCol] != null && ourBoard[selectedRow][selectedCol].getColor().equals(victorColor)) {
                if (--winningStreak == 0) return true;
            } else winningStreak = 4; //resets winning streak
        }
        return false;
    }

    }
