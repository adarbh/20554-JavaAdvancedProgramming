package Part2;

import java.util.ArrayList;

/**
 * Created by Adar on 4/21/2017.
 */
public class TicTacToeBoard implements Cloneable{

    private final int DIMENSIONS = 3;
    private TicTacToeCellValue matrix[][];

    public enum TicTacToeCellValue {
        EMPTY, X, O
    }

    public TicTacToeBoard() {
        this.matrix = new TicTacToeCellValue[this.DIMENSIONS][this.DIMENSIONS];

        /* Initialize the board matrix */
        for (int i = 0 ; i < this.DIMENSIONS ; i++) {
            for (int j = 0; j < this.DIMENSIONS ; j++) {
                this.matrix[i][j] = TicTacToeCellValue.EMPTY;
            }
        }
    }

    public TicTacToeCellValue getCell(int row, int col) {
        return this.matrix[row][col];
    }

    public void setCell(int row, int col, TicTacToeCellValue value) {
        this.matrix[row][col] = value;
    }

    public int getDimensions() {
        return this.DIMENSIONS;
    }

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

    public boolean hasXWon() {
        return this.hasPlayerWon(TicTacToeCellValue.X);
    }

    public boolean hasOWon() {
        return this.hasPlayerWon(TicTacToeCellValue.O);
    }

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
