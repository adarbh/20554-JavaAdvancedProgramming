package Part2;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Created by Adar on 4/10/2017.
 */
public class MatrixJPanel extends JPanel {
    /**
     * This class represents a matrix panel, a panel that is able to display a matrix graphically.
     */

    private LifeMatrix life_matrix;

    /**
     * Returns an MatrixJPanel object based on a given LifeMatrix
     * @param  life_matrix a matrix to draw
     * @return      the MatrixJPanel object
     */
    public MatrixJPanel(LifeMatrix life_matrix) {
        this.life_matrix = life_matrix;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        for (int i = 0 ; i < this.life_matrix.getRows() ; i++) {
            for(int j = 0 ; j < this.life_matrix.getCols() ; j++) {

                /* Draw the rectangular */
                g.setColor(Color.black);
                g.drawRect((w / this.life_matrix.getCols() * j), (h / this.life_matrix.getRows() * i),
                        h / this.life_matrix.getRows(),  w / this.life_matrix.getCols());

                /* Fill the rectangular if needed */
                if(!this.life_matrix.getMatrixInIndex(i, j)) {
                    g.setColor(Color.white);
                    g.fillRect((w / this.life_matrix.getCols() * j), (h / this.life_matrix.getRows() * i),
                            h / this.life_matrix.getRows(),  w / this.life_matrix.getCols());
                }
            }
        }
    }
}
