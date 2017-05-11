/**
 * Created by Adar on 5/11/2017.
 */
public class Main {

    public static void main(String [ ] args) {

        UndirectedGraph graph = new UndirectedGraph();
        try {
            graph.addNode(new GraphNode('a'));
            graph.addNode(new GraphNode('d'));
            graph.addNode(new GraphNode('r'));
        } catch (NodeExistsException e) {
            e.printStackTrace();
        }

        try {
            graph.addEdge(new GraphNode('a'), new GraphNode('d'));
            graph.addEdge(new GraphNode('d'), new GraphNode('r'));
        } catch (NodeDoesNotExistException e) {
            e.printStackTrace();
        } catch (EdgeExistsException e) {
            e.printStackTrace();
        }

        System.out.println(graph.toString());

        UndirectedGraph graph2 = new UndirectedGraph();
        try {
            graph2.addNode(new GraphNode('a'));
            graph2.addNode(new GraphNode('d'));
            graph2.addNode(new GraphNode('r'));
        } catch (NodeExistsException e) {
            e.printStackTrace();
        }

        try {
            graph2.addEdge(new GraphNode('a'), new GraphNode('d'));
            graph2.addEdge(new GraphNode('d'), new GraphNode('r'));
        } catch (NodeDoesNotExistException e) {
            e.printStackTrace();
        } catch (EdgeExistsException e) {
            e.printStackTrace();
        }
        System.out.println(graph.equals(graph2));
    }
}
