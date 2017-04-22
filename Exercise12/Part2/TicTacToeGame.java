package Part2;

import javax.swing.*;
import java.awt.Color;
import java.util.Random;

/**
 * Created by Adar on 4/21/2017.
 */
public class TicTacToeGame {

    private String game_title;
    private JFrame frame;

    public TicTacToeGame() {
        this.game_title = "Tic Tac Toe Game";
    }

    private boolean isUserFirstPlayer() {
        Random randomGenerator = new Random();
        return randomGenerator.nextBoolean();
    }

    private boolean startUserPlay(TicTacToeBoard board) {
        Object[] possibleValuesArray = board.getEmptyCells().toArray();

        /* Display the work and let the user choose a letter */
        Object selectedValue = JOptionPane.showInputDialog(this.frame,
                "Choose the next empty position you would what to fill:",
                this.game_title, JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);
        if (null == selectedValue) {
            return false;
        }

        /* Update the board */
        board.setCell(Integer.parseInt(((String)selectedValue).split(", ")[0]),
                Integer.parseInt(((String)selectedValue).split(", ")[1]),
                TicTacToeBoard.TicTacToeCellValue.X);
        return true;
    }

    private void startProgramPlay(TicTacToeBoard board) {
        int row = 0;
        int col = 0;
        TicTacToeBoard tmpBoard;

        /* Check if there is a move that can make the program win */
        for (int i = 0 ; i < board.getEmptyCells().size() ; i++) {
            row = Integer.parseInt(((String)board.getEmptyCells().get(i)).split(", ")[0]);
            col = Integer.parseInt(((String)board.getEmptyCells().get(i)).split(", ")[1]);

            tmpBoard = board.clone();
            tmpBoard.setCell(row, col, TicTacToeBoard.TicTacToeCellValue.O);
            if (board.hasOWon()) {
                board.setCell(row, col, TicTacToeBoard.TicTacToeCellValue.O);
                return;
            }
        }

        /* There are no moves that will make the program win. Choose the first empty cell */
        board.setCell(Integer.parseInt(((String)board.getEmptyCells().get(0)).split(", ")[0]),
                Integer.parseInt(((String)board.getEmptyCells().get(0)).split(", ")[1]),
                TicTacToeBoard.TicTacToeCellValue.O);
    }

    private void startTurn() {
        boolean isTurnOver = false;
        boolean isUserFirstPlayer = this.isUserFirstPlayer();

        /* Create a board */
        TicTacToeBoard board = new TicTacToeBoard();
        int frameDimensions = 300;
        TicTacToePanel panel = new TicTacToePanel(board, frameDimensions);
        panel.setBackground(Color.BLACK);
        this.frame.setContentPane(panel);
        this.frame.setVisible(true);

        /* Start playing */
        while (!isTurnOver) {

            if (isUserFirstPlayer) {
                if (!this.startUserPlay(board)) {
                    return;
                }
                if (!board.isBoardFull()) {
                    this.startProgramPlay(board);
                }
            } else {
                this.startProgramPlay(board);
                if (!board.isBoardFull()) {
                    if (!this.startUserPlay(board)) {
                        return;
                    }
                }
            }

            panel.repaint();

            /* Check if there is a winner */
            if (board.hasXWon()) {
                JOptionPane.showMessageDialog(this.frame, "Congratulations! You have won the game!");
                isTurnOver = true;
            } else if (board.hasOWon()) {
                JOptionPane.showMessageDialog(this.frame, "Sorry, You lost the game.");
                isTurnOver = true;
            } else if (board.isBoardFull()){
                JOptionPane.showMessageDialog(this.frame, "The game was over in a tie.");
                isTurnOver = true;
            }
        }
    }

    public void startGame() {
        this.frame = new JFrame(this.game_title);
        int startTurn = JOptionPane.YES_OPTION;

        /* Configure display */
        this.frame.setSize(316, 339);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setVisible(true);

        /* Start playing */
        while (JOptionPane.YES_OPTION == startTurn) {
            this.startTurn();
            startTurn = JOptionPane.showConfirmDialog(null,
                    "Do you want to start another game?",
                    this.game_title,
                    JOptionPane.YES_NO_OPTION);
        }

        /* Close the frame */
        frame.setVisible(false);
        frame.dispose();
    }
}
