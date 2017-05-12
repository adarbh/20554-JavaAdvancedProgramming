package Part1;

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
    /**
     * This class represents an undirected graph panel
     */

    private UndirectedGraph graph;
    private int panelDim;
    private int rows;
    private int cols;

    /**
     * Returns an UndirectedGraphPanel object with an initialized graph
     * @param  graph an undirected graph to display
     * @param  panelDim the panel dimensions
     * @return      the UndirectedGraphPanel object
     */
    public UndirectedGraphPanel(UndirectedGraph graph, int panelDim) {
        this.graph = graph;
        this.panelDim = panelDim;

        ArrayList <GraphNode> nodes = this.graph.getNodes();
        setRows(nodes.size());
        setCols(nodes.size());
    }

    /**
     * Sets the number of rows on the panel
     * @param  nodesNum the number of nodes in the graph
     */
    private void setRows(int nodesNum) {
        if (nodesNum > 0) {
            this.rows = (int) Math.ceil(Math.sqrt(nodesNum));
        } else {
            this.rows = 0;
        }
    }

    /**
     * Sets the number of cols on the panel
     * @param  nodesNum the number of nodes in the graph
     */
    private void setCols(int nodesNum) {
        if (nodesNum > 0) {
            this.cols = (int) Math.ceil(nodesNum / Math.sqrt(nodesNum));
        } else {
            this.cols = 0;
        }
    }

    /**
     * Get the center indexes of a node on the panel
     * @param  nodes the nodes of the graph
     * @param  node the node
     * @return      the center indexes of the node in a Pair format
     */
    private Pair getNodeCenterIndexes(ArrayList <GraphNode> nodes, GraphNode node) {
        int row = nodes.indexOf(node) / cols;
        int col = nodes.indexOf(node) % cols;
        int centerX = (col * (this.panelDim / cols) + ((this.panelDim / cols) / 2));
        int centerY = (row * (this.panelDim / rows) + ((this.panelDim / rows) / 2));
        return new Pair(centerX, centerY);
    }

    /**
     * Sets the graph to display on the panel
     * @param  graph an undirected graph to display
     */
    public void setUndirectedGraph(UndirectedGraph graph) {
        this.graph = graph;

        ArrayList <GraphNode> nodes = this.graph.getNodes();
        setRows(nodes.size());
        setCols(nodes.size());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int centerX = 0;
        int centerY = 0;
        String text = "";
        ArrayList <GraphNode> nodes = null;
        ArrayList <Pair> edges = null;
        double textWidth = 0;
        int ovalDim = 0;

        setSize(this.panelDim, this.panelDim);

        /* Print Nodes */
        nodes = this.graph.getNodes();
        setRows(nodes.size());
        setCols(nodes.size());

        System.out.println(nodes.size());
        for (int i = 0 ; i < nodes.size() ; i++) {

            /* Get oval location */
            centerX = (int) getNodeCenterIndexes(nodes, nodes.get(i)).getKey();
            centerY = (int) getNodeCenterIndexes(nodes, nodes.get(i)).getValue();

            text = nodes.get(i).toString();
            ovalDim = Math.min(this.panelDim / cols, this.panelDim / rows) / 2;

            /* Paint oval and text */
            g.setColor(Color.GREEN);
            g.fillOval(centerX - ovalDim / 2, centerY - ovalDim / 2, ovalDim, ovalDim);
            FontMetrics fm = g.getFontMetrics();
            textWidth = fm.getStringBounds(text, g).getWidth();
            g.setColor(Color.black);
            g.drawString(text, (int) (centerX - textWidth / 2), (int) (centerY + fm.getMaxAscent() / 2));
        }

        /* Paint edges */
        edges = this.graph.getEdges();
        for (Pair edge : edges) {
            int node1CenterX = (int) getNodeCenterIndexes(nodes, (GraphNode) edge.getKey()).getKey();
            int node1CenterY = (int) getNodeCenterIndexes(nodes, (GraphNode) edge.getKey()).getValue();
            int node2CenterX = (int) getNodeCenterIndexes(nodes, (GraphNode) edge.getValue()).getKey();
            int node2CenterY = (int) getNodeCenterIndexes(nodes, (GraphNode) edge.getValue()).getValue();

            g.drawLine(node1CenterX, node1CenterY, node2CenterX, node2CenterY);
        }
    }
}
