package Part2;

import java.util.ArrayList;

/**
 * Created by Adar on 4/21/2017.
 */
public class TicTacToeBoard implements Cloneable{
    /**
     * This class represents a TicTacToeBoard
     */

    private final int DIMENSIONS = 3;
    private TicTacToeCellValue matrix[][];

    public enum TicTacToeCellValue {
        EMPTY, X, O
    }

    /**
     * Returns an TicTacToeBoard object
     * @return      the TicTacToeBoard object
     */
    public TicTacToeBoard() {
        this.matrix = new TicTacToeCellValue[this.DIMENSIONS][this.DIMENSIONS];

        /* Initialize the board matrix */
        for (int i = 0 ; i < this.DIMENSIONS ; i++) {
            for (int j = 0; j < this.DIMENSIONS ; j++) {
                this.matrix[i][j] = TicTacToeCellValue.EMPTY;
            }
        }
    }

    /**
     * Returns the value of a cell on the board
     * @param  row the cell row
     * @param  col the cell col
     * @return   the cell value
     */
    public TicTacToeCellValue getCell(int row, int col) {
        return this.matrix[row][col];
    }

    /**
     * Sets the value of a cell on the board
     * @param  row the cell row
     * @param  col the cell col
     * @param  value the new value of the cell
     */
    public void setCell(int row, int col, TicTacToeCellValue value) {
        this.matrix[row][col] = value;
    }

    /**
     * Returns the board dimensions
     * @return   the board dimensions
     */
    public int getDimensions() {
        return this.DIMENSIONS;
    }

    /**
     * Checks if the board is full. The board is full when there is no EMPTY cell.
     * @return   true if the board is full, false otherwise
     */
    public boolean isBoardFull() {
        for (int i = 0 ; i < this.DIMENSIONS ; i++) {
            for (int j = 0; j < this.DIMENSIONS ; j++) {
                if (TicTacToeCellValue.EMPTY == this.matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a player of a given kind won the game. The winner should have a full line/column/diagonal.
     * @param  value the value attached to the player (X/O)
     * @return   true if the player won, false otherwise
     */
    private boolean hasPlayerWon(TicTacToeCellValue value) {
        int numOfMatches = 0;

        /* Look for full lines */
        for (int i = 0 ; i < this.DIMENSIONS ; i++) {
            numOfMatches = 0;
            for (int j = 0; j < this.DIMENSIONS ; j++) {
                if (value == this.matrix[i][j]) {
                    numOfMatches++;
                }
            }

            if (numOfMatches == this.DIMENSIONS) {
                return true;
            }
        }

        /* Look for full columns */
        for (int j = 0 ; j < this.DIMENSIONS ; j++) {
            numOfMatches = 0;
            for (int i = 0; i < this.DIMENSIONS ; i++) {
                if (value == this.matrix[i][j]) {
                    numOfMatches++;
                }
            }

            if (numOfMatches == this.DIMENSIONS) {
                return true;
            }
        }

        /* Look for full diagonals */
        numOfMatches = 0;
        for (int i = 0 ; i < this.DIMENSIONS ; i++) {
            if (value == this.matrix[i][i]) {
                numOfMatches++;
            }
        }

        if (numOfMatches == this.DIMENSIONS) {
            return true;
        }

        numOfMatches = 0;
        for (int i = 0 ; i < this.DIMENSIONS ; i++) {
            if (value == this.matrix[i][this.DIMENSIONS - i - 1]) {
                numOfMatches++;
            }
        }

        return (numOfMatches == this.DIMENSIONS);
    }

    /**
     * Checks if X won the game
     * @return   true if X won, false otherwise
     */
    public boolean hasXWon() {
        return this.hasPlayerWon(TicTacToeCellValue.X);
    }

    /**
     * Checks if O won the game
     * @return   true if O won, false otherwise
     */
    public boolean hasOWon() {
        return this.hasPlayerWon(TicTacToeCellValue.O);
    }

    /**
     * Returns the empty cells on the board
     * @return   the list of empty cells indexes in an String format ("<row>, <col>")
     */
    public ArrayList<String> getEmptyCells() {
        ArrayList<String> emptyCells = new ArrayList<String>();

        for (int i = 0 ; i < this.getDimensions() ; i++) {
            for (int j = 0 ; j < this.getDimensions() ; j++) {
                if (TicTacToeCellValue.EMPTY == this.matrix[i][j]) {
                    emptyCells.add(i + ", " + j);
                }
            }
        }

        return emptyCells;
    }

    @Override
    protected TicTacToeBoard clone() {
        TicTacToeBoard board = new TicTacToeBoard();

        for (int i = 0 ; i < this.DIMENSIONS ; i++) {
            for (int j = 0; j < this.DIMENSIONS ; j++) {
                board.setCell(i, j, this.matrix[i][j]);
            }
        }

        return board;
    }
}
