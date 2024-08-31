import java.util.*;

// A class to represent a game of Connect4 that implements the
// AbstractStrategyGame interface.
public class ConnectFour implements AbstractStrategyGame {
    // Private fields
    private char[][] gameBoard;
    private boolean isBlueTurn;

    // Constructs a new ConnectFour game
    // with a board of 7 columns and 6 rows
    public ConnectFour() {
        gameBoard = new char[][]{{'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},};
        isBlueTurn = true;
    }

    // Returns a String containing instructions to play the game.
    public String instructions() {
        String result = "";
        result += "Player 1 is Blue and goes first. Choose where to play by entering a column\n";
        result += "number, where 0 is the leftmost and 6 is the rightmost.\n";
        result += "Spaces show as a '-' are empty. The game ends when one player marks four spaces\n";
        result += "in a row, column, or diagonal in which case that player wins,\n";
        result += "or when the board is full, in which\n";
        result += "case the game ends in a tie.";
        return result;
    }

    // Returns a String representation of the current state of the board.
    public String toString() {
        String result = "";
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                result += gameBoard[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    // Returns an int:
    // - the index of which player's turn it is.
    // - 1 if player 1 (B), 2 if player 2 (G), -1 if the game is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return isBlueTurn ? 1 : 2;
    }

    // Given the scanned input, places a B or a G in the
    // column that the player specifies.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied.
    // Board bounds are [0, 6] cols.
    public void makeMove(Scanner input) {
        char current = isBlueTurn ? 'B' : 'G';

        System.out.print("Would you like to add (A) a piece to the board\n" +
                "or remove (R) a piece from the bottom of a column? ");
        String choice = input.next();
        if(choice.equals("A")) {
            // find a row
            System.out.print("Column (1 - 6)? ");
            int col = input.nextInt() - 1;
            int row = locateRow(col);
            makePlayerMove(row, col, current);
        } else if (choice.equals("R")) {
            System.out.print("Column (1 - 6)? ");
            int col = input.nextInt() - 1;
            removePiece(col, current);
        }
        isBlueTurn = !isBlueTurn;
    }

    // Returns a boolean - whether or not the game is over
    public boolean isGameOver() {
        return getWinner() >= 0;
    }

    // Returns an int
    // - the index of the winner of the game.
    // - 1 if player 1 (B), 2 if player 2 (G), 0 if a tie occurred,
    //   and -1 if the game is not over.
    public int getWinner() {
        boolean winnerExists = false;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length - 3; j++) {
                if(gameBoard[i][j] == gameBoard[i][j+1] && gameBoard[i][j] == gameBoard[i][j+2]
                        && gameBoard[i][j] == gameBoard[i][j+3] && gameBoard[i][j] != '-') {
                    winnerExists = true;
                    return gameBoard[i][j] == 'B' ? 1 : 2;
                }
            }
        }
        for (int i = 0; i < gameBoard.length - 3; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if(gameBoard[i][j] == gameBoard[i+1][j] && gameBoard[i][j] == gameBoard[i+2][j]
                        && gameBoard[i][j] == gameBoard[i+3][j] && gameBoard[i][j] != '-') {
                    winnerExists = true;
                    return gameBoard[i][j] == 'B' ? 1 : 2;
                }
            }
        }
        for (int i = 3; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length - 3; j++) {
                if(gameBoard[i][j] == gameBoard[i-1][j+1] &&
                        gameBoard[i][j] == gameBoard[i-2][j+2] &&
                        gameBoard[i][j] == gameBoard[i-3][j+3] &&
                        gameBoard[i][j] != '-') {
                    winnerExists = true;
                    return gameBoard[i][j] == 'B' ? 1 : 2;
                }
            }
        }
        for (int i = 0; i < gameBoard.length - 3; i++) {
            for (int j = 0; j < gameBoard[i].length - 3; j++) {
                if(gameBoard[i][j] == gameBoard[i+1][j+1] &&
                        gameBoard[i][j] == gameBoard[i+2][j+2] &&
                        gameBoard[i][j] == gameBoard[i+3][j+3] &&
                        gameBoard[i][j] != '-') {
                    winnerExists = true;
                    return gameBoard[i][j] == 'B' ? 1 : 2;
                }
            }
        }
        // Check for tie
        int empty = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j] == '-') {
                    empty++;
                }
            }
        }
        // Insufficient empty spaces
        if(empty == 0) {
            return 0;
        }
        // Rows
        int consecutive = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length - 3; j++) {
                if(gameBoard[i][j] == gameBoard[i][j+1] && gameBoard[i][j] == gameBoard[i][j+2]
                        && gameBoard[i][j] == gameBoard[i][j+3] && gameBoard[i][j] == '-') {
                    consecutive++;
                }
            }
        }
        // Columns
        for (int i = 0; i < gameBoard.length - 3; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if(gameBoard[i][j] == gameBoard[i+1][j] && gameBoard[i][j] == gameBoard[i+2][j]
                        && gameBoard[i][j] == gameBoard[i+3][j] && gameBoard[i][j] == '-') {
                    consecutive++;
                }
            }
        }
        // Diagonals
        for (int i = 3; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length - 4; j++) {
                if(gameBoard[i][j] == gameBoard[i-1][j+1] &&
                        gameBoard[i][j] == gameBoard[i-2][j+2] &&
                        gameBoard[i][j] == gameBoard[i-3][j+3] &&
                        gameBoard[i][j] == '-') {
                    consecutive++;
                }
            }
        }
        for (int i = 0; i < gameBoard.length - 3; i++) {
            for (int j = 0; j < gameBoard[i].length - 4; j++) {
                if(gameBoard[i][j] == gameBoard[i+1][j+1] &&
                        gameBoard[i][j] == gameBoard[i+2][j+2] &&
                        gameBoard[i][j] == gameBoard[i+3][j+3] &&
                        gameBoard[i][j] == '-') {
                    consecutive++;
                }
            }
        }
        // No tie!!
        if(!winnerExists && consecutive > 0) {
            return -1;
            // Tie
        } else {
            return 0;
        }
    }

    // Private helper method for makeMove.
    // Given a column, as well as player piece,
    // places a B or a G in that row and col.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied.
    // Board bounds are [0, 6] for columns and [0, 5] for rows
    private void makePlayerMove(int row, int col, char player) {
        checkColumnValidity(col);
        gameBoard[row][col] = player;
    }

    // Private helper method for makeMove [EXTENSION].
    // Given a column, as well as player piece,
    // removes a B or a G from the bottom the given col and shifts the pieces down.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied, or 
    // if the piece does not belong to the player in question.
    // Board bounds are [0, 6] for columns and [0, 5] for rows
    private void removePiece(int col, char current) {
        checkColumnValidity(col);
        checkPlayer(col, current);
        for(int i = gameBoard.length - 1; i > 0; i--) {
            if (gameBoard[i-1][col] != '-') {
                gameBoard[i][col] = gameBoard[i-1][col];
            } else {
                gameBoard[i][col] = '-';
            }
        }
    }

    // Private helper method for makeMove 
    // Locates a row in which to place a token for the player
    // when given an int - column number
    // Finds the first empty available space in the column
    // IllegalArgumentException thrown if the column is full
    private int locateRow(int col) {
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            if(gameBoard[i][col] == '-') {
                return i;
            }
        }
        throw new IllegalArgumentException("This column is full! Choose another :D");
    }

    // Private helper method for makePlayerMove and removePiece helper methods
    // Checks that the column number entered is within a valid range
    // IllegalArgumentException thrown if the integer entered falls out of range
    private void checkColumnValidity(int col) {
        if (col < 0 || col >= 6) {
            throw new IllegalArgumentException("Invalid board position: " + (col+1));
        }
    }

    // Private helper method for removePiece helper method
    // When given an integer representing a column number,
    // checks that the piece at the bottom of the column chosen
    // matches the piece of the player choosing to remove a piece
    // Throws an IllegalArgumentException if the piece does not match the player
    private void checkPlayer(int col, char player) {
        if (gameBoard[5][col] != player) {
            throw new IllegalArgumentException("Piece is not yours at: " + (col+1));
        }
    }
}
