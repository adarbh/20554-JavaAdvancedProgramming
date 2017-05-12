import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Created by Adar on 5/12/2017.
 */
public class UndirectedGraphFrame extends JFrame implements ActionListener, MouseListener {
    private int frameWidth;
    private int frameHeight;

    private UndirectedGraphPanel graphPanel;
    private UndirectedGraph graph;
    private JButton clearButton;
    private JButton removeNodeButton;
    private JButton addEdgeButton;
    private JButton removeEdgeButton;
    private JPanel buttonPanel;

    public UndirectedGraphFrame() {

        this.frameWidth = 600;
        this.frameHeight = 800;
        this.setTitle("Undirected Graph");

        /* Create the graph panel */
        this.graph = new UndirectedGraph();
        try {
            this.graph.addNode(new GraphNode('a'));
            this.graph.addNode(new GraphNode('b'));
            this.graph.addNode(new GraphNode('c'));
            this.graph.addNode(new GraphNode('d'));
            this.graph.addNode(new GraphNode('e'));
            this.graph.addNode(new GraphNode('f'));
            this.graph.addNode(new GraphNode('g'));
            this.graph.addNode(new GraphNode('h'));
            this.graph.addNode(new GraphNode('i'));
        } catch (NodeExistsException e) {
            e.printStackTrace();
        }

        try {
            graph.addEdge(new GraphNode('a'), new GraphNode('b'));
            graph.addEdge(new GraphNode('c'), new GraphNode('h'));
            graph.addEdge(new GraphNode('i'), new GraphNode('a'));
        } catch (NodeDoesNotExistException e) {
            e.printStackTrace();
        } catch (EdgeExistsException e) {
            e.printStackTrace();
        }

        this.graphPanel = new UndirectedGraphPanel(this.graph, this.frameWidth);
        add(this.graphPanel, BorderLayout.CENTER);

        /* Create buttons */
        this.buttonPanel = new JPanel();
        this.clearButton = new JButton("Clear graph");
        this.removeNodeButton = new JButton("Remove node");
        this.addEdgeButton = new JButton("Add edge");
        this.removeEdgeButton = new JButton("Remove edge");
        this.buttonPanel.add(this.clearButton);
        this.buttonPanel.add(this.removeNodeButton);
        this.buttonPanel.add(this.addEdgeButton);
        this.buttonPanel.add(this.removeEdgeButton);
        add(this.buttonPanel, BorderLayout.SOUTH);

        /* Add listeners */
        this.clearButton.addActionListener(this);
        this.removeNodeButton.addActionListener(this);
        this.addEdgeButton.addActionListener(this);
        this.removeEdgeButton.addActionListener(this);
        addMouseListener(this);

        /* Configure the frame */
        setSize(this.frameWidth,this.frameHeight);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void removeNodeFromGraph() {

        ArrayList <GraphNode> nodes = this.graph.getNodes();
        Object[] possibleValuesArray = new Object[nodes.size()];

        /* Check if there is a node to remove */
        if (0 >= nodes.size()) {
            JOptionPane.showMessageDialog(null,"There are no nodes to remove",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0 ; i < nodes.size() ; i++) {
            possibleValuesArray[i] = nodes.get(i).getData();
        }

        /* Get the node to remove */
        Object selectedValue = JOptionPane.showInputDialog(this,
                "Choose the node to remove:",
                this.getTitle(), JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);
        if (null == selectedValue) {
            return;
        }

        /* Remove node */
        try {
            this.graph.removeNode(new GraphNode((Character) selectedValue));
        } catch (NodeDoesNotExistException e) {
            JOptionPane.showMessageDialog(null,"Node does not exist",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
        }

        repaint();
    }

    public void addNodeToGraph() {

        ArrayList <GraphNode> nodes = this.graph.getNodes();

        /* Create the possible values */
        ArrayList alphabet = new ArrayList();
        int k = 0;
        for(int i = 0; i < 26; i++){
            alphabet.add((Character)(char)(97 + (k++)));
        }

        ArrayList possibleValues = new ArrayList();
        possibleValues.addAll(alphabet);
        Object[] possibleValuesArray = possibleValues.toArray();

        /* Get the node to add */
        Object selectedValue = JOptionPane.showInputDialog(this,
                "Choose the node to remove:",
                this.getTitle(), JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);
        if (null == selectedValue) {
            return;
        }

        /* Add node */
        try {
            this.graph.addNode(new GraphNode((Character) selectedValue));
        } catch (NodeExistsException e) {
            JOptionPane.showMessageDialog(null,"Node already exists",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
        }

        //this.graphPanel.repaint();
        repaint();
    }

    private void clearGraph() {
        System.out.println("clear pressed");
        this.graph = new UndirectedGraph();
        this.graphPanel.setUndirectedGraph(this.graph);
        repaint();
    }

    private void addEdgeToGraph() {
        ArrayList <GraphNode> nodes = this.graph.getNodes();
        Object[] possibleValuesArray = new Object[nodes.size()];

        /* Check if there is a node to add edges to */
        if (0 >= nodes.size()) {
            JOptionPane.showMessageDialog(null,"There are no nodes to add edges to",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0 ; i < nodes.size() ; i++) {
            possibleValuesArray[i] = nodes.get(i).getData();
        }

        /* Get first node */
        Object firstSelectedValue = JOptionPane.showInputDialog(this,
                "Choose the first node:",
                this.getTitle(), JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);
        if (null == firstSelectedValue) {
            return;
        }

        /* Get second node */
        Object secondSelectedValue = JOptionPane.showInputDialog(this,
                "Choose the second node:",
                this.getTitle(), JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);
        if (null == secondSelectedValue) {
            return;
        }

        try {
            this.graph.addEdge(new GraphNode((Character) firstSelectedValue), new GraphNode((Character) secondSelectedValue));
        } catch (NodeDoesNotExistException e) {
            JOptionPane.showMessageDialog(null,"Node dose not exist",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
        } catch (EdgeExistsException e) {
            JOptionPane.showMessageDialog(null,"Edge already exists",
                    getTitle(), JOptionPane.ERROR_MESSAGE);;
        }

        repaint();
    }

    private void removeEdgeFromGraph() {
        ArrayList <GraphNode> nodes = this.graph.getNodes();
        Object[] possibleValuesArray = new Object[nodes.size()];

        /* Check if there is a node to add edges to */
        if (0 >= nodes.size()) {
            JOptionPane.showMessageDialog(null,"There are no nodes to remove edges to",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0 ; i < nodes.size() ; i++) {
            possibleValuesArray[i] = nodes.get(i).getData();
        }

        /* Get first node */
        Object firstSelectedValue = JOptionPane.showInputDialog(this,
                "Choose the first node:",
                this.getTitle(), JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);
        if (null == firstSelectedValue) {
            return;
        }

        /* Get second node */
        Object secondSelectedValue = JOptionPane.showInputDialog(this,
                "Choose the second node:",
                this.getTitle(), JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);
        if (null == secondSelectedValue) {
            return;
        }

        try {
            this.graph.removeEdge(new GraphNode((Character) firstSelectedValue), new GraphNode((Character) secondSelectedValue));
        } catch (EdgeDoesNotExistException e) {
            JOptionPane.showMessageDialog(null,"Edge does not exists",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /* Check if one of the buttons was pressed */
        if (e.getSource() == this.clearButton) {
            clearGraph();
        } else if (e.getSource() == this.removeNodeButton) {
            removeNodeFromGraph();
        } else if (e.getSource() == this.addEdgeButton) {
            addEdgeToGraph();
        } else if (e.getSource() == this.removeEdgeButton) {
            removeEdgeFromGraph();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        addNodeToGraph();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
