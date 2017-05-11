/**
 * Created by Adar on 5/11/2017.
 */
public class GraphNode {
    private Character data;

    public GraphNode(Character data) {
        this.data = data;
    }

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
