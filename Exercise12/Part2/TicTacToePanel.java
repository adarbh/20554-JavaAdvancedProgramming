package Part2;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Created by Adar on 4/21/2017.
 */
public class TicTacToePanel extends JPanel {
    /**
     * This class represents a TicTacToePanel
     */

    private TicTacToeBoard board;
    private int frameDimensions;

    /**
     * Returns an TicTacToePanel object
     * @param  board the board to paint
     * @param  frameDimensions the frame dimensions
     * @return      the TicTacToePanel object
     */
    public TicTacToePanel(TicTacToeBoard board, int frameDimensions) {
        this.board = board;
        this.frameDimensions = frameDimensions;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellDisplayDimensions = frameDimensions / this.board.getDimensions();

        g.setColor(Color.white);

        /* Paint the grid */
        for(int i = 0 ; i < this.board.getDimensions() ; i++) {
            g.drawLine(0, i * cellDisplayDimensions, this.board.getDimensions() * cellDisplayDimensions,
                    i * cellDisplayDimensions);
            g.drawLine(i * cellDisplayDimensions, 0, i * cellDisplayDimensions,
                    this.board.getDimensions() * cellDisplayDimensions);
        }

        /* Paint X/O */
        for(int i = 0 ; i < this.board.getDimensions() ; i++) {
            for(int j = 0 ; j < this.board.getDimensions() ; j++) {

                switch (this.board.getCell(i, j)) {
                    case O:
                        g.drawOval((j * cellDisplayDimensions) + 20, (i * cellDisplayDimensions) + 20,
                                60, 60);
                        break;
                    case X:
                        g.drawLine((j * cellDisplayDimensions) + 10, (i * cellDisplayDimensions) + 10,
                                (j * cellDisplayDimensions) + 80, (i * cellDisplayDimensions) + 80);
                        g.drawLine((j * cellDisplayDimensions) + 80, (i * cellDisplayDimensions) + 10,
                                (j * cellDisplayDimensions) + 10, (i * cellDisplayDimensions) + 80);
                        break;
                }
            }
        }
    }
}
