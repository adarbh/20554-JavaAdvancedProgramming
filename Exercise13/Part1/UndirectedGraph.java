import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Created by Adar on 5/11/2017.
 */
public class UndirectedGraph {

    private ArrayList <GraphNode> nodes;
    private ArrayList <Pair> edges;

    public UndirectedGraph() {
        this.nodes = new ArrayList <GraphNode>();
        this.edges = new ArrayList <Pair>();
    }

    public UndirectedGraph(ArrayList <GraphNode> nodes, ArrayList <Pair> edges)
            throws DuplicatedNodeException, EdgeNodeDoesNotExistException {
        /* Verify and add nodes */
        this.nodes = new ArrayList <GraphNode>();
        for (int i = 0; i < nodes.size() ; i++) {
            if (this.nodes.contains(nodes.get(i))) {
                throw new DuplicatedNodeException();
            } else {
                this.nodes.add(nodes.get(i));
            }
        }

        /* Verify and add edges */
        for (int i = 0; i < edges.size() ; i++) {
            if (!this.nodes.contains(edges.get(i).getKey()) || !this.nodes.contains(edges.get(i).getValue())) {
                throw new EdgeNodeDoesNotExistException();
            }
        }

        this.edges = edges;
    }

    public void addNode(GraphNode node) throws NodeExistsException{
        if (this.nodes.contains(node)) {
            throw new NodeExistsException();
        } else {
            this.nodes.add(node);
        }
    }

    public void removeNode(GraphNode node) throws NodeDoesNotExistException{

        /* Remove the node */
        if (!this.nodes.contains(node)) {
            throw new NodeDoesNotExistException();
        } else {
            this.nodes.remove(node);
        }

        /* Remove related edges */
        for (int i = 0; i < this.nodes.size() ; i++) {
            if (doesEdgeExist(node, this.nodes.get(i))) {
                try {
                    removeEdge(node, this.nodes.get(i));
                } catch (EdgeDoesNotExistException e) {
                    return;
                }
            }
        }
    }

    public void addEdge(GraphNode node1, GraphNode node2)
            throws NodeDoesNotExistException, EdgeExistsException{
        if (!this.nodes.contains(node1) || !this.nodes.contains(node2)) {
            throw new NodeDoesNotExistException();
        } else if (this.doesEdgeExist(node1, node2) || this.doesEdgeExist(node2, node1)) {
            throw new EdgeExistsException();
        } else {
            this.edges.add(new Pair(node1, node2));
        }
    }

    public void removeEdge(GraphNode node1, GraphNode node2) throws EdgeDoesNotExistException{
        if (!this.doesEdgeExist(node1, node2) && !this.doesEdgeExist(node2, node1)) {
            throw new EdgeDoesNotExistException();
        } else {
            this.edges.remove(new Pair(node1, node2));
            this.edges.remove(new Pair(node2, node1));
        }
    }

    public boolean doesNodeExist(GraphNode node) {
        return this.nodes.contains(node);
    }

    public boolean doesEdgeExist(GraphNode node1, GraphNode node2) {
        return (this.edges.contains(new Pair(node1, node2)) || this.edges.contains(new Pair(node2, node1)));
    }

    public ArrayList <GraphNode> getNodes() {
        return this.nodes;
    }

    public ArrayList <Pair> getEdges() {
        return this.edges;
    }

    @Override
    public String toString() {
        return this.nodes.toString() + "\n" + this.edges.toString();
    }

    @Override
    public boolean equals(Object obj) {
        UndirectedGraph graph = (UndirectedGraph)(obj);
        return (this.nodes.equals(graph.getNodes()) && this.edges.equals(graph.getEdges()));
    }
}
