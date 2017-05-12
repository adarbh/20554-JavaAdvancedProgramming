package Part1;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Created by Adar on 5/11/2017.
 */
public class UndirectedGraph {
    /**
     * This class represents an undirected graph
     */

    private ArrayList <GraphNode> nodes;
    private ArrayList <Pair> edges;

    /**
     * Returns an UndirectedGraph object with no nodes or edges
     * @return      the UndirectedGraph object
     */
    public UndirectedGraph() {
        this.nodes = new ArrayList <GraphNode>();
        this.edges = new ArrayList <Pair>();
    }

    /**
     * Returns an UndirectedGraph object with initialized nodes and edges
     * @param  nodes the nodes to enter to the graph
     * @param  edges the edges to enter to the graph
     * @return      the UndirectedGraph object
     */
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
            if (!this.nodes.contains(edges.get(i).getKey()) ||
                    !this.nodes.contains(edges.get(i).getValue())) {
                throw new EdgeNodeDoesNotExistException();
            }
        }

        this.edges = edges;
    }

    /**
     * Adds a node to the graph
     * @param  node the node to add
     */
    public void addNode(GraphNode node) throws NodeExistsException{
        if (this.nodes.contains(node)) {
            throw new NodeExistsException();
        } else {
            this.nodes.add(node);
        }
    }

    /**
     * Remove a node from the graph
     * @param  node the node to remove
     */
    public void removeNode(GraphNode node) throws NodeDoesNotExistException{

        /* Remove the node */
        if (!this.nodes.contains(node)) {
            throw new NodeDoesNotExistException();
        } else {
            this.nodes.remove(node);
        }

        /* Remove related edges */
        for (GraphNode node1 : this.nodes) {
            if (doesEdgeExist(node, node1)) {
                try {
                    removeEdge(node, node1);
                } catch (EdgeDoesNotExistException e) {
                    return;
                }
            }
        }
    }

    /**
     * Adds edge to the graph
     * @param  node1 one of the nodes in the edge
     * @param  node2 on of the nodes in the edge
     */
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

    /**
     * Remove edge from the graph
     * @param  node1 one of the nodes in the edge
     * @param  node2 one of the nodes in the edge
     */
    public void removeEdge(GraphNode node1, GraphNode node2) throws EdgeDoesNotExistException{
        if (!this.doesEdgeExist(node1, node2) && !this.doesEdgeExist(node2, node1)) {
            throw new EdgeDoesNotExistException();
        } else {
            this.edges.remove(new Pair(node1, node2));
            this.edges.remove(new Pair(node2, node1));
        }
    }

    /**
     * Checks if a node is in the graph
     * @param  node the node to check
     * @return      true if the node is in the graph, false otherwise
     */
    public boolean doesNodeExist(GraphNode node) {
        return this.nodes.contains(node);
    }

    /**
     * Checks if an edge is sin the graph
     * @param  node1 one of the nodes in the edge
     * @param  node2 one of the nodes in the edge
     * @return      true if the edge is in the graph, false otherwise
     */
    public boolean doesEdgeExist(GraphNode node1, GraphNode node2) {
        return (this.edges.contains(new Pair(node1, node2)) || this.edges.contains(new Pair(node2, node1)));
    }

    /**
     * Returns the nodes in the graph
     * @return      the nodes in the graph in an ArrayList format
     */
    public ArrayList <GraphNode> getNodes() {
        return this.nodes;
    }

    /**
     * Returns the edges of the graph
     * @return      the edges of the graph in an ArrayList format
     */
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
