package Part1;

/**
 * Created by Adar on 5/11/2017.
 */
public class GraphNode {
    /**
     * This class represents a node in a graph
     */

    private Character data;

    /**
     * Returns an GraphNode object with an initialized data
     * @return      the GraphNode object
     */
    public GraphNode(Character data) {
        this.data = data;
    }

    /**
     * Returns the data of the node
     * @return   the data of the node
     */
    public Character getData() {
        return this.data;
    }

    @Override
    public boolean equals(Object obj) {
        GraphNode node = (GraphNode)(obj);
        return this.data == node.data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
