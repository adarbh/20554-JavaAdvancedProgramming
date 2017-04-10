package Part2;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * Created by Adar on 4/10/2017.
 */
public class LifeGame {
    /**
     * This class represents a life game
     */

    private String game_title;
    private static final int rows = 10;
    private static final int cols = 10;

    /**
     * Returns an LifeGame object
     * @return      the LifeGame object
     */
    public LifeGame() {
        this.game_title = "The life game";
    }

    /**
     * Starts the game. The game is composed of moves true generations
     */
    public void startGame() {

        int selectedValue = JOptionPane.OK_OPTION;
        JFrame frame = new JFrame(this.game_title);

        /* Generate a life matrix */
        LifeMatrix life_matrix = new LifeMatrix(this.rows, this.cols);

        /* Configure display */
        MatrixJPanel panel = new MatrixJPanel(life_matrix);
        panel.setLayout(new GridLayout(this.rows, this.cols));
        panel.setBounds(0, 0, this.rows, this.cols);
        panel.setBackground(Color.BLACK);

        frame.setSize(this.rows * 60, this.cols * 60);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        /* Check if to display the next generation */
        selectedValue = JOptionPane.showConfirmDialog(null, "Do you want to move to the next generation?");

        while (JOptionPane.OK_OPTION == selectedValue) {
            life_matrix.moveGeneration();
            panel.repaint();
            selectedValue = JOptionPane.showConfirmDialog(null, "Do you want to move to the next generation?");
        }

        /* Close the frame */
        frame.setVisible(false);
        frame.dispose();
    }
}
