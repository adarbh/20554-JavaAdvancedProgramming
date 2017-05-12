import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.*;
import javafx.util.Pair;

/**
 * Created by Adar on 5/12/2017.
 */
public class UndirectedGraphPanel extends JPanel {

    private UndirectedGraph graph;
    private int panelDim;
    private int rows;
    private int cols;

    public UndirectedGraphPanel(UndirectedGraph graph, int panelDim) {
        this.graph = graph;
        this.panelDim = panelDim;

        ArrayList <GraphNode> nodes = this.graph.getNodes();
        setRows(nodes.size());
        setCols(nodes.size());
    }

    private void setRows(int nodesNum) {
        if (nodesNum > 0) {
            this.rows = (int) Math.ceil(Math.sqrt(nodesNum));
        } else {
            this.rows = 0;
        }
    }

    private void setCols(int nodesNum) {
        if (nodesNum > 0) {
            this.cols = (int) Math.ceil(nodesNum / Math.sqrt(nodesNum));
        } else {
            this.cols = 0;
        }
    }

    private Pair getNodeCenterIndexes(ArrayList <GraphNode> nodes, GraphNode node) {
        System.out.println("this.panelDim: " + this.panelDim + " Rows : " + rows + " Cols: " + cols);
        int row = nodes.indexOf(node) / cols;
        int col = nodes.indexOf(node) % cols;
        int centerX = (col * (this.panelDim / cols) + ((this.panelDim / cols) / 2));
        int centerY = (row * (this.panelDim / rows) + ((this.panelDim / rows) / 2));
        return new Pair(centerX, centerY);
    }

    public void setUndirectedGraph(UndirectedGraph graph) {
        this.graph = graph;

        ArrayList <GraphNode> nodes = this.graph.getNodes();
        setRows(nodes.size());
        setCols(nodes.size());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setSize(this.panelDim, this.panelDim);

        /* Print Nodes */
        ArrayList <GraphNode> nodes = this.graph.getNodes();
        setRows(nodes.size());
        setCols(nodes.size());

        System.out.println(nodes.size());
        for (int i = 0 ; i < nodes.size() ; i++) {

            /* Get oval location */
            int centerX = (int) getNodeCenterIndexes(nodes, nodes.get(i)).getKey();
            int centerY = (int) getNodeCenterIndexes(nodes, nodes.get(i)).getValue();

            String text = nodes.get(i).toString();
            int ovalDim = Math.min(this.panelDim / cols, this.panelDim / rows) / 2;

            /* Paint oval and text */
            g.setColor(Color.GREEN);
            g.fillOval(centerX - ovalDim / 2, centerY - ovalDim / 2, ovalDim, ovalDim);
            FontMetrics fm = g.getFontMetrics();
            double textWidth = fm.getStringBounds(text, g).getWidth();
            g.setColor(Color.black);
            g.drawString(text, (int) (centerX - textWidth / 2), (int) (centerY + fm.getMaxAscent() / 2));
        }

        /* Paint edges */
        ArrayList <Pair> edges = this.graph.getEdges();
        for (int i = 0 ; i < edges.size() ; i++) {
            int node1CenterX = (int) getNodeCenterIndexes(nodes, (GraphNode) edges.get(i).getKey()).getKey();
            int node1CenterY = (int) getNodeCenterIndexes(nodes, (GraphNode) edges.get(i).getKey()).getValue();
            int node2CenterX = (int) getNodeCenterIndexes(nodes, (GraphNode) edges.get(i).getValue()).getKey();
            int node2CenterY = (int) getNodeCenterIndexes(nodes, (GraphNode) edges.get(i).getValue()).getValue();

            g.drawLine(node1CenterX, node1CenterY, node2CenterX, node2CenterY);
        }
    }
}
