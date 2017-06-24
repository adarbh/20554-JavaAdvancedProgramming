package MatchPairsGame;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsBoard {

    private int dimensions;
    private MatchPairsCard boardMatrix[][];

    public MatchPairsBoard(int dimensions) throws IllegalDimensionsException {
        if (dimensions % 2 != 0) {
            throw new IllegalDimensionsException();
        }

        this.dimensions = dimensions;
        this.boardMatrix = new MatchPairsCard[this.dimensions][this.dimensions];

        this.generateBoard();
    }

    public void generateBoard() {
        Random randomGenerator;
        randomGenerator = new Random();
        ArrayList<Pair> cellList = new ArrayList<Pair>();
        int matchCellIndex = 0;
        int Id = 0;

        /* Create a list of pairs representing all the cells of the matrix */
        for (int i=0 ; i < dimensions ; i++) {
            for (int j = 0; j < dimensions; j++) {
                cellList.add(new Pair(i, j));
            }
        }

        /* Create all the cards */
        for (int i=0 ; i < dimensions ; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (!cellList.contains(this.boardMatrix[i][j])) {
                    continue;
                }

                cellList.remove(this.boardMatrix[i][j]);
                matchCellIndex = randomGenerator.nextInt(cellList.size());
                this.boardMatrix[(int) cellList.get(matchCellIndex).getKey()][(int) cellList.get(matchCellIndex).getValue()] = new MatchPairsCard(Id);
                this.boardMatrix[i][j] = new MatchPairsCard(Id);
                cellList.remove(matchCellIndex);
                Id++;
            }
        }

    }

    public boolean isCardRevealed(int line, int row) throws IllegalIndexException {
        if (0 > line || line >= dimensions || 0 > row || row >= dimensions) {
            throw new IllegalIndexException();
        }
        return this.boardMatrix[line][row].isRevealed();
    }

    public void revealCard(int line, int row) throws IllegalIndexException {
        if (0 > line || line >= dimensions || 0 > row || row >= dimensions) {
            throw new IllegalIndexException();
        }
        this.boardMatrix[line][row].reveal();
    }

    public void hideCard(int line, int row) throws IllegalIndexException {
        if (0 > line || line >= dimensions || 0 > row || row >= dimensions) {
            throw new IllegalIndexException();
        }
        this.boardMatrix[line][row].hide();
    }

    public boolean doseCardsMatch(int line1, int row1, int line2, int row2) throws IllegalIndexException {
        if (0 > line1 || line1 >= dimensions || 0 > row1 || row1 >= dimensions ||
                0 > line2 || line2 >= dimensions || 0 > row2 || row2 >= dimensions) {
            throw new IllegalIndexException();
        }
        return this.boardMatrix[line1][row1] == this.boardMatrix[line2][row2];
    }

    public boolean areAllCardsRevealed() {
        for (int i=0 ; i < dimensions ; i++) {
            for (int j=0 ; j < dimensions ; j++) {
                if (!this.boardMatrix[i][j].isRevealed()) {
                    return false;
                }
            }
        }

        return true;
    }
}
