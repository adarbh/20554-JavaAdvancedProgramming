package Part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Adar on 4/10/2017.
 */
public class LifeMatrix {
    /**
     * This class represents a life matrix.
     * A life matrix is built from alive and dead cells who are influenced by each other in every passing generation.
     */

    private int rows;
    private int cols;
    private boolean matrix[][];

    /**
     * Returns an LifeMatrix object based on given dimensions
     * @param  rows the number of rows in the matrix
     * @param  cols the number of cols in the matrix
     * @return      the LifeMatrix object
     */
    public LifeMatrix(int rows, int cols) {
        int rand_val = 0;
        this.rows = rows;
        this.cols = cols;
        this.matrix = new boolean[this.rows][this.cols];

        /* Fill the matrix with random values */
        Random randomGenerator = new Random();

        for (int i = 0 ; i < this.rows ; i++) {
            for (int j = 0 ; j < this.cols ; j++) {
                rand_val = randomGenerator.nextInt(2);
                if (0 == rand_val) {
                    this.matrix[i][j] = false;
                } else {
                    this.matrix[i][j] = true;
                }
            }
        }
    }

    /**
     * Returns the number of rows in the matrix
     * @return   the number of rows in the matrix
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Returns the number of cols in the matrix
     * @return   the number of cols in the matrix
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Returns the neighbors of a cell
     * @param  row the row index of the cell
     * @param  col the col index of the cell
     * @return      the list of neighbors in an ArrayList format
     */
    private ArrayList getNeighbors(int row, int col) {
        ArrayList neighbors = new ArrayList();

        /* Tree neighbors */
        if (0 == row && 0 == col) {
            neighbors.add(this.matrix[row][col + 1]);
            neighbors.add(this.matrix[row + 1][col]);
            neighbors.add(this.matrix[row + 1][col + 1]);
        } else if (0 == row && this.cols - 1 == col) {
            neighbors.add(this.matrix[row][col - 1]);
            neighbors.add(this.matrix[row + 1][col]);
            neighbors.add(this.matrix[row + 1][col - 1]);
        } else if (this.rows - 1 == row && 0 == col) {
            neighbors.add(this.matrix[row][col + 1]);
            neighbors.add(this.matrix[row - 1][col]);
            neighbors.add(this.matrix[row - 1][col + 1]);
        } else if (this.rows - 1 == row && this.cols - 1 == col) {
            neighbors.add(this.matrix[row][col - 1]);
            neighbors.add(this.matrix[row - 1][col]);
            neighbors.add(this.matrix[row - 1][col - 1]);
        }
        /* Five neighbors */
        else if (0 == row && 0 < col) {
            neighbors.add(this.matrix[row][col - 1]);
            neighbors.add(this.matrix[row][col + 1]);
            neighbors.add(this.matrix[row + 1][col - 1]);
            neighbors.add(this.matrix[row + 1][col]);
            neighbors.add(this.matrix[row + 1][col + 1]);
        } else if (this.rows - 1 == row && 0 < col) {
            neighbors.add(this.matrix[row][col - 1]);
            neighbors.add(this.matrix[row][col + 1]);
            neighbors.add(this.matrix[row - 1][col - 1]);
            neighbors.add(this.matrix[row - 1][col]);
            neighbors.add(this.matrix[row - 1][col + 1]);
        } else if (0 < row && this.cols - 1 == col) {
            neighbors.add(this.matrix[row - 1][col]);
            neighbors.add(this.matrix[row + 1][col]);
            neighbors.add(this.matrix[row - 1][col - 1]);
            neighbors.add(this.matrix[row][col - 1]);
            neighbors.add(this.matrix[row + 1][col - 1]);
        } else if (0 < row && 0 == col) {
            neighbors.add(this.matrix[row - 1][col]);
            neighbors.add(this.matrix[row + 1][col]);
            neighbors.add(this.matrix[row - 1][col + 1]);
            neighbors.add(this.matrix[row][col + 1]);
            neighbors.add(this.matrix[row + 1][col + 1]);
        }
        /* Eight neighbors */
        else {
            neighbors.add(this.matrix[row][col - 1]);
            neighbors.add(this.matrix[row][col + 1]);
            neighbors.add(this.matrix[row - 1][col - 1]);
            neighbors.add(this.matrix[row - 1][col]);
            neighbors.add(this.matrix[row - 1][col + 1]);
            neighbors.add(this.matrix[row + 1][col - 1]);
            neighbors.add(this.matrix[row + 1][col]);
            neighbors.add(this.matrix[row + 1][col + 1]);
        }

        return neighbors;
    }

    /**
     * Returns the number of live neighbors of a cell
     * @param  row the row index of the cell
     * @param  col the col index of the cell
     * @return      the number of live neighbors in an ArrayList format
     */
    private int getLiveNeighborsNumber(int row, int col) {
        return Collections.frequency(this.getNeighbors(row, col), true);
    }

    /**
     * Returns a cell in a specific index of the matrix
     * @param  row the row index of the cell
     * @param  col the col index of the cell
     * @return      the value of the cell
     */
    public boolean getMatrixInIndex(int row, int col) {
        return this.matrix[row][col];
    }

    /**
     * Updates the cells of the matrix according to the process of moving to a new generation
     */
    public void moveGeneration () {

        boolean next_gen_matrix[][] = new boolean[this.rows][this.cols];

        /* Go over all the cells in the matrix */
        for (int i = 0 ; i < this.rows ; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.matrix[i][j]) {
                    /* The cell is alive */
                    if ((1 >= this.getLiveNeighborsNumber(i, j)) ||
                            (4 <= this.getLiveNeighborsNumber(i, j))) {
                        next_gen_matrix[i][j] = false;
                    }
                } else {
                    /* The cell is dead */
                    if (3 == this.getLiveNeighborsNumber(i, j)) {
                        next_gen_matrix[i][j] = true;
                    }
                }
            }
        }

        this.matrix = next_gen_matrix;
    }
}
