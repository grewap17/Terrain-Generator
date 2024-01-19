package Path;

import adt.Graph;
import adt.Node;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {
    @Test
    void testFindThePath() {
        // create a graph
        Graph graph = new Graph();
        Node node1 = new Node(0, 0);
        Node node2 = new Node(2, 2);
        Node node3 = new Node(4, 4);
        Node node4 = new Node(6, 6);
        graph.addEdge(node1, node2, 2.0);
        graph.addEdge(node2, node3, 2.0);
        graph.addEdge(node3, node4, 2.0);

        // find the path from node1 to node4
        BFS p= new BFS();
        List<Node> path = p.FindThePath(graph, node1, node4);

        // the path should be [node1, node2, node3, node4]
        List<Node> expectedPath = Arrays.asList(node1, node2, node3, node4);
        assertEquals(expectedPath, path);
    }}
